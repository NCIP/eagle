package gov.nih.nci.eagle.web.struts;

import gov.nih.nci.caintegrator.application.cache.CacheConstants;
import gov.nih.nci.caintegrator.application.lists.UserListBean;
import gov.nih.nci.eagle.bean.UserInfoBean;
import gov.nih.nci.eagle.exception.LoginException;
import gov.nih.nci.eagle.service.security.LoginService;
import gov.nih.nci.eagle.util.EAGLEConstants;
import gov.nih.nci.eagle.util.EAGLEListLoader;

import java.io.File;
import java.io.IOException;

import javax.naming.OperationNotSupportedException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * all instance variables (objects) in this action are injected
 * by the Spring container (application-context-services.xml). The
 * struts action itself is managed by spring by use of the
 * org.springframework.web.struts.DelegatingActionProxy class. The
 * action path can then be referenced by Spring in application-context-struts.xml)
 * All struts and spring config files can be found in the WEB-INF directory.
 * @author rossok
 *
 */

public final class LoginAction extends Action
{
    private static Logger logger = Logger.getLogger(LoginAction.class);
    private static String SEPERATOR = File.separator;
    private LoginService loginService;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException
	{
        LoginForm f = (LoginForm) form;
        try {
            UserInfoBean userInfoBean = loginService.loginUser(f.getUsername(),f.getPassword());
            request.getSession().setAttribute(EAGLEConstants.userInfoBean,userInfoBean);
            UserListBean userListBean = new UserListBean();
           
            
            try {
            	userListBean = EAGLEListLoader.loadDefaultLists(userListBean, request.getSession());
            } catch (OperationNotSupportedException e) {
            	// TODO Auto-generated catch block
            	e.printStackTrace();
            	System.out.println("list did not save");
            }
            
            request.getSession().setAttribute(CacheConstants.USER_LISTS,userListBean);
            
            return (mapping.findForward("success"));
        } catch (LoginException e) {
            ActionErrors errors = new ActionErrors();
            errors.add("invalidLogin", new ActionMessage("caintegrator.error.login"));
            saveMessages(request,errors);
            return (mapping.findForward("failure"));
        }		
	}

    /**
     * @return Returns the loginService.
     */
    public LoginService getLoginService() {
        return loginService;
    }

    /**
     * @param loginService The loginService to set.
     */
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }
}
