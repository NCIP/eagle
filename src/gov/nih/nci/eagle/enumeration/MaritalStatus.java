/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/eagle/LICENSE.txt for details.
 */

package gov.nih.nci.eagle.enumeration;

public enum MaritalStatus {
    MARRIED(1,"Married"),
    COHABITING(2,"Cohabiting"),
    SEPARATED(3,"Separated"),
    WIDOWED(4,"Widowed"),
    DIVORCED(5,"Divorced"),
    SINGLE(6,"Single"); 

    private final int value;
    private final String name;

    MaritalStatus(int key, String name) {
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
