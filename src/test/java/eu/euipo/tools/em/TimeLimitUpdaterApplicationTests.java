package eu.euipo.tools.em;

import eu.euipo.tools.em.constants.TimeLimitUpdaterConstants;
import eu.euipo.tools.em.persistence.entity.CurrentTask;
import eu.euipo.tools.em.service.CurrentTaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class TimeLimitUpdaterApplicationTests {

	@Autowired
	private CurrentTaskService currentTaskService;

//	@Test
	public void contextLoads() {
	}

//	@Test
	public void getTasksByEntityTest() {

		List<CurrentTask> tasks = currentTaskService.getTasksByEntity("002589755", TimeLimitUpdaterConstants.OPPO_ENTITY,
				TimeLimitUpdaterConstants.TASK_NAMES);

		for(CurrentTask task : tasks) {
			System.out.println(task);
		}
	}

}
