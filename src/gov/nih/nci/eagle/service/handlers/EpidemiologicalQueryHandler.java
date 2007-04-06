package gov.nih.nci.eagle.service.handlers;

import gov.nih.nci.caintegrator.domain.epidemiology.bean.EpidemiologicalStudyParticipant;
import gov.nih.nci.caintegrator.dto.query.EpiQueryDTO;
import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.studyQueryService.QueryHandler;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;

public class EpidemiologicalQueryHandler implements QueryHandler {

    private SessionFactory sessionFactory;

    public Integer getResultCount(QueryDTO query) {
        // TODO Auto-generated method stub
        return null;
    }

    public List getResults(QueryDTO dto, Integer page) {
        // TODO Auto-generated method stub
        return null;
    }

    public List getResults(QueryDTO query) {
//        Session currentSession = sessionFactory.getCurrentSession();
//        Criteria criteria = currentSession.createCriteria(StudyParticipant.class);
//        
//        return criteria.list();
        List participants = new ArrayList();
        EpidemiologicalStudyParticipant p = new EpidemiologicalStudyParticipant();
        p.setId(1L);
        p.setHeight(6.0);
        p.setWeight(100.0);
        p.setWaistCircumference(30.0);
        participants.add(p);
        p = new EpidemiologicalStudyParticipant();
        p.setId(2L);
        p.setHeight(6.0);
        p.setWeight(100.0);
        p.setWaistCircumference(30.0);
        participants.add(p);
        p = new EpidemiologicalStudyParticipant();
        p.setId(3L);
        p.setHeight(6.0);
        p.setWeight(100.0);
        p.setWaistCircumference(30.0);
        participants.add(p);
        p = new EpidemiologicalStudyParticipant();
        p.setId(4L);
        p.setHeight(6.0);
        p.setWeight(100.0);
        p.setWaistCircumference(30.0);
        participants.add(p);
        return participants;
    }
    
    public boolean canHandle(QueryDTO query) {
        return (query instanceof EpiQueryDTO);
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFacotry) {
        this.sessionFactory = sessionFacotry;
    }
}
