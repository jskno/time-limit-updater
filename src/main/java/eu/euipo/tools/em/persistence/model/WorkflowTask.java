package eu.euipo.tools.em.persistence.model;

/**
 * Created by canogjo on 04/04/2017.
 */
public class WorkflowTask {

    private String workflowName;
    private String taskName;
    private String workflowNumber;
    private String currentTaskId;
    private String timeLimit;

    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getWorkflowNumber() {
        return workflowNumber;
    }

    public void setWorkflowNumber(String workflowNumber) {
        this.workflowNumber = workflowNumber;
    }

    public String getCurrentTaskId() {
        return currentTaskId;
    }

    public void setCurrentTaskId(String currentTaskId) {
        this.currentTaskId = currentTaskId;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    @Override
    public String toString() {
        return "WorkflowTask{" +
                "workflowName='" + workflowName + '\'' +
                ", taskName='" + taskName + '\'' +
                ", workflowNumber='" + workflowNumber + '\'' +
                ", currentTaskId='" + currentTaskId + '\'' +
                ", timeLimit='" + timeLimit + '\'' +
                '}';
    }
}
