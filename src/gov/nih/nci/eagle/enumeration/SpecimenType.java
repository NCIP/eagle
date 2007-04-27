package gov.nih.nci.eagle.enumeration;

public enum SpecimenType {
    BLOOD(1, "Blood"), 
    TISSUE_NORMAL(2, "Tissue - Normal"),
    TISSUE_CANCER(3, "Tissue - Cancer");

    private final int value;
    private final String name;

    SpecimenType(int key, String name) {
        this.value = key;
    	this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public int getValue()	{
    	return value;
    }
    
}
