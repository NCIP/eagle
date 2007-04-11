package gov.nih.nci.eagle.enumeration;

public enum Religion {
    NONE(1, "None"),
    CATHOLIC(2, "Catholic"),
    JEWISH(3,"Jewish"),
    MOSLEM(4,"Moslem"),
    PROTESTANT(5,"Protestant"),
    OTHER(6,"Other"),
    DONT_KNOW(7,"Don't know");

    private final int key;
    private final String name;

    Religion(int key, String name) {
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
