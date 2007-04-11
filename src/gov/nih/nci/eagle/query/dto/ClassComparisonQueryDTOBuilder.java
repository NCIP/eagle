package gov.nih.nci.eagle.query.dto;

import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;


import gov.nih.nci.caintegrator.application.dtobuilder.QueryDTOBuilder;
import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.enumeration.Operator;
import gov.nih.nci.caintegrator.enumeration.StatisticalMethodType;
import gov.nih.nci.caintegrator.enumeration.StatisticalSignificanceType;
import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.StatisticTypeDE;
import gov.nih.nci.caintegrator.dto.de.StatisticalSignificanceDE;
import gov.nih.nci.caintegrator.dto.de.ExprFoldChangeDE.UpRegulation;
import gov.nih.nci.caintegrator.dto.query.ClinicalQueryDTO;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.util.ClassHelper;

import gov.nih.nci.eagle.web.struts.ClassComparisonForm;
import gov.nih.nci.eagle.web.helper.EnumCaseChecker;
import gov.nih.nci.eagle.enumeration.CoVariateType;
import gov.nih.nci.eagle.dto.de.CoVariateDE;


import org.apache.log4j.Logger;



/**
 * ClassComparisonQueryDTOBuilder builds the ClassComparisonQueryDTO to be used
 * by the ClassComparisonStrategy. It is called from the struts 
 * action and uses all values populated in the UI form.
 * @author zhangd
 *
 */

public class ClassComparisonQueryDTOBuilder implements QueryDTOBuilder{
	
	private static Logger logger = Logger.getLogger(ClassComparisonQueryDTOBuilder.class);
	private HttpSession session;
	public ClassComparisonQueryDTOBuilder() {}
	public ClassComparisonQueryDTOBuilder(HttpSession session) {
		this.session = session;		
	}
	
	  /***
     * These are the default error values used when an invalid enum type
     * parameter has been passed to the action.  These default values should
     * be verified as useful in all cases.
     */
     private StatisticalMethodType ERROR_STATISTICAL_METHOD_TYPE = StatisticalMethodType.TTest;
     private CoVariateType ERROR_COVARIATE_TYPE = CoVariateType.Age;
     
  
    
	// in the QueryDTOBuilder interface, there is only one method : QueryDTO buildQueryDTO(Object form){}
	public QueryDTO buildQueryDTO(Object form) {
		
		ClassComparisonForm classComparisonForm = (ClassComparisonForm) form;
		ClassComparisonQueryDTOImpl  classComparisondto = new ClassComparisonQueryDTOImpl();
		
		//set taskId/analysis name
        if(classComparisonForm.getAnalysisName().trim().length()>1){
        	classComparisondto.setQueryName(classComparisonForm.getAnalysisName());
        }
        
        
       //set statistical method
        if(classComparisonForm.getStatisticalMethod().trim().length()>1){
        	/*
             * This following code is here to deal with an observed problem with the changing 
             * of case in request parameters.  See the class EnumChecker for 
             * enlightenment.
             */
 		   
 		   StatisticalMethodType statisticalMethodType; 
 		   String statisticalMethodTypeString= EnumCaseChecker.getEnumTypeName(classComparisonForm.getStatisticalMethod(),StatisticalMethodType.values());
            if(statisticalMethodTypeString!=null) {
           	 statisticalMethodType = StatisticalMethodType.valueOf(statisticalMethodTypeString);
            }
            
            else {
         	   	logger.error("Invalid StatisticalMethodType value given in request");
        		logger.error("Selected StatisticalMethodType value = "+classComparisonForm.getStatisticalMethod());
        		logger.error("Using the default StatisticalMethodType type of :"+ERROR_STATISTICAL_METHOD_TYPE);
        		statisticalMethodType = ERROR_STATISTICAL_METHOD_TYPE;            
                }
            
           StatisticTypeDE statisticTypeDE = new StatisticTypeDE(statisticalMethodType);
           classComparisondto.setStatisticTypeDE(statisticTypeDE);
       
 	        }
        
	// set up co-variate 
        
        //set statistical method
        if(classComparisonForm.getCovariate()!= null && classComparisonForm.getCovariate().length()>1){
        	/*
             * This following code is here to deal with an observed problem with the changing 
             * of case in request parameters.  See the class EnumChecker for 
             * enlightenment.
             */
 		   
           CoVariateType coVariateType; 
 		   String covariateString= EnumCaseChecker.getEnumTypeName(classComparisonForm.getCovariate(),CoVariateType.values());
            if(covariateString!=null) {
            	coVariateType = CoVariateType.valueOf(covariateString);
            }
            
            else {
         	   	logger.error("Invalid covariateType value given in request");
        		logger.error("Selected covariateType value = "+classComparisonForm.getExistingCovariates());
        		logger.error("Using the default covariateType type of :"+ERROR_COVARIATE_TYPE);
        		coVariateType = ERROR_COVARIATE_TYPE;            
                }
            
            CoVariateDE coVariateDE = new CoVariateDE(coVariateType);
            classComparisondto.setCoVariateDE(coVariateDE);
       
 	        }
     
        
	 
	 // set comparison groups
	   List<ClinicalQueryDTO> clinicalQueryCollection = new ArrayList<ClinicalQueryDTO>();
    
	   if(classComparisonForm.getSelectedGroups() != null && classComparisonForm.getSelectedGroups().length >=2) {
		   
		   for(int i=0; i<classComparisonForm.getSelectedGroups().length; i++){
			  String[] uiDropdownString = classComparisonForm.getSelectedGroups()[i].split("#");  
			  String myClassName = uiDropdownString[0];
			  String myValueName = uiDropdownString[1];
			  
			  Class myClass = ClassHelper.createClass(myClassName);
			  if(myClass.isInstance(new UserList())) {
				  PatientUserListQueryDTO patientUserListQueryDTO = new PatientUserListQueryDTO(session, myValueName);
				  clinicalQueryCollection.add(patientUserListQueryDTO);
				  if(i==1) {
					  // the second group is always the baseline, when the statisticalMethod is not F-test
				    if(!"FTest".equals(classComparisonForm.getStatisticalMethod())) {
				    	patientUserListQueryDTO.setBaseline(true);
				    }
				  }
			  }
			  classComparisondto.setComparisonGroups(clinicalQueryCollection);  
		   }
		  //  finishing add comparison groups from the form 
		   
	   }
        
	   
	   //set up fold change
	   
	   if(classComparisonForm.getFoldChange()!= null && classComparisonForm.getFoldChange()!= "") {
		   UpRegulation exprFoldChangeDE = new UpRegulation(new Float(classComparisonForm.getFoldChange()));           
		   classComparisondto.setExprFoldChangeDE(exprFoldChangeDE);
	   }
       
	   // set up p value
	   
	   if(classComparisonForm.getPvalue()!= null && classComparisonForm.getPvalue()!="") {		   
		   StatisticalSignificanceDE statisticalSignificanceDE = new StatisticalSignificanceDE(new Double(classComparisonForm.getPvalue()),Operator.LE,StatisticalSignificanceType.pValue);  
		   classComparisondto.setStatisticalSignificanceDE(statisticalSignificanceDE);	   
	   }
	   
	   // set up platform
	   
	   if(classComparisonForm.getPlatform()!= "" || classComparisonForm.getPlatform().length() != 0){       
           ArrayPlatformDE arrayPlatformDE = new ArrayPlatformDE(classComparisonForm.getPlatform());
           classComparisondto.setArrayPlatformDE(arrayPlatformDE);
       }
		
	   return classComparisondto;
		
	}	

}
