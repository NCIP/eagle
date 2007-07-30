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
}
