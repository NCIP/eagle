package gov.nih.nci.eagle.web.reports;

import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.ajax.CommonListFunctions;
import gov.nih.nci.caintegrator.domain.study.bean.StudyParticipant;
import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.service.task.TaskResult;
import gov.nih.nci.eagle.util.EAGLEListValidator;
import gov.nih.nci.eagle.util.FieldBasedComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.naming.OperationNotSupportedException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class EpiReport extends SortableReport{
    
    private QueryDTO queryDTO;
    private String listName;
    private static Logger logger = Logger.getLogger(EpiReport.class);

    public EpiReport() {
        
    }
    
    @PostConstruct
    protected void init() {
       sortedBy = "patientId";
       sortAscending = true;

       if(task != null && reportBeans == null) {
           taskResult = (TaskResult)findingsManager.getTaskResult(task);
           this.queryDTO = task.getQueryDTO();
            reportBeans = new ArrayList();
            for(StudyParticipant p : (Collection<StudyParticipant>)taskResult.getTaskResults()) {
                reportBeans.add(new EpiReportBean(p));
            }
        }
        sortComparator = new FieldBasedComparator(sortedBy, sortAscending);
    }
    
    public void setPatientData(Collection patientData) {
        reportBeans = new ArrayList();
        if(patientData != null) {
            for(StudyParticipant p : (Collection<StudyParticipant>)patientData) {
                reportBeans.add(new EpiReportBean(p));
            }
        }
    }
    
    public Integer saveList(String name) {
        listName = name;
        logger.debug("Saving list: " + listName);
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        HttpServletResponse rp = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        List<String> patients = new ArrayList<String>();
        Integer patientsSaved = 0;
        for(EpiReportBean bean : (Collection<EpiReportBean>)reportBeans) {
            if(bean.getSelected()) {
                logger.debug("Patient is selected: " + bean.getPatientId());
                patients.add(bean.getPatientId());
                patientsSaved++;
            }
        }
        EAGLEListValidator validator = null;
        try {
            validator = new EAGLEListValidator(ListType.PatientDID,patients);
        } catch (OperationNotSupportedException e) {
            logger.error("problem creating list", e);
        }

        String status = CommonListFunctions.createGenericListWithSession(ListType.PatientDID,null,patients,listName,validator,session);
        logger.debug("Returned status: " + status);
        return patientsSaved;
    }

    public QueryDTO getQueryDTO() {
        return queryDTO;
    }

    public void setQueryDTO(QueryDTO queryDTO) {
        this.queryDTO = queryDTO;
    }
    public void selectPatient(String patientId, boolean selected) {
        logger.debug("Selecting patient: " + patientId );
        for(EpiReportBean bean : (Collection<EpiReportBean>)reportBeans) {
            if(bean.getPatientId().equals(patientId))
                bean.setSelected(selected);
        }
    }
    
    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }
    
    

}
