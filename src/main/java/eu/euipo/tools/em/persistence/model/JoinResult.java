package eu.euipo.tools.em.persistence.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by canogjo on 06/04/2017.
 */
public class JoinResult {

    List<CurrentTaskVO> updatedTasks;
    List<CurrentTaskVO> notUpdatedTask;

    public JoinResult() {
        this.updatedTasks = new ArrayList<>();
        this.notUpdatedTask = new ArrayList<>();
    }

    public List<CurrentTaskVO> getUpdatedTasks() {
        return updatedTasks;
    }

    public void setUpdatedTasks(List<CurrentTaskVO> updatedTasks) {
        this.updatedTasks = updatedTasks;
    }

    public List<CurrentTaskVO> getNotUpdatedTask() {
        return notUpdatedTask;
    }

    public void setNotUpdatedTask(List<CurrentTaskVO> notUpdatedTask) {
        this.notUpdatedTask = notUpdatedTask;
    }
}
