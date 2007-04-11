package gov.nih.nci.eagle.service.strategies;

import gov.nih.nci.caintegrator.analysis.messaging.ClassComparisonRequest;
import gov.nih.nci.caintegrator.analysis.messaging.SampleGroup;
import gov.nih.nci.caintegrator.application.analysis.AnalysisServerClientManager;
import gov.nih.nci.caintegrator.application.cache.BusinessTierCache;
import gov.nih.nci.caintegrator.application.cache.CacheFactory;
import gov.nih.nci.caintegrator.enumeration.ArrayPlatformType;
import gov.nih.nci.caintegrator.enumeration.StatisticalMethodType;
import gov.nih.nci.caintegrator.dto.query.*;
import gov.nih.nci.caintegrator.enumeration.*;
import gov.nih.nci.caintegrator.service.findings.ClassComparisonFinding;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.caintegrator.service.findings.strategies.SessionBasedFindingStrategy;
import gov.nih.nci.caintegrator.util.ValidationUtility;
import gov.nih.nci.caintegrator.exceptions.FindingsAnalysisException;
import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;
import gov.nih.nci.caintegrator.exceptions.ValidationException;

import gov.nih.nci.eagle.query.dto.PatientUserListQueryDTO;

import java.util.*;

import javax.jms.JMSException;
import javax.naming.NamingException;

import org.apache.log4j.Logger;



/**
* caIntegrator License
* 
* Copyright 2001-2005 Science Applications International Corporation ("SAIC"). 
* The software subject to this notice and license includes both human readable source code form and machine readable, 
* binary, object code form ("the caIntegrator Software"). The caIntegrator Software was developed in conjunction with 
* the National Cancer Institute ("NCI") by NCI employees and employees of SAIC. 
* To the extent government employees are authors, any rights in such works shall be subject to Title 17 of the United States
* Code, section 105. 
* This caIntegrator Software License (the "License") is between NCI and You. "You (or "Your") shall mean a person or an 
* entity, and all other entities that control, are controlled by, or are under common control with the entity. "Control" 
* for purposes of this definition means (i) the direct or indirect power to cause the direction or management of such entity,
*  whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, or (iii) 
* beneficial ownership of such entity. 
* This License is granted provided that You agree to the conditions described below. NCI grants You a non-exclusive, 
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and royalty-free right and license in its rights 
* in the caIntegrator Software to (i) use, install, access, operate, execute, copy, modify, translate, market, publicly 
* display, publicly perform, and prepare derivative works of the caIntegrator Software; (ii) distribute and have distributed 
* to and by third parties the caIntegrator Software and any modifications and derivative works thereof; 
* and (iii) sublicense the foregoing rights set out in (i) and (ii) to third parties, including the right to license such 
* rights to further third parties. For sake of clarity, and not by way of limitation, NCI shall have no right of accounting
* or right of payment from You or Your sublicensees for the rights granted under this License. This License is granted at no
* charge to You. 
* 1. Your redistributions of the source code for the Software must retain the above copyright notice, this list of conditions
*    and the disclaimer and limitation of liability of Article 6, below. Your redistributions in object code form must reproduce 
*    the above copyright notice, this list of conditions and the disclaimer of Article 6 in the documentation and/or other materials
*    provided with the distribution, if any. 
* 2. Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: "This 
*    product includes software developed by SAIC and the National Cancer Institute." If You do not include such end-user 
*    documentation, You shall include this acknowledgment in the Software itself, wherever such third-party acknowledgments 
*    normally appear.
* 3. You may not use the names "The National Cancer Institute", "NCI" "Science Applications International Corporation" and 
*    "SAIC" to endorse or promote products derived from this Software. This License does not authorize You to use any 
*    trademarks, service marks, trade names, logos or product names of either NCI or SAIC, except as required to comply with
*    the terms of this License. 
* 4. For sake of clarity, and not by way of limitation, You may incorporate this Software into Your proprietary programs and 
*    into any third party proprietary programs. However, if You incorporate the Software into third party proprietary 
*    programs, You agree that You are solely responsible for obtaining any permission from such third parties required to 
*    incorporate the Software into such third party proprietary programs and for informing Your sublicensees, including 
*    without limitation Your end-users, of their obligation to secure any required permissions from such third parties 
*    before incorporating the Software into such third party proprietary software programs. In the event that You fail 
*    to obtain such permissions, You agree to indemnify NCI for any claims against NCI by such third parties, except to 
*    the extent prohibited by law, resulting from Your failure to obtain such permissions. 
* 5. For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications and 
*    to the derivative works, and You may provide additional or different license terms and conditions in Your sublicenses 
*    of modifications of the Software, or any derivative works of the Software as a whole, provided Your use, reproduction, 
*    and distribution of the Work otherwise complies with the conditions stated in this License.
* 6. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, 
*    THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. 
*    IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, SAIC, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
*    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE 
*    GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
*    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
*    OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
* 
*/

public class ClassComparisonFindingStrategy extends SessionBasedFindingStrategy {
	private static Logger logger = Logger.getLogger(ClassComparisonFindingStrategy.class);
	private ClassComparisonQueryDTO myQueryDTO = null;
	private String sessionId;
	private String taskId;
	private ClassComparisonRequest classComparisonRequest = null;
	private ClassComparisonFinding classComparisonFinding = null;
	private Collection<SampleGroup> sampleGroups = new ArrayList<SampleGroup>(); 	
	private AnalysisServerClientManager analysisServerClientManager = null;
	private BusinessTierCache cacheManager = CacheFactory.getBusinessTierCache();
	
	
	public ClassComparisonFindingStrategy(String sessionId, String taskId, ClassComparisonQueryDTO queryDTO) throws ValidationException {
		if(validate(queryDTO)) {
		   this.sessionId = sessionId;
		   this.taskId = taskId;
		    myQueryDTO = queryDTO;
		    classComparisonRequest = new ClassComparisonRequest(sessionId, taskId);
		    try {
		    	analysisServerClientManager = AnalysisServerClientManager.getInstance();
		    }
		    catch(NamingException ex) {		    	
		    	logger.error(ex.getMessage());
		    	logger.error(ex);		    	
		    }
		    catch (JMSException ex) {
		    	logger.error(ex.getMessage());
		    	logger.error(ex);			    	
		    }
		    
		}
		
		/*
		 * set finding in cache
		 */
		
		FindingStatus currentStatus = FindingStatus.Running;
		classComparisonFinding = new ClassComparisonFinding (sessionId, taskId, currentStatus, null);
		classComparisonFinding.setQueryDTO(myQueryDTO);	
		cacheManager.addToSessionCache(sessionId, taskId, classComparisonFinding);
		
	   }
	
	/*
	 * this method is used to verify if the correct number of comparison groups are chosen for T-test, 
	 * it needs to be 2 groups, for F-test, there is a separate stategy file to take care of it
	 */
	public boolean createQuery() throws FindingsQueryException{
		
		// retrieve statisticType, for now only have T-test, no Wilcoxin yet
		StatisticalMethodType statisticType = myQueryDTO.getStatisticTypeDE().getValueObject();
		if(myQueryDTO.getComparisonGroups()!= null) {
			if(statisticType.equals(StatisticalMethodType.TTest)) {
				if(myQueryDTO.getComparisonGroups().size()!= 2) {
					throw new FindingsQueryException("Incorrect Number of queries passed for the TTest type, for TTest, the number of queries has to be 2");
				}
			}
			
			return true;
		}
		
    return false;// TODO: need to check here to make sure why it is false;
		
	}
	
	/*
	 *  this method puts sample Ids for the groups into sample groups
	 *  since when the comparison groups are chosen, they are based on patient dids
	 *  and the ptdids need to convert to sampel ids
	 */
	 
	public boolean executeQuery() throws FindingsQueryException{
		
		List<ClinicalQueryDTO> clinicalQueries = myQueryDTO.getComparisonGroups();
		for(ClinicalQueryDTO clinicalDataQuery : clinicalQueries) {
			if(clinicalDataQuery instanceof PatientUserListQueryDTO) {
			  try {
				  
				  PatientUserListQueryDTO ptQuery = (PatientUserListQueryDTO)clinicalDataQuery;
				  List<String> sampleIds = ptQuery.getPatientDIDs();
				  if(sampleIds != null  && sampleIds.size() > 0){        
					  SampleGroup sampleGroup = new SampleGroup(clinicalDataQuery.getQueryName(),sampleIds.size());
                     sampleGroup.addAll(sampleIds);
                     sampleGroups.add(sampleGroup);
						 
			            }			  
			  }// end of try
			  catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getMessage());
		  			throw new FindingsQueryException(e.getMessage());
		        }
			}                   
			
		}
		
		return true;	
	  }
		
	public boolean analyzeResultSet() throws FindingsAnalysisException{
		
		
		StatisticalMethodType statisticType = myQueryDTO.getStatisticTypeDE().getValueObject();
		classComparisonRequest.setStatisticalMethod(statisticType);
	
		try{
		
			switch (statisticType){
			case TTest:
			case GLM:
				{
			
                // set baseline group			
			    Object[] obj = sampleGroups.toArray();
			    if(obj.length>=2) {
				   classComparisonRequest.setGroup1((SampleGroup)obj[0]);
				    classComparisonRequest.setBaselineGroup((SampleGroup)obj[1]);			 
			    }
			
		     // set statistical method			
		      classComparisonRequest.setStatisticalMethod(statisticType);
			
             //set MultiGroupComparisonAdjustmentType
			//  classComparisonRequest.setMultiGroupComparisonAdjustmentType(myQueryDTO.getMultiGroupComparisonAdjustmentTypeDE().getValueObject());	
			
		     // set foldchange	
			  classComparisonRequest.setFoldChangeThreshold(myQueryDTO.getExprFoldChangeDE().getValueObject());
         
			 //	set PvalueThreshold
			   classComparisonRequest.setPvalueThreshold(myQueryDTO.getStatisticalSignificanceDE().getValueObject());
		    
			
			  // set platform type
			   classComparisonRequest.setArrayPlatform(myQueryDTO.getArrayPlatformDE().getValueObjectAsArrayPlatformType()); 

			
			 
			 // go the correct matrix to fetch data
			 
			 if (classComparisonRequest.getArrayPlatform() == ArrayPlatformType.BLOOD_ARRAY) {					 
				  classComparisonRequest.setDataFileName(System.getProperty("gov.nih.nci.eagleportal.blood_data_matrix"));
				}
				else if (classComparisonRequest.getArrayPlatform() == ArrayPlatformType.TISSUE_ARRAY) {
				  classComparisonRequest.setDataFileName(System.getProperty("gov.nih.nci.eagleportal.tissue_data_matrix"));
				}
				else {
				  logger.warn("Unrecognized array platform type for ClassComparisionRequest");
				  //may want to return false and show an error on the page.
				}	
		
		
		   analysisServerClientManager.sendRequest(classComparisonRequest);		    
          return true;
	      }
         }
       } 
     catch (JMSException e) {
	      logger.error(e.getMessage());
		   throw new FindingsAnalysisException(e.getMessage());
         } 
     catch(Exception e){
	      logger.error(e.getMessage());
	       throw new FindingsAnalysisException("Error in setting ClassComparisonRequest object");
        }

    return false;
}
	
	/*
	 * This is to validate if the classComparasonQuery is valid or not.
	 */
	public boolean validate(QueryDTO queryDTO) throws ValidationException{
		 
		boolean validStatus = false;
		if(queryDTO instanceof ClassComparisonQueryDTO) {
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
	
	public Finding getFinding() {
		return classComparisonFinding;
	}

   }
	
	
	


	
	
	
	

		