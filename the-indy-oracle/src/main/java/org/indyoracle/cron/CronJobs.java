package org.indyoracle.cron;

import java.util.ArrayList;

import org.indyoracle.beans.Mail;
import org.indyoracle.data.DataManager;
import org.indyoracle.gmail.GmailManager;
import org.indyoracle.security.UserManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.directory.CustomData;

@Component
public class CronJobs {
	
	private DataManager dataManager;
	
	@Scheduled(fixedRate = 20000)
	public void runMailProcess() {
		dataManager = DataManager.getInstance();
		dataManager.setUptime(System.currentTimeMillis() - dataManager.getStartTime());
		
		ArrayList<Mail> smsEmails = GmailManager.getSmsMessages();
		
		// Messages:
		if (smsEmails.size() > 0) {
			for (Mail m : smsEmails) {
				String phoneNumber = GmailManager.extractSmsNumber(m);
				
				ArrayList<Account> accounts = UserManager.getAllOptedInUsers();
				
				for (Account a : accounts) {
					CustomData data = a.getCustomData();
					String emailText = m.getText().trim().replaceAll("\\r", "").replaceAll("\\n", "");
					GmailManager.sendHelpRequestEmail(data.get("phoneNumber").toString(), data.get("phoneCarrier").toString(), phoneNumber, emailText);
				}
			}
		}
	}
}
