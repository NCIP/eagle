/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/eagle/LICENSE.txt for details.
 */

package gov.nih.nci.eagle.web.struts;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


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

public final class LogoutAction extends Action
{
    private static Logger logger = Logger.getLogger(LogoutAction.class);
    public ActionForward execute(ActionMapping mapping, ActionForm form,
    								HttpServletRequest request, HttpServletResponse response)
    {
        HttpSession session = request.getSession();
        try{
            session.invalidate();
            return (mapping.findForward("logout"));
        }
        catch(Exception e){
            logger.error(e);
            return (mapping.findForward("logout"));
        }

    }
}
