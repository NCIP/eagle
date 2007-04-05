package gov.nih.nci.eagle.web.struts;

import org.apache.struts.validator.ValidatorForm;

public class ClassComparisonForm extends ValidatorForm{

	private String [] existingCovariates;
	private String [] selectedCovariates;
	
	private String [] existingGroups;
	private String [] selectedGroups;
	private String baseline;
	
	private String analysisName;
	private String statisticalMethod;
	
	private String foldChange;
	private String pvalue;
	
	private String platform;

	public String getAnalysisName() {
		return analysisName;
	}

	public void setAnalysisName(String analysisName) {
		this.analysisName = analysisName;
	}

	public String getBaseline() {
		return baseline;
	}

	public void setBaseline(String baseline) {
		this.baseline = baseline;
	}

	public String[] getExistingCovariates() {
		return existingCovariates;
	}

	public void setExistingCovariates(String[] existingCovariates) {
		this.existingCovariates = existingCovariates;
	}

	public String[] getExistingGroups() {
		return existingGroups;
	}

	public void setExistingGroups(String[] existingGroups) {
		this.existingGroups = existingGroups;
	}

	public String getFoldChange() {
		return foldChange;
	}

	public void setFoldChange(String foldChange) {
		this.foldChange = foldChange;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getPvalue() {
		return pvalue;
	}

	public void setPvalue(String pvalue) {
		this.pvalue = pvalue;
	}

	public String[] getSelectedCovariates() {
		return selectedCovariates;
	}

	public void setSelectedCovariates(String[] selectedCovariates) {
		this.selectedCovariates = selectedCovariates;
	}

	public String[] getSelectedGroups() {
		return selectedGroups;
	}

	public void setSelectedGroups(String[] selectedGroups) {
		this.selectedGroups = selectedGroups;
	}

	public String getStatisticalMethod() {
		return statisticalMethod;
	}

	public void setStatisticalMethod(String statisticalMethod) {
		this.statisticalMethod = statisticalMethod;
	}
}
