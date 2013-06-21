/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/eagle/LICENSE.txt for details.
 */

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
