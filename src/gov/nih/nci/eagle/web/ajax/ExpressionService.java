package gov.nih.nci.eagle.web.ajax;

import gov.nih.nci.eagle.ui.rde.ContinuousDataSource;

import java.util.Collection;

public interface ExpressionService {

    public Collection<ExpressionValue> getExpressionValuesForRegion(String chromosome, Long start, Long end);
    public void setContinuousDataSource(ContinuousDataSource source);
}
