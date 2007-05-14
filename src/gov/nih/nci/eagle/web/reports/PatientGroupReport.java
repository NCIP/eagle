package gov.nih.nci.eagle.web.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;

public class PatientGroupReport {

    private List patients;
    private String groupName;
    
    public PatientGroupReport() {
        
    }
 
    public void generateCSV(ActionEvent event)	{
    	List<List> csv = new ArrayList<List>();
    	
    	ArrayList labels = new ArrayList();
    	labels.add("subject id");
    	labels.add("sex");
    	labels.add("smoking status");
    	labels.add("grade");
   		for(Object l : patients){
   			HashMap<String, HashMap> m = (HashMap<String, HashMap>)l;
   			if(csv.size()==0){
   				csv.add(labels);
   			}
   			ArrayList row = new ArrayList();
   			row.add(m.get("patientId"));
   			row.add(m.get("sex"));
   			row.add(m.get("smoking_status"));
   			row.add(m.get("grade"));  			
   			csv.add(row);
		}
		
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
		try {
			CSVUtil.renderCSV(response, csv);
		} catch (Exception e) {
			// TODO: handle exception
		} finally	{
			FacesContext.getCurrentInstance().responseComplete();
		}

    }
    
    public void setPatients(List patients) {
        this.patients = patients;
    }
    
    public List getPatients() {
        return patients;
    }

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}
