/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/eagle/LICENSE.txt for details.
 */

package gov.nih.nci.eagle.enumeration;

public enum Gender {
    MALE(1, "Male"), 
    FEMALE(2, "Female");

    private final int value;
    private final String name;

    Gender(int key, String name) {
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
