package gov.nih.nci.eagle.web.reports;

import gov.nih.nci.caintegrator.analysis.messaging.FTestResultEntry;

public class FTestReportBean {

    private FTestResultEntry entry;
    private String geneSymbol;
    
    public FTestReportBean(FTestResultEntry entry, String geneSymbol) {
        this.entry = entry;
        this.geneSymbol = geneSymbol;
    }
    
    public String getReporterId() {
        return entry.getReporterId();
    }
    
    public Double getPvalue() {
        return entry.getPvalue();
    }
    
    public double[] getGroupMeans() {
        return entry.getGroupMeans();
    }
    
    public Double getMaximumFoldChange() {
        return entry.getMaximumFoldChange();
    }
    
    public String getGeneSymbol() {
        return geneSymbol;
    }
}
