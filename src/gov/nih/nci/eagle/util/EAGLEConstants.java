package gov.nih.nci.eagle.util;

public final class EAGLEConstants {

    public static final String userInfoBean = "userInfoBean";
    public static final String loggedIn = "loggedIn";
    public static final String NOT_INCLUDED = "none";
    public static final String ALL = "all";
    public static final int PB_BLAST_COLUMNS = 2;
    public static final int BM_BLAST_COLUMNS = 4;
    public static final String  APPLICATION_RESOURCES = "ApplicationResources"; //test


    public static String getCSMAppName()	{
		String appname = System.getProperty("gov.nih.nci.eagle.csm_app_name") != null ? (String) System.getProperty("gov.nih.nci.eagle.csm_app_name") : "eagle";
		return appname;
	}
}
