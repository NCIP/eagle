package gov.nih.nci.eagle.query.dto;

import gov.nih.nci.caintegrator.application.dtobuilder.QueryDTOBuilder;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.studyQueryService.dto.epi.*;
import gov.nih.nci.eagle.web.struts.EpiForm;
import org.apache.log4j.Logger;


import java.util.*;


/**

 * User: Ram Bhattaru
 * Date: Apr 16, 2007
 * Time: 11:31:50 AM

 */
public class EPIQueryDTOBuilder implements QueryDTOBuilder {
    private static Logger logger = Logger.getLogger(EPIQueryDTOBuilder.class);
    
    public EPIQueryDTOBuilder() {}

    public QueryDTO buildQueryDTO(Object form, String cacheId) {
        EpiForm epiForm = (EpiForm) form;            // test

        EPIQueryDTO  epiQueryDTO = new EPIQueryDTO();

        if(epiForm.getQueryName() != null)
            epiQueryDTO.setQueryName(epiForm.getQueryName());

        // this contains most of StudyPart and Lifestyle
        PatientCharacteristicsCriterion patCharacterCrit = new PatientCharacteristicsCriterion();
        populatePatientCharactersticsCrit(epiForm, patCharacterCrit);
        epiQueryDTO.setPatientCharacteristicsCriterion(patCharacterCrit);

//	this is only for depression and anxiety, fagerstrom was done seperately below (dont know why)        
//        BehavioralCriterion behaveCrit = new BehavioralCriterion();
//        populateBehaviorCrit(epiForm, behaveCrit);
//        epiQueryDTO.setBehavioralCriterion(behaveCrit);
		
        TobaccoConsumptionCriterion tobaccoCrit = new TobaccoConsumptionCriterion();
        populateTobaccoCrit(epiForm, tobaccoCrit);
        epiQueryDTO.setTobaccoConsumptionCriterion(tobaccoCrit);

        BehavioralCriterion behaviorCrit = new BehavioralCriterion();
        if (epiForm.getFagerstromScore() != null && epiForm.getFagerstromScore().length()>0)
            behaviorCrit.setFagerstromScore(Integer.parseInt(epiForm.getFagerstromScore()));
        epiQueryDTO.setBehavioralCriterion(behaviorCrit);

        EnvironmentalTobaccoSmokeCriterion environCrit = new EnvironmentalTobaccoSmokeCriterion();
        pupulateEnvironmentalTobaccoCrit(epiForm, environCrit);
        epiQueryDTO.setEnvironmentalTobaccoSmokeCriterion(environCrit);

        FamilyHistoryCriterion familyHistCrit = new FamilyHistoryCriterion();
        populateFamilyHistoryCrit(epiForm, familyHistCrit);
        epiQueryDTO.setFamilyHistoryCriterion(familyHistCrit);

        //Populate the patient group
        UserListBeanHelper ulbh = new UserListBeanHelper(cacheId);

        String myGroupName = epiForm.getPatientGroup();
        List myGroupValues = ulbh.getItemsFromList(myGroupName);
        epiQueryDTO.setPatientIds(myGroupValues);

        return epiQueryDTO;
    }

    private void populateFamilyHistoryCrit(EpiForm epiForm, FamilyHistoryCriterion familyHistCrit) {

        /*  1. handle Smoking Relatives */
        String[] smokingRelatives = epiForm.getRelativesWhoSmoked();
        if (smokingRelatives != null && smokingRelatives.length > 0) {
            final Collection<Relative> smokingRelativeCol = new ArrayList<Relative>();
            for (int i = 0; i < smokingRelatives.length; i++) {
                Relative  smokingRelative = Enum.valueOf(Relative.class, smokingRelatives[i]);
                smokingRelativeCol.add(smokingRelative);
            }
            familyHistCrit.setSmokingRelativeCollection(smokingRelativeCol);
        }

        /*  2. handle Smoking Relatives */
        String[] lungCancerRelatives = epiForm.getRelativesWithCancer();
        if (lungCancerRelatives != null && lungCancerRelatives.length > 0) {
            final Collection<Relative> lungCancerRelativeCol = new ArrayList<Relative>();
            for (int i = 0; i < lungCancerRelatives.length; i++) {
                Relative  lungCancerRelative = Enum.valueOf(Relative.class, lungCancerRelatives[i]);
                lungCancerRelativeCol.add(lungCancerRelative);
            }
            familyHistCrit.setLungCancerRelativeCollection(lungCancerRelativeCol);
        }

    }


    private void pupulateEnvironmentalTobaccoCrit(EpiForm epiForm, EnvironmentalTobaccoSmokeCriterion environCrit) {
        prepareAndSetSmokingAreaExposureCrit(epiForm, environCrit);
        prepareAndSetOccupationalExposureCrit(epiForm, environCrit);
        prepareAndSetLicingCompanionExposureCrit(epiForm, environCrit);
    }

    private void prepareAndSetSmokingAreaExposureCrit(EpiForm epiForm, EnvironmentalTobaccoSmokeCriterion environCrit) {
        if (epiForm.getSmokingAreas() != null && epiForm.getSmokingAreas().length > 0) {
            String[] areas = epiForm.getSmokingAreas();
            List<SmokingExposure> exposures = new ArrayList<SmokingExposure>();
            for (String area : areas) exposures.add(Enum.valueOf(SmokingExposure.class, area));
            environCrit.setSmokingExposureCollection(exposures);
        }
    }

    private void prepareAndSetOccupationalExposureCrit(EpiForm epiForm, EnvironmentalTobaccoSmokeCriterion environCrit) {
         Map exposureMap = epiForm.getJobsMap();
         Set exposureLevels = exposureMap.keySet();
         Collection<OccupationalExposure>  exposureObjs = new ArrayList<OccupationalExposure>();
         for (Iterator iterator = exposureLevels.iterator(); iterator.hasNext();) {
             ExposureLevel expLevel = Enum.valueOf(ExposureLevel.class, (String)iterator.next());
             OccupationalExposure exposureObj = new OccupationalExposure();
             exposureObj.setSmokingExposure(expLevel);
             if (exposureMap.get(expLevel.toString()) != null) {
                String value = (String)exposureMap.get(expLevel.toString());
                if (value.trim().length() > 0) {
                    exposureObj.setYearsExposed(Float.parseFloat(value.trim()));
                    exposureObjs.add(exposureObj);
                }
             }
         }
         environCrit.setOccupationalExposureCollection(exposureObjs);
     }

    private void prepareAndSetLicingCompanionExposureCrit(EpiForm epiForm, EnvironmentalTobaccoSmokeCriterion environCrit) {
         Map exposureMap = epiForm.getLivingCompanionsMap();
         Set tobaccoTypes = exposureMap.keySet();
         Map<TobaccoType, LivingCompanionExposure>  exposureObjs = new HashMap<TobaccoType,LivingCompanionExposure>();
         for (Iterator iterator = tobaccoTypes.iterator(); iterator.hasNext();) {
             String key = (String)iterator.next();
             int endIndex = key.indexOf('_');
             String enumString = key.substring(0, endIndex);
             TobaccoType tobaccoType = Enum.valueOf(TobaccoType.class, enumString);
             LivingCompanionExposure exposureObj = exposureObjs.get(tobaccoType);
             if (exposureObj == null) {
                 exposureObj = new LivingCompanionExposure(); // create a new one
                 exposureObjs.put(tobaccoType, exposureObj);
             }
             exposureObj.setTobaccoType(tobaccoType);
             if(exposureMap.get(tobaccoType.toString() + "_yrs") != null) {
                String tobaccoYears = (String)exposureMap.get(tobaccoType.toString() + "_yrs");
                if (tobaccoYears != null && tobaccoYears.trim().length() > 0)
                    exposureObj.setYearsExposued(Float.parseFloat(tobaccoYears.trim()));
             }
             if(exposureMap.get(tobaccoType.toString() + "_hrs") != null) {
                String tobaccoHoursPerDay = (String)exposureMap.get(tobaccoType.toString() + "_hrs");
                if (tobaccoHoursPerDay != null && tobaccoHoursPerDay.trim().length() > 0)
                    exposureObj.setHoursPerDayExposed(Float.parseFloat(tobaccoHoursPerDay.trim()));
             }

         }
        Collection<LivingCompanionExposure> allExposureObjs = exposureObjs.values();
        Collection <LivingCompanionExposure> results = new ArrayList<LivingCompanionExposure>();
        for (Iterator<LivingCompanionExposure> iterator = allExposureObjs.iterator(); iterator.hasNext();) {
            LivingCompanionExposure exposureObj =  iterator.next();
            if (exposureObj.getHoursPerDayExposed() != null || exposureObj.getYearsExposued() != null)
                results.add(exposureObj);
        }

        environCrit.setLivingCompanionExposureCollection(results);
     }


    private void populateTobaccoCrit(EpiForm epiForm, TobaccoConsumptionCriterion tobaccoCrit) {

        if (epiForm.getAgeAtInitiationLower() != null && epiForm.getAgeAtInitiationLower().trim().length() > 0)
            tobaccoCrit.setAgeAtInitiationLower(Integer.parseInt(epiForm.getAgeAtInitiationLower()));
        if (epiForm.getAgeAtInitiationUpper() != null && epiForm.getAgeAtInitiationUpper().trim().length() > 0)
            tobaccoCrit.setAgeAtInitiationUpper(Integer.parseInt(epiForm.getAgeAtInitiationUpper()));

        if (epiForm.getDurationLower() != null && epiForm.getDurationLower().trim().length() > 0)
            tobaccoCrit.setDurationLower(Integer.parseInt(epiForm.getDurationLower()));
        if (epiForm.getDurationUpper() != null && epiForm.getDurationUpper().trim().length() > 0)
            tobaccoCrit.setDurationUpper(Integer.parseInt(epiForm.getDurationUpper()));


        if (epiForm.getIntensityLower() != null && epiForm.getIntensityLower().trim().length() > 0)
            tobaccoCrit.setIntensityLower(Double.parseDouble(epiForm.getIntensityLower()));
        if (epiForm.getIntensityUpper() != null && epiForm.getIntensityUpper().trim().length() > 0)
            tobaccoCrit.setIntensityUpper(Double.parseDouble(epiForm.getIntensityUpper()));


        if (epiForm.getSmokingStatus() != null)	{
            try {
				tobaccoCrit.setSmokingStatus(SmokingStatus.valueOf(epiForm.getSmokingStatus()));
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
        }
        
        /*  default it to CIGARETTE for this release */
        tobaccoCrit.setTobaccoType(TobaccoType.CIGARETTE);
        if (epiForm.getYearsSinceQuittingLower() != null && epiForm.getYearsSinceQuittingLower().trim().length() > 0)
            tobaccoCrit.setYearsSinceQuittingLower(Integer.parseInt(epiForm.getYearsSinceQuittingLower()));
        if (epiForm.getYearsSinceQuittingUpper() != null && epiForm.getYearsSinceQuittingUpper().trim().length() > 0)
            tobaccoCrit.setYearsSinceQuittingUpper(Integer.parseInt(epiForm.getYearsSinceQuittingUpper()));

    }

    private void populateBehaviorCrit(EpiForm epiForm, BehavioralCriterion behaveCrit) {
        if (epiForm.getAnxietyScore() != null)
            behaveCrit.setAnxietyScore(Integer.parseInt(epiForm.getAnxietyScore()));

        if (epiForm.getDepressionScore() != null)
            behaveCrit.setDepressionScore(Integer.parseInt(epiForm.getDepressionScore()));
    }

    private void populatePatientCharactersticsCrit(EpiForm epiForm, PatientCharacteristicsCriterion patCharacterCrit) {
        populateAgeCriteria(epiForm, patCharacterCrit);
        populateEducationLevelCrit(epiForm, patCharacterCrit);

        if(epiForm.getHeightLower() != null && epiForm.getHeightLower().trim().length() > 0)
            patCharacterCrit.setHeightLowerLimit(Double.parseDouble(epiForm.getHeightLower()));
        if(epiForm.getHeightUpper() != null && epiForm.getHeightUpper().trim().length() > 0)
            patCharacterCrit.setHeightUpperLimit(Double.parseDouble(epiForm.getHeightUpper()));

        if (epiForm.getMaritalStatus() != null)	{
            try {
				patCharacterCrit.setMaritalStatus(MaritalStatus.valueOf(epiForm.getMaritalStatus()));
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}            
        }

        if(epiForm.getGender() != null)	{
            try {
				patCharacterCrit.setSelfReportedgender(Gender.valueOf(epiForm.getGender()));
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
        }

//	dont have this data now
//        if (epiForm.getSocioEconomicStatus() != null)
//            patCharacterCrit.setSocioEconomicStatus(Enum.valueOf(SocioEconomicStatus.class, epiForm.getSocioEconomicStatus()));
//
//        if(epiForm.getWaistLower() != null && epiForm.getWaistLower().trim().length() > 0 )
//            patCharacterCrit.setWaistLowerLimit(Double.parseDouble(epiForm.getWaistLower()));
//        if(epiForm.getWaistUpper() != null && epiForm.getWaistUpper().trim().length() > 0)
//            patCharacterCrit.setWaisUpperLimit(Double.parseDouble(epiForm.getWaistUpper()));

        if(epiForm.getWeightLower() != null && epiForm.getWeightLower().trim().length() > 0)
            patCharacterCrit.setWeightLowerLimit(Double.parseDouble(epiForm.getWeightLower()));
        if(epiForm.getWeightUpper() != null && epiForm.getWeightUpper().trim().length() > 0)
            patCharacterCrit.setWeightUpperLimit(Double.parseDouble(epiForm.getWeightUpper()));

        if(epiForm.getResidentialArea() != null && epiForm.getResidentialArea().length() > 0)	{
            try {
				patCharacterCrit.setResidentialArea(ResidentialArea.valueOf(epiForm.getResidentialArea() ));
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
        }
        
        if(epiForm.getReligion() != null && epiForm.getReligion().length() > 0)
			try {
				patCharacterCrit.setReligion(Religion.valueOf(epiForm.getReligion() ));
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}

    }

    private void populateEducationLevelCrit(EpiForm epiForm, PatientCharacteristicsCriterion patCharacterCrit) {
        if (epiForm.getEducationLevel() != null) {
            //EducationLevel eduLevel = Enum.valueOf(EducationLevel.class, epiForm.getEducationLevel());
            EducationLevel eduLevel;
			try {
				eduLevel = EducationLevel.valueOf(epiForm.getEducationLevel());
				patCharacterCrit.setEducationLevel(eduLevel);
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
        }
    }

    private void populateAgeCriteria(EpiForm epiForm, PatientCharacteristicsCriterion patCharacterCrit) {
        if (epiForm.getAgeLower() != null && epiForm.getAgeLower().trim().length() > 0) {
            Double lowerLimit = Double.parseDouble(epiForm.getAgeLower());
            patCharacterCrit.setAgeLowerLimit(lowerLimit);
        }

        if (epiForm.getAgeUpper() != null && epiForm.getAgeUpper().trim().length() > 0) {
            Double upperLimit = Double.parseDouble(epiForm.getAgeUpper());
            patCharacterCrit.setAgeUpperLimit(upperLimit);
        }
    }
}
