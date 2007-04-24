package gov.nih.nci.eagle.web.reports;

import gov.nih.nci.caintegrator.service.findings.FTestFinding;

import java.util.Collection;
import java.util.Map;

public class FTestReport {

    private FTestFinding finding;
    
    public FTestReport(FTestFinding finding) {
        this.finding = finding;
    }
    
    public Collection getResultEntries() {
        return finding.getResultEntries();
    }
    
    public Map getAnnotationMap() {
        return finding.getReporterAnnotationsMap();
    }
    public int getNumberGroups() {
        return finding.getGroupNames().size();
    }
    public Collection getGroupNames() {
        return finding.getGroupNames();
    }
}
