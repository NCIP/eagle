/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/eagle/LICENSE.txt for details.
 */

package gov.nih.nci.eagle.enumeration;

public enum SmokingStatus {
    NEVER(0, "Never Smoker"), 
    FORMER(1, "Former Smoker"), 
    CURRENT(2, "Current Smoker"), 
    NO_INFO(9, "No Information");

    private final int value;
    private final String name;

    SmokingStatus(int key, String name) {
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
