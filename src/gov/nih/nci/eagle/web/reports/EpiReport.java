package gov.nih.nci.eagle.web.reports;

import gov.nih.nci.caintegrator.domain.study.bean.StudyParticipant;
import gov.nih.nci.caintegrator.service.task.TaskResult;
import gov.nih.nci.eagle.util.FieldBasedComparator;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;

public class EpiReport extends SortableReport{
    

    public EpiReport() {
        
    }
    
    @PostConstruct
    protected void init() {

       sortedBy = "patientId";
       sortAscending = true;

        
        taskResult = (TaskResult)findingsManager.getTaskResult(task);
        reportBeans = new ArrayList();
        for(StudyParticipant p : (Collection<StudyParticipant>)taskResult.getTaskResults()) {
            reportBeans.add(new EpiReportBean(p));
        }
        sortComparator = new FieldBasedComparator(sortedBy, sortAscending);
    }
    

}
