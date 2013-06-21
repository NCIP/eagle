/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/eagle/LICENSE.txt for details.
 */

package gov.nih.nci.eagle.web.ajax;

import java.util.Collection;

public interface AnnotationService {

    public Collection<ChromosomeCytoband> getChromosomeAnnotations(String chromosome);
}
