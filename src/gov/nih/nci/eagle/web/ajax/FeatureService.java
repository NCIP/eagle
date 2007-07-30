package gov.nih.nci.eagle.web.ajax;

import java.util.Collection;

public interface FeatureService {

    public Object getSnpCalls(String snpId, Collection<String> patientList1, Collection<String> patientList2);
}
