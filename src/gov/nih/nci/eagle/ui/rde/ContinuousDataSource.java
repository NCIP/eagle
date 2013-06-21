/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/eagle/LICENSE.txt for details.
 */

package gov.nih.nci.eagle.ui.rde;

import gov.nih.nci.eagle.web.ajax.ExpressionValue;

import java.util.List;

public interface ContinuousDataSource {

    public List<ExpressionValue> getContinuousData();
}
