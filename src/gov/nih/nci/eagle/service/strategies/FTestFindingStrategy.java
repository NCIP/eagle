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

import gov.nih.nci.eagle.query.dto.ClassComparisonQueryDTOImpl;
import gov.nih.nci.eagle.query.dto.PatientUserListQueryDTO;


import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;


import javax.jms.JMSException;
import javax.naming.NamingException;


import org.apache.log4j.Logger;

public class FTestFindingStrategy extends AsynchronousFindingStrategy{
	
	private static  Logger logger = Logger.getLogger(FTestFindingStrategy.class);
	
	
	private ClassComparisonQueryDTO myQueryDTO = null;
	
	private FTestRequest fTestRequest = null;		
	
	private AnalysisServerClientManager analysisServerClientManager = null;		
	
	private List<SampleGroup> sampleGroups = new ArrayList<SampleGroup>();	
	
	
	/*public FTestFindingStrategy(String sessionId, String taskId, ClassComparisonQueryDTO queryDTO) throws ValidationException  {
		if(validate(queryDTO)) {
		   this.sessionId = sessionId;
		   this.taskId = taskId;
		   myQueryDTO = queryDTO;
		   fTestRequest = new FTestRequest(this.sessionId,this.taskId);
		
		   try {
			   analysisServerClientManager = AnalysisServerClientManager.getInstance();
		     }
		   catch(NamingException ex) {
			   logger.error(ex.getMessage());
			   logger.error(ex);
		     }
		   catch(JMSException ex) {
			   logger.error(ex.getMessage());
			   logger.error(ex);
		     }
		}// end if
		*/
	    /*
		 * set Finding in cache
		 */
		/*FindingStatus currentStatus  = FindingStatus.Running;
		fTestFinding = new FTestFinding (this.sessionId,this.taskId,currentStatus, null);
		fTestFinding.setQueryDTO(myQueryDTO);		
		cacheManager.addToSessionCache(this.sessionId,this.taskId, fTestFinding);		
	}
	*/
	
	// this method is used to verified the # of comparison groups for Ftest
	public boolean 	createQuery() throws FindingsQueryException{
		
       // retrieve statisticType for F-test, it ususally deals with multiple groups
		StatisticalMethodType statisticType = myQueryDTO.getStatisticTypeDE().getValueObject();
		
		if(myQueryDTO.getComparisonGroups()!= null) {			
		    if(statisticType==StatisticalMethodType.FTest) {
		    	if(myQueryDTO.getComparisonGroups().size()<2) {
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

		
		/*List<ClinicalQueryDTO> clinicalQueries = myQueryDTO.getComparisonGroups();
		for(ClinicalQueryDTO clinicalDataQuery: clinicalQueries ) {
			if(clinicalDataQuery instanceof PatientUserListQueryDTO) {
				try {
				    PatientUserListQueryDTO ptQuery = (PatientUserListQueryDTO)clinicalDataQuery;
				    List<String> sampleIds = ptQuery.getPatientDIDs();
				    if(sampleIds != null  && sampleIds.size() > 0){					
					    SampleGroup sampleGroup = new SampleGroup(clinicalDataQuery.getQueryName(),sampleIds.size());
					    sampleGroup.addAll(sampleIds);
					    sampleGroups.add(sampleGroup);				
				       }  
				    }
				catch(Exception ex) {
					ex.printStackTrace();
					logger.error(ex.getMessage());
		  			throw new FindingsQueryException(ex.getMessage());
				    }  	    
			
		     }	
		
	     }
	  return true;
	  */
	}
	
	
	/*
	 * 
	 */
	public boolean analyzeResultSet() throws  FindingsAnalysisException {
		StatisticalMethodType statisticType = myQueryDTO.getStatisticTypeDE().getValueObject();
		
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
			    fTestRequest.setMultiGrpComparisonAdjType(myQueryDTO.getMultiGroupComparisonAdjustmentTypeDE().getValueObject());
			
			    // set foldChange
			    fTestRequest.setFoldChangeThreshold(myQueryDTO.getExprFoldChangeDE().getValueObject());
			
			    // set pvalue
			    fTestRequest.setPValueThreshold(myQueryDTO.getStatisticalSignificanceDE().getValueObject());
			
			    // set arrayplat form
			    fTestRequest.setArrayPlatform(myQueryDTO.getArrayPlatformDE().getValueObjectAsArrayPlatformType());
			
			    // go the correct matrix to fetch data			
			
			   if (fTestRequest.getArrayPlatform() == ArrayPlatformType.BLOOD_ARRAY) {				
				   fTestRequest.setDataFileName(System.getProperty("gov.nih.nci.eagle.blood_data_matrix"));				
			    }
			   else if (fTestRequest.getArrayPlatform() == ArrayPlatformType.TISSUE_ARRAY)  {
				   fTestRequest.setDataFileName(System.getProperty("gov.nih.nci.eagle.tissue_data_matrix"));					
			    }				
		      
		
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
	        return (query instanceof ClassComparisonQueryDTO);
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

	
}

