package gov.nih.nci.eagle.web.reports;

import gov.nih.nci.caintegrator.domain.epidemiology.bean.EpidemiologicalStudyParticipant;
import gov.nih.nci.caintegrator.domain.epidemiology.bean.TobaccoConsumption;
import gov.nih.nci.caintegrator.domain.study.bean.StudyParticipant;

import java.util.List;

public class EpiReportBean implements ReportBean {

    private StudyParticipant studyParticipant;
    
    public EpiReportBean(StudyParticipant participant) {
        this.studyParticipant = participant;
    }
    
    public String getPatientId() {
        return studyParticipant.getStudySubjectIdentifier();
    }
    
    public String getGender() {
        return studyParticipant.getAdministrativeGenderCode();
    }
    
    public String getSmokingStatus() {
        if(((EpidemiologicalStudyParticipant)studyParticipant).getTobaccoConsumptionCollection() != null && 
                ((EpidemiologicalStudyParticipant)studyParticipant).getTobaccoConsumptionCollection().size() > 0) {
            return ((TobaccoConsumption)((EpidemiologicalStudyParticipant)studyParticipant).getTobaccoConsumptionCollection().iterator().next()).getSmokingStatus();
        }
        return "N/A";
    }
    public List getRow() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
    }

    public List getRowLabels() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
    }

}
