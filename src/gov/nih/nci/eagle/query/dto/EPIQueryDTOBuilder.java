package gov.nih.nci.eagle.query.dto;

import gov.nih.nci.caintegrator.application.dtobuilder.QueryDTOBuilder;
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

        PatientCharacteristicsCriterion patCharacterCrit = new PatientCharacteristicsCriterion();
        populatePatientCharactersticsCrit(epiForm, patCharacterCrit);
        epiQueryDTO.setPatientCharacteristicsCriterion(patCharacterCrit);

        BehavioralCriterion behaveCrit = new BehavioralCriterion();
        populateBehaviorCrit(epiForm, behaveCrit);
        epiQueryDTO.setBehavioralCriterion(behaveCrit);

        TobaccoConsumptionCriterion tobaccoCrit = new TobaccoConsumptionCriterion();
        populateTobaccoCrit(epiForm, tobaccoCrit);
        epiQueryDTO.setTobaccoConsumptionCriterion(tobaccoCrit);

        TobaccoDependencyCriterion tobaccoDependCrit = new TobaccoDependencyCriterion();
        if (epiForm.getFagerstromScore() != null)
            tobaccoDependCrit.setFagerstromScore(Integer.parseInt(epiForm.getFagerstromScore()));
        epiQueryDTO.setTobaccoDependencyCriterion(tobaccoDependCrit);

        EnvironmentalTobaccoSmokeCriterion environCrit = new EnvironmentalTobaccoSmokeCriterion();
        pupulateEnvironmentalTobaccoCrit(epiForm, environCrit);
        epiQueryDTO.setEnvironmentalTobaccoSmokeCriterion(environCrit);

        FamilyHistoryCriterion familyHistCrit = new FamilyHistoryCriterion();
        populateFamilyHistoryCrit(epiForm, familyHistCrit);
        epiQueryDTO.setFamilyHistoryCriterion(familyHistCrit);

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
            tobaccoCrit.setIntensityLower(Integer.parseInt(epiForm.getIntensityLower()));
        if (epiForm.getIntensityUpper() != null && epiForm.getIntensityUpper().trim().length() > 0)
            tobaccoCrit.setIntensityUpper(Integer.parseInt(epiForm.getIntensityUpper()));


        if (epiForm.getSmokingStatus() != null)
            tobaccoCrit.setSmokingStatus(Enum.valueOf(SmokingStatus.class, epiForm.getSmokingStatus()));

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
            patCharacterCrit.setHeightLowerLimit(Float.parseFloat(epiForm.getHeightLower()));
        if(epiForm.getHeightUpper() != null && epiForm.getHeightUpper().trim().length() > 0)
            patCharacterCrit.setHeightUpperLimit(Float.parseFloat(epiForm.getHeightUpper()));

        if (epiForm.getMaritalStatus() != null)
            patCharacterCrit.setMaritalStatus(Enum.valueOf(MaritalStatus.class, epiForm.getMaritalStatus()));

        if(epiForm.getGender() != null)
            patCharacterCrit.setSelfReportedgender(Enum.valueOf(Gender.class, epiForm.getGender()));

        if (epiForm.getSocioEconomicStatus() != null)
            patCharacterCrit.setSocioEconomicStatus(Enum.valueOf(SocioEconomicStatus.class, epiForm.getSocioEconomicStatus()));

        if(epiForm.getWaistLower() != null && epiForm.getWaistLower().trim().length() > 0 )
            patCharacterCrit.setWaistLowerLimit(Float.parseFloat(epiForm.getWaistLower()));
        if(epiForm.getWaistUpper() != null && epiForm.getWaistUpper().trim().length() > 0)
            patCharacterCrit.setWaisUpperLimit(Float.parseFloat(epiForm.getWaistUpper()));

        if(epiForm.getWeightLower() != null && epiForm.getWeightLower().trim().length() > 0)
            patCharacterCrit.setWeightLowerLimit(Float.parseFloat(epiForm.getWeightLower()));
        if(epiForm.getWeightUpper() != null && epiForm.getWeightUpper().trim().length() > 0)
            patCharacterCrit.setWeightUpperLimit(Float.parseFloat(epiForm.getWeightUpper()));

        if(epiForm.getResidentialArea() != null && epiForm.getResidentialArea().length() > 0)
            patCharacterCrit.setResidentialAreas(Enum.valueOf(ResidentialArea.class, epiForm.getResidentialArea() ));

        if(epiForm.getReligion() != null && epiForm.getReligion().length() > 0)
            patCharacterCrit.setReligion(Enum.valueOf(Religion.class, epiForm.getReligion() ));

    }

    private void populateEducationLevelCrit(EpiForm epiForm, PatientCharacteristicsCriterion patCharacterCrit) {
        if (epiForm.getEducationLevel() != null) {
            EducationLevel eduLevel = Enum.valueOf(EducationLevel.class, epiForm.getEducationLevel());
            patCharacterCrit.setEducationLevel(eduLevel);
        }
    }

    private void populateAgeCriteria(EpiForm epiForm, PatientCharacteristicsCriterion patCharacterCrit) {
        if (epiForm.getAgeLower() != null && epiForm.getAgeLower().trim().length() > 0) {
            Integer lowerLimit = Integer.parseInt(epiForm.getAgeLower());
            patCharacterCrit.setAgeLowerLimit(lowerLimit);
        }

        if (epiForm.getAgeUpper() != null && epiForm.getAgeUpper().trim().length() > 0) {
            Integer upperLimit = Integer.parseInt(epiForm.getAgeUpper());
            patCharacterCrit.setAgeUpperLimit(upperLimit);
        }
    }
}
