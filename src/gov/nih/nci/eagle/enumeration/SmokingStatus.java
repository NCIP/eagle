package gov.nih.nci.eagle.enumeration;

public enum SmokingStatus {
    NEVER(0, "Never Smoker"), 
    FORMER(1, "Former Smoker"), 
    CURRENT(2, "Current Smoker"), 
    NO_INFO(9, "No Information");

    private int key;
    private String name;

    SmokingStatus(int key, String name) {
        this.key = key;
    	this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public int getKey()	{
    	return key;
    }
    
}
