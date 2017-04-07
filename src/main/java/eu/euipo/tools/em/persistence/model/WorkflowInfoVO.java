package eu.euipo.tools.em.persistence.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by canogjo on 03/04/2017.
 */
public class WorkflowInfoVO {

    private Map<Integer, String> taskIdTimeLimitMap;
    private List<String> dossiersNotFound;
    private List<WorkflowTask> workflowTaskWithNoTimeLimitField;

    public WorkflowInfoVO() {
        this.taskIdTimeLimitMap = new HashMap<>();
        this.dossiersNotFound = new ArrayList<>();
        this.workflowTaskWithNoTimeLimitField = new ArrayList<>();
    }

    public Map<Integer, String> getTaskIdTimeLimitMap() {
        return taskIdTimeLimitMap;
    }

    public void setTaskIdTimeLimitMap(Map<Integer, String> taskIdTimeLimitMap) {
        this.taskIdTimeLimitMap = taskIdTimeLimitMap;
    }

    public List<String> getDossiersNotFound() {
        return dossiersNotFound;
    }

    public void setDossiersNotFound(List<String> dossiersNotFound) {
        this.dossiersNotFound = dossiersNotFound;
    }

    public List<WorkflowTask> getWorkflowTaskWithNoTimeLimitField() {
        return workflowTaskWithNoTimeLimitField;
    }

    public void setWorkflowTaskWithNoTimeLimitField(List<WorkflowTask> workflowTaskWithNoTimeLimitField) {
        this.workflowTaskWithNoTimeLimitField = workflowTaskWithNoTimeLimitField;
    }
}
