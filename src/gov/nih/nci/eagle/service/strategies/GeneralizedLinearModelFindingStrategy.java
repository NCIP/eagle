package gov.nih.nci.eagle.service.strategies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;

import org.apache.log4j.Logger;



import gov.nih.nci.caintegrator.analysis.messaging.GeneralizedLinearModelRequest;
import gov.nih.nci.caintegrator.analysis.messaging.SampleGroup;
import gov.nih.nci.caintegrator.application.analysis.AnalysisServerClientManager;
import gov.nih.nci.caintegrator.application.cache.BusinessTierCache;
import gov.nih.nci.caintegrator.application.service.strategy.AsynchronousFindingStrategy;
import gov.nih.nci.caintegrator.dto.query.ClassComparisonQueryDTO;
import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.enumeration.ArrayPlatformType;
import gov.nih.nci.caintegrator.enumeration.CoVariateType;
import gov.nih.nci.caintegrator.enumeration.StatisticalMethodType;
import gov.nih.nci.caintegrator.exceptions.FindingsAnalysisException;
import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;
import gov.nih.nci.caintegrator.exceptions.ValidationException;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.caintegrator.service.findings.GeneralizedLinearModelFinding;
import gov.nih.nci.caintegrator.service.task.Task;
import gov.nih.nci.caintegrator.service.task.TaskResult;
import gov.nih.nci.caintegrator.util.ValidationUtility;
import gov.nih.nci.eagle.dto.de.CoVariateDE;
import gov.nih.nci.eagle.query.dto.ClassComparisonQueryDTOImpl;

public class GeneralizedLinearModelFindingStrategy extends
		AsynchronousFindingStrategy {
	
	 private static Logger logger = Logger.getLogger(GeneralizedLinearModelFindingStrategy.class);
	 private Collection<SampleGroup> sampleGroups = new ArrayList<SampleGroup>();
	 private List<CoVariateType> coVariateTypes = new ArrayList<CoVariateType>();
	 private GeneralizedLinearModelRequest glmRequest = null;
	 private AnalysisServerClientManager analysisServerClientManager;
	 private Map<ArrayPlatformType, String> dataFileMap;
	 
	
	 /*
	     * (non-Javadoc)
	     * 
	     * @see gov.nih.nci.caintegrator.service.findings.strategies.FindingStrategy#createQuery()
	     *      This method validates that 2 groups were passed for generalized linear model as the statistical method
	     */
	    public boolean createQuery() throws FindingsQueryException {

	      
	        // because each layer is valid I am assured I will be getting a fulling
	        // populated query object
	        StatisticalMethodType statisticType = getQueryDTO()
	                .getStatisticTypeDE().getValueObject();

	        if (getQueryDTO().getComparisonGroups() != null) {
	        	
	        	if(statisticType==StatisticalMethodType.GLM) {	           
	                if (getQueryDTO().getComparisonGroups().size() != 2) {
	                    throw new FindingsQueryException(
	                            "Incorrect Number of queries passed for the Generalized Linear Model statistical type");
	                }	               
	        	}
	            return true;
	        }
	        return false;
	    }
	    
	    protected void executeStrategy() {			
			
	    	glmRequest = new GeneralizedLinearModelRequest(taskResult
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
			    if(statisticType==StatisticalMethodType.GLM) {	
			    	
			    	 // set statistical method
	                glmRequest.setStatisticalMethod(statisticType);
			    	
			    	 // Set sample groups
	                HashMap<String, List> comparisonGroupsMap = getQueryDTO().getComparisonGroupsMap();
	                HashMap<String, List> baselineGroupMap = getQueryDTO().getBaselineGroupMap();
	                for(String name : baselineGroupMap.keySet()) {
	                    SampleGroup baseline = new SampleGroup(name);
	                    baseline.addAll(baselineGroupMap.get(name));	                   
	                    glmRequest.setBaselineGroup(baseline);
	                }
	                for(String name : comparisonGroupsMap.keySet()) {
	                    SampleGroup comparison = new SampleGroup(name);
	                    comparison.addAll(comparisonGroupsMap.get(name));	                    
	                    glmRequest.setGroup1(comparison);
	                }
	                
	               
	                
				 // set Co-variates
	                
	                List<CoVariateDE> coVariateDEs = getQueryDTO().getCoVariateDEs();
	                Object[] obj = coVariateDEs.toArray();
	                for(int i=0; i<obj.length;i++) {
	                	CoVariateDE coVariateDE = (CoVariateDE)obj[i];
	                	coVariateTypes.add(coVariateDE.getValueObject());	                	
	                }
	                glmRequest.setCoVariateTypes(coVariateTypes);              
				   
				   
				
				    // set Multiple Comparison Adjustment type
	                glmRequest.setMultiGrpComparisonAdjType(getQueryDTO().getMultiGroupComparisonAdjustmentTypeDE().getValueObject());
				
				    // set foldChange
	                glmRequest.setFoldChangeThreshold(getQueryDTO().getExprFoldChangeDE().getValueObject());
				
				    // set pvalue
	                glmRequest.setPValueThreshold(getQueryDTO().getStatisticalSignificanceDE().getValueObject());
				
				    // set arrayplat form, come back to this to figure out how to pass the platform
	               // glmRequest.setArrayPlatform(getQueryDTO().getArrayPlatformDE().getValueObjectAsArrayPlatformType());
				
				    // go the correct matrix to fetch data			
				
	                glmRequest.setDataFileName("eagle_tissue_23APR07.Rda");
				  /* if (fTestRequest.getArrayPlatform() == ArrayPlatformType.BLOOD_ARRAY) {				
					   fTestRequest.setDataFileName(System.getProperty("gov.nih.nci.eagle.blood_data_matrix"));				
				    }
				   else if (fTestRequest.getArrayPlatform() == ArrayPlatformType.TISSUE_ARRAY)  {
					   fTestRequest.setDataFileName(System.getProperty("gov.nih.nci.eagle.tissue_data_matrix"));					
				    }				
			      */
			
			   analysisServerClientManager.sendRequest(glmRequest);
			   return true;	
			    }
			}// end of try
			catch(JMSException ex) {
				logger.error(ex.getMessage());
	  			throw new FindingsAnalysisException(ex.getMessage());
			}
			catch(Exception ex) {
				logger.error(ex.getMessage());
				throw new FindingsAnalysisException("Error in setting glmRequest object");
			}
			return false;
		}
		

		public Finding getFinding() {
	        return (GeneralizedLinearModelFinding) taskResult;
	    }
		
		 public boolean validate(QueryDTO queryDTO) throws ValidationException {
		        boolean _valid = false;
		        if (queryDTO instanceof ClassComparisonQueryDTO) {
		            ClassComparisonQueryDTO classComparisonQueryDTO = (ClassComparisonQueryDTO) queryDTO;
		            try {
		                
		                ValidationUtility.checkForNull(classComparisonQueryDTO
		                        .getArrayPlatformDE());
		                ValidationUtility.checkForNull(classComparisonQueryDTO
		                        .getComparisonGroups());
		                ValidationUtility.checkForNull(classComparisonQueryDTO
		                        .getExprFoldChangeDE());
		                ValidationUtility.checkForNull(classComparisonQueryDTO
		                        .getMultiGroupComparisonAdjustmentTypeDE());
		                ValidationUtility.checkForNull(classComparisonQueryDTO
		                        .getQueryName());
		                ValidationUtility.checkForNull(classComparisonQueryDTO
		                        .getStatisticalSignificanceDE());
		                ValidationUtility.checkForNull(classComparisonQueryDTO
		                        .getStatisticTypeDE());	                          
		                
		                _valid = true;
		            } catch (ValidationException ex) {
		                logger.error(ex.getMessage());
		                throw ex;
		            }
		        }
		        return _valid;
		    }

	    private ClassComparisonQueryDTOImpl getQueryDTO() {
	        return (ClassComparisonQueryDTOImpl) taskResult.getTask().getQueryDTO();
	    }
    
	    public TaskResult retrieveTaskResult(Task task) {
	        TaskResult taskResult = (TaskResult) businessCacheManager
	                .getObjectFromSessionCache(task.getCacheId(), task.getId());
	        return taskResult;
	    }
	    
	    public boolean canHandle(QueryDTO query) {
	        if(query instanceof ClassComparisonQueryDTO) {
	            ClassComparisonQueryDTO dto = (ClassComparisonQueryDTO)query;
	            return ( dto.getStatisticTypeDE().equals(StatisticalMethodType.GLM));
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
	    
	    public Map getDataFileMap() {
	        return dataFileMap;
	    }

	    public void setDataFileMap(Map dataFileMap) {
	        this.dataFileMap = dataFileMap;
	    }

}
