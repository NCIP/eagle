package gov.nih.nci.eagle.web.ajax;

import java.util.Collection;
import java.util.List;

public interface ChromosomeBrowser {

    public List getDataForRegion(String chromosome, Long start, Long end);

    public Collection<ChromosomeCytoband> getChromosomeCytobands(String chr);

    public Collection<ChromosomeCytoband> getChromosomeArms(String chr);

    public Collection<Feature> getChromosomeFeatures(String chr);

}
