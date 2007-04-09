package gov.nih.nci.eagle.web.struts;

import gov.nih.nci.caintegrator.application.cache.PresentationCacheManager;
import gov.nih.nci.caintegrator.dto.query.EpiQueryDTO;
import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;
import gov.nih.nci.caintegrator.service.task.Task;
import gov.nih.nci.caintegrator.studyQueryService.FindingsManager;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.actions.DispatchAction;

/**
 * all instance variables (objects) in this action are injected by the Spring
 * container (application-context-services.xml). The struts action itself is
 * managed by spring by use of the
 * org.springframework.web.struts.DelegatingActionProxy class. The action path
 * can then be referenced by Spring in application-context-struts.xml) All
 * struts and spring config files can be found in the WEB-INF directory.
 * 
 * @author landyr
 */

public class EpiAction extends DispatchAction {
    private FindingsManager findingsManager;
    private PresentationCacheManager presentationCacheManager;

    public ActionForward submit(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        EpiQueryDTO dto = new EpiQueryDTO();
        dto.setQueryName("test");
        try {
            Task task = findingsManager.submitQuery(dto);
            presentationCacheManager.addNonPersistableToSessionCache(request
                    .getSession().getId(), task.getId(), task);
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
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        return mapping.findForward("success");
    }

    public FindingsManager getFindingsManager() {
        return findingsManager;
    }

    public void setFindingsManager(FindingsManager findingsManager) {
        this.findingsManager = findingsManager;
    }

    public PresentationCacheManager getPresentationCacheManager() {
        return presentationCacheManager;
    }

    public void setPresentationCacheManager(
            PresentationCacheManager presentationCacheManager) {
        this.presentationCacheManager = presentationCacheManager;
    }
}
