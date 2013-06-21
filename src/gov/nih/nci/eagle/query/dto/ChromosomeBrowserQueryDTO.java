/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/eagle/LICENSE.txt for details.
 */

package gov.nih.nci.eagle.query.dto;

import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.ExprFoldChangeDE;
import gov.nih.nci.caintegrator.dto.de.InstitutionDE;
import gov.nih.nci.caintegrator.dto.de.MultiGroupComparisonAdjustmentTypeDE;
import gov.nih.nci.caintegrator.dto.de.StatisticTypeDE;
import gov.nih.nci.caintegrator.dto.de.StatisticalSignificanceDE;
import gov.nih.nci.caintegrator.dto.query.ClassComparisonQueryDTO;
import gov.nih.nci.caintegrator.dto.query.ClinicalQueryDTO;
import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.eagle.dto.de.CoVariateDE;
import gov.nih.nci.eagle.enumeration.SpecimenType;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class ChromosomeBrowserQueryDTO implements QueryDTO, ClassComparisonQueryDTO {


    private String queryName;
    private List<SpecimenType> specimenTypes;
    //statistic method 
    private StatisticTypeDE statisticTypeDE ;   
    
    
     //  co-variate
    private CoVariateDE coVariateDE;
    
     //  co-variates
    private List<CoVariateDE> coVariateDEs;
    
     //  comparison groups
    private List<ClinicalQueryDTO> comparisonGroups;
    
    //hold the lists as maps listName->listValues, seperated for baseline
    private HashMap<String, List> comparisonGroupsMap;
    private HashMap<String, List> baselineGroupMap;
    
    //   fold change
    private ExprFoldChangeDE exprFoldChangeDE;
    
    // p value
    private StatisticalSignificanceDE statisticalSignificanceDE ;
    
    
    // platform type: blood or tissue
    private ArrayPlatformDE arrayPlatformDE;
    
    //   right now eagle does not have them yet, but rembrandt and ispy do: FWER or FDR
    private MultiGroupComparisonAdjustmentTypeDE multiGroupComparisonAdjustmentTypeDE ; 
    
    
    
    
    public void setQueryName(String name) {
        queryName = name;
    }

    public String getQueryName() {      
        return queryName;
    }
    
    
    public StatisticTypeDE getStatisticTypeDE() {       
        return statisticTypeDE;
    }

    public void setStatisticTypeDE(StatisticTypeDE statisticTypeDE) {
        this.statisticTypeDE = statisticTypeDE;
    }
        
    
    public CoVariateDE getCoVariateDE() {       
        return coVariateDE;
    }

    public void setCoVariateDE(CoVariateDE coVariateDE) {
        this.coVariateDE = coVariateDE;
    }
    

    public List<CoVariateDE> getCoVariateDEs() {
        return coVariateDEs;
    }

    public void setCoVariateDEs(List<CoVariateDE> coVariateDEs) {
        this.coVariateDEs = coVariateDEs;
    }

    public List<ClinicalQueryDTO> getComparisonGroups() {       
        return comparisonGroups;
    }

    public void setComparisonGroups(List<ClinicalQueryDTO> comparisonGroups) {
        this.comparisonGroups = comparisonGroups;
    }
    
    public ExprFoldChangeDE getExprFoldChangeDE() {     
        return exprFoldChangeDE;
    }

    public void setExprFoldChangeDE(ExprFoldChangeDE exprFoldChangeDE) {
        this.exprFoldChangeDE = exprFoldChangeDE;
    }


    public StatisticalSignificanceDE getStatisticalSignificanceDE() {       
        return statisticalSignificanceDE;
    }

    public void setStatisticalSignificanceDE(
            StatisticalSignificanceDE statisticalSignificanceDE) {
           this.statisticalSignificanceDE = statisticalSignificanceDE;
    }
    
    
    public ArrayPlatformDE getArrayPlatformDE() {
        
        return arrayPlatformDE;
    }

    public void setArrayPlatformDE(ArrayPlatformDE arrayPlatformDE) {
        this.arrayPlatformDE = arrayPlatformDE;
    }

    public MultiGroupComparisonAdjustmentTypeDE getMultiGroupComparisonAdjustmentTypeDE() {     
           return multiGroupComparisonAdjustmentTypeDE;
    }

    public void setMultiGroupComparisonAdjustmentTypeDE(
            MultiGroupComparisonAdjustmentTypeDE multiGroupComparisonAdjustmentTypeDE) {
            this.multiGroupComparisonAdjustmentTypeDE = multiGroupComparisonAdjustmentTypeDE;

    }

    
    

    public HashMap<String, List> getBaselineGroupMap() {
        return baselineGroupMap;
    }

    public void setBaselineGroupMap(HashMap<String, List> baselineGroupMap) {
        this.baselineGroupMap = baselineGroupMap;
    }

    public HashMap<String, List> getComparisonGroupsMap() {
        return comparisonGroupsMap;
    }

    public void setComparisonGroupsMap(HashMap<String, List> comparisonGroupsMap) {
        this.comparisonGroupsMap = comparisonGroupsMap;
    }

    
    public List<SpecimenType> getSpecimenTypes() {
        return specimenTypes;
    }

    public void setSpecimenTypes(List<SpecimenType> specimenTypes) {
        this.specimenTypes = specimenTypes;
    }

    public Collection<InstitutionDE> getInstitutionDEs() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
    }

    public void setInstitutionDEs(Collection<InstitutionDE> institutionDE) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
    }





}
