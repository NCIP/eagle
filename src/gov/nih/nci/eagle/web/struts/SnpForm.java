package gov.nih.nci.eagle.web.struts;

import java.util.List;

import org.apache.struts.validator.ValidatorForm;

public class SnpForm extends ValidatorForm {

	private String queryName;
	private String groupName;
	private String snp;
	
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
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
