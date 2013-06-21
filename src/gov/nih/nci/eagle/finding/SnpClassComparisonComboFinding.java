/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/eagle/LICENSE.txt for details.
 */

package gov.nih.nci.eagle.finding;

import gov.nih.nci.caintegrator.domain.finding.variation.germline.bean.GenotypeFinding;
import gov.nih.nci.caintegrator.service.findings.CompoundClassComparisonFinding;

import java.util.Collection;

public class SnpClassComparisonComboFinding extends
        CompoundClassComparisonFinding {

    private Collection<GenotypeFinding> baselineGroupSnps;
    private Collection<GenotypeFinding> comparisonGroupSnps;
    
    
    public Collection<GenotypeFinding> getComparisonGroupSnps() {
        return comparisonGroupSnps;
    }
    public void setComparisonGroupSnps(
            Collection<GenotypeFinding> comparisonGroupSnps) {
        this.comparisonGroupSnps = comparisonGroupSnps;
    }
    public Collection<GenotypeFinding> getBaselineGroupSnps() {
        return baselineGroupSnps;
    }
    public void setBaselineGroupSnps(Collection<GenotypeFinding> baselineGroupSnps) {
        this.baselineGroupSnps = baselineGroupSnps;
    }
}
