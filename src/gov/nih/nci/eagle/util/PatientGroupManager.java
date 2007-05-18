package gov.nih.nci.eagle.util;

import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientGroupManager {

    public String patientFileName;
    public Map<String, Map> patientMap;
    
    public String getPatientFileName() {
        return patientFileName;
    }

    public void setPatientFileName(String patientFileName) {
        this.patientFileName = patientFileName;
    }

    public List getPatientInfo(List<String> patientIds) {
        if(patientMap == null) {
            buildPatientMap();
        }
        List patients = new ArrayList<HashMap>();
        for(String id : patientIds) {
            patients.add(patientMap.get(id));
        }
        return patients;
    }
    
    public Map getPatientInfo(String patientId) {
        if(patientMap == null) {
            buildPatientMap();
        }
        return patientMap.get(patientId);
    }

    private void buildPatientMap() {
        patientMap = new HashMap<String, Map>();
            File inFile = new File(patientFileName);
            BufferedReader br;
            try {
                br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(inFile)));
                String line = br.readLine();
                while(line != null) {
                   String[] values = StringUtils.split(line, ",");
                   HashMap<String, String> patient = new HashMap<String, String>();
                   if(values ==  null || values.length < 1) {
                       line = br.readLine();
                       continue;
                   }
                   patient.put("patientId", values[0]);
                   String sex = null;
                   if(values[1].equals("1.00")) {
                       sex = "M";
                   } else {
                       sex = "F";
                   }
                   patient.put("sex", sex);
                   String smoke = null;
                   if(values[2].equals("0.00")) {
                       smoke = "NEVER";
                   } else if(values[2].equals("1.00")) {
                       smoke = "FORMER";
                   } else if(values[2].equals("2.00")) {
                      smoke = "CURRENT";
                   } else	{ //some are 9.0 == n/a
                	   smoke = "N/A";
                   }
                   
                   patient.put("smoking_status", smoke);
                   patient.put("age", values[3]);
                   if(values.length > 4)	{
                       patient.put("grade", values[4]);
                   }
                   else	{
                	 patient.put("grade", "N/A");  //no grade for these  
                   }
                   patientMap.put(values[0], patient);
                   line = br.readLine();
                }
                br.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        
    }
}
