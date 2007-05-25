package gov.nih.nci.eagle.web.reports;

import gov.nih.nci.caintegrator.service.findings.ReporterBasedFinding;
import gov.nih.nci.caintegrator.service.task.Task;
import gov.nih.nci.caintegrator.service.task.TaskResult;
import gov.nih.nci.caintegrator.studyQueryService.FindingsManager;
import gov.nih.nci.eagle.query.dto.ClassComparisonQueryDTOImpl;
import gov.nih.nci.eagle.util.FieldBasedComparator;
import gov.nih.nci.eagle.util.PatientGroupManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;

public abstract class BaseClassComparisonReport extends SortableReport {


    protected PatientGroupManager patientManager;
    protected PatientGroupReport groupReport;
    protected Map<String, List> patientInfoMap;
    

    
    public ClassComparisonQueryDTOImpl getQueryDTO() {

        return (ClassComparisonQueryDTOImpl) taskResult.getTask().getQueryDTO();
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
        return ((ReporterBasedFinding)taskResult).getReporterAnnotationsMap();
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

    public Map<String, List> getPatientInfoMap() {
        return patientInfoMap;
    }

    public void setPatientInfoMap(Map<String, List> patientInfoMap) {
        this.patientInfoMap = patientInfoMap;
    }
}
