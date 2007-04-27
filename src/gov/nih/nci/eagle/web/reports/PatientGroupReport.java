package gov.nih.nci.eagle.web.reports;

import java.util.List;

public class PatientGroupReport {

    private List patients;
    
    public PatientGroupReport() {
        
    }
    
    public void setPatients(List patients) {
        this.patients = patients;
    }
    
    public List getPatients() {
        return patients;
    }
}
