package eu.euipo.tools.em.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * Created by canogjo on 31/03/2017.
 */
@SpringBootApplication(scanBasePackages = "eu.euipo.tools.em")
public class TimeLimitUpdaterApp {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(TimeLimitUpdaterApp.class);
        Application app = context.getBean(Application.class);
        app.run(args);
        ((AbstractApplicationContext) context).close();
	}
}
