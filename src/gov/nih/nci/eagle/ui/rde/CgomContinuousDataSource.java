package gov.nih.nci.eagle.ui.rde;

import gov.nih.nci.caintegrator.analysis.messaging.ClassComparisonResultEntry;
import gov.nih.nci.caintegrator.service.findings.ClassComparisonFinding;
import gov.nih.nci.caintegrator.service.findings.CompoundClassComparisonFinding;
import gov.nih.nci.eagle.web.ajax.ExpressionValue;

import java.util.ArrayList;
import java.util.List;

public class CgomContinuousDataSource implements ContinuousDataSource {

    private List<ExpressionValue> continuousData;
    
    public CgomContinuousDataSource(CompoundClassComparisonFinding result) {
        continuousData = new ArrayList<ExpressionValue>();
        for(ClassComparisonFinding finding : result.getClassComparisonFindingList()) {
            for(ClassComparisonResultEntry entry : finding.getResultEntries()) {
                ExpressionValue val = new ExpressionValue();
                val.setReporterId(entry.getReporterId());
                val.setFoldChange(entry.getFoldChange());
                val.setSampleType(finding.getBaselineGroup().getGroupName());
                continuousData.add(val);
            }
        }
    }
    
    public List<ExpressionValue> getContinuousData() {
        return continuousData.subList(0, 2000);
    }

}
