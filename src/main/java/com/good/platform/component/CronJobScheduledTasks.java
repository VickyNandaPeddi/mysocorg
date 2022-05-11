package com.good.platform.component;

import java.io.IOException;

import org.springframework.scheduling.annotation.Scheduled;

import com.good.platform.exception.SOException;
import com.good.platform.service.BeneficiaryService;

import lombok.RequiredArgsConstructor;

/**
 * Schedule the time for the specific task in this component
 * 
 * @author Arya C Achari
 * @since 25-Feb-2022
 *
 */
@RequiredArgsConstructor
public class CronJobScheduledTasks {

	// Pattern of Cron(examples)
	// ===============================================================================================
	// = "0 0 * * * *" = the top of every hour of every day. =
	// = "*/10 * * * * *" = every ten seconds. =
	// = "0 0 8-10 * * *" = 8, 9 and 10 o'clock of every day. =
	// = "0 0 6,19 * * *" = 6:00 AM and 7:00 PM every day. =
	// = "0 0/30 8-10 * * *" = 8:00, 8:30, 9:00, 9:30, 10:00 and 10:30 every day.=
	// = "0 0 9-17 * * MON-FRI" = on the hour 9:00 AM to 5:00 PM weekdays =
	// = "0 0 0 25 12 ?" = every Christmas Day at midnight =
	// ===============================================================================================

	private final BeneficiaryService beneficiaryService;

	/**
	 * Onboarding the imported beneficiary from excel file, login credentials
	 * creating in the keycloak
	 * 
	 * This job will run everyday at 11:30 PM
	 * 
	 * UAT/TEST server time zone is UTC. IST is 5hr 30min ahead of UTC. IST: 23:30
	 * (11:30 PM) UTC: 18:30 (06:30 PM)
	 * 
	 */
//	@Scheduled(cron = "0 30 18 * * *")
//	public void autoBeneficiaryOnBoarding() throws SOException, IOException {
//		beneficiaryService.sapBeneficiaryOnBoarding();
//	}

}
