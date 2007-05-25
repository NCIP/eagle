package gov.nih.nci.eagle.util;

import gov.nih.nci.caintegrator.application.configuration.SpringContext;
import gov.nih.nci.caintegrator.application.lists.ListLoader;
import gov.nih.nci.caintegrator.application.lists.ListManager;
import gov.nih.nci.caintegrator.application.lists.ListOrigin;
import gov.nih.nci.caintegrator.application.lists.ListSubType;
import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.lists.UserListBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.naming.OperationNotSupportedException;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class EAGLEListLoader extends ListLoader {
    private static Logger logger = Logger.getLogger(EAGLEListLoader.class);    

    public EAGLEListLoader() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public static UserListBean loadDefaultLists(UserListBean userListBean, HttpSession session) throws OperationNotSupportedException{
        ListManager listManager = new ListManager();
       
        //      use RBT stuff for testing
        //	this is obviously testing stuff
//        String oligos = "E09264,E09358,E09448,E09503,E09661,E09664,E09690,E09804,E09811,E09860,E09867,E09885,E09893,HF0087,HF0251,HF0285,HF0291,HF0327,HF0329,HF0332,HF0434,HF0453,HF0471,HF0488,HF0510,HF0599,HF0615,HF0639,HF0670,HF0726,HF0749,HF0813,HF0816,HF0822,HF0828,HF0831,HF0835,HF0897,HF0899,HF0914,HF0920,HF0931,HF0960,HF0962,HF0966,HF0975,HF1136,HF1150,HF1156,HF1167,HF1185,HF1219,HF1227,HF1235,HF1264,HF1325,HF1334,HF1345,HF1348,HF1380,HF1381,HF1433,HF1489,HF1493,HF1502,HF1551,HF1553,HF1606,HF1613,HF1677,MD508984";
//        String astros = "E09137,E09176,E09214,E09262,E09334,E09362,E09394,E09408,E09471,E09542,E09673,E09743,E09759,E09800,E09801,E09818,E09826,E09848,E09900,E09922,E09923,E09945,HF0017,HF0026,HF0108,HF0152,HF0189,HF0223,HF0450,HF0491,HF0608,HF0757,HF0778,HF0953,HF1000,HF1032,HF1114,HF1139,HF1232,HF1246,HF1269,HF1295,HF1316,HF1344,HF1366,HF1398,HF1407,HF1442,HF1463,HF1469,HF1487,HF1511,HF1568,HF1581,HF1587,HF1708,MD547038,MD554082";
 //first 5+ astros are not in rbinary
//        String oligos = "IGC-10-1107,IGC-08-1053,IGC-02-1067,IGC-02-1059";
//        String astros = "IGC-10-1051,IGC-12-1077,IGC-09-1090,IGC-03-1007";
//        String ftest = "IGC-08-1094,IGC-09-1085,IGC-13-1106,IGC-04-1113";
        ReadablePropertyPlaceholder propBean = (ReadablePropertyPlaceholder)SpringContext.getBean("patientLists");
        Properties props = propBean.getProps();
        for(Object listName : props.keySet()) {
            if(listName.toString().indexOf("data.") != -1)
                continue;
            List<String> sampleList = new ArrayList<String>();
            String samples = props.getProperty(listName.toString());
            sampleList = Arrays.asList( StringUtils.split(samples, ",") );
            EAGLEListValidator listValidator = new EAGLEListValidator(ListType.PatientDID, sampleList);
            UserList mySampleList = listManager.createList(ListType.PatientDID,listName.toString(),sampleList,listValidator); 
            mySampleList.setListOrigin(ListOrigin.Default);
            userListBean.addList(mySampleList);
        }
//        List<String> oligoSamplesList = new ArrayList<String>();
//        oligoSamplesList = Arrays.asList( StringUtils.split(oligos, ",") );
//
//      
//        EAGLEListValidator listValidator = new EAGLEListValidator(ListType.PatientDID, ListSubType.Default, oligoSamplesList);
//        UserList myOligoSampleList = listManager.createList(ListType.PatientDID,"testOligo",oligoSamplesList,listValidator); 
//        myOligoSampleList.setListSubType(ListSubType.Default);
//        userListBean.addList(myOligoSampleList);
//        
//        List<String> astroSamplesList = new ArrayList<String>();
//        astroSamplesList = Arrays.asList( StringUtils.split(astros, ",") );
//     
//        listValidator = new EAGLEListValidator(ListType.PatientDID, ListSubType.Default, astroSamplesList);
//        UserList myAstroSampleList = listManager.createList(ListType.PatientDID,"testAstro",astroSamplesList,listValidator); 
//        myAstroSampleList.setListSubType(ListSubType.Default);
//        userListBean.addList(myAstroSampleList);
//        
//        List<String> ftestList = new ArrayList<String>();
//        ftestList = Arrays.asList( StringUtils.split(ftest, ",") );
//     
//        listValidator = new EAGLEListValidator(ListType.PatientDID, ListSubType.Default, ftestList);
//        UserList ftestSampleList = listManager.createList(ListType.PatientDID,"ftest",ftestList,listValidator); 
//        ftestSampleList.setListSubType(ListSubType.Default);
//        userListBean.addList(ftestSampleList);

        return userListBean;        
    }
}
