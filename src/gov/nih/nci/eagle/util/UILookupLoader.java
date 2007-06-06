package gov.nih.nci.eagle.util;

import gov.nih.nci.caintegrator.domain.epidemiology.bean.Lifestyle;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class UILookupLoader {

    public static final String RESIDENTIAL_AREA = "residentialArea";
    private HibernateTemplate hibernateTemplate;
    private Map<String, List<String>> valueMap;

    public List<String> getLookupValues(String field) {
        if (valueMap == null)
            init();
        return valueMap.get(field);
    }

    private void init() {
        valueMap = new HashMap<String, List<String>>();
        hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session sess)
                    throws HibernateException, SQLException {
                
                Criteria criteria = sess.createCriteria(Lifestyle.class);
                criteria.setProjection(Projections.distinct(Projections.property(RESIDENTIAL_AREA)));
                List list = criteria.list();
                list.remove(null);
                valueMap.put(RESIDENTIAL_AREA, list);
                return null;
            }
        });
    }

    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }
}
