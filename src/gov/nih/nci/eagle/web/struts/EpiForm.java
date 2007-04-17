package gov.nih.nci.eagle.web.struts;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts.validator.ValidatorForm;

public class EpiForm extends ValidatorForm{
	
	private String queryName;
	private String patientGroup;
	
	//tobacco consumption (cigarette only for now)
	private String smokingStatus;
	private String intensity;
	private String duration;
	private String ageAtInitiation;
	private String yearsSinceQuitting;

	//tobacco dependency
	private String fagerstromScore;
	
	//patient characteristics
	private String ageLower;
	private String ageUpper;
	private String gender;
	
	private String weightLower;
	private String weightUpper;
	private String weightMeasure; //metric for now
	
	private String heightLower;
	private String heightUpper;
	private String heightMeasure; //metric for now
	
	private String waistLower;
	private String waistUpper;
	private String waistMeasure;	//metric for now
	
	private String educationLevel;
	private String socioEconomicStatus;
	private String residentialArea;
	private String maritalStatus;
	private String religion;
	
	//behavorial
	private String depressionScore;
	private String anxietyScore;

	private final Map foodItemsMap = new HashMap();
	/*
	 keys: item_n, freq_n
	 foodItems[item_1], [freq_1], [item_2], [freq_2] ...etc
	 access in struts UI:  foodItems(item_1), foodItems(frequency_1), foodItems(item_2), etc
	*/
	
	//family history
	private final Map relativesMap = new HashMap();
	/*
	 *keys:  relative_n, condition_n, currentlyAlive_n
	 */
	
	private String [] relativesWithCancer;
	
	private String [] relativesWhoSmoked;
	
	//environmental tobacco smoke
	private final Map jobsMap = new HashMap();
	/*
	keys:  jobName_n, startDate_n, endDate_n, smokiness_n
	*/

	private final Map livingCompanionsMap = new HashMap();
	/*
	keys: livingCompanion_n, companionYears_n, companionHoursPerDay_n, companionProduct_n
	*/
	
	private String [] smokingAreas;

	public String getAgeAtInitiation() {
		return ageAtInitiation;
	}

	public void setAgeAtInitiation(String ageAtInitiation) {
		this.ageAtInitiation = ageAtInitiation;
	}

	public String getAgeLower() {
		return ageLower;
	}

	public void setAgeLower(String ageLower) {
		this.ageLower = ageLower;
	}

	public String getAgeUpper() {
		return ageUpper;
	}

	public void setAgeUpper(String ageUpper) {
		this.ageUpper = ageUpper;
	}

	public String getAnxietyScore() {
		return anxietyScore;
	}

	public void setAnxietyScore(String anxietyScore) {
		this.anxietyScore = anxietyScore;
	}

	public String getDepressionScore() {
		return depressionScore;
	}

	public void setDepressionScore(String depressionScore) {
		this.depressionScore = depressionScore;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getEducationLevel() {
		return educationLevel;
	}

	public void setEducationLevel(String educationLevel) {
		this.educationLevel = educationLevel;
	}

	public String getFagerstromScore() {
		return fagerstromScore;
	}

	public void setFagerstromScore(String fagerstromScore) {
		this.fagerstromScore = fagerstromScore;
	}

	public Object getFoodItems(String key) {
		return foodItemsMap.get(key);
	}

	public void setFoodItems(String key, Object value) {
		this.foodItemsMap.put(key, value);
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHeightLower() {
		return heightLower;
	}

	public void setHeightLower(String height) {
		this.heightLower = height;
	}

	public String getHeightUpper() {
		return heightUpper;
	}

	public void setHeightUpper(String heightUpper) {
		this.heightUpper = heightUpper;
	}

	public String getHeightMeasure() {
		return heightMeasure;
	}

	public void setHeightMeasure(String heightMeasure) {
		this.heightMeasure = heightMeasure;
	}

	public String getIntensity() {
		return intensity;
	}

	public void setIntensity(String intensity) {
		this.intensity = intensity;
	}

	public Object getJobs(String key) {
		return jobsMap.get(key);
	}

	public void setJobs(String key, Object value) {
		this.jobsMap.put(key, value);
	}

	public Object getLivingCompanions(String key) {
		return livingCompanionsMap.get(key);
	}

	public void setLivingCompanions(String key, Object value) {
		this.livingCompanionsMap.put(key, value);
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
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

	public Object getRelatives(String key) {
		return relativesMap.get(key);
	}

	public void setRelatives(String key, Object value) {
		this.relativesMap.put(key, value);
	}

	public String[] getRelativesWhoSmoked() {
		return relativesWhoSmoked;
	}

	public void setRelativesWhoSmoked(String[] relativesWhoSmoked) {
		this.relativesWhoSmoked = relativesWhoSmoked;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getResidentialArea() {
		return residentialArea;
	}

	public void setResidentialArea(String residentialArea) {
		this.residentialArea = residentialArea;
	}

	public String[] getSmokingAreas() {
		return smokingAreas;
	}

	public void setSmokingAreas(String[] smokingAreas) {
		this.smokingAreas = smokingAreas;
	}

	public String getSmokingStatus() {
		return smokingStatus;
	}

	public void setSmokingStatus(String smokingStatus) {
		this.smokingStatus = smokingStatus;
	}

	public String getSocioEconomicStatus() {
		return socioEconomicStatus;
	}

	public void setSocioEconomicStatus(String socioEconomicStatus) {
		this.socioEconomicStatus = socioEconomicStatus;
	}

	public String getWaistLower() {
		return waistLower;
	}

	public void setWaistLower(String waist) {
		this.waistLower = waist;
	}

	public String getWaistUpper() {
		return waistUpper;
	}

	public void setWaistUpper(String waistUpper) {
		this.waistUpper = waistUpper;
	}

	public String getWaistMeasure() {
		return waistMeasure;
	}

	public void setWaistMeasure(String waistMeasure) {
		this.waistMeasure = waistMeasure;
	}

	public String getWeightLower() {
		return weightLower;
	}

	public void setWeightLower(String weight) {
		this.weightLower = weight;
	}

	public String getWeightUpper() {
		return weightUpper;
	}

	public void setWeightUpper(String weightUpper) {
		this.weightUpper = weightUpper;
	}

	public String getWeightMeasure() {
		return weightMeasure;
	}

	public void setWeightMeasure(String weightMeasure) {
		this.weightMeasure = weightMeasure;
	}

	public String getYearsSinceQuitting() {
		return yearsSinceQuitting;
	}

	public void setYearsSinceQuitting(String yearsSinceQuitting) {
		this.yearsSinceQuitting = yearsSinceQuitting;
	}

	public String[] getRelativesWithCancer() {
		return relativesWithCancer;
	}

	public void setRelativesWithCancer(String[] relativesWithCancer) {
		this.relativesWithCancer = relativesWithCancer;
	}
	
}
