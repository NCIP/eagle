package gov.nih.nci.eagle.web.ajax;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ChromosomeBrowserImpl implements ChromosomeBrowser {

    private AnnotationService annotationService;
    private ExpressionService expressionService;
    private FeatureService featureService;

    // private Integer chromosomeScale = 200000;

    public List getDataForRegion(String chromosome, Long start, Long end) {
        List<ExpressionValue> expressions = expressionService.getExpressionValuesForRegion(chromosome, start, end);
        return expressions;
    }

    public String getDataForRegionJSON(String chromosome, Long start, Long end)	{
    	List expressions = getDataForRegion(chromosome, start, end);
    	
    	JSONArray repArray = new JSONArray();
    	ListIterator listItr = expressions.listIterator();
    	
    	LinkedHashMap reportersMap = new LinkedHashMap(); //map of JSONObjects with repId as Key
    	
    	JSONObject rep = null;
    	JSONArray dataTypes = null;
    	
    	while(listItr.hasNext()) {
    		ExpressionValue ev = (ExpressionValue) listItr.next();
    		//loop thru reporters	
    		//is this entry already in the Map? composite key: rep|start|end
    		if(reportersMap.containsKey(ev.getReporterId() + "|" + ev.getStart() + "|" + ev.getEnd()))	{
    			//already exists
    			rep = (JSONObject) reportersMap.get(ev.getReporterId() + "|" + ev.getStart() + "|" + ev.getEnd());
    			dataTypes = (JSONArray) rep.get("dataTypes");
    		}
    		else	{
    			rep = new JSONObject();
		    	rep.put("reporterId", ev.getReporterId());
		    	rep.put("start", ev.getStart());
		    	rep.put("end", ev.getEnd());
		    	rep.put("chromosome", ev.getChromosome());
		    	dataTypes = new JSONArray();
    		}
   		 
    		//loop through datatypes for this reporter
    		JSONObject singleData = new JSONObject();
    		singleData.put("dataType", ev.getSampleType().replace(" ", "").replace("-", ""));
    		singleData.put("foldChange", ev.getFoldChange());
    		dataTypes.add(singleData);
    		
    		//put the new or updated JSONArray back in
	    	rep.put("dataTypes", dataTypes);
    	
	    	reportersMap.put(ev.getReporterId() + "|" + ev.getStart() + "|" + ev.getEnd(), rep);
	    	//repArray.add(rep);
    	}
    	
    	//convert the LinkedHashMap to JSONArray
		for (Iterator it = reportersMap.values().iterator(); it.hasNext();) {
		      repArray.add((JSONObject) it.next());
		}
		
		return repArray.toString();
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
