package gov.nih.nci.eagle.web.reports;

import gov.nih.nci.caintegrator.analysis.messaging.ClassComparisonResultEntry;
import gov.nih.nci.caintegrator.service.findings.ClassComparisonFinding;

import java.util.List;
import java.util.Map;

public class ClassComparisonReport {

    private ClassComparisonFinding finding;
    
    public ClassComparisonReport() {
        
    }
    
    public ClassComparisonReport(ClassComparisonFinding finding) {
        this.finding = finding;
    }
    
    public List<ClassComparisonResultEntry> getResultEntries() {
        return finding.getResultEntries();
    }
    
    public Map getAnnotationMap() {
        return finding.getReporterAnnotationsMap();
    }
    
}
