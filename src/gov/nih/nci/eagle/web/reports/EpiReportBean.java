package gov.nih.nci.eagle.web.reports;

import gov.nih.nci.caintegrator.domain.epidemiology.bean.BehavioralAssessment;
import gov.nih.nci.caintegrator.domain.epidemiology.bean.Lifestyle;
import gov.nih.nci.caintegrator.domain.epidemiology.bean.TobaccoConsumption;
import gov.nih.nci.caintegrator.domain.study.bean.StudyParticipant;
import gov.nih.nci.caintegrator.studyQueryService.dto.epi.EducationLevel;
import gov.nih.nci.caintegrator.studyQueryService.dto.epi.MaritalStatus;
import gov.nih.nci.caintegrator.studyQueryService.dto.epi.Religion;
import gov.nih.nci.caintegrator.studyQueryService.dto.epi.SmokingStatus;
import gov.nih.nci.eagle.util.IntegerEnumResolver;

import java.util.Collection;
import java.util.List;

public class EpiReportBean implements ReportBean {

    private StudyParticipant studyParticipant;
    private Lifestyle lifestyle;
    private TobaccoConsumption cigaretteConsumption;
    private BehavioralAssessment behavioralAssessment;
    private boolean selected;


    public EpiReportBean(StudyParticipant participant) {
        this.studyParticipant = participant;
        this.lifestyle = participant.getEpidemiologicalFinding().getLifestyle();
        this.behavioralAssessment = participant.getEpidemiologicalFinding().getBehavioralAssessment();
        Collection<TobaccoConsumption> tobaccoConsumptionCollection = participant.getEpidemiologicalFinding().getTobaccoConsumptionCollection();
        for(TobaccoConsumption c : tobaccoConsumptionCollection) {
            if("CIGT".equals(c.getTobaccoType())) {
                this.cigaretteConsumption = c;
            }
        } 
    }
    
    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }    

    public String getPatientId() {
        return studyParticipant.getStudySubjectIdentifier();
    }

    public String getGender() {
        return studyParticipant.getAdministrativeGenderCode();
    }

    public String getSmokingStatus() {
        if (cigaretteConsumption != null) {
            return IntegerEnumResolver.resolveEnum(
                    SmokingStatus.class,
                    cigaretteConsumption.getSmokingStatus()).getName();
        }
        return "N/A";
    }
    
    public String getCaseStatus() {
        return studyParticipant.getCaseControlStatus();
    }
    
    public String getMaritalStatus() {
        if(lifestyle != null) {
            MaritalStatus status = IntegerEnumResolver.resolveEnum(MaritalStatus.class, lifestyle.getMaritalStatus());
            if(status != null)
                return status.getName();
        }
        return "N/A";
    }
    
    public String getReligion() {
        if(lifestyle != null) {
            Religion religion = IntegerEnumResolver.resolveEnum(Religion.class, lifestyle.getReligion());
            if(religion != null)
                return religion.getName();
        }
        return "N/A";
    }
    
    public String getEducationLevel() {
        if(lifestyle != null) {
            EducationLevel ed = IntegerEnumResolver.resolveEnum(EducationLevel.class, lifestyle.getEducationLevel());
            if(ed != null)
                return ed.getName();
        }
        return "N/A";
    }
    
    public String getIntensity() {
        if (cigaretteConsumption != null && cigaretteConsumption.getIntensity() != null) {
            return cigaretteConsumption.getIntensity().toString();
        }
        return "N/A";
    }
    
    public String getDuration() {
        if (cigaretteConsumption != null && cigaretteConsumption.getDuration() != null) {
            return cigaretteConsumption.getDuration().toString();
        }
        return "N/A";
    }
    
    public String getAgeAtInitiation() {
        if (cigaretteConsumption != null && cigaretteConsumption.getAgeAtInitiation() != null) {
            return cigaretteConsumption.getAgeAtInitiation().toString();
        }
        return "N/A";
    }
    
    public String getYearsSinceQuitting() {
        if (cigaretteConsumption != null && cigaretteConsumption.getYearsSinceQuitting() != null) {
            return cigaretteConsumption.getYearsSinceQuitting().toString();
        }
        return "N/A";
    }
    
    public String getFagerstrom() {
        if (behavioralAssessment != null && behavioralAssessment.getFagerstromScore() != null) {
            return behavioralAssessment.getFagerstromScore().toString();
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
