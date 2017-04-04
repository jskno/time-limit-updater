package eu.euipo.tools.em.persistence.model;

import java.util.Map;

/**
 * Created by canogjo on 03/04/2017.
 */
public class PairFromWFW {

    private String taskName;
    private String timeLimit;

    public PairFromWFW(String taskName, String timeLimit) {
        this.taskName = taskName;
        this.timeLimit = timeLimit;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }
}
