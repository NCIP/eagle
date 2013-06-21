/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/eagle/LICENSE.txt for details.
 */

package gov.nih.nci.eagle.web.ajax;


import gov.nih.nci.caintegrator.domain.finding.bean.Finding;
import gov.nih.nci.caintegrator.domain.finding.variation.germline.bean.GenotypeFinding;
import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;
import gov.nih.nci.caintegrator.service.task.Task;
import gov.nih.nci.caintegrator.service.task.TaskResult;
import gov.nih.nci.caintegrator.studyQueryService.FindingsManager;
import gov.nih.nci.eagle.query.dto.SnpQueryDTO;
import gov.nih.nci.eagle.ui.rde.CategoricalDataSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class FeatureServiceImpl implements FeatureService {

    private FindingsManager findingsManager;
    HibernateTemplate hibernateTemplate;
    private CategoricalDataSource categoricalDataSource;
    private Map<String, List<Feature>> chromosomeMap;
    
    public Collection getFeatureDetails(String snpId, Collection<String> patientList1,
            Collection<String> patientList2) {
        SnpQueryDTO dto = new SnpQueryDTO();
        dto.setSnpId(snpId);
        dto.setPatientIds(patientList1);
        dto.setQueryName("test");
        Collection<Finding> findings = null;
        try {
           findings =  findingsManager.getFindings(dto);
        } catch (FindingsQueryException e) {
            e.printStackTrace();
        }
        return findings;
    }
    public HashMap getSnpCallReport() {
        HttpSession session = WebContextFactory.get().getSession();
        Task task = (Task)session.getAttribute("snpTask");
        TaskResult result = (TaskResult)findingsManager.getTaskResult(task);
        SnpQueryDTO sdto = (SnpQueryDTO) result.getTask().getQueryDTO();

        HashMap<String, String> findingMap = new HashMap<String, String>();
        findingMap.put("groupName", sdto.getPatientGroupName());
        findingMap.put("snpId", sdto.getSnpId());
        
        for(GenotypeFinding finding : (Collection<GenotypeFinding>)result.getTaskResults()) {
            String call = finding.getCall();

            if(findingMap.containsKey(call)) {
                findingMap.put(call, String.valueOf( Integer.parseInt(findingMap.get(call)) + 1) );
            } else {
                findingMap.put(call, String.valueOf(1));
            }
        }
        
        return findingMap;
    }
    
    public Collection<Feature> getFeaturesForRegion(String chromosome) {
        return getFeaturesForRegion(chromosome, null, null);
    }
    
    public Collection<Feature> getFeaturesForRegion(final String chromosome, Long start, Long stop) {
        if(chromosomeMap == null)
            buildMap();
        return chromosomeMap.get(chromosome);
    }
    

    private void buildMap() {
        chromosomeMap = new HashMap<String, List<Feature>>();
        for(Feature feature : categoricalDataSource.getCategoricalData()) {
            addFeature(feature);
        }
    }

    private void addFeature(Feature feature) {
        List<Feature> values = chromosomeMap.get(feature.getChromosome());
        if(feature != null) {
            if(values == null) {
                values = new ArrayList<Feature>();
            }
            values.add(feature);
            chromosomeMap.put(feature.getChromosome(), values);
        }
    }
    
    public FindingsManager getFindingsManager() {
        return findingsManager;
    }

    public void setFindingsManager(FindingsManager findingsManager) {
        this.findingsManager = findingsManager;
    }

    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }
    
    public void setCategoricalDatasource(CategoricalDataSource source) {
        chromosomeMap = null;
        this.categoricalDataSource = source;
    }


}
