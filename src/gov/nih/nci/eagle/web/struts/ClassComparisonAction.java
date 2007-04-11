package gov.nih.nci.eagle.web.struts;


import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.caintegrator.studyQueryService.FindingsManager;
import gov.nih.nci.caintegrator.application.cache.PresentationCacheManager;
import gov.nih.nci.caintegrator.service.task.Task;
import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;

import gov.nih.nci.eagle.query.dto.ClassComparisonQueryDTOBuilder;
import gov.nih.nci.eagle.query.dto.ClassComparisonQueryDTOImpl;



import java.util.ArrayList;
import java.util.List;
import java.io.IOException;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.actions.DispatchAction;
import org.apache.log4j.Logger;
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
	
	 private static Logger logger = Logger.getLogger(ClassComparisonAction.class);

	
	private FindingsManager findingsManager;
	private ClassComparisonQueryDTOBuilder dtoBuilder;
	private ClassComparisonQueryDTOImpl ccQueryDTO;
	private PresentationCacheManager presentationCacheManager;    
	   
	
	
    public ActionForward submit(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws IOException	{
    	
    	      dtoBuilder = new ClassComparisonQueryDTOBuilder(request.getSession());    	   
    	      ccQueryDTO = (ClassComparisonQueryDTOImpl)dtoBuilder.buildQueryDTO(form);  
    	      try {             
    	            Task task = findingsManager.submitQuery(ccQueryDTO);
    	            presentationCacheManager.addNonPersistableToSessionCache(request.getSession().getId(),task.getId(),task);           
    	        } catch (FindingsQueryException e) {
    	            ActionErrors errors = new ActionErrors();
    	            errors.add("queryErrors", new ActionMessage(
    	                    "caintegrator.error.query"));
    	            saveMessages(request, errors);
    	            return (mapping.findForward("failure"));
    	        }
    	    
               
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
    
    
    /**
     * @return Returns the findingsManager.
     */
    public FindingsManager getFindingsManager() {
        return findingsManager;
    }

    /**
     * @param findingsManager The findingsManager to set.
     */
    public void setFindingsManager(FindingsManager findingsManager) {
        this.findingsManager = findingsManager;
    }
    
    /**
     * @return Returns the dtoBuilder.
     */
    public ClassComparisonQueryDTOBuilder getDtoBuilder() {
        return dtoBuilder;
    }

    /**
     * @param dtoBuilder
     *            The dtoBuilder to set.
     */
    public void setDtoBuilder(ClassComparisonQueryDTOBuilder dtoBuilder) {
        this.dtoBuilder = dtoBuilder;
    }
    
}
