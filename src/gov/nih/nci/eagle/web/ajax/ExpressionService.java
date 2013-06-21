/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/eagle/LICENSE.txt for details.
 */

package gov.nih.nci.eagle.web.ajax;

import gov.nih.nci.eagle.ui.rde.ContinuousDataSource;

import java.util.List;

public interface ExpressionService {

    public List<ExpressionValue> getExpressionValuesForRegion(String chromosome, Long start, Long end);
    public void setContinuousDataSource(ContinuousDataSource source);
}
