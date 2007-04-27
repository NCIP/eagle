package gov.nih.nci.eagle.web.reports;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

public class CSVUtil {


	public static void renderCSV(HttpServletResponse response, List<List> csv)	{
		PrintWriter out = null;
		
		String csvString = "";
		for(List row : csv)	{
			csvString += StringUtils.join(row.toArray(), ",") + "\r\n";
		}
		long randomness = System.currentTimeMillis();
		response.setContentType("application/csv");
		response.setHeader("Content-Disposition", "attachment; filename=report_"+randomness+".csv");
		try	{
			out = response.getWriter();
			out.write(csvString);
		}
		catch(Exception e)	{
			out.write("error generating report");
		}
	}
}
