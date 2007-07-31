package gov.nih.nci.eagle.web.ajax;

import java.util.Collection;

public interface ChromosomeBrowser {

    public Collection getDataForRegion(String chromosome, Long start, Long end);

    public Collection<ChromosomeCytoband> getChromosomeCytobands(String chr);

    public Collection<ChromosomeCytoband> getChromosomeArms(String chr);

    public Collection<Feature> getChromosomeFeatures(String chr);

}
