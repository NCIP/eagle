/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/eagle/LICENSE.txt for details.
 */

package gov.nih.nci.eagle.web.ajax;

public class Feature {

    public String type;
    public String featureId;
    public String chromosome;
    public Long physicalLocation;
    
    public String getChromosome() {
        return chromosome;
    }
    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }
    public String getFeatureId() {
        return featureId;
    }
    public void setFeatureId(String featureId) {
        this.featureId = featureId;
    }
    public Long getPhysicalLocation() {
        return physicalLocation;
    }
    public void setPhysicalLocation(Long physicalLocation) {
        this.physicalLocation = physicalLocation;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    
    @Override
    public boolean equals(Object obj) {
        Feature f = (Feature)obj;
        if(!chromosome.equals(f.getChromosome()))
            return false;
        if(!featureId.equals(f.getFeatureId()))
            return false;
        if(!physicalLocation.equals(f.getPhysicalLocation()))
            return false;
        return (type.equals(f.getType()));
    }
}
