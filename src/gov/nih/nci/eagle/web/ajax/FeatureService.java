package gov.nih.nci.eagle.web.ajax;

import gov.nih.nci.eagle.ui.rde.CategoricalDataSource;

import java.util.Collection;

public interface FeatureService {

    public Object getFeatureDetails(String snpId, Collection<String> patientList1, Collection<String> patientList2);

    public Collection<Feature> getFeaturesForRegion(String chromosome, Long start, Long stop);

    public Collection<Feature> getFeaturesForRegion(String chromosome);

    public void setCategoricalDatasource(CategoricalDataSource source);
    
    
}
