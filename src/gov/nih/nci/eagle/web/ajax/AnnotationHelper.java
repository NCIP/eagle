package gov.nih.nci.eagle.web.ajax;

import gov.nih.nci.caintegrator.domain.annotation.gene.bean.CytobandPosition;
import gov.nih.nci.caintegrator.domain.annotation.gene.bean.GeneAlias;
import gov.nih.nci.caintegrator.domain.annotation.service.AnnotationManager;
import gov.nih.nci.caintegrator.domain.annotation.snp.bean.VariationReporter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class AnnotationHelper {

    private AnnotationManager annotationManager;
    private HashMap<String, String[]> geneLookups = new HashMap<String, String[]>();

    public AnnotationManager getAnnotationManager() {
        return annotationManager;
    }

    public void setAnnotationManager(AnnotationManager annotationManager) {
        this.annotationManager = annotationManager;
    }

    public String[] getCytobandsForChromosome(String chromosome) {

        List<CytobandPosition> cytobandPositions = annotationManager
                .getCytobandPositions(chromosome);
        String[] cytobands = new String[cytobandPositions.size()];
        for (int i = 0; i < cytobands.length; i++) {
            cytobands[i] = cytobandPositions.get(i).getCytoband();
        }
        Arrays.sort(cytobands);
        return cytobands;
    }

    public String[] getGenesForSymbol(String symbol) {
        if (geneLookups.get(symbol.substring(0, 1)) == null) {
            List<GeneAlias> genes = annotationManager
                    .getGeneAliasForSymbol(symbol);
            HashSet<String> ids = new HashSet<String>();
            for (int i = 0; i < genes.size(); i++) {
                if (genes.get(i).getAlias() != null)
                    ids.add(genes.get(i).getAlias());
                if (genes.get(i).getHugoGeneSymbol() != null)
                    ids.add(genes.get(i).getHugoGeneSymbol());
            }
            String[] returnArray = ids.toArray(new String[ids.size()]);
            if (returnArray != null) {
                geneLookups.put(symbol.substring(0, 1), returnArray);
                return ids.toArray(new String[ids.size()]);
            } else {
                return new String[] {};
            }
        } else {
            return geneLookups.get(symbol.substring(0, 1));
        }
    }

    public String[] getDbSnpIds(String symbol) {
        if (symbol.indexOf("rs") == 0 && symbol.length() >= 3) {
            List<VariationReporter> reportersForDbSnpId = annotationManager
                    .getReportersForDbSnpId(symbol + "%");
            String[] ids = new String[reportersForDbSnpId.size()];
            for (int i = 0; i < ids.length; i++) {
                ids[i] = reportersForDbSnpId.get(i).getSnpAnnotation()
                        .getDbsnpId();
            }
            System.out.println(ids.length);
            return ids;
        } else
            return new String[] {};
    }
}
