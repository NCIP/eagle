package gov.nih.nci.eagle.web.struts;

import java.util.List;

import org.apache.struts.validator.ValidatorForm;

public class SnpForm extends ValidatorForm {

	private String queryName;
	private List<String> groupNames;
	private String snp;
	
	
	public List<String> getGroupNames() {
		return groupNames;
	}
	public void setGroupNames(List<String> groupNames) {
		this.groupNames = groupNames;
	}
	public String getQueryName() {
		return queryName;
	}
	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}
	public String getSnp() {
		return snp;
	}
	public void setSnp(String snp) {
		this.snp = snp;
	}
}
