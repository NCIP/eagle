package gov.nih.nci.eagle.web.reports;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.caintegrator.analysis.messaging.FTestResultEntry;

public class FTestReportBean implements ReportBean {

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
    
    public List getRow()	{
    	List row = new ArrayList();
    	row.add(entry.getReporterId());
    	row.add(entry.getPvalue());
    	for(double d : entry.getGroupMeans())	{
    		row.add(String.valueOf(d));
    	}
    	row.add(entry.getMaximumFoldChange());
    	row.add(this.getGeneSymbol());
    	
    	return row; 
    }
    
    public List getRowLabels()	{
    	//this is here only to keep the CSV column config in one class
       	List row = new ArrayList();
    	row.add("Reporter");
    	row.add("p-value");
    	for(int i=0; i<entry.getGroupMeans().length; i++)	{
    		row.add("group" + i + " mean"); //TODO: need to get real grp labels from report, bean does not know
    	}
    	row.add("max fold change");
    	row.add("gene symbol");
    	return row; 
    }

}
