package gov.nih.nci.eagle.service.test;

import gov.nih.nci.caintegrator.domain.study.bean.StudyParticipant;
import gov.nih.nci.caintegrator.dto.query.EpiQueryDTO;
import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;
import gov.nih.nci.caintegrator.service.task.Task;
import gov.nih.nci.caintegrator.service.task.TaskResult;
import gov.nih.nci.caintegrator.studyQueryService.FindingsManager;
import gov.nih.nci.caintegrator.test.BaseSpringTestCase;

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
        EpiQueryDTO dto = new EpiQueryDTO();
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
}
