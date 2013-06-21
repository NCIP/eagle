/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/eagle/LICENSE.txt for details.
 */

package gov.nih.nci.eagle.service.handlers;

import gov.nih.nci.caintegrator.domain.finding.variation.germline.bean.GenotypeFinding;
import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.studyQueryService.QueryHandler;
import gov.nih.nci.eagle.query.dto.SnpQueryDTO;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;

public class SnpQueryHandler implements QueryHandler {

    private SessionFactory sessionFactory;
    
    public List getResults(QueryDTO query) {
        Session sess = sessionFactory.getCurrentSession();
        Criteria criteria = sess.createCriteria(GenotypeFinding.class);
        criteria.createAlias("specimen", "sp");
        criteria.createAlias("specimen.studyParticipant", "patient");
        criteria.createAlias("snpAnnotation", "snp");
        //criteria.createCriteria("specimen.studyParticipant.epidemiologicalFinding", "finding").setFetchMode("relativeCollection", FetchMode.JOIN);
        
        SnpQueryDTO dto = (SnpQueryDTO)query;
        
        if(dto.getPatientIds() != null && dto.getPatientIds().size() > 0) {
            criteria.add(Restrictions.in("patient.studySubjectIdentifier", dto.getPatientIds()));
        }
        
        if(dto.getSnpId() != null) {
            Disjunction ids = Expression.disjunction();
            ids.add(Restrictions.eq("snp.dbsnpId", dto.getSnpId()));
            ids.add(Restrictions.eq("snp.secondaryIdentifier", dto.getSnpId()));
            
            criteria.add(ids);
        }
        return criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        
    }
    
    public boolean canHandle(QueryDTO query) {
        return (query instanceof SnpQueryDTO);
    }

    public Integer getResultCount(QueryDTO query) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
    }

    public List getResults(QueryDTO dto, Integer page) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
    }



    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
