package gov.nih.nci.eagle.web.reports;

import gov.nih.nci.caintegrator.analysis.messaging.FTestResultEntry;
import gov.nih.nci.caintegrator.domain.annotation.gene.bean.GeneBiomarker;
import gov.nih.nci.caintegrator.service.findings.FTestFinding;
import gov.nih.nci.eagle.util.FTestComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.faces.event.ActionEvent;

public class FTestReport {

    private FTestFinding finding;
    private Boolean sortAscending;
    private FTestComparator sortComparator;
    private List reportBeans;

    public FTestReport(FTestFinding finding) {

        this.finding = finding;
        sortAscending = true;
        sortComparator = new FTestComparator("pvalue", sortAscending);

        reportBeans = new ArrayList<FTestReportBean>();

        for (FTestResultEntry entry : finding.getResultEntries()) {
            FTestReportBean bean = new FTestReportBean(entry, ((GeneBiomarker)finding
                    .getReporterAnnotationsMap().get(entry.getReporterId()))
                    .getHugoGeneSymbol());
            reportBeans.add(bean);
        }
    }

    public Collection getReportBeans() {
        Collections.sort(reportBeans, sortComparator);
        return reportBeans;
    }

    public int getNumberGroups() {
        return finding.getGroupNames().size();
    }

    public Collection getGroupNames() {
        return finding.getGroupNames();
    }

    public void sortDataList(ActionEvent event) {
        String sortFieldAttribute = getAttribute(event, "sortField");

        // Get and set sort field and sort order.
        // Get and set sort field and sort order.
        if (sortFieldAttribute != null
                && sortFieldAttribute.equals(sortComparator.getField())) {
            sortAscending = !sortAscending;
        } else {
            sortAscending = true;
        }
        if (sortFieldAttribute != null) {
            sortComparator = new FTestComparator(sortFieldAttribute,
                    sortAscending);
        }

    }

    private static String getAttribute(ActionEvent event, String name) {
        return (String) event.getComponent().getAttributes().get(name);
    }

    public boolean getSortAscending() {
        return sortAscending;
    }

    public void setSortAscending(boolean sortAscending) {
        this.sortAscending = sortAscending;
    }
}
