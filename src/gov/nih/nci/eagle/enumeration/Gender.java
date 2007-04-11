package gov.nih.nci.eagle.enumeration;

public enum Gender {
    MALE(1, "Male"), 
    FEMALE(2, "Female");

    private int key;
    private String name;

    Gender(int key, String name) {
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
