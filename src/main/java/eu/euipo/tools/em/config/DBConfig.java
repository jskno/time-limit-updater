package eu.euipo.tools.em.config;


import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.sql.DataSource;

/**
 * Created by canogjo on 31/03/2017.
 */
@Configuration
public class DBConfig {
    
    @Bean
    @Primary
//    @ConfigurationProperties("em.euipoInt.datasource")
    @ConfigurationProperties("em.datasource")
    public DataSource emDataSource() {
        return DataSourceBuilder.create().build();
    }

//    @Bean(destroyMethod="")
//    @ConfigurationProperties()
//    public DataSource wfwDataSource() throws Exception {
//        JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
//        return dataSourceLookup.getDataSource("wfw.ejb.wfwservicemanager");
//    }
}
