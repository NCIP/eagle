package gov.nih.nci.eagle.web.struts;

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
	private String socioEconomicLevel;
	private String residentialArea;
	private String maritialStatus;
	private String religion;
	
	//behavorial
	private String depressionScore;
	private String anxietyScore;
	
	//diet notes...multiple?  limit to 3?
	private String foodItem; //array?
	private String foodFrequency; //array?
	
	//family history
		//next 3 are multiples, consider array of hashmaps
	private String relative;
	private String condition;
	private boolean currentlyAlive; //as of?
	
	private String [] relativesWhoSmoked;
	
	//environmental tobacco smoke
		//next 4 are multiples, consider array of hashmaps
	private String jobName;
	private String startDate;
	private String endDate;
	private String smokiness;
		//next 4 are multiples, consider array of hashmaps
	private String livingCompanion;
	private String companionYears;
	private String companionHoursPerDay;
	private String companionProduct;
	
	private String [] smokingAreas;
	
}
