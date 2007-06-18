package gov.nih.nci.eagle.web.reports;

import gov.nih.nci.caintegrator.domain.epidemiology.bean.BehavioralAssessment;
import gov.nih.nci.caintegrator.domain.epidemiology.bean.EnvironmentalFactor;
import gov.nih.nci.caintegrator.domain.epidemiology.bean.Lifestyle;
import gov.nih.nci.caintegrator.domain.epidemiology.bean.Relative;
import gov.nih.nci.caintegrator.domain.epidemiology.bean.TobaccoConsumption;
import gov.nih.nci.caintegrator.domain.study.bean.Histology;
import gov.nih.nci.caintegrator.domain.study.bean.StudyParticipant;
import gov.nih.nci.caintegrator.studyQueryService.dto.epi.EducationLevel;
import gov.nih.nci.caintegrator.studyQueryService.dto.epi.MaritalStatus;
import gov.nih.nci.caintegrator.studyQueryService.dto.epi.RelativeLungCancer;
import gov.nih.nci.caintegrator.studyQueryService.dto.epi.Religion;
import gov.nih.nci.caintegrator.studyQueryService.dto.epi.SmokingStatus;
import gov.nih.nci.eagle.util.IntegerEnumResolver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public class EpiReportBean implements ReportBean {

    private StudyParticipant studyParticipant;
    private Lifestyle lifestyle;
    private TobaccoConsumption cigaretteConsumption;
    private BehavioralAssessment behavioralAssessment;
    private Histology histology;
    private boolean selected;
    private List availableSpecimens;

    public EpiReportBean(StudyParticipant participant) {
        this.studyParticipant = participant;
        this.lifestyle = participant.getEpidemiologicalFinding().getLifestyle();
        this.behavioralAssessment = participant.getEpidemiologicalFinding().getBehavioralAssessment();
        this.histology = participant.getHistology();
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
    
    public String getRelativesWhoSmoked()	{
    	String rel = "";
    	List<String> extypes = new ArrayList<String>();
    	for(Relative r : studyParticipant.getEpidemiologicalFinding().getRelativeCollection())	{
    		if(r.getSmokingStatus().equals("1"))	{
    			extypes.add(r.getRelationshipType());
    		}
    	}
    	
       	Map<String, Integer> m = CollectionUtils.getCardinalityMap(extypes);
    	for(String s : m.keySet()){
    		rel += " " + s;
    		if(m.get(s) > 1)	{
    			rel+= "(" + m.get(s) + ") ";
    		}
    	}
//    	rel = StringUtils.join(extypes.toArray(), " ");
    	
    	return (!rel.equals("")) ? rel : "-";
    }
    
    public String getEtsExposure()	{
    	String ets = "";
    	List<String> extypes = new ArrayList<String>();
    	for(EnvironmentalFactor ef : studyParticipant.getEpidemiologicalFinding().getEnvironmentalFactorCollection())	{
    		extypes.add(ef.getExposureType());
    	} 

//		int hit = StringUtils.countMatches(ets, ef.getExposureType());
    	Map<String, Integer> m = CollectionUtils.getCardinalityMap(extypes);
    	for(String s : m.keySet()){
    		
    		ets += " " + s;
    		if(m.get(s) > 1)	{
    			ets+= "(" + m.get(s) + ") ";
    		}
    	}
    	return (!ets.equals("")) ? ets : "-";
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
    
    public String getStage() {
        if(histology != null && histology.getStage() != null) {
            return histology.getStage();
        }
        return "N/A";
    }
    
    public String getResidentialArea() {
        if(lifestyle != null && lifestyle.getResidentialArea() != null) {
             return lifestyle.getResidentialArea();
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

	public List getAvailableSpecimens() {
		return availableSpecimens;
	}

	public void setAvailableSpecimens(List availableSpecimens) {
		this.availableSpecimens = availableSpecimens;
	}
    
    public String getRelativeWithLungCancer() {
        RelativeLungCancer cancer = IntegerEnumResolver.resolveEnum(RelativeLungCancer.class, studyParticipant.getEpidemiologicalFinding().getRelativeWithLungCancer());
        if(cancer != null) {
            return cancer.getName();
        }
        return "N/A";
        
    }


}
