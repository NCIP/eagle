/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/eagle/LICENSE.txt for details.
 */

package gov.nih.nci.eagle.service.validation;

import gov.nih.nci.caintegrator.application.configuration.SpringContext;
import gov.nih.nci.eagle.enumeration.SpecimenType;
import gov.nih.nci.eagle.util.ReadablePropertyPlaceholder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

public class ListValidationServiceImpl implements ListValidationService {

    private Map<SpecimenType, List> dataPatientMap;
    
    public List<String> validateList(List<String> items, SpecimenType specimenType) {
        if(dataPatientMap == null)
            buildMap();
        List list = dataPatientMap.get(specimenType);
        List returnList = new ArrayList(list);
        returnList.retainAll(items);
        return returnList;
    }

    private void buildMap() {
        dataPatientMap = new HashMap<SpecimenType, List>();
        ReadablePropertyPlaceholder props = (ReadablePropertyPlaceholder)SpringContext.getBean("patientLists");
        Properties properties = props.getProps();
        String tissueTumor = properties.getProperty("data.tissue_tumor");
        String tissueNormal = properties.getProperty("data.tissue_normal");
        String blood = properties.getProperty("data.blood");
        List normalList =  Arrays.asList( StringUtils.split(tissueNormal, ",") );
        List tumorList = Arrays.asList( StringUtils.split(tissueTumor, ",") );
        List bloodList = Arrays.asList( StringUtils.split(blood, ",") );
        
        dataPatientMap.put(SpecimenType.BLOOD, bloodList);
        dataPatientMap.put(SpecimenType.TISSUE_CANCER, tumorList);
        dataPatientMap.put(SpecimenType.TISSUE_NORMAL, normalList);
    }

}
