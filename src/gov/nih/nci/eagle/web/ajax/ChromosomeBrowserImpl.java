package gov.nih.nci.eagle.web.ajax;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ChromosomeBrowserImpl implements ChromosomeBrowser {

    private AnnotationService annotationService;
    private ExpressionService expressionService;
    private FeatureService featureService;

    // private Integer chromosomeScale = 200000;

    public Collection getDataForRegion(String chromosome, Long start, Long end) {
        Collection<ExpressionValue> expressions = expressionService.getExpressionValuesForRegion(chromosome, start, end);
        return expressions;
    }

    public Collection<ChromosomeCytoband> getChromosomeCytobands(String chr) {

        Collection<ChromosomeCytoband> cytobands = annotationService
                .getChromosomeAnnotations(chr);
        Collection<ChromosomeCytoband> items = new ArrayList<ChromosomeCytoband>();
        for (ChromosomeCytoband pos : cytobands) {
            if (!("p arm").equals(pos.getName())
                    && !("q arm").equals(pos.getName())
                    && !("WHOLE").equals(pos.getName())) {
                items.add(pos);
            }
        }
        return items;
    }

    public Collection<ChromosomeCytoband> getChromosomeArms(String chr) {

        Collection<ChromosomeCytoband> cytobands = annotationService
                .getChromosomeAnnotations(chr);
        List<ChromosomeCytoband> items = new ArrayList<ChromosomeCytoband>();
        for (ChromosomeCytoband pos : cytobands) {
            if (("p arm").equals(pos.getName())) {
                items.add(0, pos);
            }
            if (("q arm").equals(pos.getName())) {
                items.add(1, pos);
            }
        }
        return items;
    }

    public Collection<Feature> getChromosomeFeatures(String chr) {
        Collection<Feature> features = featureService.getFeaturesForRegion(chr);
        return features;
    }

    public AnnotationService getAnnotationService() {
        return annotationService;
    }

    public void setAnnotationService(AnnotationService annotationService) {
        this.annotationService = annotationService;
    }

    public ExpressionService getExpressionService() {
        return expressionService;
    }

    public void setExpressionService(ExpressionService expressionService) {
        this.expressionService = expressionService;
    }

    public FeatureService getFeatureService() {
        return featureService;
    }

    public void setFeatureService(FeatureService featureService) {
        this.featureService = featureService;
    }
}
