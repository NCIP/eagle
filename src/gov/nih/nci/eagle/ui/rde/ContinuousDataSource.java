package gov.nih.nci.eagle.ui.rde;

import gov.nih.nci.eagle.web.ajax.ExpressionValue;

import java.util.List;

public interface ContinuousDataSource {

    public List<ExpressionValue> getContinuousData();
}
