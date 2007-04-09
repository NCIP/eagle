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
	private String weight;
	private String weightMeasure;
	private String height;
	private String heightMeasure;
	private String waist;
	private String waistMeasure;
	private String educationLevel;
	private String socioEconomicStatus;
	private String residentialArea;
	private String maritalStatus;
	private String religion;
	
	//behavorial
	private String depressionScore;
	private String anxietyScore;
	
	//diet notes...multiple?  limit to 3?
	private final Map foodItemsMap = new HashMap();
	// foodItems[item1], [freq1], [item2], [freq2] ...etc
	// access in struts UI:  foodItems(item1), foodItems(frequency1), foodItems(item2), etc
	// code proper get/sets
	//private String foodItem; //array?
	//private String foodFrequency; //array?
	
	//family history
		//next 3 are multiples, consider array of hashmaps
	private final Map relativesMap = new HashMap();
	//private String relative;
	//private String condition;
	//private boolean currentlyAlive; //as of?
	
	private String [] relativesWhoSmoked;
	
	//environmental tobacco smoke
		//next 4 are multiples, consider array of hashmaps
	private final Map jobsMap = new HashMap();
	/*
	private String jobName;
	private String startDate;
	private String endDate;
	private String smokiness;
	*/
		//next 4 are multiples, consider array of hashmaps
	private final Map livingCompanionsMap = new HashMap();
	/*
	private String livingCompanion;
	private String companionYears;
	private String companionHoursPerDay;
	private String companionProduct;
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

	public void setFoodItems(String key, String value) {
		this.foodItemsMap.put(key, value);
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
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

	public void setJobs(String key, String value) {
		this.jobsMap.put(key, value);
	}

	public Object getLivingCompanions(String key) {
		return livingCompanionsMap.get(key);
	}

	public void setLivingCompanions(String key, String value) {
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

	public void setRelatives(String key, String value) {
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

	public String getWaist() {
		return waist;
	}

	public void setWaist(String waist) {
		this.waist = waist;
	}

	public String getWaistMeasure() {
		return waistMeasure;
	}

	public void setWaistMeasure(String waistMeasure) {
		this.waistMeasure = waistMeasure;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
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
	
}
