package gov.nih.nci.eagle.query.dto;

import gov.nih.nci.caintegrator.dto.query.QueryDTO;

import java.util.Collection;

public class SnpQueryDTO implements QueryDTO {

    private String queryName;
    private String snpId;
    private String patientGroupName;
    private Collection<String> patientIds;
    
    public Collection<String> getPatientIds() {
        return patientIds;
    }

    public void setPatientIds(Collection<String> patientIds) {
        this.patientIds = patientIds;
    }

    public String getPatientGroupName() {
        return patientGroupName;
    }

    public void setPatientGroupName(String patientGroupName) {
        this.patientGroupName = patientGroupName;
    }

    public String getSnpId() {
        return snpId;
    }

    public void setSnpId(String snpId) {
        this.snpId = snpId;
    }

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String name) {
        queryName = name;
    }

}
