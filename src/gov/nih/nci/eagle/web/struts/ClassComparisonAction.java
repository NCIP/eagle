package gov.nih.nci.eagle.web.struts;

import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.util.LabelValueBean;

/**
 * all instance variables (objects) in this action are injected
 * by the Spring container (application-context-services.xml). The
 * struts action itself is managed by spring by use of the
 * org.springframework.web.struts.DelegatingActionProxy class. The
 * action path can then be referenced by Spring in application-context-struts.xml)
 * All struts and spring config files can be found in the WEB-INF directory.
 * @author landyr
 *
 */

public class ClassComparisonAction extends DispatchAction{
    public ActionForward submit(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws IOException	{
               
            return (mapping.findForward("success"));
    }
    
    public ActionForward setup(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)throws Exception {
        
    	UserListBeanHelper helper = new UserListBeanHelper(request.getSession());
        List<UserList> patientLists = helper.getLists(ListType.PatientDID);
        List<LabelValueBean> sampleGroups = new ArrayList<LabelValueBean>();
        for(UserList patientList: patientLists){
        	//sampleGroups.add(new LabelValueBean(patientList.getName(),patientList.getClass().getCanonicalName() + "#" + patientList.getName()));
        	sampleGroups.add(new LabelValueBean(patientList.getName(),patientList.getName()));
        }
        ((ClassComparisonForm) form).setExistingGroups(sampleGroups);
        
        return mapping.findForward("success");
    } 
    
}
