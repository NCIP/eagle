package gov.nih.nci.eagle.enumeration;

public enum Religion {
    NONE(1, "None"),
    CATHOLIC(2, "Catholic"),
    JEWISH(3,"Jewish"),
    MOSLEM(4,"Moslem"),
    PROTESTANT(5,"Protestant"),
    OTHER(6,"Other"),
    DONT_KNOW(7,"Don't know");

    private final int value;
    private final String name;

    Religion(int key, String name) {
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
