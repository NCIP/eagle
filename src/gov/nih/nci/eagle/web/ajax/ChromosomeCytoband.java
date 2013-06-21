/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/eagle/LICENSE.txt for details.
 */

package gov.nih.nci.eagle.web.ajax;

public class ChromosomeCytoband {

    private Long start;
    private Long end;
    private String name;
    
    public Long getEnd() {
        return end;
    }
    public void setEnd(Long end) {
        this.end = end;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getStart() {
        return start;
    }
    public void setStart(Long start) {
        this.start = start;
    }
}
