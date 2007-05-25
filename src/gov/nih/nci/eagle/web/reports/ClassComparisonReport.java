package gov.nih.nci.eagle.web.reports;

import gov.nih.nci.caintegrator.analysis.messaging.ClassComparisonResultEntry;
import gov.nih.nci.caintegrator.domain.annotation.gene.bean.GeneBiomarker;
import gov.nih.nci.caintegrator.service.findings.ClassComparisonFinding;
import gov.nih.nci.caintegrator.service.task.TaskResult;
import gov.nih.nci.eagle.util.ClassComparisonComparator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

public class ClassComparisonReport extends BaseReport {

    public ClassComparisonReport() {
    }
    
    @PostConstruct
    protected void init() {
        sortAscending = true;
        TaskResult result = (TaskResult)findingsManager.getTaskResult(task);
        this.results = (ClassComparisonFinding)result;
        sortComparator = new ClassComparisonComparator("pvalue", sortAscending);
        sortedBy = "pvalue";
        
        reportBeans = new ArrayList();

        for (ClassComparisonResultEntry entry : ((ClassComparisonFinding)results).getResultEntries()) {
            ClassComparisonReportBean bean = new ClassComparisonReportBean(
                    entry, ((GeneBiomarker) ((ClassComparisonFinding)results).getReporterAnnotationsMap()
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
