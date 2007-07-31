package gov.nih.nci.eagle.web.ajax;

import java.util.ArrayList;
import java.util.Collection;

public class ExpressionServiceImpl implements ExpressionService {


    public Collection<ExpressionValue> getExpressionValuesForRegion(String chromosome, Long start, Long end) {
        System.out.println("Got request for region: " + start + " to " + end);
        Collection values = new ArrayList();
        ExpressionValue val = new ExpressionValue("reporter1", "blood", 2.0);
        values.add(val);
        val = new ExpressionValue("reporter1", "tissue", 1.5);
        values.add(val);
        val = new ExpressionValue("reporter1", "cancer", -2.0);
        values.add(val);
        val = new ExpressionValue("reporter2", "blood", 2.0);
        values.add(val);
        val = new ExpressionValue("reporter2", "tissue", 3.0);
        values.add(val);
        val = new ExpressionValue("reporter3", "cancer", 10.0);
        values.add(val);
        
        return values;
    }
}
