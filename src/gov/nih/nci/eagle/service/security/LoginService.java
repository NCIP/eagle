package gov.nih.nci.eagle.service.security;

import gov.nih.nci.eagle.bean.UserInfoBean;
import gov.nih.nci.eagle.exception.LoginException;

/**
 * This is an interface for the LoginService.  It has one method
 * that throws an Exception if the user is not authenticated, or 
 * returns a UserInfoBean if they are.
 * 
 *
 * @author caIntegrator Team
 */
public interface LoginService {

    public UserInfoBean loginUser(String username, String password) throws LoginException;
}
