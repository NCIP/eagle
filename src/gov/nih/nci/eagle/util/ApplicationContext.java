/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/eagle/LICENSE.txt for details.
 */

package gov.nih.nci.eagle.util;

import gov.nih.nci.caintegrator.application.analysis.AnalysisServerClientManager;
import gov.nih.nci.caintegrator.application.util.PropertyLoader;
import gov.nih.nci.caintegrator.application.cache.CacheFactory;




import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.jms.JMSException;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;


public class ApplicationContext{
	private static Map mappings = new HashMap();
	private static Logger logger = Logger.getLogger(ApplicationContext.class);
	private static Properties labelProps = null;
	private static Document doc =null;
   /**
    * COMMENT THIS
    * @return
    */
    public static Properties getLabelProperties() {
        return labelProps;
    }
    public static Map getDEtoBeanAttributeMappings() {
    	return mappings;
    }

    @SuppressWarnings("unused")
	public static void init() {
    	 logger.debug("Loading Application Resources");
         labelProps = PropertyLoader.loadProperties(EAGLEConstants.APPLICATION_RESOURCES);
         String appPropertiesFileName = null;

        try {

           //Load the the eagle application properties and set them as system properties
 		   Properties eaglePortalProperties = new Properties();
 		   appPropertiesFileName = System.getProperty("gov.nih.nci.eagleportal.propertiesFile");


 		   logger.info("Attempting to load application system properties from file: " + appPropertiesFileName);

 		   FileInputStream in = new FileInputStream(appPropertiesFileName);
 		   eaglePortalProperties.load(in);

 		   if (eaglePortalProperties.isEmpty()) {
 		     logger.error("Error: no properties found when loading properties file: " + appPropertiesFileName);
 		   }

 		   String key = null;
 		   String val = null;
 		   for (Iterator i = eaglePortalProperties.keySet().iterator(); i.hasNext(); ) {
 			  key = (String) i.next();
 			  val = eaglePortalProperties.getProperty(key);
 		      System.setProperty(key, val);
 		   }



           @SuppressWarnings("unused") AnalysisServerClientManager analysisServerClientManager = AnalysisServerClientManager.getInstance();
		   analysisServerClientManager.setCache(CacheFactory.getBusinessTierCache());

		   //intialize database

		   String dbalias = System.getProperty("gov.nih.nci.eagle.dbalias");
		   String username = System.getProperty("gov.nih.nci.eagle.db.username");
		   String password = System.getProperty("gov.nih.nci.eagle.db.password");

		   //end of initialize


	       // getting jms related info

	       String jmsProviderURL     = System.getProperty("gov.nih.nci.eagleportal.jms.jboss_url");
		   String jndiFactoryName    = System.getProperty("gov.nih.nci.eagleportal.jms.factory_jndi");
		   String requestQueueName   = System.getProperty("gov.nih.nci.eagleportal.jms.analysis_request_queue");
		   String responseQueueName  = System.getProperty("gov.nih.nci.eagleportal.jms.analysis_response_queue");

		  analysisServerClientManager.setJMSparameters(jmsProviderURL, jndiFactoryName,requestQueueName, responseQueueName);

		  analysisServerClientManager.establishQueueConnection();

          //Initialize Annotation loading
		  // need to use what Andrew wrote  annotationService


		} catch (NamingException e) {
	        logger.error(new IllegalStateException("Error getting an instance of AnalysisServerClientManager" ));
			logger.error(e.getMessage());
			logger.error(e);
		} catch (JMSException e) {
	        logger.error(new IllegalStateException("Error getting an instance of AnalysisServerClientManager" ));
			logger.error(e.getMessage());
			logger.error(e);
		} catch (IOException e) {
		    logger.error("IOError loading properties file: " + appPropertiesFileName);
		    logger.error(e);
		}  catch(Throwable t) {
			logger.error(t.getMessage());
			logger.error(t);
		}

    }
}
