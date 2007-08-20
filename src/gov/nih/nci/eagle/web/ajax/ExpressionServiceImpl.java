package gov.nih.nci.eagle.web.ajax;

import gov.nih.nci.eagle.ui.rde.ContinuousDataSource;

import java.util.ArrayList;
import java.util.Collection;

public class ExpressionServiceImpl implements ExpressionService {

    private ContinuousDataSource continousDataSource;
    public Collection<ExpressionValue> getExpressionValuesForRegion(String chromosome, Long start, Long end) {

        return continousDataSource.getContinuousData();
    }

    public void setContinuousDataSource(ContinuousDataSource source) {
        this.continousDataSource = source;
    }
}
