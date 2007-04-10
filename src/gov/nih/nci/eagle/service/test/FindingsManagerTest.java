package gov.nih.nci.eagle.service.test;

import gov.nih.nci.caintegrator.domain.study.bean.StudyParticipant;
import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;
import gov.nih.nci.caintegrator.service.task.Task;
import gov.nih.nci.caintegrator.service.task.TaskResult;
import gov.nih.nci.caintegrator.studyQueryService.FindingsManager;
import gov.nih.nci.caintegrator.studyQueryService.dto.epi.EPIQueryDTO;
import gov.nih.nci.caintegrator.test.BaseSpringTestCase;
import gov.nih.nci.eagle.query.dto.EagleClinicalQueryDTO;

import java.util.Collection;

public class FindingsManagerTest extends BaseSpringTestCase {

    FindingsManager fm;
    @Override
    public String[] getConfigFiles() {
        return new String[]{  "test/applicationContext-junit.xml", 
                             "../caintegrator-spec/src/applicationContext-services.xml",
                              "/WebRoot/WEB-INF/applicationContext-services.xml"
                              };
    }
    
    public void setUp() {
        fm = (FindingsManager) ctx.getBean("eagleFindingsManager");
    }
    
    public void testManager() throws FindingsQueryException {
        EPIQueryDTO dto = new EPIQueryDTO();
        dto.setQueryName("test");
        Task task = fm.submitQuery(dto);
        task = fm.checkStatus(task);
        while(task.getStatus().equals(FindingStatus.Running)) {
            task = fm.checkStatus(task);
        }
        TaskResult taskResult = fm.getTaskResult(task);
        Collection taskResults = taskResult.getTaskResults();
        for(StudyParticipant p : (Collection<StudyParticipant>)taskResults) {
            System.out.println(p.getId());
        }
        
    }
    public void testClinical() throws FindingsQueryException {
        EagleClinicalQueryDTO dto = new EagleClinicalQueryDTO();
        dto.setQueryName("test");
        Task task = fm.submitQuery(dto);
        task = fm.checkStatus(task);
        while(task.getStatus().equals(FindingStatus.Running)) {
            task = fm.checkStatus(task);
        }
        TaskResult taskResult = fm.getTaskResult(task);
        Collection taskResults = taskResult.getTaskResults();
        if(taskResults != null) {
            for(StudyParticipant p : (Collection<StudyParticipant>)taskResults) {
                System.out.println(p.getId());
            }
        }
    }
}
