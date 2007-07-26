package gov.nih.nci.eagle.web.struts;

import gov.nih.nci.caintegrator.application.cache.PresentationCacheManager;
import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.caintegrator.service.task.Task;
import gov.nih.nci.eagle.util.ManagedBeanUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.util.LabelValueBean;

public class SnpAction extends DispatchAction {

	private PresentationCacheManager presentationCacheManager;

	public ActionForward submit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		// stuff here
		return (mapping.findForward("success"));
	}

	public ActionForward setup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// init fields here
    	//set the group names
    	UserListBeanHelper helper = new UserListBeanHelper(request.getSession());
        List<UserList> patientLists = helper.getLists(ListType.PatientDID);
        List<LabelValueBean> lvbeans = new ArrayList<LabelValueBean>();
        for(UserList patientList: patientLists){
        	//sampleGroups.add(new LabelValueBean(patientList.getName(),patientList.getClass().getCanonicalName() + "#" + patientList.getName()));
        	lvbeans.add(new LabelValueBean(patientList.getName(),patientList.getName()));
        }
        request.setAttribute("groupNamesList", lvbeans);
		
		return mapping.findForward("success");
	}

	public ActionForward runReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();

		try {
			Task task = (Task) presentationCacheManager.getNonPersistableObjectFromSessionCache(session.getId(),request.getParameter("taskId"));
			session.setAttribute("snpTask", task);
			ManagedBeanUtil.clearReport(session, task);
			return (mapping.findForward("success"));

		} catch (Exception e) {
			return (mapping.findForward("failure"));
		}
	}

}
