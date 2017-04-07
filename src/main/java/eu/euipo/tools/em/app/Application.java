package eu.euipo.tools.em.app;

import eu.euipo.tools.em.constants.TaskNameVariablesMapping;
import eu.euipo.tools.em.constants.TimeLimitUpdaterConstants;
import eu.euipo.tools.em.persistence.entity.CurrentTask;
import eu.euipo.tools.em.persistence.model.CurrentTaskVO;
import eu.euipo.tools.em.persistence.model.JoinResult;
import eu.euipo.tools.em.persistence.model.WorkflowInfoVO;
import eu.euipo.tools.em.persistence.model.WorkflowTask;
import eu.euipo.tools.em.service.CurrentTaskService;
import eu.euipo.tools.em.service.ScriptWriter;
import eu.euipo.tools.em.service.WorkflowManager;
import eu.euipo.tools.em.utils.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    private List<CurrentTask> tasksFromDB;
    private Set<String> dossierIds = new HashSet<>();
    private Date starDate;
    private Date endDate;
    private String taskName;
    private int numberOfRows;

    private StringBuilder failedCasesBuilder = new StringBuilder();

    public void run(String[] args) {

        validateUserInputs(args);
        tasksFromDB = getTasksFromDB(args);
        taskFromDBToDossierIdSet(tasksFromDB);
        List<Integer> currentTaskIds = taskFromDBTotaskIdSet(tasksFromDB);

        // currentTaskId - timeLimit FROM WFW
        WorkflowInfoVO workflowInfo = null;
        if(!dossierIds.isEmpty()) {
            // Gets the workflow info related with the dossiersId of the tasks from DB
            workflowInfo = getWorkflowInfo(dossierIds);
        }

        // Write update script
        if(!workflowInfo.getTaskIdTimeLimitMap().isEmpty()) {
            JoinResult joinResult = matchingTasksToVO(workflowInfo.getTaskIdTimeLimitMap(), currentTaskIds);
            scriptWriter.writeUpdatedScript(joinResult.getUpdatedTasks());
            scriptWriter.writeNotUpdatedScript(joinResult.getNotUpdatedTask());
        }

        if(!workflowInfo.getWorkflowTaskWithNoTimeLimitField().isEmpty()) {
            List<WorkflowTask> matchingFailedTasks = getMatchingFailedTasks(workflowInfo.getWorkflowTaskWithNoTimeLimitField(), currentTaskIds);
            scriptWriter.writeFieldNotFound(matchingFailedTasks);
        }
        scriptWriter.writeDossierNotFound(workflowInfo.getDossiersNotFound());

    }

    private WorkflowInfoVO getWorkflowInfo(Set<String> dossierIds) {
        WorkflowInfoVO workflowInfo = new WorkflowInfoVO();
        if(getTaskName().equals("all") || getTaskName().equals("byDossierId")) {
            workflowManager.getWorkflowInfo(workflowInfo, failedCasesBuilder, dossierIds);
        } else {
            workflowManager.getWorkflowByTaskName(workflowInfo, failedCasesBuilder, dossierIds, getTaskName());
        }
        return workflowInfo;
    }

    private void validateUserInputs(String[] args) {
        if(args == null || args.length == 2 || args.length > 4) {
            throw new RuntimeException("Wrong numbers of arguments");
        } else if(args.length ==1) {
            getDossierIds().add(args[0]);
            setTaskName("byDossierId");
        } else if(args.length == 3) {
            try {
                setStarDate(DateUtil.getDateFromString(args[0]));
                setEndDate(DateUtil.getDateFromString(args[1]));
            } catch (Exception e) {
                throw new RuntimeException("Date format is not correct");
            }
            setTaskName(args[2]);
        } else if(args.length == 4) {
            try {
                setStarDate(DateUtil.getDateFromString(args[0]));
                setEndDate(DateUtil.getDateFromString(args[1]));
            } catch (Exception e) {
                throw new RuntimeException("Date format is not correct");
            }
            setTaskName(args[2]);
            setNumberOfRows(Integer.valueOf(args[3]));
        }
    }

    private List<CurrentTask> getTasksFromDB(String[] args) {
        List<CurrentTask> tasks;
        if(args.length == 1) {
            tasks = currentTaskService.getTasksByEntity(args[0], TimeLimitUpdaterConstants.OPPO_ENTITY, TaskNameVariablesMapping.getNames());
        } else if(args.length == 3 && !getTaskName().equals("all")) {
            tasks = currentTaskService.getTasksByDatesAndTaskName(TimeLimitUpdaterConstants.OPPO_ENTITY, getTaskName(), getStarDate(), getEndDate());
        } else if(args.length == 3 && getTaskName().equals("all")) {
            tasks = currentTaskService.getTasksByDates(TimeLimitUpdaterConstants.OPPO_ENTITY, TaskNameVariablesMapping.getNames(), getStarDate(), getEndDate());
        } else if(args.length ==4 && getTaskName().equals("all")) {
            tasks = currentTaskService.getLimitedTasksByDates(numberOfRows, starDate, endDate);
        } else { // args.lenth == 4
            int idtask = TaskNameVariablesMapping.fromValue(getTaskName()).getIdtask();
            tasks = currentTaskService.getLimitedTasksByDatesAndTaskName(numberOfRows, idtask, starDate, endDate);
        }
        log.info("************** DATABASE ****************");
        log.info("Number of records from database: " + tasks.size());
        return tasks;
    }

    private JoinResult matchingTasksToVO(Map<Integer, String> mapFromWFW, List<Integer> currentTaskIds) {
        log.info("*************** MERGING INFO ************************");
        JoinResult joinResult = new JoinResult();
        List<Integer> notUpdatedTasks = new ArrayList<>(currentTaskIds);
        List<Integer> copyWorkflowTaskIds = new ArrayList<>(mapFromWFW.keySet());
        int count = 0;
        for(Integer key : mapFromWFW.keySet()) {
            if(currentTaskIds.contains(key) && mapFromWFW.get(key) != null && !mapFromWFW.get(key).isEmpty()) {
                log.info("Updated TaskID: " + key + " timeLimit : " + mapFromWFW.get(key));
                CurrentTaskVO currentTaskVO = new CurrentTaskVO();
                currentTaskVO.setIdcurrenttask(Integer.valueOf(key));
                currentTaskVO.setDtendtimelimit(DateUtil.getDateFromStringUSA(mapFromWFW.get(key)));
                joinResult.getUpdatedTasks().add(currentTaskVO);
                notUpdatedTasks.remove(key);
                copyWorkflowTaskIds.remove(key);
                count++;
            }
        }
        for(Integer taskId : notUpdatedTasks) {
            for(CurrentTask taskFromDB : tasksFromDB) {
                if(taskId.equals(taskFromDB.getIdcurrenttask())) {
                    CurrentTaskVO currentTaskVO = new CurrentTaskVO();
                    currentTaskVO.setIdcurrenttask(taskFromDB.getIdcurrenttask());
                    currentTaskVO.setIdentity(taskFromDB.getIdentity());
                    currentTaskVO.setTaskDesc(taskFromDB.getTaskType().getDsctask());
                    joinResult.getNotUpdatedTask().add(currentTaskVO);
                }
            }
        }
        log.info("Tasks retrieve from database: " + currentTaskIds.size());
        log.info("Tasks retrieved from workflow: " + mapFromWFW.keySet().size());
        log.info("Matching tasks:" + count);
        log.info("CurrentTask from database with no task in Workflow: " + notUpdatedTasks.size());
        log.info("List : " + notUpdatedTasks);
        log.info("Workflow task no matching task in database: " + copyWorkflowTaskIds.size());
        log.info("List : " + copyWorkflowTaskIds);
        log.info("********************************************");
        return joinResult;
    }

    private List<WorkflowTask> getMatchingFailedTasks(List<WorkflowTask> currentTaskIdWithNoTimeLimitField, List<Integer> currentTaskIds) {
        List<WorkflowTask> matchingFailedTasks = new ArrayList<>();
        for(WorkflowTask taskWithNoField : currentTaskIdWithNoTimeLimitField) {
            if(currentTaskIds.contains(taskWithNoField.getCurrentTaskId())) {
                matchingFailedTasks.add(taskWithNoField);
            }
        }
        return matchingFailedTasks;
    }


    private void taskFromDBToDossierIdSet(List<CurrentTask> tasksFromDB) {
        for(CurrentTask currentTask : tasksFromDB) {
            dossierIds.add(currentTask.getIdentity());
        }
        log.info("Number of dossierIds set: " + dossierIds.size());
    }

    private List<Integer> taskFromDBTotaskIdSet(List<CurrentTask> tasksFromDB) {
        List<Integer> currentTaskIds = new ArrayList<>();
        for(CurrentTask currentTask : tasksFromDB) {
            currentTaskIds.add(currentTask.getIdcurrenttask());
        }
        log.info("Number of currentTaskIds: " + currentTaskIds.size());
        return currentTaskIds;
    }

    public Set<String> getDossierIds() {
        return dossierIds;
    }

    public void setDossierIds(Set<String> dossierIds) {
        this.dossierIds = dossierIds;
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

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public StringBuilder getFailedCasesBuilder() {
        return failedCasesBuilder;
    }

    public void setFailedCasesBuilder(StringBuilder failedCasesBuilder) {
        this.failedCasesBuilder = failedCasesBuilder;
    }
}
