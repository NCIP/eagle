package gov.nih.nci.eagle.web.reports;

import gov.nih.nci.caintegrator.domain.study.bean.StudyParticipant;
import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.service.task.TaskResult;
import gov.nih.nci.eagle.util.FieldBasedComparator;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;

public class EpiReport extends SortableReport{
    
    private QueryDTO queryDTO;
    public EpiReport() {
        
    }
    
    @PostConstruct
    protected void init() {

       sortedBy = "patientId";
       sortAscending = true;

       if(task != null && reportBeans == null) {
           taskResult = (TaskResult)findingsManager.getTaskResult(task);
           this.queryDTO = task.getQueryDTO();
            reportBeans = new ArrayList();
            for(StudyParticipant p : (Collection<StudyParticipant>)taskResult.getTaskResults()) {
                reportBeans.add(new EpiReportBean(p));
            }
        }
        sortComparator = new FieldBasedComparator(sortedBy, sortAscending);
    }
    
    public void setPatientData(Collection patientData) {
        reportBeans = new ArrayList();
        if(patientData != null) {
            for(StudyParticipant p : (Collection<StudyParticipant>)patientData) {
                reportBeans.add(new EpiReportBean(p));
            }
        }
    }

    public QueryDTO getQueryDTO() {
        return queryDTO;
    }

    public void setQueryDTO(QueryDTO queryDTO) {
        this.queryDTO = queryDTO;
    }
    
    

}
