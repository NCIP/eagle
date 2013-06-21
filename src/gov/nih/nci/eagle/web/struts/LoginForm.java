package gov.nih.nci.eagle.web.struts;
import java.io.File;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.validator.ValidatorForm;

public final class LoginForm extends ValidatorForm
{
private static Logger logger = Logger.getLogger(LoginForm.class);

private String username;
private String password;

/**
 * @return Returns the username.
 */
public String getUsername() {
    return username;
}
/**
 * @param username The username to set.
 */
public void setUsername(String username) {
    this.username = username;
}
public void setPassword(String argPassword)	{
	password = argPassword;
}
public String getPassword()	{
	return password;
}

}
