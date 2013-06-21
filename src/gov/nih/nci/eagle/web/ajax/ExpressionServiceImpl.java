/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/eagle/LICENSE.txt for details.
 */

package gov.nih.nci.eagle.web.ajax;

import gov.nih.nci.eagle.ui.rde.ContinuousDataSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpressionServiceImpl implements ExpressionService {

    private ContinuousDataSource continousDataSource;
    private Map<String, List<ExpressionValue>> chromosomeMap;
    public List<ExpressionValue> getExpressionValuesForRegion(String chromosome, Long start, Long end) {
        if(chromosomeMap == null) {
            buildMap();
        }
        return getRegion(chromosomeMap.get(chromosome), start, end);
    }
    
    private List<ExpressionValue> getRegion(List<ExpressionValue> values, Long start, Long end) {
        List<ExpressionValue> regionValues = new ArrayList<ExpressionValue>();
        if(start == null || end == null) {
            Collections.sort(values, new LocationComparator());

            return values;
        }
        for(ExpressionValue value : values) {
            if(value.getStart() >= start && value.getStart() <= end) {
                regionValues.add(value);
            }
        }
        Collections.sort(regionValues, new LocationComparator());

        return regionValues;
    }

    private void buildMap() {
        chromosomeMap = new HashMap<String, List<ExpressionValue>>();
        for(ExpressionValue value : continousDataSource.getContinuousData()) {
            addExpressionValue(value);
        }
    }

    private void addExpressionValue(ExpressionValue value) {
        List<ExpressionValue> values = chromosomeMap.get(value.getChromosome());
        if(value != null) {
            if(values == null) {
                values = new ArrayList<ExpressionValue>();
            }
            values.add(value);
            chromosomeMap.put(value.getChromosome(), values);
        }
    }

    public void setContinuousDataSource(ContinuousDataSource source) {
        chromosomeMap = null;
        this.continousDataSource = source;
    }
}
