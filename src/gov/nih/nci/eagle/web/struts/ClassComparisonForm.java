/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/eagle/LICENSE.txt for details.
 */

package gov.nih.nci.eagle.web.struts;

import java.util.List;

import org.apache.struts.validator.ValidatorForm;

public class ClassComparisonForm extends ValidatorForm{

	//TODO:  store the cacheId here as a string?
	
	private List existingCovariates;
	private String [] selectedCovariates;
	
	private String covariate;
	
	private List existingGroups;
	private List existingTissueGroups;
	
	private String [] selectedGroups;
	private String baseline;
	
	private String analysisName;
	private String statisticalMethod;
	
	private String foldChange;
	private String pvalue;
	
	private String platform;
	
	private List existingSpecimenTypes;
	private String specimenType;

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

	public List getExistingCovariates() {
		return existingCovariates;
	}

	public void setExistingCovariates(List existingCovariates) {
		this.existingCovariates = existingCovariates;
	}
	
	
	public String getCovariate() {
		return covariate;
	}

	public void setCovariate(String covariate) {
		this.covariate = covariate;
	}

	public List getExistingGroups() {
		return existingGroups;
	}

	public void setExistingGroups(List existingGroups) {
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

	public String getSpecimenType() {
		return specimenType;
	}

	public void setSpecimenType(String specimenType) {
		this.specimenType = specimenType;
	}

	public List getExistingSpecimenTypes() {
		return existingSpecimenTypes;
	}

	public void setExistingSpecimenTypes(List existingSpecimenTypes) {
		this.existingSpecimenTypes = existingSpecimenTypes;
	}

	public List getExistingTissueGroups() {
		return existingTissueGroups;
	}

	public void setExistingTissueGroups(List existingTissueGroups) {
		this.existingTissueGroups = existingTissueGroups;
	}
}
