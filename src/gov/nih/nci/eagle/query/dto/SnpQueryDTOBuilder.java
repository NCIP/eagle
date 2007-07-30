package gov.nih.nci.eagle.query.dto;

import gov.nih.nci.caintegrator.application.dtobuilder.QueryDTOBuilder;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.eagle.web.struts.SnpForm;

import java.util.Collection;

public class SnpQueryDTOBuilder implements QueryDTOBuilder {

    public QueryDTO buildQueryDTO(Object form, String cacheId) {
        SnpForm snpForm = (SnpForm) form;

        SnpQueryDTO dto = new SnpQueryDTO();

        // Populate snp id from form
        if (snpForm.getSnp() != null) {
            dto.setSnpId(snpForm.getSnp());
        }

        // Populate query name
        if (snpForm.getQueryName() != null) {
            dto.setQueryName(snpForm.getQueryName());
        }

        // Populate patient ids from group
        UserListBeanHelper ulbh = new UserListBeanHelper(cacheId);

        if (snpForm.getGroupName() != null) {

            Collection<String> ids = null;

            ids = ulbh.getItemsFromList(snpForm.getGroupName());
            dto.setPatientIds(ids);
        }
        return dto;

    }

}
