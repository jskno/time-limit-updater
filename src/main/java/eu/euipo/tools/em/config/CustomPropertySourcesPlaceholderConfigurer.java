package eu.euipo.tools.em.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.Map;
import java.util.Properties;

/**
 * Created by Jose on 4/4/2017.
 */
public class CustomPropertySourcesPlaceholderConfigurer
        extends PropertySourcesPlaceholderConfigurer implements InitializingBean {


    public void afterPropertiesSet(){
        try{
            Properties loadedProperties = this.mergeProperties();
            for(Map.Entry<Object, Object> singleProperty : loadedProperties.entrySet()){
                logger.info("LoadedProperty: "+singleProperty.getKey()+"="+singleProperty.getValue());
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
