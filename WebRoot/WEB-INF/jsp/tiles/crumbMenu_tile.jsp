<%@ page import="gov.nih.nci.eagle.util.EAGLEConstants" %>
<%@ page import="gov.nih.nci.eagle.bean.UserInfoBean" %>
<div id="crumb">
<span style="float:left">
<a style="" href="javascript:Help.popHelp('Welcome');">help</a>&nbsp;&nbsp;&nbsp;
<a style="" href="http://ncicb.nci.nih.gov/NCICB/support" target="_blank">support</a>&nbsp;&nbsp;&nbsp;
<a style="" href="#f">user guide</a>&nbsp;&nbsp;&nbsp;
</span>

<% UserInfoBean userInfoBean = (UserInfoBean)session.getAttribute(EAGLEConstants.userInfoBean); %>
  <span style="text-align:right;">
    Welcome, &nbsp;
    <% if(userInfoBean!=null&&userInfoBean.getUserName()!=null){
    	out.print(userInfoBean.getUserName()); 
    %>
	    &nbsp;|&nbsp;
	    <a href="logout.do">
	      Logout
	    </a>
	<%
		}
		else	{
    		out.println(" Guest");
    	}
    %>
  </span>	
</div>
