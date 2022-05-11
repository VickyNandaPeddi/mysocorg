package com.good.platform.service.impl;

import java.net.URLEncoder;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.good.platform.exception.ErrorCode;
import com.good.platform.exception.SOException;
import com.good.platform.repository.UserRepository;
import com.good.platform.response.dto.PhoneOTPResponseDTO;
import com.good.platform.service.SmsGatewayService;
import com.good.platform.utility.Constants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SmsGatewayServiceImpl implements SmsGatewayService {

	private RestTemplate restTemplate = new RestTemplate();

	@Value("${sms.gateway.url}")
	private String url;

	@Value("${sms.gateway.username}")
	private String userName;

	@Value("${sms.gateway.password}")
	private String password;

	@Value("${sms.gateway.sender}")
	private String sender;

	@Value("${sms.gateway.type}")
	private String type;

	@Value("${sms.gateway.invite.template.id}")
	private String template;

	@Value("${sms.gateway.download.link.arpan}")
	private String arpanDownloadLink;

	@Value("${sms.gateway.download.link.ummeed}")
	private String ummeedDownloadLink;

	@Value("${sms.gateway.invite.template.id.arpan}")
	private String arpanInviteId;

	@Value("${sms.gateway.invite.template.id.ummeed}")
	private String ummeedInviteId;
	
	@Value("${sms.gateway.invite.template.id.vlclpl.otp}")
	private String vlclplRegUserOtpId;

	@Value("${sms.gateway.sender.id.arpan}")
	private String arpanSenderId;

	@Value("${sms.gateway.sender.id.ummeed}")
	private String ummeedSenderId;
	
	@Value("${sms.gateway.sender.id.vlclpl}")
	private String vlclplSenderId;

	@Value("${sms.gateway.sender.id.so-user-registration}")
	private String userReistrationSenderId;
	
	@Value("${sms.gateway.template.id.so-user-registration}")
	private String userReistrationTemplateId;
	
	@Value("${sms.gateway.template.id.vlclpl-registration}")
	private String vlclplRegistrationTemplateId;

	@Autowired
	UserRepository userRepo;

	@Override
	public boolean sendArpanInviteSms(String phoneNumber, String beneficiaryName, String emailId,
			String beneficiaryPassword, String downloadLink) {
		try {
			String encoding = "UTF-8";
			StringBuilder urlData = new StringBuilder();
			urlData.append("user=" + URLEncoder.encode(userName, encoding));
			urlData.append("&password=" + URLEncoder.encode(password, encoding));
			urlData.append("&mobile=" + URLEncoder.encode(phoneNumber, encoding));
			urlData.append("&message= Dear " + beneficiaryName +" "+ "Welcome to ARPAN Program!");
			urlData.append("\r\n");
			urlData.append("Download the APK file: " + arpanDownloadLink);
			urlData.append("\r\n");
			urlData.append("Username: " + emailId.toString());
			urlData.append("\r\n");
			urlData.append("Password: " + beneficiaryPassword.toString());
			urlData.append("\r\n");
			urlData.append("VCLARP");
			urlData.append("&sender=" + URLEncoder.encode(arpanSenderId, encoding));
			urlData.append("&type=" + URLEncoder.encode(type, encoding));
			urlData.append("&template_id=" + URLEncoder.encode(arpanInviteId, encoding));
			System.out.println(url + urlData.toString());
			restTemplate.getForObject(url + urlData.toString(), Object.class);
			return true;
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.SENDING_SMS_FAIL);
		}
	}

	@Override
	public boolean sendUmmeedInviteSms(String phoneNumber, String beneficiaryName, String emailId,
			String beneficiaryPassword, String downloadLink) {
		try {
			String encoding = "UTF-8";
			StringBuilder urlData = new StringBuilder();
			urlData.append("user=" + URLEncoder.encode(userName, encoding));
			urlData.append("&password=" + URLEncoder.encode(password, encoding));
			urlData.append("&mobile=" + URLEncoder.encode(phoneNumber, encoding));
			urlData.append("&message= Dear " + beneficiaryName + ",");
			urlData.append("\r\n");
			urlData.append("Welcome to Ummeed Program!");
			urlData.append("\r\n");
			urlData.append("Download the APK file: " + ummeedDownloadLink);
			urlData.append("\r\n");
			urlData.append("Username: " + emailId.toString());
			urlData.append("\r\n");
			urlData.append("Password: " + beneficiaryPassword.toString());
			urlData.append("\r\n");
			urlData.append("VCLUMD");
			urlData.append("&sender=" + URLEncoder.encode(ummeedSenderId, encoding));
			urlData.append("&type=" + URLEncoder.encode(type, encoding));
			urlData.append("&template_id=" + URLEncoder.encode(ummeedInviteId, encoding));
			System.out.println(url + urlData.toString());
			restTemplate.getForObject(url + urlData.toString(), Object.class);
			return true;
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.SENDING_SMS_FAIL);
		}
	}

	@Override
	public boolean sendUserRegistrationOTP(PhoneOTPResponseDTO otpDetails, String firstName) {

		try {
			String encoding = "UTF-8";
			StringBuilder urlData = new StringBuilder();
			urlData.append("user=" + URLEncoder.encode(userName, encoding));
			urlData.append("&password=" + URLEncoder.encode(password, encoding));
			urlData.append("&mobile=" + URLEncoder.encode(otpDetails.getPhone(), encoding));
			urlData.append("&message= Dear " + firstName + ",OTP(One Time Password) to complete your registration is "
					+ otpDetails.getOtp() + "." + "\r\nVLCLPL");
			urlData.append("&sender=" + vlclplSenderId);
			urlData.append("&type=" + type);
			urlData.append("&template_id=" + vlclplRegUserOtpId);
			restTemplate.getForObject(url + urlData.toString(), Object.class);
			return true;
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.SENDING_SMS_FAIL);
		}
	}

	
	@Override
	public boolean sendSapInviteSms(String programName, String phoneNumber, String beneficiaryName, String emailId,
			String beneficiaryPassword, String downloadLink) {
		try {
			String encoding = "UTF-8";
			StringBuilder urlData = new StringBuilder();
			urlData.append("user=" + URLEncoder.encode(userName, encoding));
			urlData.append("&password=" + URLEncoder.encode(password, encoding));
			urlData.append("&mobile=" + URLEncoder.encode(phoneNumber, encoding));
			urlData.append("&message= Dear " + beneficiaryName + ",");
			urlData.append("\r\n");
			urlData.append("Welcome to "+programName+"!");
			urlData.append("\r\n");
			urlData.append("Click on the link to download the APK file and install the APP: " + downloadLink);
			urlData.append("\r\n");
			urlData.append("Username: " + emailId.toString());
			urlData.append("\r\n");
			urlData.append("Password: " + beneficiaryPassword.toString());
			urlData.append("\r\n");
			urlData.append("VLCLPL");
			urlData.append("&sender=" + URLEncoder.encode(vlclplSenderId, encoding));
			urlData.append("&type=" + URLEncoder.encode(type, encoding));
			urlData.append("&template_id=" + URLEncoder.encode(vlclplRegistrationTemplateId, encoding));
			System.out.println(url + urlData.toString());
			restTemplate.getForObject(url + urlData.toString(), Object.class);
			return true;
		} catch (Exception exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.SENDING_SMS_FAIL);
		}
	}
}
