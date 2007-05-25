package gov.nih.nci.eagle.web.reports;

import gov.nih.nci.caintegrator.service.findings.ReporterBasedFinding;
import gov.nih.nci.caintegrator.service.task.Task;
import gov.nih.nci.caintegrator.service.task.TaskResult;
import gov.nih.nci.caintegrator.studyQueryService.FindingsManager;
import gov.nih.nci.eagle.query.dto.ClassComparisonQueryDTOImpl;
import gov.nih.nci.eagle.util.ClassComparisonComparator;
import gov.nih.nci.eagle.util.PatientGroupManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;

public abstract class BaseReport {

    protected TaskResult results;
    protected PatientGroupManager patientManager;
    protected Task task;
    protected PatientGroupReport groupReport;
    protected FindingsManager findingsManager;
    protected Map<String, List> patientInfoMap;

    protected String sortedBy;

    protected ClassComparisonComparator sortComparator;
    protected Boolean sortAscending;
    protected List reportBeans;
    
    protected abstract void init();
    
    public ClassComparisonQueryDTOImpl getQueryDTO() {

        return (ClassComparisonQueryDTOImpl) results.getTask().getQueryDTO();
    }
    
    public String displayGroup() {
        FacesContext context = FacesContext.getCurrentInstance();
        String val = (String) context.getExternalContext()
                .getRequestParameterMap().get("group");
        List<String> itemsFromList = null;
        if(getQueryDTO().getBaselineGroupMap() != null && getQueryDTO().getBaselineGroupMap().containsKey(val))
            itemsFromList = getQueryDTO().getBaselineGroupMap().get(val);
        else
            itemsFromList = getQueryDTO().getComparisonGroupsMap().get(val);

        List patientInfo = patientManager.getPatientInfo(itemsFromList);
        groupReport.setPatients(patientInfo);
        groupReport.setGroupName(val);
        return "groupReport";
    }
    
    public void sortDataList(ActionEvent event) {
        String sortFieldAttribute = getAttribute(event, "sortField");

        sortedBy = sortFieldAttribute;
        // Get and set sort field and sort order.
        if (sortFieldAttribute != null
                && sortFieldAttribute.equals(sortComparator.getField())) {
            sortAscending = !sortAscending;
        } else {
            sortAscending = true;
        }
        if (sortFieldAttribute != null) {
            sortComparator = new ClassComparisonComparator(sortFieldAttribute,
                    sortAscending);
        }

    }    
    
    public void generateCSV(ActionEvent event)  {
        List reportBeans = this.getReportBeans();
        List<List> csv = new ArrayList<List>();
        
        for(ReportBean ccrb : (List<ReportBean>)reportBeans){
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
        } finally   {
            FacesContext.getCurrentInstance().responseComplete();
        }

    }    

    public Map getAnnotationMap() {
        return ((ReporterBasedFinding)results).getReporterAnnotationsMap();
    }

    public List getReportBeans() {
        Collections.sort(reportBeans, sortComparator);
        return reportBeans;
    }
    
    public List getBaselineGroups() {
        if(getQueryDTO().getBaselineGroupMap() == null)
            return null;
        return new ArrayList(getQueryDTO().getBaselineGroupMap().keySet());
    }

    public List getComparisonGroups() {
        return new ArrayList(getQueryDTO().getComparisonGroupsMap().keySet());
    }    
    public PatientGroupReport getGroupReport() {
        return groupReport;
    }

    public void setGroupReport(PatientGroupReport groupReport) {
        this.groupReport = groupReport;
    }

    public PatientGroupManager getPatientManager() {
        return patientManager;
    }

    public void setPatientManager(PatientGroupManager patientManager) {
        this.patientManager = patientManager;
    }

    public TaskResult getResults() {
        return results;
    }

    public void setResults(TaskResult results) {
        this.results = results;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }    
    protected static String getAttribute(ActionEvent event, String name) {
        return (String) event.getComponent().getAttributes().get(name);
    }

    public boolean getSortAscending() {
        return sortAscending;
    }

    public void setSortAscending(boolean sortAscending) {
        this.sortAscending = sortAscending;
    }

    public FindingsManager getFindingsManager() {
        return findingsManager;
    }

    public void setFindingsManager(FindingsManager findingsManager) {
        this.findingsManager = findingsManager;
    }

    public Map<String, List> getPatientInfoMap() {
        return patientInfoMap;
    }

    public void setPatientInfoMap(Map<String, List> patientInfoMap) {
        this.patientInfoMap = patientInfoMap;
    }

    public ClassComparisonComparator getSortComparator() {
        return sortComparator;
    }

    public void setSortComparator(ClassComparisonComparator sortComparator) {
        this.sortComparator = sortComparator;
    }

    public String getSortedBy() {
        return sortedBy;
    }

    public void setSortedBy(String sortedBy) {
        this.sortedBy = sortedBy;
    }

    public void setSortAscending(Boolean sortAscending) {
        this.sortAscending = sortAscending;
    }

    public void setReportBeans(List reportBeans) {
        this.reportBeans = reportBeans;
    }    
}
