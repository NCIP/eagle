/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/eagle/LICENSE.txt for details.
 */

package gov.nih.nci.eagle.web.reports;

import gov.nih.nci.caintegrator.analysis.messaging.FTestResultEntry;
import gov.nih.nci.caintegrator.application.configuration.SpringContext;
import gov.nih.nci.caintegrator.domain.annotation.gene.bean.GeneBiomarker;
import gov.nih.nci.caintegrator.domain.annotation.gene.bean.GeneExprReporter;
import gov.nih.nci.caintegrator.service.findings.ClassComparisonFinding;
import gov.nih.nci.caintegrator.service.findings.FTestFinding;
import gov.nih.nci.caintegrator.service.findings.GeneralizedLinearModelFinding;
import gov.nih.nci.caintegrator.service.task.TaskResult;
import gov.nih.nci.eagle.util.FieldBasedComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;

public class FTestReport extends BaseClassComparisonReport {



    @PostConstruct
    protected void init() {

        TaskResult result = (TaskResult)findingsManager.getTaskResult(task);
        this.taskResult = (FTestFinding)result;
        sortAscending = true;
        sortComparator = new FieldBasedComparator("pvalue", sortAscending);
        sortedBy = "pvalue";
        reportBeans = new ArrayList<FTestReportBean>();

        for (FTestResultEntry entry : ((FTestFinding)result).getResultEntries()) {
            GeneBiomarker gene = null;
            Collection genes = ((GeneExprReporter) ((FTestFinding)taskResult).getReporterAnnotationsMap()
                    .get(entry.getReporterId())).getGeneBiomarkerCollection();
            if(genes != null && genes.size() > 0) {
                gene = (GeneBiomarker)genes.iterator().next();
            }
            String geneName = null;
            if(gene != null)
                geneName = gene.getHugoGeneSymbol();
            FTestReportBean bean = new FTestReportBean(entry, geneName);
            reportBeans.add(bean);
        }
        
//        patientInfoMap = new HashMap<String, List>();
//
//
//        for(String groupName : (List<String>)getComparisonGroups()) {
//            List patients = getQueryDTO().getComparisonGroupsMap().get(groupName);
//            List patientInfo = patientManager.getPatientInfo(patients);
//            patientInfoMap.put(groupName, patientInfo);
//        }
    }
    
    public void sortDataList(ActionEvent event) {
        String sortFieldAttribute = getAttribute(event, "sortField");
        //js doesnt like the [] notation, so had to use another Att with an underscore
        sortedBy = getAttribute(event, "sortedBy") != null ? getAttribute(event, "sortedBy") : sortFieldAttribute.replace("[", "_").replace("]", "");
        // Get and set sort field and sort order.
        // Get and set sort field and sort order.
        if (sortFieldAttribute != null
                && sortFieldAttribute.equals(sortComparator.getField())) {
            sortAscending = !sortAscending;
        } else {
            sortAscending = true;
        }
        if (sortFieldAttribute != null) {
            sortComparator = new FieldBasedComparator(sortFieldAttribute,
                    sortAscending);
        }

    }

    public int getNumberGroups() {
        return ((FTestFinding)taskResult).getGroupNames().size();
    }

    public Collection getGroupNames() {
        return ((FTestFinding)taskResult).getGroupNames();
    }
    
    @Override
    public void generateCSV(ActionEvent event)	{
    	Collection reportBeans = this.getReportBeans();
    	List<List> csv = new ArrayList<List>();
    	
   		for(FTestReportBean rb : (List<FTestReportBean>)reportBeans){
   			if(csv.size()==0){
   				List headers = rb.getRowLabels();
   				//since the columns are dynamic based on the # of groups, we need to overwrite some of the header with
   				//values from the Report, as the ReportBean doesnt have this info
   				for(int i=0; i<this.getGroupNames().size(); i++){
   					
   					try {
						headers.set(i+2, this.getGroupNames().toArray()[i].toString() + " group avg"); 
						//overwrite, with an offset of 2
					} catch (RuntimeException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
   				
   				}
   				csv.add(headers);
   			}
   			csv.add(rb.getRow());
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
