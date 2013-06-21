/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/eagle/LICENSE.txt for details.
 */

package gov.nih.nci.eagle.query.dto;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.caintegrator.dto.query.ClinicalQueryDTO;

public class PatientUserListQueryDTO implements ClinicalQueryDTO {
	
	private static final long serialVersionUID = 1L;

	private String queryName;    
    private boolean isBaseline = false;	
	private List<String> patientDIDs = new ArrayList<String>();
	
	
	public PatientUserListQueryDTO() {
		super();		
	} 	
	
	
	public PatientUserListQueryDTO(HttpSession session, String listName) {
        super();
        setQueryName(listName);
        setPatientDIDs(session);
    }
    

	public void setQueryName(String name) {
		this.queryName = name;

	}

	public String getQueryName() {		
		return queryName;
	}
	
	
	/**
     * @return Returns the isBaseline.
     */
    public boolean isBaseline() {
        return isBaseline;
    }


    /**
     * @param isBaseline The isBaseline to set.
     */
    public void setBaseline(boolean isBaseline) {
        this.isBaseline = isBaseline;
    }
    
    public void setPatientDIDs(HttpSession session){        
        UserListBeanHelper helper = new UserListBeanHelper(session);
        patientDIDs = helper.getItemsFromList(this.queryName);        
    }
    
	public void setPatientDIDs(List<String> sampleIds){        
	     patientDIDs = sampleIds;        
	 }
	 
	 /**
	     * @return Returns the patientDIDs.
	     */
	public List<String> getPatientDIDs() {
	      return patientDIDs;
	 }

}
