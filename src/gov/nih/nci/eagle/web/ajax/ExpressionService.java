package gov.nih.nci.eagle.web.ajax;

import java.util.Collection;

public interface ExpressionService {

    public Collection<ExpressionValue> getExpressionValuesForRegion(String chromosome, Long start, Long end);
}
