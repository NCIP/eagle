package gov.nih.nci.eagle.web.ajax;

import gov.nih.nci.caintegrator.domain.annotation.gene.bean.CytobandPosition;
import gov.nih.nci.caintegrator.domain.annotation.service.AnnotationManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AnnotationServiceImpl implements AnnotationService {

    private AnnotationManager annotationManager;
    
    public Collection<ChromosomeCytoband> getChromosomeAnnotations(
            String chromosome) {
        List<CytobandPosition> cytobandPositions = annotationManager.getCytobandPositions(chromosome);
        
        Collection<ChromosomeCytoband> bands = new ArrayList<ChromosomeCytoband>();
        for(CytobandPosition pos : cytobandPositions) {
            ChromosomeCytoband cc = new ChromosomeCytoband();
            cc.setName(pos.getCytoband());
            cc.setStart(pos.getCytobandStartPosition());
            cc.setEnd(pos.getCytobandEndPosition());
            bands.add(cc);
        }
        return bands;
        
    }

    public AnnotationManager getAnnotationManager() {
        return annotationManager;
    }

    public void setAnnotationManager(AnnotationManager annotationManager) {
        this.annotationManager = annotationManager;
    }

}
