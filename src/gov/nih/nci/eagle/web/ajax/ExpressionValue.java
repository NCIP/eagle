package gov.nih.nci.eagle.web.ajax;

public class ExpressionValue {

    private String reporterId;
    private String sampleType;
    private Double foldChange;
    
    public ExpressionValue(String reporterId, String sampleType, Double foldChange) {
        this.reporterId = reporterId;
        this.sampleType = sampleType;
        this.foldChange = foldChange;
    }
    
    public Double getFoldChange() {
        return foldChange;
    }
    public void setFoldChange(Double foldChange) {
        this.foldChange = foldChange;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getReporterId() {
        return reporterId;
    }
    public void setReporterId(String reporterId) {
        this.reporterId = reporterId;
    }
}
