package gov.nih.nci.eagle.ui.rde;

import gov.nih.nci.caintegrator.analysis.messaging.ClassComparisonResultEntry;
import gov.nih.nci.caintegrator.domain.annotation.gene.bean.GeneExprReporter;
import gov.nih.nci.caintegrator.domain.annotation.gene.bean.GeneReporterAnnotation;
import gov.nih.nci.caintegrator.service.findings.ClassComparisonFinding;
import gov.nih.nci.caintegrator.service.findings.CompoundClassComparisonFinding;
import gov.nih.nci.eagle.web.ajax.ExpressionValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CgomContinuousDataSource implements ContinuousDataSource {

    private List<ExpressionValue> continuousData;
    
    public CgomContinuousDataSource(CompoundClassComparisonFinding result) {
        continuousData = new ArrayList<ExpressionValue>();
        for(ClassComparisonFinding finding : result.getClassComparisonFindingList()) {
            for(ClassComparisonResultEntry entry : finding.getResultEntries()) {
                Map map = result.getReporterAnnotationsMap();
                GeneExprReporter reporter = (GeneExprReporter)map.get(entry.getReporterId());
                Collection<GeneReporterAnnotation> annotations = null;
                if(reporter != null)
                    annotations = reporter.getGeneReporterAnnotationCollection();
                if(annotations == null || annotations.size() == 0) {
                    System.out.println("no annotations for reporter: " + entry.getReporterId());
                    continue;
                } 
                for(GeneReporterAnnotation ann : annotations) {
                    ExpressionValue val = new ExpressionValue();
                    val.setReporterId(entry.getReporterId());
                    val.setFoldChange(entry.getFoldChange());
                    val.setSampleType(finding.getBaselineGroup().getGroupName());
                    val.setChromosome(ann.getChromosome());
                    val.setStart(ann.getStartPhysicalLocation());
                    val.setEnd(ann.getEndPhysicalLocation());
                    continuousData.add(val);
                }

            }
        }
    }
    
    public List<ExpressionValue> getContinuousData() {
        return continuousData;
    }

}
