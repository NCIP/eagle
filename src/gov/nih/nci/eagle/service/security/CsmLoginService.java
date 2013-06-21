/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/eagle/LICENSE.txt for details.
 */

package gov.nih.nci.eagle.service.security;

import javax.security.sasl.AuthenticationException;

import org.apache.log4j.Logger;

import gov.nih.nci.caintegrator.security.UserCredentials;
import gov.nih.nci.caintegrator.security.SecurityManager;
import gov.nih.nci.eagle.bean.UserInfoBean;
import gov.nih.nci.eagle.exception.LoginException;


/**
 * The CsmLoginService logs a user in using the Common Security
 * Module.
 * 
 *
 * @author caIntegrator Team
 */
public class CsmLoginService implements LoginService {

    private static final String APP_NAME = "eagle";
    private static Logger logger = Logger.getLogger(CsmLoginService.class);

    /**
     * This method will authenticate a user agains the security manager
     * for the specified project.  Throws a LoginException if the user
     * cannot be authenticated, otherwise returns a UserInfoBean with information
     * about the user.
     *
     * @param userName
     * @param password
     * @return
     * @throws LoginException
     */
    public UserInfoBean loginUser(String userName, String password)
            throws LoginException {

        UserInfoBean userInfo = new UserInfoBean();

        UserCredentials user = null;
        SecurityManager secManager = SecurityManager.getInstance(APP_NAME);
        try {
            if (secManager.authenticate(userName, password)) {
                user = secManager.authorization(userName);
            }
        } catch (AuthenticationException e) {
            throw new LoginException(e);
        }
        userInfo.setLoggedIn(true);
        userInfo.setUserName(userName);

        return userInfo;
    }

}
