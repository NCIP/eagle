package gov.nih.nci.eagle.web.reports;

import gov.nih.nci.caintegrator.analysis.messaging.ClassComparisonResultEntry;
import gov.nih.nci.caintegrator.domain.annotation.gene.bean.GeneBiomarker;
import gov.nih.nci.caintegrator.service.findings.ClassComparisonFinding;
import gov.nih.nci.eagle.util.FTestComparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;

public class ClassComparisonReport {

    private ClassComparisonFinding finding;
    private List reportBeans;
    private FTestComparator sortComparator;
    private Boolean sortAscending;

    public ClassComparisonReport() {

    }

    public ClassComparisonReport(ClassComparisonFinding finding) {
        sortAscending = true;
        this.finding = finding;
        sortComparator = new FTestComparator("pvalue", sortAscending);

        reportBeans = new ArrayList();

        for (ClassComparisonResultEntry entry : finding.getResultEntries()) {
            ClassComparisonReportBean bean = new ClassComparisonReportBean(entry,
                    ((GeneBiomarker) finding.getReporterAnnotationsMap().get(
                            entry.getReporterId())).getHugoGeneSymbol());
            reportBeans.add(bean);
        }
    }

    public List getReportBeans() {
        Collections.sort(reportBeans, sortComparator);
        return reportBeans;
    }

    public Map getAnnotationMap() {
        return finding.getReporterAnnotationsMap();
    }

    public void sortDataList(ActionEvent event) {
        String sortFieldAttribute = getAttribute(event, "sortField");

        // Get and set sort field and sort order.
        if (sortFieldAttribute != null
                && sortFieldAttribute.equals(sortComparator.getField())) {
            sortAscending = !sortAscending;
        } else {
            sortAscending = true;
        }
        if (sortFieldAttribute != null) {
            sortComparator = new FTestComparator(sortFieldAttribute,
                    sortAscending);
        }

    }

    private static String getAttribute(ActionEvent event, String name) {
        return (String) event.getComponent().getAttributes().get(name);
    }

    public boolean getSortAscending() {
        return sortAscending;
    }

    public void setSortAscending(boolean sortAscending) {
        this.sortAscending = sortAscending;
    }
    
    public void generateCSV(ActionEvent event)	{
    	List reportBeans = this.getReportBeans();
    	List<List> csv = new ArrayList<List>();
    	
   		for(ClassComparisonReportBean ccrb : (List<ClassComparisonReportBean>)reportBeans){
   			if(csv.size()==0){
   				csv.add(ccrb.getRowLabels());
   			}
   			csv.add(ccrb.getRow());
		}
		
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
		try {
			CSVUtil.renderCSV(response, csv);
		} catch (Exception e) {
			// TODO: handle exception
		} finally	{
			FacesContext.getCurrentInstance().responseComplete();
		}

    }

}
