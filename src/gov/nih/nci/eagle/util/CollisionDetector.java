package gov.nih.nci.eagle.util;

import gov.nih.nci.caintegrator.application.lists.UserList;

import java.util.List;

public class CollisionDetector {
	
	public static String renameOnCollision(String needle, List<String> haystack){
    	String iKey = "_copy";
    	
    	String cleanName = needle;
    	int i = 0;
    	 
    		if(haystack.contains(needle))	{ 		
    			 //hit
    			 if(cleanName.indexOf(iKey)!= -1)	{
    				 String c = cleanName.substring(cleanName.indexOf(iKey)+iKey.length());
    				 Integer it;
    				 try	{
    					 it = Integer.parseInt(c)+1;
    				 }
    				 catch (Exception e) {
    					 it = 1;
					}
    				 cleanName = cleanName.substring(0, cleanName.indexOf(iKey)) +  iKey + it; 
    			 }
    			 else	{
    				 //this is the first hit
    				 cleanName += iKey+"1";
    			 }
    			 cleanName = renameOnCollision(cleanName, haystack);
    		 }
   
    	//get the names of all the lists in the session
    	//see if listname == any of the current names
    	//if collide, rename new list to listname_1, and recheck until we dont collide
    	return cleanName;
	}
}
