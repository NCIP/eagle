/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/eagle/LICENSE.txt for details.
 */

package gov.nih.nci.eagle.service.strategies;


import gov.nih.nci.caintegrator.analysis.messaging.ClassComparisonRequest;
import gov.nih.nci.caintegrator.analysis.messaging.FTestRequest;
import gov.nih.nci.caintegrator.service.findings.ClassComparisonFinding;
import gov.nih.nci.caintegrator.service.findings.FTestFinding;
import gov.nih.nci.caintegrator.application.analysis.AnalysisServerClientManager;
import gov.nih.nci.caintegrator.application.cache.BusinessTierCache;
import gov.nih.nci.caintegrator.application.cache.CacheFactory;
import gov.nih.nci.caintegrator.application.service.strategy.AsynchronousFindingStrategy;
import gov.nih.nci.caintegrator.service.findings.strategies.SessionBasedFindingStrategy;
import gov.nih.nci.caintegrator.dto.query.ClassComparisonQueryDTO;
import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.caintegrator.service.task.Task;
import gov.nih.nci.caintegrator.service.task.TaskResult;
import gov.nih.nci.caintegrator.exceptions.FindingsAnalysisException;
import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;
import gov.nih.nci.caintegrator.analysis.messaging.SampleGroup;
import gov.nih.nci.caintegrator.exceptions.ValidationException;
import gov.nih.nci.caintegrator.util.ValidationUtility;
import gov.nih.nci.caintegrator.enumeration.StatisticalMethodType;
import gov.nih.nci.caintegrator.dto.query.*;
import gov.nih.nci.caintegrator.enumeration.ArrayPlatformType;

import gov.nih.nci.eagle.enumeration.SpecimenType;
import gov.nih.nci.eagle.query.dto.ClassComparisonQueryDTOImpl;
import gov.nih.nci.eagle.query.dto.PatientUserListQueryDTO;


import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;


import javax.jms.JMSException;
import javax.naming.NamingException;


import org.apache.log4j.Logger;

public class FTestFindingStrategy extends AsynchronousFindingStrategy{
	
	private static  Logger logger = Logger.getLogger(FTestFindingStrategy.class);
	
	
	
	private FTestRequest fTestRequest = null;		
	
	private AnalysisServerClientManager analysisServerClientManager = null;		
	
	private List<SampleGroup> sampleGroups = new ArrayList<SampleGroup>();	
    private Map<String, String> dataFileMap;
	
	// this method is used to verified the # of comparison groups for Ftest
	public boolean 	createQuery() throws FindingsQueryException{
		
       // retrieve statisticType for F-test, it ususally deals with multiple groups
		StatisticalMethodType statisticType = getQueryDTO().getStatisticTypeDE().getValueObject();
		
		if(getQueryDTO().getComparisonGroups()!= null) {			
		    if(statisticType==StatisticalMethodType.FTest) {
		    	if(getQueryDTO().getComparisonGroups().size()<2) {
		    		throw new FindingsQueryException("Incorrect numbers of comparison groups are passed for FTest");		    		
		    	}
		    	
		    }		
		return true;
		}	
		return false;		
	}
	
	 protected void executeStrategy() {
		
		
		fTestRequest = new FTestRequest(taskResult
                .getTask().getCacheId(), taskResult.getTask().getId());
		businessCacheManager.addToSessionCache(getTaskResult().getTask()
                .getCacheId(), getTaskResult().getTask().getId(),
                getTaskResult());
	}
	
	
	/*
	 * 
	 */
	public boolean analyzeResultSet() throws  FindingsAnalysisException {
		StatisticalMethodType statisticType = getQueryDTO().getStatisticTypeDE().getValueObject();
		
		try {
		    if(statisticType==StatisticalMethodType.FTest) {
			
                //	set sample groups
			  /*  if (sampleGroups.size() >= 2) {
			    	fTestRequest.setSampleGroups(sampleGroups);
			     }
			    */
			    // Set sample groups
                HashMap<String, List> comparisonGroupsMap = getQueryDTO().getComparisonGroupsMap();
               
                for(String name : comparisonGroupsMap.keySet()) {
                    SampleGroup comparison = new SampleGroup(name);
                    comparison.addAll(comparisonGroupsMap.get(name));
                    sampleGroups.add(comparison);
                    fTestRequest.setSampleGroups(sampleGroups);
                }
			    // set statistical method
			    fTestRequest.setStatisticalMethod(statisticType);
			
			    // set Multiple Comparison Adjustment type
			    fTestRequest.setMultiGrpComparisonAdjType(getQueryDTO().getMultiGroupComparisonAdjustmentTypeDE().getValueObject());
			
			    // set foldChange
			    fTestRequest.setFoldChangeThreshold(getQueryDTO().getExprFoldChangeDE().getValueObject());
			
			    // set pvalue
			    fTestRequest.setPValueThreshold(getQueryDTO().getStatisticalSignificanceDE().getValueObject());
			
			    // set arrayplat form
			    fTestRequest.setArrayPlatform(getQueryDTO().getArrayPlatformDE().getValueObjectAsArrayPlatformType());
			
			    // go the correct matrix to fetch data			
			
                ArrayPlatformType arrayPlatform = getQueryDTO()
                .getArrayPlatformDE()
                .getValueObjectAsArrayPlatformType();

                fTestRequest.setArrayPlatform(arrayPlatform);
                
                // Set data file
                fTestRequest.setDataFileName(dataFileMap.get(getQueryDTO().getSpecimenTypeEnum().name()));
		
		   analysisServerClientManager.sendRequest(fTestRequest);
		   return true;	
		    }
		}// end of try
		catch(JMSException ex) {
			logger.error(ex.getMessage());
  			throw new FindingsAnalysisException(ex.getMessage());
		}
		catch(Exception ex) {
			logger.error(ex.getMessage());
			throw new FindingsAnalysisException("Error in setting FTestRequest object");
		}
		return false;
	}
	
	/*
	 * get Finding object
	 */
	public Finding getFinding() {
		return (ClassComparisonFinding) taskResult;
	}
	/*
	 * This is to validate if the classComparasonQuery is valid or not.
	 */
	public boolean validate(QueryDTO queryDTO) throws ValidationException{		
		boolean validStatus = false;
		
		if(queryDTO instanceof ClassComparisonQueryDTO){
			ClassComparisonQueryDTO classComparisonQueryDTO = (ClassComparisonQueryDTO)queryDTO;
			try {
				
				ValidationUtility.checkForNull(classComparisonQueryDTO.getComparisonGroups());
				ValidationUtility.checkForNull(classComparisonQueryDTO.getStatisticTypeDE());
				ValidationUtility.checkForNull(classComparisonQueryDTO.getMultiGroupComparisonAdjustmentTypeDE());
				ValidationUtility.checkForNull(classComparisonQueryDTO.getExprFoldChangeDE());
				ValidationUtility.checkForNull(classComparisonQueryDTO.getStatisticalSignificanceDE());
				ValidationUtility.checkForNull(classComparisonQueryDTO.getArrayPlatformDE());					
			    ValidationUtility.checkForNull(classComparisonQueryDTO.getQueryName());							
			    validStatus = true;		
				
			  }// end of try block
			catch(ValidationException ex) {
				 logger.error(ex.getMessage());
				 throw ex;	
			  }
			
			}
		
		return validStatus;
		
	}
	 private ClassComparisonQueryDTOImpl getQueryDTO() {
	        return (ClassComparisonQueryDTOImpl) taskResult.getTask().getQueryDTO();
	    }
	 
	 @Override
	    public TaskResult retrieveTaskResult(Task task) {
	        TaskResult taskResult = (TaskResult) businessCacheManager
	                .getObjectFromSessionCache(task.getCacheId(), task.getId());
	        return taskResult;
	    }

	    @Override
	    public boolean canHandle(QueryDTO query) {
            if(query instanceof ClassComparisonQueryDTOImpl) {
                ClassComparisonQueryDTO dto = (ClassComparisonQueryDTO)query;
                return dto.getStatisticTypeDE().getValueObject().equals(StatisticalMethodType.FTest);
            }
            return false;
	    }

	    public AnalysisServerClientManager getAnalysisServerClientManager() {
	        return analysisServerClientManager;
	    }

	    public void setAnalysisServerClientManager(
	            AnalysisServerClientManager analysisServerClientManager) {
	        this.analysisServerClientManager = analysisServerClientManager;
	    }

	    public BusinessTierCache getBusinessCacheManager() {
	        return businessCacheManager;
	    }

	    public void setBusinessCacheManager(BusinessTierCache cacheManager) {
	        this.businessCacheManager = cacheManager;
	    }

        public Map<String, String> getDataFileMap() {
            return dataFileMap;
        }

        public void setDataFileMap(Map<String, String> dataFileMap) {
            this.dataFileMap = dataFileMap;
        }

	
}

