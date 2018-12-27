package org.indyoracle.security;

import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.application.ApplicationList;
import com.stormpath.sdk.application.Applications;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.client.ClientBuilder;
import com.stormpath.sdk.client.Clients;
import com.stormpath.sdk.tenant.Tenant;

/**
 * Helper class for accessing Stormpath account properties.
 * 
 * @author Guy
 *
 */
public class AccountManager {
	
	/**
	 * Retrieves the Stormpath Account object.
	 * 
	 * @return
	 */
	public static Application getApplication() {
		ClientBuilder builder = Clients.builder(); 
		Client client = builder.build();
		
		Tenant tenant = client.getCurrentTenant();
		ApplicationList applications = tenant.getApplications(
		        Applications.where(Applications.name().eqIgnoreCase("indy-oracle"))
		);
		
		return applications.iterator().next();
	}
}
