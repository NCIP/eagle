package gov.nih.nci.eagle.service.handlers;

import gov.nih.nci.caintegrator.domain.epidemiology.bean.EpidemiologicalStudyParticipant;
import gov.nih.nci.caintegrator.domain.epidemiology.bean.Lifestyle;
import gov.nih.nci.caintegrator.domain.epidemiology.bean.TobaccoConsumption;
import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.studyQueryService.QueryHandler;
import gov.nih.nci.caintegrator.studyQueryService.dto.epi.*;
import gov.nih.nci.caintegrator.util.HQLHelper;

import java.util.List;
import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;
import java.text.MessageFormat;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Query;
import org.hibernate.dialect.Dialect;
import org.hibernate.sql.QuerySelect;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Expression;

public class EpidemiologicalQueryHandler implements QueryHandler {

    private SessionFactory sessionFactory;
    private static final String TARGET_FINDING_ALIAS = " finding";

    public Integer getResultCount(QueryDTO query) {
        throw new UnsupportedOperationException();
    }

    public List getResults(QueryDTO dto, Integer page) {
        throw new UnsupportedOperationException();
    }

    public List getResults(QueryDTO queryDTO) {

        EPIQueryDTO epiQueryDTO = (EPIQueryDTO) queryDTO;
        Session session = sessionFactory.getCurrentSession();
        Criteria targetCrit = session.createCriteria(EpidemiologicalStudyParticipant.class);

        /* 1.  Handle  PatientCharacteristics Criterion   */
        PatientCharacteristicsCriterion patCharacterCrit = epiQueryDTO.getPatientCharacteristicsCriterion();
        if (patCharacterCrit != null) populatePatientCharacteristicsCriterion(patCharacterCrit, targetCrit);

        /* 2. Handle Tobacco Dependency  Criterion */
        BehavioralCriterion behaviorCrit = epiQueryDTO.getBehavioralCriterion();
        if (behaviorCrit != null) populateBehaviorCriterion(behaviorCrit, targetCrit);

        /* Handle Tobacco Consumption Criterion */
        TobaccoConsumptionCriterion tobaccoCrit = epiQueryDTO.getTobaccoConsumptionCriterion();
        if (tobaccoCrit != null) populateTobaccoConsumptionCrit(tobaccoCrit, targetCrit);

        FamilyHistoryCriterion familyHistcrit = epiQueryDTO.getFamilyHistoryCriterion();
        if (familyHistcrit != null) populateFamilyHistoryCrit(familyHistcrit, targetCrit);

        EnvironmentalTobaccoSmokeCriterion envCrit = epiQueryDTO.getEnvironmentalTobaccoSmokeCriterion();
        if (envCrit != null) {
           Collection<OccupationalExposure> ocExp = envCrit.getOccupationalExposureCollection();
            for (Iterator<OccupationalExposure> iterator = ocExp.iterator(); iterator.hasNext();) {
                OccupationalExposure occupationalExposure =  iterator.next();
                
            } 
        
        }

        targetCrit.addOrder(Order.asc("id"));
        List<EpidemiologicalStudyParticipant> l = targetCrit.list();

        initializeProxies(l);
        return l;
    }

    private void populateFamilyHistoryCrit(FamilyHistoryCriterion familyHistcrit, Criteria targetCrit) {
        Collection<Relative> lungCancerrelativeCrit = familyHistcrit.getLungCancerRelativeCollection();
        if (lungCancerrelativeCrit != null) {
            targetCrit.createAlias("lungCancerRelativeCollection", "lcr");
            targetCrit.add(Restrictions.in("lungCancerRelativeCollection", lungCancerrelativeCrit));
        }
        Collection<Relative> smokingRelativeCrit = familyHistcrit.getLungCancerRelativeCollection();
        if (smokingRelativeCrit != null) {
            targetCrit.createAlias("smokingRelativeCollection", "srCol");
            targetCrit.add(Restrictions.in("smokingRelativeCollection", smokingRelativeCrit ));
        }
    }

    private void initializeProxies(List<EpidemiologicalStudyParticipant> l) {

        /*  TODO:  Fix this so that it does not issue multiple SQL statements one for each finding */
        for (int i = 0; i < l.size(); i++) {
           EpidemiologicalStudyParticipant epidemiologicalStudyParticipant =  l.get(i);
           epidemiologicalStudyParticipant.getTobaccoConsumptionCollection().size();
           epidemiologicalStudyParticipant.getBehavioralAssessment();
          /*
           epidemiologicalStudyParticipant.getLungCancerRelativeCollection().size();
           epidemiologicalStudyParticipant.getSmokingRelativeCollection().size();
           */
           epidemiologicalStudyParticipant.getDietaryConsumptionCollection().size();
           epidemiologicalStudyParticipant.getEnvironmentalFactorCollection().size();
        }
    }

    private void populateTobaccoConsumptionCrit(TobaccoConsumptionCriterion tobaccoCrit, Criteria targetCrit) {
        targetCrit.createAlias("tobaccoConsumptionCollection", "tc");

        if (tobaccoCrit != null) {
            Double lowerIntensity = tobaccoCrit.getIntensityLower();
            Double upperIntensity = tobaccoCrit.getIntensityUpper();
            if (lowerIntensity != null && upperIntensity != null) {
                assert(upperIntensity.compareTo(lowerIntensity) > 0 );
                targetCrit.add(Restrictions.between("tc.intensity", lowerIntensity, upperIntensity));
            }
            Integer durationLower = tobaccoCrit.getDurationLower();
            Integer durationUpper = tobaccoCrit.getDurationUpper();
            if (durationLower != null && durationUpper != null) {
                assert(durationUpper.compareTo(durationUpper) > 0 );
                targetCrit.add(Restrictions.between("tc.duration", durationLower, durationUpper));
            }
            Integer ageLower = tobaccoCrit.getAgeAtInitiationLower();
            Integer ageUpper = tobaccoCrit.getAgeAtInitiationUpper();
            if (ageLower != null && ageUpper != null) {
                assert(ageUpper.compareTo(ageLower) > 0 );
                targetCrit.add(Restrictions.between("tc.ageAtInitiation", ageLower, ageUpper));
            }

        }
    }

    private void populateBehaviorCriterion(BehavioralCriterion behaviorCrit, Criteria targetCrit) {
            targetCrit.createAlias("behavioralAssessment", "ba");
            Integer fScore = behaviorCrit.getFagerstromScore();
            if (fScore != null)
                targetCrit.add(Expression.eq("ba.fagerstromScore", fScore));

            Integer dScore = behaviorCrit.getDepressionScore();
            if (dScore != null)
                targetCrit.add(Expression.eq("ba.depressionScore", dScore));

            Integer aScore = behaviorCrit.getAnxietyScore();
            if (aScore != null)
                targetCrit.add(Expression.eq("ba.anxietyScore", aScore));
    }

    private void populatePatientCharacteristicsCriterion(PatientCharacteristicsCriterion patCharacterCrit,
                                                         Criteria targetCrit) {
        assert(patCharacterCrit != null) ;
        pupulatePatientAttributesCriterion(patCharacterCrit, targetCrit);
        populateLifeStyleCriterion(targetCrit, patCharacterCrit);
    }

    private void pupulatePatientAttributesCriterion(PatientCharacteristicsCriterion patCharacterCrit, Criteria targetCrit) {
        Double lowerAgeLimit = patCharacterCrit.getAgeLowerLimit();
        Double upperAgeLimit = patCharacterCrit.getAgeUpperLimit();
        if ((lowerAgeLimit != null && lowerAgeLimit != 0) && (upperAgeLimit != null && upperAgeLimit != 0) )
        targetCrit.add(Restrictions.between("ageAtDiagnosis.absoluteValue", lowerAgeLimit, upperAgeLimit));

        Gender gender = patCharacterCrit.getSelfReportedgender();
        if (gender != null) targetCrit.add(Restrictions.eq("administrativeGenderCode", gender.getValue()));

        Double lowerWtLimit = patCharacterCrit.getWeightLowerLimit();
        Double upperWtLimit = patCharacterCrit.getWeightUpperLimit();
        if ((lowerWtLimit != null && lowerWtLimit != 0) && (upperWtLimit != null && upperWtLimit != 0) )
        targetCrit.add(Restrictions.between("weight", lowerWtLimit, upperWtLimit));

        Double lowerHtLimit = patCharacterCrit.getHeightLowerLimit();
        Double upperHtLimit = patCharacterCrit.getHeightUpperLimit();
        if ((lowerHtLimit != null && lowerHtLimit != 0) && (upperHtLimit != null && upperHtLimit != 0) )
        targetCrit.add(Restrictions.between("height", lowerHtLimit, upperHtLimit));

        Double lowerWaistLimit = patCharacterCrit.getWaistLowerLimit();
        Double upperWaistLimit = patCharacterCrit.getWaisUpperLimit();
        if ((lowerWaistLimit != null && lowerWaistLimit != 0) && (upperWaistLimit != null && upperWaistLimit != 0) )
        targetCrit.add(Restrictions.between("waistCircumference", lowerWaistLimit, upperWaistLimit));
    }

    private void populateLifeStyleCriterion(Criteria targetCrit, PatientCharacteristicsCriterion patCharacterCrit) {
        targetCrit.createAlias("lifestyle", "ls");

        MaritalStatus mStatus = patCharacterCrit.getMaritalStatus();
        if (mStatus != null) targetCrit.add(Expression.eq("ls.maritalStatus", mStatus.getValue()));

        Religion religion = patCharacterCrit.getReligion();
        if (religion != null) targetCrit.add(Expression.eq("ls.religion", religion.getValue()));

        ResidentialArea ra = patCharacterCrit.getResidentialArea();
        if (ra != null) targetCrit.add(Expression.eq("ls.residentialArea", ra.getValue()));

        EducationLevel el = patCharacterCrit.getEducationLevel();
        if (el != null) targetCrit.add(Expression.eq("ls.educationLevel", el.getValue()));
    }

    public boolean canHandle(QueryDTO query) {
        return (query instanceof EPIQueryDTO);
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFacotry) {
        this.sessionFactory = sessionFacotry;
    }
}
