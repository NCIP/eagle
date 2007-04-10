package gov.nih.nci.eagle.service.handlers;

import gov.nih.nci.caintegrator.domain.epidemiology.bean.EpidemiologicalStudyParticipant;
import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.studyQueryService.QueryHandler;
import gov.nih.nci.caintegrator.studyQueryService.dto.epi.EPIQueryDTO;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class EpidemiologicalQueryHandler implements QueryHandler {

    private SessionFactory sessionFactory;

    public Integer getResultCount(QueryDTO query) {
        throw new UnsupportedOperationException();
    }

    public List getResults(QueryDTO dto, Integer page) {
        throw new UnsupportedOperationException();
    }

    public List getResults(QueryDTO query) {
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Session currentSession = sessionFactory.getCurrentSession();
        Criteria criteria = currentSession.createCriteria(EpidemiologicalStudyParticipant.class);
        
        return criteria.list();

    }
    
    public boolean canHandle(QueryDTO query) {
        return (query instanceof EPIQueryDTO);
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFacotry) {
        this.sessionFactory = sessionFacotry;
    }
}
