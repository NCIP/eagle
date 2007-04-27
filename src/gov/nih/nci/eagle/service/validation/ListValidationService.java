package gov.nih.nci.eagle.service.validation;

import gov.nih.nci.eagle.enumeration.SpecimenType;

import java.util.List;

public interface ListValidationService {

    public List<String> validateList(List<String> items, SpecimenType specimenType);
    
}
