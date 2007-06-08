package gov.nih.nci.eagle.web.ajax;

import gov.nih.nci.caintegrator.domain.annotation.gene.bean.CytobandPosition;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class ChromosomeBrowserHelper {

    private HibernateTemplate hibernateTemplate;
    private Integer chromosomeScale = 400000;

    public String getDataForRange(Integer start, Integer end) {
        System.out.println("Got request start: " + start + " end: " + end);
        return "Viewing from: " + (start * chromosomeScale) + " to: " + (end * chromosomeScale);
    }

    public Collection getChromosomeCytobands(final String chr) {


        List<CytobandPosition> cytobands = (List<CytobandPosition>)hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session sess)
                    throws HibernateException, SQLException {
                Criteria criteria = sess.createCriteria(CytobandPosition.class);
                criteria.add(Restrictions.eq("chromosomeName", chr));
                criteria.addOrder(Order.asc("cytobandStartPosition"));
                return criteria.list();
            }
        });
        Collection items = new ArrayList();
        ChromosomeCytoband cc = null;
        for(CytobandPosition pos : cytobands) {
            if(!("p arm").equals(pos.getCytoband()) && !("q arm").equals(pos.getCytoband()) && !("WHOLE").equals(pos.getCytoband())) {
                cc = new ChromosomeCytoband();
                cc.setName(pos.getCytoband());
                cc.setStart(pos.getCytobandStartPosition() / chromosomeScale);
                cc.setEnd(pos.getCytobandEndPosition() / chromosomeScale);
                items.add(cc);
            }
            
        }
        return items;
    }
    
    public Collection getChromosomeArms(final String chr) {
        List<CytobandPosition> arms = (List<CytobandPosition>)hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session sess)
                    throws HibernateException, SQLException {
                Criteria criteria = sess.createCriteria(CytobandPosition.class);
                criteria.add(Restrictions.eq("chromosomeName", chr));
                criteria.add(Restrictions.in("cytoband", new String[]{"p arm", "q arm"}));
                criteria.addOrder(Order.asc("cytobandStartPosition"));
                return criteria.list();
            }
        });
        List items = new ArrayList();
        ChromosomeCytoband cc = null;
        for(CytobandPosition pos : arms) {
            if(("p arm").equals(pos.getCytoband())) {
                cc = new ChromosomeCytoband();
                cc.setName(pos.getCytoband());
                cc.setStart(pos.getCytobandStartPosition() / chromosomeScale);
                cc.setEnd(pos.getCytobandEndPosition() / chromosomeScale);
                items.add(0, cc);
            }
            if(("q arm").equals(pos.getCytoband())) {
                cc = new ChromosomeCytoband();
                cc.setName(pos.getCytoband());
                cc.setStart(pos.getCytobandStartPosition() / chromosomeScale);
                cc.setEnd(pos.getCytobandEndPosition() / chromosomeScale);
                items.add(1, cc);
            }
            
        }
        return items;
    }

    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }
}
