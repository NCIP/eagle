package gov.nih.nci.eagle.web.reports;

import gov.nih.nci.caintegrator.analysis.messaging.ClassComparisonResultEntry;
import gov.nih.nci.caintegrator.domain.annotation.gene.bean.GeneBiomarker;
import gov.nih.nci.caintegrator.service.findings.ClassComparisonFinding;
import gov.nih.nci.caintegrator.service.task.TaskResult;
import gov.nih.nci.eagle.util.FieldBasedComparator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

public class ClassComparisonReport extends BaseClassComparisonReport {

    public ClassComparisonReport() {
    }
    
    @PostConstruct
    protected void init() {
        sortAscending = true;
        TaskResult result = (TaskResult)findingsManager.getTaskResult(task);
        this.taskResult = (ClassComparisonFinding)result;
        sortComparator = new FieldBasedComparator("pvalue", sortAscending);
        sortedBy = "pvalue";
        
        reportBeans = new ArrayList();

        for (ClassComparisonResultEntry entry : ((ClassComparisonFinding)taskResult).getResultEntries()) {
            ClassComparisonReportBean bean = new ClassComparisonReportBean(
                    entry, ((GeneBiomarker) ((ClassComparisonFinding)taskResult).getReporterAnnotationsMap()
                            .get(entry.getReporterId())).getHugoGeneSymbol());
            reportBeans.add(bean);
        }
        patientInfoMap = new HashMap<String, List>();
        for(String groupName : (List<String>)getBaselineGroups()) {
            List patients = getQueryDTO().getBaselineGroupMap().get(groupName);
            List patientInfo = patientManager.getPatientInfo(patients);
            patientInfoMap.put(groupName, patientInfo);
        }
        for(String groupName : (List<String>)getComparisonGroups()) {
            List patients = getQueryDTO().getComparisonGroupsMap().get(groupName);
            List patientInfo = patientManager.getPatientInfo(patients);
            patientInfoMap.put(groupName, patientInfo);
        }
    }


}
