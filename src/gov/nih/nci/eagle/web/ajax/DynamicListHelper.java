/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/eagle/LICENSE.txt for details.
 */

package gov.nih.nci.eagle.web.ajax;

import gov.nih.nci.caintegrator.application.lists.ListSubType;
import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.caintegrator.application.lists.ajax.CommonListFunctions;
import gov.nih.nci.eagle.enumeration.SpecimenType;
import gov.nih.nci.eagle.service.validation.ListValidationService;
import gov.nih.nci.eagle.util.EAGLEListFilter;
import gov.nih.nci.eagle.util.EAGLEListValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import uk.ltd.getahead.dwr.ExecutionContext;

public class DynamicListHelper {
/**
 * Basically a wrapper for app-commons/application/lists/ajax/CommonListFunctions
 * except this specifically sets the ListValidator for this context and passes it off
 * to the CommonListFunctions
 *
 */
	
	public DynamicListHelper() {}
	
    private ListValidationService listValidationService;
   	
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
		
		/*
		//call CommonListFunctions.getAllLists(listTypesList);
		//deconstruct the json array and add some attributes for each samples (blood-y/n, tissuetumor-y/n, tissuenormal-y/n, or 1 field called specimens with these enums)
		JSONArray listContainerArray = new JSONArray();
		Object o = JSONValue.parse(CommonListFunctions.getAllLists(listTypesList));
		listContainerArray = (JSONArray)o;
		for(Object lc : listContainerArray){
			JSONObject listContainer = (JSONObject)lc;
			//list container: [listType], [listItems ]
			//listItems = JSONArray of JSONObjects
			JSONArray listItems = new JSONArray();
			Object li = listContainer.get("listItems");
			listItems = (JSONArray)li;
			for(Object l : listItems){
				JSONObject myList = (JSONObject)l;
				String items = myList.get("listItems").toString();
				//items in comma seperated format
			}
		}
		return listContainerArray.toString();
		*/
		
		return CommonListFunctions.getAllLists(listTypesList);
	}
	
	public String getDetailsFromList(String listName)	{
		WebContext ctx = WebContextFactory.get();
		String cacheId = ctx.getSession(false).getId();
		UserListBeanHelper ulbh = new UserListBeanHelper(cacheId);
		String jdetails = ulbh.getDetailsFromList(listName);
		Object o = JSONValue.parse(jdetails);
		//object [string -> listName], [string -> listType], [jsonArray->validItems]
		//validItems array of JSONObjects [name], [notes], [rank]
		JSONObject listDetails = (JSONObject)o;
		Object oo = listDetails.get("validItems");
		JSONArray validItems = (JSONArray)oo;
		for(Object ooo : validItems){
			JSONObject itemDesc = (JSONObject)ooo;
			//see which specimens this item has
			ArrayList specimens = new ArrayList();
			for(SpecimenType sp : SpecimenType.values()){
				List justone = new ArrayList();
				justone.add(itemDesc.get("name"));
				List shouldbeone = listValidationService.validateList(justone, sp);
				if(shouldbeone.size()==1){
					//hit
					specimens.add(sp.toString());
				}
				else	{
					specimens.add("");
				}
			}
			itemDesc.put("specimens", StringUtils.join(specimens.toArray(), ","));
		}

		return listDetails.toString();
	}
	
	public static String exportListasTxt(String name, HttpSession session){
		return CommonListFunctions.exportListasTxt(name, session);
	}

		
	public static String uniteLists(String[] sLists, String groupName, String groupType, String action)	{	
		return CommonListFunctions.uniteLists(sLists, groupName, groupType, action);
	}

	public ListValidationService getListValidationService() {
		return listValidationService;
	}

	public void setListValidationService(ListValidationService listValidationService) {
		this.listValidationService = listValidationService;
	}
}
