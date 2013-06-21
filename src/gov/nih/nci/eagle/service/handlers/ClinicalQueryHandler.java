/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/eagle/LICENSE.txt for details.
 */

package gov.nih.nci.eagle.service.handlers;

import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.studyQueryService.QueryHandler;
import gov.nih.nci.eagle.query.dto.EagleClinicalQueryDTO;

import java.util.List;

import org.hibernate.SessionFactory;

public class ClinicalQueryHandler implements QueryHandler {

    private SessionFactory sessionFactory;
    
    public boolean canHandle(QueryDTO query) {
        return (query instanceof EagleClinicalQueryDTO);
    }

    public Integer getResultCount(QueryDTO query) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
    }

    public List getResults(QueryDTO dto, Integer page) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
    }

    public List getResults(QueryDTO query) {
        System.out.println("issuing clinical query");
        return null;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
