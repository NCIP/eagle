package gov.nih.nci.eagle.analysis.messaging;

import java.util.HashMap;

import gov.nih.nci.caintegrator.analysis.messaging.SampleGroup;

public class EagleSampleGroup extends SampleGroup {

	//[sid] => map of annotations ( [age] => 35, [gender] => M , ...)
	public HashMap<String, HashMap> annotationMap;
	
	public EagleSampleGroup(String groupName, HashMap<String,HashMap> am) {
		super(groupName, 50);
		this.setAnnotationMap(am);
	}
	
	public HashMap<String, HashMap> getAnnotationMap() {
		return annotationMap;
	}

	public void setAnnotationMap(HashMap<String, HashMap> annotationMap) {
		this.annotationMap = annotationMap;
	}
}
