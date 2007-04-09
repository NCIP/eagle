package gov.nih.nci.eagle.web.struts;

import org.apache.struts.validator.ValidatorForm;

public class ClinicalForm extends ValidatorForm{
	
	private String queryName;
	private String patientGroup;
	
	private String tvalue;
	private String nvalue;
	private String mvalue;
	
	private String [] stage;
	private String [] grade;
	private String [] histology;
	
	private String cotanineMin;
	private String cotanineMax;
	
	
	public String getCotanineMax() {
		return cotanineMax;
	}
	public void setCotanineMax(String cotanineMax) {
		this.cotanineMax = cotanineMax;
	}
	public String getCotanineMin() {
		return cotanineMin;
	}
	public void setCotanineMin(String cotanineMin) {
		this.cotanineMin = cotanineMin;
	}
	public String[] getGrade() {
		return grade;
	}
	public void setGrade(String[] grade) {
		this.grade = grade;
	}
	public String[] getHistology() {
		return histology;
	}
	public void setHistology(String[] histology) {
		this.histology = histology;
	}
	public String getNvalue() {
		return nvalue;
	}
	public void setNvalue(String nvalue) {
		this.nvalue = nvalue;
	}
	public String getPatientGroup() {
		return patientGroup;
	}
	public void setPatientGroup(String patientGroup) {
		this.patientGroup = patientGroup;
	}
	public String getQueryName() {
		return queryName;
	}
	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}
	public String[] getStage() {
		return stage;
	}
	public void setStage(String[] stage) {
		this.stage = stage;
	}
	public String getTvalue() {
		return tvalue;
	}
	public void setTvalue(String tvalue) {
		this.tvalue = tvalue;
	}
	public String getMvalue() {
		return mvalue;
	}
	public void setValue(String value) {
		this.mvalue = value;
	}
}
