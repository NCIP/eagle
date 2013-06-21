/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/eagle/LICENSE.txt for details.
 */

package gov.nih.nci.eagle.web.reports;

import java.util.List;

public interface ReportBean {

    public List getRowLabels();
    public List getRow();
}
