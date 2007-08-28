package gov.nih.nci.eagle.web.ajax;

import java.util.Comparator;

public class LocationComparator implements Comparator {

    public int compare(Object o1, Object o2) {
        ExpressionValue val1 = (ExpressionValue)o1;
        ExpressionValue val2 = (ExpressionValue)o2;
        if(val1.getStart() == val2.getStart())
            return val1.getSampleType().compareTo(val2.getSampleType());
        return val1.getStart().compareTo(val2.getStart());
    }

}
