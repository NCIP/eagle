package gov.nih.nci.eagle.util;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class ReadablePropertyPlaceholder extends PropertyPlaceholderConfigurer {

    /**
     * The properties used for BeanFactory post-processing.
     */
    private Properties props;
    
    /**
     * Gets the properties used in post-processing the BeanFactory.
     * 
     * @return the properties to use in post-processing.
     */
    public Properties getProps() {
        return props;
    }

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        this.props = props;
        super.processProperties(beanFactoryToProcess, props);
    }
    
}
