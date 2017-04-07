package eu.euipo.tools.em.service;

import eu.euipo.tools.em.persistence.entity.CurrentTask;

import java.util.Date;
import java.util.List;

/**
 * Created by canogjo on 31/03/2017.
 */
public interface CurrentTaskService {

    List<CurrentTask> getTasksByEntity(String identity, Integer tyentity, List<String> tasksNames);

    List<CurrentTask> getTasksByDates(Integer tyentity, List<String> tasksNames, Date startDate, Date endDate);

    List<CurrentTask> getLimitedTasksByDates(int numOfRows, Date startDate, Date endDate);

    List<CurrentTask> getLimitedTasksByDatesAndTaskName(int numOfRows, int idtask, Date startDate, Date endDate);

    List<CurrentTask> getTasksByDatesAndTaskName(Integer tyentity, String tasksName, Date startDate, Date endDate);

}
