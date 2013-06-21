/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/eagle/LICENSE.txt for details.
 */

package gov.nih.nci.eagle.ui.rde;

import gov.nih.nci.caintegrator.domain.annotation.snp.bean.SNPAnnotation;
import gov.nih.nci.caintegrator.domain.finding.variation.germline.bean.GenotypeFinding;
import gov.nih.nci.eagle.finding.SnpClassComparisonComboFinding;
import gov.nih.nci.eagle.web.ajax.Feature;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CgomCategoricalDataSource implements CategoricalDataSource {

    private List<Feature> categoricalData;

    public CgomCategoricalDataSource(SnpClassComparisonComboFinding snpFinding) {
        Collection<GenotypeFinding> baselineFindings = snpFinding.getBaselineGroupSnps();
        Collection<GenotypeFinding> comparisonFindings = snpFinding.getComparisonGroupSnps();
        Set<Feature> tempSet = new HashSet<Feature>();
        for(GenotypeFinding finding : baselineFindings) {
            SNPAnnotation annotation = finding.getSnpAnnotation();
            Feature f = new Feature();
            f.setChromosome(annotation.getChromosomeName());
            f.setFeatureId(annotation.getDbsnpId());
            f.setPhysicalLocation(annotation.getChromosomeLocation());
            f.setType("snp");
            tempSet.add(f);
        }
        
        for(GenotypeFinding finding : comparisonFindings) {
            SNPAnnotation annotation = finding.getSnpAnnotation();
            Feature f = new Feature();
            f.setChromosome(annotation.getChromosomeName());
            f.setFeatureId(annotation.getDbsnpId());
            f.setPhysicalLocation(annotation.getChromosomeLocation());
            f.setType("snp");
            tempSet.add(f);
        }
        categoricalData = new ArrayList<Feature>(tempSet);
    }

    public List<Feature> getCategoricalData() {

        return categoricalData;
    }

}
