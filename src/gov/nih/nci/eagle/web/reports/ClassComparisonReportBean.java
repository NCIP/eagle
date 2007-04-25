package gov.nih.nci.eagle.web.reports;

import gov.nih.nci.caintegrator.analysis.messaging.ClassComparisonResultEntry;

public class ClassComparisonReportBean {

    private ClassComparisonResultEntry entry;
    private String geneSymbol;
    
    public ClassComparisonReportBean(ClassComparisonResultEntry entry, String hugoGeneSymbol) {
        this.entry = entry;
        this.geneSymbol = hugoGeneSymbol;
    }
    
    public String getReporterId() {
        return entry.getReporterId();
    }
    
    public Double getPvalue() {
        return entry.getPvalue();
    }
    
    public Double getMeanBaselineGrp() {
        return entry.getMeanBaselineGrp();
    }
    
    public Double getMeanGrp1() {
        return entry.getMeanGrp1();
    }
    
    public Double getAbsoluteFoldChange() {
        return entry.getAbsoluteFoldChange();
    }
    
    public String getGeneSymbol() {
        return geneSymbol;
    }

}
