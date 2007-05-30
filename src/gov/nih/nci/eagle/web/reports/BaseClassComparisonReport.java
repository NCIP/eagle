package gov.nih.nci.eagle.web.reports;

import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;
import gov.nih.nci.caintegrator.service.findings.ReporterBasedFinding;
import gov.nih.nci.caintegrator.studyQueryService.dto.epi.EPIQueryDTO;
import gov.nih.nci.eagle.query.dto.ClassComparisonQueryDTOImpl;
import gov.nih.nci.eagle.util.PatientGroupManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;

public abstract class BaseClassComparisonReport extends SortableReport {


    protected PatientGroupManager patientManager;
    protected EpiReport epiReport;
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

        EPIQueryDTO dto = new EPIQueryDTO();
        dto.setQueryName(val);
        dto.setPatientGroupName(val);
        dto.setPatientIds(itemsFromList);
        Collection patientInfo = null;
        try {
            patientInfo = findingsManager.getFindings(dto);
        } catch (FindingsQueryException e) {
            e.printStackTrace();
        }
        epiReport.setPatientData(patientInfo);
        epiReport.setQueryDTO(dto);
        return "epiReport";
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

    public EpiReport getEpiReport() {
        return epiReport;
    }

    public void setEpiReport(EpiReport epiReport) {
        this.epiReport = epiReport;
    }
}
