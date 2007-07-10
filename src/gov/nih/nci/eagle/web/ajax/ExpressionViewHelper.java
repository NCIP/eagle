package gov.nih.nci.eagle.web.ajax;

import java.util.ArrayList;
import java.util.Collection;

public class ExpressionViewHelper {

    public Collection getValuesForRegion(String chr, Long start, Long stop) {
        Collection values = new ArrayList();
        ReporterExpressionValue val = new ReporterExpressionValue("reporter1", "blood", 2.0);
        values.add(val);
        val = new ReporterExpressionValue("reporter1", "tissue", 1.5);
        values.add(val);
        val = new ReporterExpressionValue("reporter1", "cancer", -2.0);
        values.add(val);
        val = new ReporterExpressionValue("reporter2", "blood", 2.0);
        values.add(val);
        val = new ReporterExpressionValue("reporter2", "tissue", 3.0);
        values.add(val);
        
        return values;
    }
}
