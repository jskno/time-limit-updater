package eu.euipo.tools.em.config;

import eu.euipo.tools.em.config.filenet.P8Connection;
import eu.euipo.tools.em.constants.TaskNameVariablesMapping;
import eu.euipo.tools.em.constants.TimeLimitUpdaterConstants;
import eu.euipo.tools.em.persistence.entity.CurrentTask;
import eu.euipo.tools.em.persistence.model.CurrentTaskVO;
import eu.euipo.tools.em.persistence.model.PairFromWFW;
import eu.euipo.tools.em.service.CurrentTaskService;
import eu.euipo.tools.em.service.ScriptWriter;
import eu.euipo.tools.em.service.WorkflowManager;
import eu.euipo.tools.em.service.impl.WorkflowManagerImpl;
import eu.euipo.tools.em.utils.DateUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

/**
 * Created by canogjo on 31/03/2017.
 */
@SpringBootApplication(scanBasePackages = "eu.euipo.tools.em")
public class Application {

    public static void main(String[] args) {
//        SpringApplication.run(Application.class);

//        args = new String[2];
//        args[0] = "28-03-2017";
//        args[1] = "04-04-2017";
//        args[2] = "100";

        ApplicationContext context = SpringApplication.run(Application.class);

        WorkflowManagerImpl workflowManager = (WorkflowManagerImpl) context.getBean(WorkflowManager.class);
        ScriptWriter scriptWriter = context.getBean(ScriptWriter.class);
        CurrentTaskService currentTaskService = context.getBean(CurrentTaskService.class);
        StringBuilder failedCases;

        // Get the tasks
        List<CurrentTask> taksFromDB = null;
        if(args.length == 1) {
            taksFromDB = currentTaskService.getTasksByEntity("002734575", TimeLimitUpdaterConstants.OPPO_ENTITY, TimeLimitUpdaterConstants.TASK_NAMES);
        } else if(args.length == 2) {
            Date startDate = null;
            Date endDate = null;
            try {
                startDate = DateUtil.getDateFromString(args[0]);
                endDate = DateUtil.getDateFromString(args[1]);
            } catch (Exception e) {
                System.out.println("Wrong dates format");
                return;
            }

            taksFromDB = currentTaskService.getTasksByDates(
                    TimeLimitUpdaterConstants.OPPO_ENTITY,
                    TimeLimitUpdaterConstants.TASK_NAMES,
                    startDate,
                    endDate);
//                    DateUtil.getDateFromString("01-03-2016"),
//                    DateUtil.getDateFromString("02-03-2016")
        } else if(args.length == 3) {
            taksFromDB = currentTaskService.get100TasksByDates(DateUtil.getDateFromString(args[0]), DateUtil.getDateFromString(args[1]));
        } else {
            System.out.println("Wrong number of arguments");
            return;
        }

        System.out.println("Number of records from database: " + taksFromDB.size());
        List<String> tasksIds = extractListFromTasks(taksFromDB);
        System.out.println("TASK IDS : " +tasksIds);

        // Gets the workflow related with the tasks from DB
        Map<String, String> mapFromWFW = new HashMap<>();
        failedCases = new StringBuilder();
        for(String taskName : TimeLimitUpdaterConstants.TASK_NAMES) {
            TaskNameVariablesMapping mapping = TaskNameVariablesMapping.fromValue(taskName);
            workflowManager.getWorkflow(mapFromWFW, mapping, failedCases, tasksIds);
        }
        scriptWriter.writeFailScript(failedCases);


        List<CurrentTaskVO> updatedTasks = new ArrayList<>();
        for(String key : mapFromWFW.keySet()) {
            if(mapFromWFW.get(key) != null && !mapFromWFW.get(key).isEmpty()) {
                System.out.println("TaskID: " + key + " timeLimit : " + mapFromWFW.get(key));
                CurrentTaskVO currentTaskVO = new CurrentTaskVO();
                currentTaskVO.setIdcurrenttask(Integer.valueOf(key));
                currentTaskVO.setDtendtimelimit(DateUtil.getDateFromStringUSA(mapFromWFW.get(key)));
                updatedTasks.add(currentTaskVO);
            }
        }

        // Write the script
        scriptWriter.writeUpdateScript(updatedTasks);

        ((AbstractApplicationContext) context).close();
    }

    private static List<String> extractListFromTasks(List<CurrentTask> tasksByDates) {

        List<String> taskIds = new ArrayList<>();
        tasksByDates.stream().forEach(currentTaskEntity ->
                taskIds.add(String.valueOf(currentTaskEntity.getIdcurrenttask()))
            );
        return taskIds;
    }

}
