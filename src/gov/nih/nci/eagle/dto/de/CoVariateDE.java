package gov.nih.nci.eagle.dto.de;

import gov.nih.nci.caintegrator.dto.de.DomainElement;
import gov.nih.nci.caintegrator.enumeration.CoVariateType;

import java.io.Serializable;

public class CoVariateDE extends DomainElement implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Initializes a newly created <code>CoVariateDE</code> object so that it
	 * represents a CoVariateDE.
	 */
	public CoVariateDE(CoVariateType coVariateType) {
		super(coVariateType);
	}
	
	
	/**
	 * Sets the value for this <code>CoVariateDE</code> object
	 * 
	 * @param object
	 *            the value
	 */
	public void setValue(Object obj) throws Exception {
		if (!(obj instanceof CoVariateType))
			throw new Exception(
					"Could not set the value.  Parameter is of invalid data type: "
							+ obj);
		setValueObject((CoVariateType) obj);
	}
	
	
	/**
	 * Returns the coVariateType for this CoVariateDE obect.
	 * 
	 * @return the coVariateType for this <code>CoVariateDE</code> object
	 */
	public  CoVariateType  getValueObject() {
		return (CoVariateType) getValue();
	}
	
	
	/**
	 * Sets the statisticType for this <code>CoVariateDE</code> object
	 * 
	 * @param CoVariateType
	 *            the CoVariateType
	 */
	public void setValueObject(CoVariateType coVariateType) {
		if (coVariateType != null) {
			value = coVariateType;
		}
	}
	
	/**
	 * Overrides the protected Object.clone() method exposing it as public.
	 * It performs a 2 tier copy, that is, it does a memcopy of the instance
	 * and then sets all the non-primitive data fields to clones of themselves.
	 * 
	 * @return -A minimum 2 deep copy of this object.
	 */
	public Object clone() {
		CoVariateDE myClone = (CoVariateDE) super.clone();
		return myClone;
	}

}
