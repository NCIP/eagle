/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/eagle/LICENSE.txt for details.
 */

package gov.nih.nci.eagle.service.test;

import gov.nih.nci.caintegrator.domain.study.bean.StudyParticipant;
import gov.nih.nci.caintegrator.domain.epidemiology.bean.EpidemiologicalStudyParticipant;
import gov.nih.nci.caintegrator.domain.epidemiology.bean.Lifestyle;
import gov.nih.nci.caintegrator.domain.epidemiology.bean.BehavioralAssessment;
import gov.nih.nci.caintegrator.domain.epidemiology.bean.TobaccoConsumption;
import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;
import gov.nih.nci.caintegrator.service.task.Task;
import gov.nih.nci.caintegrator.service.task.TaskResult;
import gov.nih.nci.caintegrator.studyQueryService.FindingsManager;
import gov.nih.nci.caintegrator.studyQueryService.dto.epi.*;
import gov.nih.nci.caintegrator.test.BaseSpringTestCase;
import gov.nih.nci.eagle.query.dto.EagleClinicalQueryDTO;

import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;

public class FindingsManagerTest extends BaseSpringTestCase {
    FindingsManager fm;
    EPIQueryDTO epiQueryDTO;

    @Override
    public String[] getConfigFiles() {
        return new String[]{  "C:/Projects/eagle/test/applicationContext-junit.xml",
                              "C:/Projects/caintegrator-spec/src/applicationContext-services.xml",
                              "C:/Projects/eagle/WebRoot/WEB-INF/applicationContext-services.xml"
                              };
    }

    public void setUp() {
        fm = (FindingsManager) ctx.getBean("eagleFindingsManager");
        epiQueryDTO = new EPIQueryDTO();
        System.out.println("Done");
    }

    private void setUpPatientCharacteristicsCriterion () {
        PatientCharacteristicsCriterion patCharacterCrit;

        // set Age crit
        patCharacterCrit = new PatientCharacteristicsCriterion();
        patCharacterCrit.setAgeLowerLimit(40.0);
        patCharacterCrit.setAgeUpperLimit(57.0);    //75.86

        // set Gender code
        patCharacterCrit.setSelfReportedgender(Gender.FEMALE);

        //set Weight
        patCharacterCrit.setWeightLowerLimit(38.0);
        patCharacterCrit.setWeightUpperLimit(49.0);

        // set Height
        patCharacterCrit.setHeightLowerLimit(155.0 );
        patCharacterCrit.setHeightUpperLimit(165.0);

        // set WaistCircumference
        patCharacterCrit.setWaistLowerLimit(28.0);
        patCharacterCrit.setWaisUpperLimit(33.1 );

        // setLifeStyle Crit
    /*    patCharacterCrit.setMaritalStatus(MaritalStatus.MARRIED);
        patCharacterCrit.setReligion(Religion.PROTESTANT);
        patCharacterCrit.setResidentialArea(ResidentialArea.USA);
        patCharacterCrit.setEducationLevel(EducationLevel.POSTGRAD);
     */
        epiQueryDTO.setPatientCharacteristicsCriterion(patCharacterCrit);
    }

    private void setUpBehaviorCriterion() {
        BehavioralCriterion behaviorCrit = new BehavioralCriterion();
        behaviorCrit.setFagerstromScore(new Integer(5));
        //behaviorCrit.setAnxietyScore(new Integer(10));
        //behaviorCrit.setDepressionScore(new Integer(0));
        epiQueryDTO.setBehavioralCriterion(behaviorCrit);
    }

     private void setUpFamilyHistory() {

        FamilyHistoryCriterion fhCrit = new FamilyHistoryCriterion();

        /* set Lung Cancer Relative Crit   */
/*
        Collection<Relative> lcrelativeCrit = new ArrayList();
        lcrelativeCrit.add(Relative.GRANDFATHER);
        fhCrit.setLungCancerRelativeCollection(lcrelativeCrit);
*/

         /* set Smoking Relative Crit   */
        Collection<Relative> srelativeCrit = new ArrayList();
        srelativeCrit.add(Relative.GRANDFATHER);
        fhCrit.setSmokingRelativeCollection(srelativeCrit);

        epiQueryDTO.setFamilyHistoryCriterion(fhCrit);
    }

    private void setUpTobaccoConsumptionCriterion() {
        TobaccoConsumptionCriterion tcCrit = new TobaccoConsumptionCriterion();

        tcCrit.setIntensityLower(0.5);
        tcCrit.setIntensityUpper(1.5);

        tcCrit.setAgeAtInitiationLower(15);
        tcCrit.setAgeAtInitiationUpper(20);

        tcCrit.setDurationLower(10);
        tcCrit.setDurationUpper(51);

        tcCrit.setYearsSinceQuittingLower(1900);
        tcCrit.setYearsSinceQuittingUpper(2000);


        epiQueryDTO.setTobaccoConsumptionCriterion(tcCrit);
    }

    public void testManager() throws FindingsQueryException {
        epiQueryDTO.setQueryName("test");
        setUpPatientCharacteristicsCriterion();
        //setUpBehaviorCriterion();
        //setUpTobaccoConsumptionCriterion();
        setUpFamilyHistory();

        Task task = fm.submitQuery(epiQueryDTO);
        task = fm.checkStatus(task);
        while(task.getStatus().equals(FindingStatus.Running)) {
            task = fm.checkStatus(task);
        }
        TaskResult taskResult = fm.getTaskResult(task);
        Collection taskResults = taskResult.getTaskResults();
        System.out.println("Total Results Found: " + taskResults.size());
        for(StudyParticipant p : (Collection<StudyParticipant>)taskResults) {
            printEpiStudyParticipant((EpidemiologicalStudyParticipant) p);
        }

    }
    private void printEpiStudyParticipant(EpidemiologicalStudyParticipant studyParticipant) {
        System.out.println("Partcipant ID:          " + studyParticipant.getId());
        System.out.println("CaseControlStatus:      " + studyParticipant.getCaseControlStatus());
        System.out.println("FamilyHistory:          " + studyParticipant.getFamilyHistory());
        System.out.println("Gender:                 " + studyParticipant.getAdministrativeGenderCode());
        System.out.println("Wight:                  " + studyParticipant.getWeight());
        System.out.println("Height:                 " + studyParticipant.getHeight());
        System.out.println("WaistCircumference:     " + studyParticipant.getWaistCircumference());
        Lifestyle ls = studyParticipant.getLifestyle();
        if (ls != null) {
            System.out.println("Marital Status:         " + ls.getMaritalStatus());
            System.out.println("Religion :              " + ls.getReligion());
            System.out.println("Education Level:        " + ls.getEducationLevel());
            System.out.println("Residential Area:       " + ls.getResidentialArea());
        }
        BehavioralAssessment bAssessment = studyParticipant.getBehavioralAssessment();
        if (bAssessment != null) {
            System.out.println("Fagerstrom Score:       " + bAssessment.getFagerstromScore());
            System.out.println("Depression Score:       " + bAssessment.getDepressionScore());
            System.out.println("Anxiety Score:          " + bAssessment.getAnxietyScore());
        }

        Collection<TobaccoConsumption> tConsumpCol = studyParticipant.getTobaccoConsumptionCollection();
        if (tConsumpCol != null) {
            for (Iterator<TobaccoConsumption> iterator = tConsumpCol.iterator(); iterator.hasNext();) {
                TobaccoConsumption tobaccoConsumption =  iterator.next();
                System.out.println("TobaccoType:            " + tobaccoConsumption.getTobaccoType());
                System.out.println("Intensity:              " + tobaccoConsumption.getIntensity());
                System.out.println("AgeAtInitiation:        " + tobaccoConsumption.getAgeAtInitiation());
                System.out.println("Duration:               " + tobaccoConsumption.getDuration());
                System.out.println("Years Since Quitting:   " + tobaccoConsumption.getYearsSinceQuitting());

            }
        }

        System.out.println("\n\n\n");
    }
    public void testClinical() throws FindingsQueryException {
        EagleClinicalQueryDTO dto = new EagleClinicalQueryDTO();
        dto.setQueryName("test");
        Task task = fm.submitQuery(dto);
        task = fm.checkStatus(task);
        while(task.getStatus().equals(FindingStatus.Running)) {
            task = fm.checkStatus(task);
        }
        TaskResult taskResult = fm.getTaskResult(task);
        Collection taskResults = taskResult.getTaskResults();
        if(taskResults != null) {
            for(StudyParticipant p : (Collection<StudyParticipant>)taskResults) {
                System.out.println(p.getId());
            }
        }
    }
}
