package gov.nih.nci.eagle.query.dto;

import gov.nih.nci.caintegrator.dto.query.QueryDTO;

public class EagleClinicalQueryDTO implements QueryDTO {

    private String queryName;
    
    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String name) {
        queryName = name;
    }

}
