package eu.euipo.tools.em.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by canogjo on 31/03/2017.
 */
@SpringBootApplication(scanBasePackages = "eu.euipo.tools.em")
@EntityScan("eu.euipo.tools.em.persistence.entity")
@EnableJpaRepositories("eu.euipo.tools.em.persistence.repository")
public class TimeLimitUpdaterApp {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(TimeLimitUpdaterApp.class, args[0]);
        Application app = context.getBean(Application.class);

        String[] parameters = new String[args.length - 1];
        for(int i = 0; i < args.length - 1; i++) {
            parameters[i] = args[i+1];
        }
        app.run(parameters);
        ((AbstractApplicationContext) context).close();
	}
}
