/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/eagle/LICENSE.txt for details.
 */

package gov.nih.nci.eagle.web.struts;


import gov.nih.nci.caintegrator.application.cache.PresentationCacheManager;
import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;
import gov.nih.nci.caintegrator.service.findings.ClassComparisonFinding;
import gov.nih.nci.caintegrator.service.findings.FTestFinding;
import gov.nih.nci.caintegrator.service.findings.GeneralizedLinearModelFinding;
import gov.nih.nci.caintegrator.service.task.Task;
import gov.nih.nci.caintegrator.service.task.TaskResult;
import gov.nih.nci.caintegrator.studyQueryService.FindingsManager;
import gov.nih.nci.eagle.enumeration.SpecimenType;
import gov.nih.nci.eagle.query.dto.ClassComparisonQueryDTOBuilder;
import gov.nih.nci.eagle.query.dto.ClassComparisonQueryDTOImpl;
import gov.nih.nci.eagle.util.CollisionDetector;
import gov.nih.nci.eagle.util.ManagedBeanUtil;
import gov.nih.nci.eagle.web.reports.ClassComparisonReport;
import gov.nih.nci.eagle.web.reports.FTestReport;
import gov.nih.nci.eagle.web.reports.GLMReport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
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
	
	 private static Logger logger = Logger.getLogger(ClassComparisonAction.class);

	
	private FindingsManager findingsManager;
	private ClassComparisonQueryDTOBuilder dtoBuilder;
	private ClassComparisonQueryDTOImpl ccQueryDTO;
	private PresentationCacheManager presentationCacheManager;    
	   
	
	
    public ActionForward submit(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws IOException	{
    		
    		//verify the query name
    		List<String> taskNames = new ArrayList<String>();
    		Collection tasks = presentationCacheManager.getAllSessionTasks(request.getSession().getId());
    		for(Object t : tasks){
    			taskNames.add( ((Task)t).getId() );
    		}
    		((ClassComparisonForm)form).setAnalysisName(CollisionDetector.renameOnCollision( ((ClassComparisonForm)form).getAnalysisName() , taskNames ));
    	
    	      ccQueryDTO = (ClassComparisonQueryDTOImpl)dtoBuilder.buildQueryDTO(form, request.getSession().getId());  
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

    	//set the group names
    	UserListBeanHelper helper = new UserListBeanHelper(request.getSession());
        List<UserList> patientLists = helper.getLists(ListType.PatientDID);
        List<LabelValueBean> sampleGroups = new ArrayList<LabelValueBean>();
        
        TreeMap tm = new TreeMap();
        for(UserList patientList: patientLists){
        	tm.put(patientList.getName(), patientList);
        	//sampleGroups.add(new LabelValueBean(patientList.getName(),patientList.getClass().getCanonicalName() + "#" + patientList.getName()));
        	//sampleGroups.add(new LabelValueBean(patientList.getName(),patientList.getName()));
        }
        for(Object u : tm.values()){
        	UserList ul = (UserList)u;
           	sampleGroups.add(new LabelValueBean(ul.getName(),ul.getName()));
        }
        
        ((ClassComparisonForm) form).setExistingGroups(sampleGroups); 
        
        //for tissue, we can not do case v control since its using seperate rbinary files for now
        List<LabelValueBean> tissueSampleGroups = new ArrayList<LabelValueBean>();
        tissueSampleGroups.addAll(sampleGroups);
        tissueSampleGroups.remove(new LabelValueBean("all_case", "all_case"));
        tissueSampleGroups.remove(new LabelValueBean("all_control", "all_control"));
        ((ClassComparisonForm) form).setExistingTissueGroups(tissueSampleGroups); 


        //set the covariate options
        
        //set the specimenTypes
        List<LabelValueBean> sTypes = new ArrayList<LabelValueBean>();
        for(SpecimenType st : SpecimenType.values())	{
        	sTypes.add(new LabelValueBean(st.getName(), st.toString()));
        }
        ((ClassComparisonForm) form).setExistingSpecimenTypes(sTypes);        
       
        return mapping.findForward("success");
    } 
    
    public ActionForward runReport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        
        HttpSession session = request.getSession();
        
        try{            
               Task task = (Task) presentationCacheManager.getNonPersistableObjectFromSessionCache(session.getId(),request.getParameter("taskId"));
 
                session.setAttribute("task", task);
                ManagedBeanUtil.clearReport(session, task);

            return (mapping.findForward("success"));
            
        }
        catch(Exception e){            
            logger.error("Error getting findings", e);
            return (mapping.findForward("failure"));
        }
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
    
    /**
     * @return Returns the presentationCacheManager.
     */
    public PresentationCacheManager getPresentationCacheManager() {
        return presentationCacheManager;
    }

    /**
     * @param presentationCacheManager The presentationCacheManager to set.
     */
    public void setPresentationCacheManager(
            PresentationCacheManager presentationCacheManager) {
        this.presentationCacheManager = presentationCacheManager;
    }
    
}
