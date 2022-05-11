package com.good.platform.service.impl;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.good.platform.entity.EmailEntity;
import com.good.platform.entity.User;
import com.good.platform.exception.EmailException;
import com.good.platform.exception.ErrorCode;
import com.good.platform.exception.SOException;
import com.good.platform.mapper.EmailMapper;
import com.good.platform.model.EmailModel;
import com.good.platform.repository.EmailServiceRepository;
import com.good.platform.repository.UserRepository;
import com.good.platform.request.dto.BeneficiaryOnboardingEmailRequestDto;
import com.good.platform.request.dto.ConfirmEmailAccountRequestDto;
import com.good.platform.request.dto.EmailContentRequestDto;
import com.good.platform.request.dto.EmailRequestDto;
import com.good.platform.response.dto.EmailResponseDto;
import com.good.platform.response.dto.OtpRegisterResponseDTO;
import com.good.platform.response.dto.OtpResponseDTO;
import com.good.platform.service.EmailService;
import com.good.platform.utility.Constants;
import com.good.platform.utility.EmailUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

	private final JavaMailSender javaMailSender;
	
	@Autowired
    private SpringTemplateEngine templateEngine;

	@Autowired
	EmailServiceRepository emailServiceDao;
	
	@Autowired
	EmailUtils emailUtils;
	
	@Autowired
	EmailMapper emailMapper;
	@Autowired
	UserRepository  userRepo;
	
	@Override
	public void sendEmail(EmailRequestDto requestDto) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("no-reply@consumerlinks.in");
		msg.setTo(requestDto.getEmail());
		msg.setSubject(Constants.VERIFICATION_MAIL_SUBJECT);
		
		msg.setText("Dear "+requestDto.getFirstName()+","+ '\n' +'\n' + Constants.VERIFICATION_MAIL_CONTENT+requestDto.getValidatorTokenId()+" to reset your password."+'\n'+'\n'+"\r\nFor further information contact support@consumerlinks.in\n"
				+ "		*This is a system generated email by  The Platform. Please do not reply to this email.");
		
		try {
			javaMailSender.send(msg);
		} catch (Exception ex) {
			log.error(ExceptionUtils.getStackTrace(ex));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.EMAIL_SEND_FAILURE);
		}
	}
	
	//@Override
	public void inviteEmailSender(EmailContentRequestDto emailContentRequestDto) {
		log.info("Send invite user email starts");
		EmailModel mail = new EmailModel();
	    mail.setFromEmailId(emailContentRequestDto.getFromMailId());
		mail.setToEmailId(emailContentRequestDto.getEmailId());
		mail.setSubject(emailContentRequestDto.getSubject());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("name", emailContentRequestDto.getUserName());
		model.put("sign", emailContentRequestDto.getSign() );
		model.put("username", emailContentRequestDto.getEmailId());
		model.put("passcode", emailContentRequestDto.getPassword());
		model.put("downloadLink", emailContentRequestDto.getDownloadLink());
		mail.setProps(model);
		sendEmailWithTemplate(mail);
		log.info("Send invite user email ends");
	}
	
	private void sendEmailWithTemplate(EmailModel mail) {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message,
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			Context context = new Context();
			context.setVariables(mail.getProps());

			String html = templateEngine.process("email-template", context);
			helper.setTo(mail.getToEmailId());
			helper.setText(html, true);
			helper.setSubject(mail.getSubject());
			helper.setFrom(mail.getFromEmailId());
			javaMailSender.send(message);
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.EMAIL_SEND_FAILURE);
		}
	}
	
	@Override
	public OtpRegisterResponseDTO generateEmailOtpToRegister(String email,Long expTime,String otp) {
		if(!userRepo.existsByEmailIdIgnoreCase(email)) {
			throw new SOException(ErrorCode.NOT_FOUND, Constants.USER_EMAIL_NOT_FOUND);
		}
	    User user =	userRepo.findByEmailIdIgnoreCase(email);
		EmailRequestDto validatorRequestDto = generateEmailOtp(email,expTime,otp);
		 validatorRequestDto.setFirstName(user.getFirstName());
		sendEmail(validatorRequestDto);
		OtpRegisterResponseDTO otpRegisterResponse = new OtpRegisterResponseDTO("success", email,user.getId(),null);
		return otpRegisterResponse;
	}
	
	private EmailRequestDto generateEmailOtp(String email,Long expTime,String otp) {
		EmailEntity token = emailServiceDao.findByEmailIgnoreCase(email);
		if (token != null) {
			emailServiceDao.delete(token);
		}
		EmailResponseDto responseDto = createValidatorEntity(email,expTime,otp);
		EmailRequestDto validatorRequestDto = emailMapper.emailBodyValuesMapper(responseDto);
		saveTokenData(validatorRequestDto);
		return validatorRequestDto;
	}
	
	public EmailResponseDto createValidatorEntity(String email,Long expTime,String otp) {
		EmailEntity emailEntity = EmailEntity.builder()
				//.email(email).validatorTokenId(OtpUtility.generateOTP()).isExpired(false)
				.email(email).validatorTokenId(otp).isExpired(false)
				.timeSpan(String.valueOf(EmailUtils.EXPIRATION_MINUTES))
				.expirationStartTime(new Date().getTime())
				//.expirationTime(emailUtils.getExpirationTime(EmailUtils.EXPIRATION_MINUTES)).build();
				.expirationTime(expTime).build();
		return emailMapper.emailBodyEntityToResponseMapper(emailEntity);
	}
	
	@Override
	public boolean activateUserAccountByEmail( ConfirmEmailAccountRequestDto confirmAccountRequestDto) {
		OtpResponseDTO emailOtpResponse = verifyEmailOtp(confirmAccountRequestDto.getEmailOtp(),
				confirmAccountRequestDto.getEmail());
		if (!(emailOtpResponse.getStatus().equals("Success") && emailOtpResponse.getDetails().equals("OTP Matched"))) {
			return false;
		}
		deleteTokenById(confirmAccountRequestDto);
		return true;
	}

	@Override
	public EmailResponseDto saveTokenData(EmailRequestDto requestDto) {
		EmailResponseDto responseDto = null;
		EmailEntity entityData = emailServiceDao.findByEmailIgnoreCase(requestDto.getEmail());
		if(!Objects.isNull(entityData)){
			emailServiceDao.delete(entityData);
		}
		EmailEntity entityToBeSaved = emailMapper.emailBodyValuesMapper(requestDto);
		EmailEntity savedEntity = emailServiceDao.save(entityToBeSaved);
		if(Objects.isNull(savedEntity)) {
			throw new EmailException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.EMAIL_VERIFICATION_TOKEN_SAVE_FAIL);
		}
		responseDto = emailMapper.emailBodyEntityToResponseMapper(savedEntity);
		return responseDto;
	}
	
	@Override
	public EmailResponseDto deleteTokenById(ConfirmEmailAccountRequestDto confirmEmailAccountRequestDto) {
		EmailResponseDto responseDto = null;
		EmailEntity token = emailServiceDao.findByValidatorTokenIdAndEmailIgnoreCase(confirmEmailAccountRequestDto.getEmailOtp(),
				confirmEmailAccountRequestDto.getEmail());
		if(Objects.isNull(token)) {
			throw new EmailException(ErrorCode.NOT_FOUND, Constants.EMAIL_VERIFICATION_DATA_NOT_FOUND);
		}
		responseDto = emailMapper.emailBodyEntityToResponseMapper(token);
		emailServiceDao.delete(token);
		return responseDto ;
	}

	@Override
	public OtpResponseDTO verifyEmailOtp(String tokenId,String email) {
		
		EmailEntity emailValidatorEntity = emailServiceDao.findByValidatorTokenIdAndEmailIgnoreCase(tokenId,email);
		if(Objects.isNull(emailValidatorEntity)) { 
			throw new EmailException(ErrorCode.NOT_FOUND, Constants.EMAIL_VERIFICATION_DATA_NOT_FOUND);
		}
		if (emailUtils.isTokenExpired(emailValidatorEntity.getExpirationTime())) {
			throw new EmailException(ErrorCode.INVALID_OPERATION, Constants.EMAIL_VERIFICATION_OTP_EXPIRED);
		}
		OtpResponseDTO emailOtpResponse = new OtpResponseDTO("OTP Matched","Success");
		return emailOtpResponse;
	}


	@Override
	public void sendUserInvitation(EmailContentRequestDto emailContentRequestDto) {
		EmailModel mail = new EmailModel();
	    mail.setFromEmailId(emailContentRequestDto.getFromMailId());
		mail.setToEmailId(emailContentRequestDto.getEmailId());
		mail.setSubject(emailContentRequestDto.getSubject());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("name", emailContentRequestDto.getUserName());
		model.put("username", emailContentRequestDto.getEmailId());
		model.put("passcode", emailContentRequestDto.getPassword());
		mail.setProps(model);
		sendInvitationUserWithTemplate(mail);
	}
	
	@Override
	public void sendUserNotificationPeerReview(EmailContentRequestDto emailContentRequestDto) {
		EmailModel mail = new EmailModel();
	    mail.setFromEmailId(emailContentRequestDto.getFromMailId());
		mail.setToEmailId(emailContentRequestDto.getEmailId());
		mail.setSubject(emailContentRequestDto.getSubject());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("name", emailContentRequestDto.getUserName());
		model.put("projectName", emailContentRequestDto.getProjectName());
		model.put("organisationName", emailContentRequestDto.getOrgName());
		model.put("sign", emailContentRequestDto.getSign());
		mail.setProps(model);
		sendUserNotificationWithTemplate(mail);
	}
	
	private void sendInvitationUserWithTemplate(EmailModel mail) {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message,
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			Context context = new Context();
			context.setVariables(mail.getProps());

			String html = templateEngine.process("invite-user-email-template2", context);
			helper.setTo(mail.getToEmailId());
			helper.setText(html, true);
			helper.setSubject(mail.getSubject());
			helper.setFrom(mail.getFromEmailId());
			javaMailSender.send(message);
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.EMAIL_SEND_FAILURE);
		}
	}
	
	private void sendUserNotificationWithTemplate(EmailModel mail) {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message,
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			Context context = new Context();
			context.setVariables(mail.getProps());

			String html = templateEngine.process("notify-user-review-email-template", context);
			helper.setTo(mail.getToEmailId());
			helper.setText(html, true);
			helper.setSubject(mail.getSubject());
			helper.setFrom(mail.getFromEmailId());
			javaMailSender.send(message);
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.EMAIL_SEND_FAILURE);
		}
	}

	public void inviteFieldAgent(EmailContentRequestDto emailContentRequestDto) {
		log.info("Send field agent invitation starts");
		EmailModel mail = new EmailModel();
	    mail.setFromEmailId(emailContentRequestDto.getFromMailId());
		mail.setToEmailId(emailContentRequestDto.getEmailId());
		mail.setSubject(emailContentRequestDto.getSubject());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("name", emailContentRequestDto.getUserName());
		model.put("sign", emailContentRequestDto.getSign() );
		model.put("username", emailContentRequestDto.getEmailId());
		model.put("passcode", emailContentRequestDto.getPassword());
		model.put("downloadLink", emailContentRequestDto.getDownloadLink());
		model.put("programName", emailContentRequestDto.getProjectName());
		model.put("orgName", emailContentRequestDto.getOrgName());
		model.put("role", emailContentRequestDto.getRole());
		mail.setProps(model);
		sendFieldAgentEmailWithTemplate(mail);
		log.info("Send field agent invitation  ends");
	}
	
	private void sendFieldAgentEmailWithTemplate(EmailModel mail) {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message,
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			Context context = new Context();
			context.setVariables(mail.getProps());
			String html = templateEngine.process("volunter-invitation-email-template", context);
			helper.setTo(mail.getToEmailId());
			helper.setText(html, true);
			helper.setSubject(mail.getSubject());
			helper.setFrom(mail.getFromEmailId());
			javaMailSender.send(message);
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.EMAIL_SEND_FAILURE);
		}
	}
	
	/**
	 * NOT USINING CURRENTLY
	 * Email sent to beneficiary with login credetials
	 * @author Arya C Achari
	 * @since 25-Feb-2022
	 * 
	 * @param requestDto
	 */
	@Override
	public void beneficiaryOnboardingEmail(BeneficiaryOnboardingEmailRequestDto requestDto) {
		log.info("Send email to beneficiary begins");
		EmailModel emailModel = new EmailModel();
		emailModel.setFromEmailId(requestDto.getFromMailId());
		emailModel.setToEmailId(requestDto.getEmailId());
		emailModel.setSubject(requestDto.getSubject());
		Map<String, Object> model = new HashMap<String, Object>();

		model.put("beneficiaryName", requestDto.getBeneficiaryName());
		model.put("userName", requestDto.getUserName());
		model.put("password", requestDto.getPassword());

		model.put("emailId", requestDto.getEmailId());
		model.put("subject", requestDto.getSubject());
		model.put("FromMailId", requestDto.getFromMailId());
		emailModel.setProps(model);
		sendforBeneficiaryOnboarding(emailModel);
		log.info("Send email to beneficiary ends");
	}
	
	//NOT USINING CURRENTLY
	private void sendforBeneficiaryOnboarding(EmailModel emailModel) {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			Context context = new Context();
			context.setVariables(emailModel.getProps());

			String html = templateEngine.process("beneficiary_onboarding", context);
			helper.setTo(emailModel.getToEmailId());
			helper.setText(html, true);
			helper.setSubject(emailModel.getSubject());
			helper.setFrom(emailModel.getFromEmailId());
			javaMailSender.send(message);
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));

			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.EMAIL_SEND_FAILURE);
		}
	}

}
