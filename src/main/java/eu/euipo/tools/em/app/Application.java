package eu.euipo.tools.em.app;

import eu.euipo.tools.em.constants.TaskNameVariablesMapping;
import eu.euipo.tools.em.constants.TimeLimitUpdaterConstants;
import eu.euipo.tools.em.persistence.entity.CurrentTask;
import eu.euipo.tools.em.persistence.model.CurrentTaskVO;
import eu.euipo.tools.em.service.CurrentTaskService;
import eu.euipo.tools.em.service.ScriptWriter;
import eu.euipo.tools.em.service.WorkflowManager;
import eu.euipo.tools.em.service.impl.WorkflowManagerImpl;
import eu.euipo.tools.em.utils.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.*;

/**
 * Created by canogjo on 31/03/2017.
 */
@Component
public class Application {

    private static Logger log = Logger.getLogger(Application.class);

    @Autowired
    WorkflowManager workflowManager;

    @Autowired
    ScriptWriter scriptWriter;

    @Autowired
    CurrentTaskService currentTaskService;

    private Date starDate;
    private Date endDate;
    private List<CurrentTask> tasksFromDB;
    private StringBuilder failedCases = new StringBuilder();

    public void run(String[] args) {

        StringBuilder failedCases;

        validateUserInputs(args);
        tasksFromDB = getTasksFromDB(args);
        List<String> tasksIds = extractListFromTasks(tasksFromDB);

        // Gets the workflow related with the tasks from DB
        Map<String, String> mapFromWFW = new HashMap<>();
        getFromWorkflowTaskIdTimeLimit(tasksIds, mapFromWFW);
        scriptWriter.writeFailScript(getFailedCases());

        List<CurrentTaskVO> updatedTasks = mapUpdatedTasksToVO(mapFromWFW);
        // Write the script
        scriptWriter.writeUpdateScript(updatedTasks);


    }

    private void validateUserInputs(String[] args) {
        if(args == null || args.length > 3) {
            throw new RuntimeException("Wrong numbers of arguments");
        }
        if(args.length == 2) {
            try {
                setStarDate(DateUtil.getDateFromString(args[0]));
                setEndDate(DateUtil.getDateFromString(args[1]));
            } catch (Exception e) {
                throw new RuntimeException("Date format is not correct");
            }
        }
    }

    private List<CurrentTask> getTasksFromDB(String[] args) {
        List<CurrentTask> tasks;
        if(args.length == 1) {
            tasks = currentTaskService.getTasksByEntity(args[0], TimeLimitUpdaterConstants.OPPO_ENTITY, TimeLimitUpdaterConstants.TASK_NAMES);
        } else if(args.length == 2) {
            tasks = currentTaskService.getTasksByDates(TimeLimitUpdaterConstants.OPPO_ENTITY, TimeLimitUpdaterConstants.TASK_NAMES, getStarDate(), getEndDate());
        } else  {
            tasks = currentTaskService.get100TasksByDates(getStarDate(), getEndDate());
        }
        log.info("Number of records from database: " + tasksFromDB.size());
        return tasks;
    }

    private List<String> extractListFromTasks(List<CurrentTask> tasksByDates) {
        List<String> tasksIds = new ArrayList<>();
        tasksByDates.stream().forEach(currentTaskEntity ->
                tasksIds.add(String.valueOf(currentTaskEntity.getIdcurrenttask()))
        );
        log.info("TASK IDS : " + tasksIds);
        return tasksIds;
    }

    private void getFromWorkflowTaskIdTimeLimit(List<String> tasksIds, Map<String, String> mapFromWFW) {
        for(TaskNameVariablesMapping mapping : TaskNameVariablesMapping.values()) {
            workflowManager.getWorkflow(mapFromWFW, mapping, failedCases, tasksIds);
        }
    }

    private List<CurrentTaskVO> mapUpdatedTasksToVO(Map<String, String> mapFromWFW) {
        List<CurrentTaskVO> updatedTasks = new ArrayList<>();
        for(String key : mapFromWFW.keySet()) {
            if(mapFromWFW.get(key) != null && !mapFromWFW.get(key).isEmpty()) {
                log.info("TaskID: " + key + " timeLimit : " + mapFromWFW.get(key));
                CurrentTaskVO currentTaskVO = new CurrentTaskVO();
                currentTaskVO.setIdcurrenttask(Integer.valueOf(key));
                currentTaskVO.setDtendtimelimit(DateUtil.getDateFromStringUSA(mapFromWFW.get(key)));
                updatedTasks.add(currentTaskVO);
            }
        }
        return updatedTasks;
    }



    public Date getStarDate() {
        return starDate;
    }

    public void setStarDate(Date starDate) {
        this.starDate = starDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<CurrentTask> getTasksFromDB() {
        return tasksFromDB;
    }

    public void setTasksFromDB(List<CurrentTask> tasksFromDB) {
        this.tasksFromDB = tasksFromDB;
    }

    public StringBuilder getFailedCases() {
        return failedCases;
    }

    public void setFailedCases(StringBuilder failedCases) {
        this.failedCases = failedCases;
    }
}
