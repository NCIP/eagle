package gov.nih.nci.eagle.web.ajax;

import java.util.Collection;

public interface AnnotationService {

    public Collection<ChromosomeCytoband> getChromosomeAnnotations(String chromosome);
}
