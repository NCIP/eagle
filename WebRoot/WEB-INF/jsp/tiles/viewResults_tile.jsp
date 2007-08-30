<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


<%@ page import="java.util.*, java.lang.*, java.io.*, java.net.URLEncoder " %>
<%@ page import="gov.nih.nci.caintegrator.application.cache.*" %>
<%@ page import="gov.nih.nci.caintegrator.service.findings.*" %>
<%@ page import="gov.nih.nci.caintegrator.service.task.*" %>
<%@ page import="gov.nih.nci.caintegrator.studyQueryService.dto.epi.*" %>
<%@ page import="gov.nih.nci.caintegrator.dto.query.*" %>
<%@ page import="gov.nih.nci.eagle.query.dto.*" %>
<%@ page import="gov.nih.nci.caintegrator.enumeration.*" %>
<%@ page import="gov.nih.nci.caintegrator.exceptions.*" %>
<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>
<%@ page import="gov.nih.nci.caintegrator.enumeration.StatisticalMethodType" %>

<script language="javascript">
	if(location.href.indexOf("viewResults") == -1)	{
		location.replace("viewResults.do");
	}
</script>
<h2 style="text-align:center">
	<script type="text/javascript">Help.insertHelp("View_results_overview", "align='right'");</script>
	View the results of your queries
</h2>

     <div class="viewResultsBoxTop">Report Results</div>    
     <div class="viewResultsBox">
       	<div id="loadingMsg" style="color:#a90101;font-weight:bold;">&nbsp;</div>
       	<ul>
<%   			
		PresentationTierCache ptc = CacheFactory.getPresentationTierCache();							
		if(ptc!=null){
			//get all tasks from presentation tier
			Collection tasks = ptc.getAllSessionTasks(request.getSession().getId());
			if(tasks!=null && !tasks.isEmpty()){
		
						
				//looks like we have some findings, generate the JS to check the status of them
				%>
				<script language="javascript" src="js/a_functions.js"></script>
				<script language="javascript">	
					//testMap("testingtesting");
					var customError = function(message)	{};
					DWREngine.setWarningHandler(customError);
					DWREngine.setErrorHandler(customError);
					
					setTimeout("A_checkAllFindingStatus('<%=session.getId()%>')", 0200);
					var vr_checker = setInterval("A_checkAllFindingStatus('<%=session.getId()%>')", 5000);
	
				</script>
				<%
				
				//why arent these in the backingbean?  - we need to check these directly from cache
				// because they are dynamic - we can not look at a copy placed in the backing bean
				//for(Object o : sessionFindings)	{ //no 1.5 stuff allowed
				for(Iterator i = tasks.iterator();i.hasNext();)	{
		
					Task task = (Task) i.next();
					String qname = "N/A";										
					qname =  task.getId();						
					String comments = "";
					
					String currentStatus = "running";
					if(task.getStatus() == FindingStatus.Completed)
						currentStatus = "<b id=\"" +task.getId() + "_status\">completed</b>  <img src='images/check.png' alt='complete' id=\"" + task.getId() + "_image\"/>";
					else if(task.getStatus() == FindingStatus.Running)
						currentStatus = "<b id=\"" + task.getId() + "_status\" >running</b> <img src='images/circle.gif' alt='running' id=\"" + task.getId() + "_image\" />";
					else if(task.getStatus() == FindingStatus.Error)	{					
						comments = StringEscapeUtils.escapeJavaScript(task.getStatus().getComment());
						currentStatus = "<b id=\"" + task.getId() + "_status\" ><script language=\"javascript\">document.write(showErrorHelp('"+comments+"','error'));</script></b> <img src='images/error.png' alt='error' id=\"" + task.getId() + "_image\" />";
					}
					
					
					out.println("<span style='color:#a90101; float:right'>" + currentStatus + "</span> ");					
					String onclick="";					
					if(task.getStatus()!= FindingStatus.Completed)	{
						onclick = "javascript:alert('Analysis Not yet complete');return false;";
					}					
					//check the type of finding and create the appropriate link
					if(task.getQueryDTO() instanceof EagleClinicalQueryDTO){						
						out.println("<li><a id=\"" + task.getId() + "_link\" href=\"javascript:spawnx('clinicalReport.do?method=runReport&taskId=' + encodeURIComponent('" + URLEncoder.encode(task.getId()) + "') + '&cacheId=" + URLEncoder.encode(task.getCacheId()) + "', 750, 500,'clinical_report');\" onclick=\"" + onclick + "\">" + qname + "</a> <i>(Clinical)</i> ");
					}
					else if(task.getQueryDTO() instanceof EPIQueryDTO){						
						out.println("<li><a id=\"" + task.getId() + "_link\" href=\"javascript:spawnx('epiReport.do?method=runReport&taskId=' + encodeURIComponent('" + URLEncoder.encode(task.getId()) + "') + '&cacheId=" + URLEncoder.encode(task.getCacheId()) + "', 750, 500,'epi_report');\" onclick=\"" + onclick + "\">" + qname + "</a> <i>(Epi)</i> ");
					} 
					else if(task.getQueryDTO() instanceof SnpQueryDTO){						
						out.println("<li><a id=\"" + task.getId() + "_link\" href=\"javascript:spawnx('snpReport.do?method=runReport&taskId=' + encodeURIComponent('" + URLEncoder.encode(task.getId()) + "') + '&cacheId=" + URLEncoder.encode(task.getCacheId()) + "', 750, 200,'epi_report');\" onclick=\"" + onclick + "\">" + qname + "</a> <i>(SNP)</i> ");
					} 
					else if(task.getQueryDTO() instanceof ChromosomeBrowserQueryDTO){	
					    out.println("<li><a id=\"" + task.getId() + "_link\" href=\"javascript:spawnx('rde.do?method=runReport&taskId=' + encodeURIComponent('" + URLEncoder.encode(task.getId()) + "') + '&cacheId=" + URLEncoder.encode(task.getCacheId()) + "', 750, 500,'epi_report');\" onclick=\"" + onclick + "\">" + qname + "</a> <i>(Chromosome Browser)</i> ");
					}
					else if(task.getQueryDTO() instanceof ClassComparisonQueryDTO){	
						ClassComparisonQueryDTO dto = (ClassComparisonQueryDTO)task.getQueryDTO();
						if(dto.getStatisticTypeDE().getValueObject().equals(StatisticalMethodType.TTest))
							out.println("<li><a id=\"" + task.getId() + "_link\" href=\"javascript:spawnx('classComparisonReport.do?method=runReport&taskId=' + encodeURIComponent('" + URLEncoder.encode(task.getId()) + "') + '&cacheId=" + task.getCacheId() + "', 750, 500,'cc_report');\" onclick=\"" + onclick + "\">" + qname + "</a> <i>(Class Comparison)</i> ");
						else if(dto.getStatisticTypeDE().getValueObject().equals(StatisticalMethodType.FTest))
							out.println("<li><a id=\"" + task.getId() + "_link\" href=\"javascript:spawnx('ftestReport.do?method=runReport&taskId=' + encodeURIComponent('" + URLEncoder.encode(task.getId()) + "') + '&cacheId=" + task.getCacheId() + "', 750, 500,'cc_report');\" onclick=\"" + onclick + "\">" + qname + "</a> <i>(Class Comparison)</i> ");
						else if(dto.getStatisticTypeDE().getValueObject().equals(StatisticalMethodType.GLM) || dto.getStatisticTypeDE().getValueObject().equals(StatisticalMethodType.ANOVA))
							out.println("<li><a id=\"" + task.getId() + "_link\" href=\"javascript:spawnx('glmReport.do?method=runReport&taskId=' + encodeURIComponent('" + URLEncoder.encode(task.getId()) + "') + '&cacheId=" + task.getCacheId() + "', 750, 500,'cc_report');\" onclick=\"" + onclick + "\">" + qname + "</a> <i>(Class Comparison)</i> ");
						else	{
								out.println("<li>" + qname + "  ");					
						}
					} 
					else	{
							out.println("<li>unknown analysis: " + qname + "  ");					
					}
 
					
					out.println("<span style=\"font-size:10px\">(elapsed time: <span id=\"" + task.getId() + "_time\" >" + task.getElapsedTime() + "</span>ms) </span>");
					out.println("</li>");
					out.println("<br clear=\"all\" />");
					out.println("<br clear=\"all\" />");
				}
			}
			else{
			//no tasks found in cache
			out.println("<strong>No Report Results at this time.</strong><br/><br/>");
			}
		}
		else{
		  out.println("<strong>No presentation tier cache available at this time.</strong><br/><br/>");
		}
		
		
     
     
     %>
     </ul>
     <br/><br/>
     <div style="font-size:9px;text-align:center;">
     (CC) Class Comparison | 
     (HC) Hierarchical Clustering | 
     (PCA) Principal Component Analysis
     </div>
     
</div>
<script type="text/javascript">
	Nifty("div.viewResultsBoxTop","top");
</script>
<br /><br />
     