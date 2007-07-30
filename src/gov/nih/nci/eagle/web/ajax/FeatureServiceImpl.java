package gov.nih.nci.eagle.web.ajax;


import gov.nih.nci.caintegrator.domain.finding.bean.Finding;
import gov.nih.nci.caintegrator.domain.finding.variation.germline.bean.GenotypeFinding;
import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;
import gov.nih.nci.caintegrator.service.task.Task;
import gov.nih.nci.caintegrator.service.task.TaskResult;
import gov.nih.nci.caintegrator.studyQueryService.FindingsManager;
import gov.nih.nci.eagle.query.dto.SnpQueryDTO;

import java.util.Collection;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class FeatureServiceImpl implements FeatureService {

    private FindingsManager findingsManager;
    private HibernateTemplate hibernateTemplate;
    
    public Collection getSnpCalls(String snpId, Collection<String> patientList1,
            Collection<String> patientList2) {
        SnpQueryDTO dto = new SnpQueryDTO();
        dto.setSnpId(snpId);
        dto.setPatientIds(patientList1);
        dto.setQueryName("test");
        Collection<Finding> findings = null;
        try {
           findings =  findingsManager.getFindings(dto);
        } catch (FindingsQueryException e) {
            e.printStackTrace();
        }
        return findings;
    }
    public HashMap getSnpCallReport() {
        HttpSession session = WebContextFactory.get().getSession();
        Task task = (Task)session.getAttribute("snpTask");
        TaskResult result = (TaskResult)findingsManager.getTaskResult(task);
        
        HashMap<String, Integer> findingMap = new HashMap<String, Integer>();
        for(GenotypeFinding finding : (Collection<GenotypeFinding>)result.getTaskResults()) {
            String call = finding.getCall();

            if(findingMap.containsKey(call)) {
                findingMap.put(call, findingMap.get(call) + 1);
            } else {
                findingMap.put(call, 1);
            }
        }
        
        return findingMap;
    }
    
    

    public FindingsManager getFindingsManager() {
        return findingsManager;
    }

    public void setFindingsManager(FindingsManager findingsManager) {
        this.findingsManager = findingsManager;
    }

    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }


}
