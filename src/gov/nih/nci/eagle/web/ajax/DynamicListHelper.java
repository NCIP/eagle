package gov.nih.nci.eagle.web.ajax;

import gov.nih.nci.caintegrator.application.lists.ListSubType;
import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.ajax.CommonListFunctions;
import gov.nih.nci.eagle.util.EAGLEListFilter;
import gov.nih.nci.eagle.util.EAGLEListValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import javax.servlet.http.HttpSession;

public class DynamicListHelper {
/**
 * Basically a wrapper for app-commons/application/lists/ajax/CommonListFunctions
 * except this specifically sets the ListValidator for this context and passes it off
 * to the CommonListFunctions
 *
 */
	
	public DynamicListHelper() {}
	
    	
	public static String createGenericList(String listType, List<String> list, String name) throws OperationNotSupportedException	{
        try {
            String[] tps = CommonListFunctions.parseListType(listType);
            //tps[0] = ListType
            //tps[1] = ListSubType (if not null)
            ListType lt = ListType.valueOf(tps[0]);
            if(tps.length > 1 && tps[1] != null){
                //create a list out of [1]
                ArrayList<ListSubType> lst = new ArrayList();
                lst.add(ListSubType.valueOf(tps[1]));
                EAGLEListValidator lv = new EAGLEListValidator(ListType.valueOf(tps[0]), ListSubType.valueOf(tps[1]), list);
                return CommonListFunctions.createGenericList(lt, lst.get(0), list, name, lv);
            }
            else if(tps.length >0 && tps[0] != null)    {
                //no subtype, only a primary type - typically a PatientDID then
                EAGLEListValidator lv = new EAGLEListValidator(ListType.valueOf(tps[0]), list);
                return CommonListFunctions.createGenericList(lt, list, name, lv);
            }
            else    {
                //no type or subtype, not good, force to clinical in catch                
                throw new Exception();
            }
        }
        catch(Exception e)  {
            //try as a patient list as default, will fail validation if its not accepted
            return CommonListFunctions.createGenericList(ListType.PatientDID, list, name, new EAGLEListValidator(ListType.PatientDID, list));
        }
    }
	
	public static String getAllLists()	{
		//create a list of allowable types
		ArrayList listTypesList = new ArrayList();
		for(ListType l  : EAGLEListFilter.values())	{
			listTypesList.add(l.toString());
		}
		//call CommonListFunctions.getAllLists(listTypesList);
		return CommonListFunctions.getAllLists(listTypesList);
	}
	
	public static String exportListasTxt(String name, HttpSession session){
		return CommonListFunctions.exportListasTxt(name, session);
	}

		
	public static String uniteLists(String[] sLists, String groupName, String groupType, String action)	{	
		return CommonListFunctions.uniteLists(sLists, groupName, groupType, action);
	}
}
