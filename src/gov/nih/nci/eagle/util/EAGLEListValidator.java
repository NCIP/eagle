/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/eagle/LICENSE.txt for details.
 */

package gov.nih.nci.eagle.util;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import gov.nih.nci.caintegrator.application.lists.ListSubType;
import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.ListValidator;

public class EAGLEListValidator extends ListValidator{

    /**
     * 
     */
    public EAGLEListValidator() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @param listType
     * @param unvalidatedList
     * @throws OperationNotSupportedException
     */
    public EAGLEListValidator(ListType listType, List<String> unvalidatedList) throws OperationNotSupportedException {
        super(listType, unvalidatedList);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param listType
     * @param listSubType
     * @param unvalidatedList
     * @throws OperationNotSupportedException
     */
    public EAGLEListValidator(ListType listType, ListSubType listSubType, List<String> unvalidatedList) throws OperationNotSupportedException {
        super(listType, listSubType, unvalidatedList);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void validate(ListType listType, List<String> unvalidatedList) throws OperationNotSupportedException {
        // TODO Auto-generated method stub
        validList.addAll(unvalidatedList);
       
    }

    @Override
    public void validate(ListType listType, ListSubType listSubType, List<String> unvalidatedList) throws OperationNotSupportedException {
        /**TODO 
         * add validation!
         */
        validList.addAll(unvalidatedList);
        
    }

}
