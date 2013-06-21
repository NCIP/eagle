/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/eagle/LICENSE.txt for details.
 */

package gov.nih.nci.eagle.query.dto;

import gov.nih.nci.caintegrator.application.dtobuilder.QueryDTOBuilder;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.MultiGroupComparisonAdjustmentTypeDE;
import gov.nih.nci.caintegrator.dto.de.StatisticTypeDE;
import gov.nih.nci.caintegrator.dto.de.StatisticalSignificanceDE;
import gov.nih.nci.caintegrator.dto.de.ExprFoldChangeDE.UpRegulation;
import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.enumeration.CoVariateType;
import gov.nih.nci.caintegrator.enumeration.MultiGroupComparisonAdjustmentType;
import gov.nih.nci.caintegrator.enumeration.Operator;
import gov.nih.nci.caintegrator.enumeration.StatisticalMethodType;
import gov.nih.nci.caintegrator.enumeration.StatisticalSignificanceType;
import gov.nih.nci.eagle.dto.de.CoVariateDE;
import gov.nih.nci.eagle.enumeration.SpecimenType;
import gov.nih.nci.eagle.service.validation.ListValidationService;
import gov.nih.nci.eagle.web.helper.EnumCaseChecker;
import gov.nih.nci.eagle.web.struts.ChromosomeBrowserForm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * ClassComparisonQueryDTOBuilder builds the ClassComparisonQueryDTO to be used
 * by the ClassComparisonStrategy. It is called from the struts action and uses
 * all values populated in the UI form.
 * 
 * @author zhangd
 */

public class ChromosomeBrowserQueryDTOBuilder implements QueryDTOBuilder {

    private static Logger logger = Logger
            .getLogger(ChromosomeBrowserQueryDTOBuilder.class);
    private ListValidationService listValidationService;

    public ChromosomeBrowserQueryDTOBuilder() {
    }

    /***************************************************************************
     * These are the default error values used when an invalid enum type
     * parameter has been passed to the action. These default values should be
     * verified as useful in all cases.
     */
    private StatisticalMethodType ERROR_STATISTICAL_METHOD_TYPE = StatisticalMethodType.TTest;
    private CoVariateType ERROR_COVARIATE_TYPE = CoVariateType.Age;

    public QueryDTO buildQueryDTO(Object form) {
        // dont use
        throw (new UnsupportedOperationException());
    }

    // in the QueryDTOBuilder interface, there is only one method : QueryDTO
    // buildQueryDTO(Object form){}
    public QueryDTO buildQueryDTO(Object form, String cacheId) {

        ChromosomeBrowserForm chromosomeBrowserForm = (ChromosomeBrowserForm) form;
        ChromosomeBrowserQueryDTO browserDto = new ChromosomeBrowserQueryDTO();

        // set taskId/analysis name
        if (chromosomeBrowserForm.getAnalysisName().trim().length() > 1) {
            browserDto.setQueryName(chromosomeBrowserForm.getAnalysisName());
        }

        // set statistical method
        if (chromosomeBrowserForm.getStatisticalMethod().trim().length() > 1) {
            /*
             * This following code is here to deal with an observed problem with
             * the changing of case in request parameters. See the class
             * EnumChecker for enlightenment.
             */

            StatisticalMethodType statisticalMethodType;
            String statisticalMethodTypeString = EnumCaseChecker
                    .getEnumTypeName(chromosomeBrowserForm
                            .getStatisticalMethod(), StatisticalMethodType
                            .values());
            if (statisticalMethodTypeString != null) {
                statisticalMethodType = StatisticalMethodType
                        .valueOf(statisticalMethodTypeString);
            }

            else {
                logger
                        .error("Invalid StatisticalMethodType value given in request");
                logger.error("Selected StatisticalMethodType value = "
                        + chromosomeBrowserForm.getStatisticalMethod());
                logger
                        .error("Using the default StatisticalMethodType type of :"
                                + ERROR_STATISTICAL_METHOD_TYPE);
                statisticalMethodType = ERROR_STATISTICAL_METHOD_TYPE;
            }

            StatisticTypeDE statisticTypeDE = new StatisticTypeDE(
                    statisticalMethodType);
            browserDto.setStatisticTypeDE(statisticTypeDE);

        }

        // set up MultiGroupComparisonAdjustmentType
        browserDto
                .setMultiGroupComparisonAdjustmentTypeDE(new MultiGroupComparisonAdjustmentTypeDE(
                        MultiGroupComparisonAdjustmentType.NONE));

        // set up co-variates

        if (chromosomeBrowserForm.getSelectedCovariates() != null
                && chromosomeBrowserForm.getSelectedCovariates().length >= 1) {

            CoVariateType coVariateType;
            List<CoVariateDE> coVariateDEs = new ArrayList<CoVariateDE>();

            for (int i = 0; i < chromosomeBrowserForm.getSelectedCovariates().length; i++) {
                String myCovariateName = (String) chromosomeBrowserForm
                        .getSelectedCovariates()[i];
                String covariateString = EnumCaseChecker.getEnumTypeName(
                        myCovariateName, CoVariateType.values());
                if (covariateString != null) {
                    coVariateType = CoVariateType.valueOf(covariateString);
                } else {
                    logger
                            .error("Invalid covariateType value given in request");
                    logger.error("Selected covariateType value = "
                            + chromosomeBrowserForm.getExistingCovariates());
                    logger.error("Using the default covariateType type of :"
                            + ERROR_COVARIATE_TYPE);
                    coVariateType = ERROR_COVARIATE_TYPE;
                }

                CoVariateDE coVariateDE = new CoVariateDE(coVariateType);
                coVariateDEs.add(coVariateDE);

            }

            browserDto.setCoVariateDEs(coVariateDEs);

        }

        // look at the classComparisonForm.getSpecimenType() and then
        // reconstruct the enum, then setSpecimenType(enum) in DTO
        if (chromosomeBrowserForm.getSpecimenType() != ""
                || chromosomeBrowserForm.getSpecimenType().length() != 0) {
            // TODO: Change this to actually read the values from the form
            List<SpecimenType> types = new ArrayList<SpecimenType>();
            types.add(SpecimenType.BLOOD);
            types.add(SpecimenType.TISSUE_CANCER);
            types.add(SpecimenType.TISSUE_NORMAL);
            browserDto.setSpecimenTypes(types);
        }

        // set comparison groups
        UserListBeanHelper ulbh = new UserListBeanHelper(cacheId);
        HashMap<String, List> compGroups = new HashMap();
        HashMap baselineMap = new HashMap();
        
        for (SpecimenType type : browserDto.getSpecimenTypes()) {

            if (chromosomeBrowserForm.getSelectedGroups() != null
                    && chromosomeBrowserForm.getSelectedGroups().length == 1) {

                // Setup comparison groups for each specimen type
                String myGroupName = chromosomeBrowserForm.getSelectedGroups()[0];
                List<String> myGroupValues = new ArrayList();
                myGroupValues = ulbh.getItemsFromList(myGroupName);
                List<String> validList = listValidationService.validateList(
                        myGroupValues, type);
                compGroups.put(type.getName(), validList);

                browserDto.setComparisonGroupsMap(compGroups);
            }

            // set the baseline group in a similar manner as above
            if (chromosomeBrowserForm.getBaseline() != null) {
                List<String> baselineValues = ulbh
                        .getItemsFromList(chromosomeBrowserForm.getBaseline());
                List<String> validList = listValidationService.validateList(
                        baselineValues, type);
                baselineMap.put(type.getName(), validList);
                browserDto.setBaselineGroupMap(baselineMap);
            }

        }
        // set up fold change

        if (chromosomeBrowserForm.getFoldChange() != null
                && chromosomeBrowserForm.getFoldChange() != "") {
            UpRegulation exprFoldChangeDE = new UpRegulation(new Float(
                    chromosomeBrowserForm.getFoldChange()));
            browserDto.setExprFoldChangeDE(exprFoldChangeDE);
        }

        // set up p value

        if (chromosomeBrowserForm.getPvalue() != null
                && chromosomeBrowserForm.getPvalue() != "") {
            StatisticalSignificanceDE statisticalSignificanceDE = new StatisticalSignificanceDE(
                    new Double(chromosomeBrowserForm.getPvalue()), Operator.LE,
                    StatisticalSignificanceType.pValue);
            browserDto.setStatisticalSignificanceDE(statisticalSignificanceDE);
        }

        // set up platform
        /*
         * if(classComparisonForm.getPlatform()!= "" ||
         * classComparisonForm.getPlatform().length() != 0){ ArrayPlatformDE
         * arrayPlatformDE = new
         * ArrayPlatformDE(classComparisonForm.getPlatform());
         * //classComparisondto.setArrayPlatformDE(arrayPlatformDE);
         * classComparisondto.setArrayPlatformDE(new
         * ArrayPlatformDE("ALL_PLATFORM")); }
         */
        // Set array platform so that the reporters can be annotated for the
        // report.
        browserDto.setArrayPlatformDE(new ArrayPlatformDE("ALL_PLATFORM"));

        return browserDto;

    }

    public ListValidationService getListValidationService() {
        return listValidationService;
    }

    public void setListValidationService(
            ListValidationService listValidationService) {
        this.listValidationService = listValidationService;
    }

}
