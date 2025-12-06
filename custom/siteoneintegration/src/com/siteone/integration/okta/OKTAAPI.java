package com.siteone.integration.okta;

import com.siteone.integration.services.okta.data.OktaChangePasswordResponseData;
import com.siteone.integration.services.okta.data.OktaCreateOrUpdateUserResponseData;
import com.siteone.integration.services.okta.data.OktaForgotPwdResponseData;
import com.siteone.integration.services.okta.data.OktaResetPasswordResponseData;
import com.siteone.integration.services.okta.data.OktaSetPasswordResponseData;
import com.siteone.integration.services.okta.data.OktaUnlockUserResponseData;
import com.siteone.integration.services.okta.data.OktaVerifyRecoveryTokenResponseData;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.client.ResourceAccessException;

import com.siteone.integration.exception.okta.OktaInvalidPasswordException;
import com.siteone.integration.exception.okta.OktaInvalidTokenException;
import com.siteone.integration.exception.okta.OktaRecentlyUsedPasswordException;
import com.siteone.integration.exception.okta.OktaUnknownUserException;
import com.siteone.integration.okta.data.SiteOneOktaSessionData;
import com.siteone.integration.okta.response.OktaSessionResponse;
import com.siteone.integration.services.okta.data.SiteoneOktaResponseData;

import de.hybris.platform.b2b.model.B2BCustomerModel;

/**
 * Created by arun.
 */
public interface OKTAAPI {

    /**
     * Calls OKTA auth API with the given credentials. If authentication is successful,
     * sessionToken will bew returned.
     * @param username
     * @param password
     * @return SiteOneOktaSessionData
     */
    SiteOneOktaSessionData doAuth(String username, String password);

    /**
     * Calls OKTA sessions API. Establish session with the given sessionToken.
     * @see com.siteone.integration.okta.OKTAAPI#doAuth(String, String)
     * @param sessionToken
     * @return OktaSessionResponse
     */
    OktaSessionResponse establishSession(String sessionToken);
    
    OktaSessionResponse getSessionDetails(String sessionId);
    
    OktaSessionResponse refreshSession(String sessionId);
    
    void closeSession(String userOktaId);

//    /**
//     * Wrapper for auth and sesion api calls. Internally calls doAuth and estabishSesdion and returns uid.
//     * @param username
//     * @param password
//     * @return SiteOneOktaSessionData
//     */
//    SiteOneOktaSessionData establishSessionWithCredentials(String username, String password);


	OktaCreateOrUpdateUserResponseData createUser(B2BCustomerModel b2bCustomerModel) throws ResourceAccessException;

	OktaCreateOrUpdateUserResponseData updateUser(B2BCustomerModel b2bCustomerModel) throws ResourceAccessException;
	
	void removeUserFromGroup(final String groupId,final String userID);
    OktaCreateOrUpdateUserResponseData getUser(String userId) throws OktaUnknownUserException;
	
     OktaChangePasswordResponseData changePasswordForUser(String oldPassword, String newPassword, String userId);
    
    OktaSetPasswordResponseData setPasswordForUser(B2BCustomerModel b2bCustomerModel, String password);
    
    void activateUser(String userId);
    
    OktaUnlockUserResponseData unlockUser(String userId);
    
    OktaForgotPwdResponseData forgotPasswordForUser(String userId);
	
	OktaVerifyRecoveryTokenResponseData recoverytokenVerification(String userId) throws OktaInvalidTokenException;
	
    OktaResetPasswordResponseData resetPasswordForUser(String stateToken, String newPassword) throws OktaInvalidTokenException,OktaInvalidPasswordException,OktaRecentlyUsedPasswordException;
    
    void addUserToGroup(final String groupId,final String userID);

    Boolean deleteUser(String userId);

    Boolean isUserInOkta (String uid);

	void getOAuth(String uid, String plainPassword);

    Boolean suspendUser(String userId);

    Boolean unSuspendUser(String userId);
}
