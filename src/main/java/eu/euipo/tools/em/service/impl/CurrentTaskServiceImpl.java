package eu.euipo.tools.em.service.impl;

import eu.euipo.tools.em.persistence.entity.CurrentTask;
import eu.euipo.tools.em.persistence.repository.CurrentTaskRepository;
import eu.euipo.tools.em.service.CurrentTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by canogjo on 31/03/2017.
 */
@Service
public class CurrentTaskServiceImpl implements CurrentTaskService {

    @Autowired
    CurrentTaskRepository currentTaskRepository;

    @Override
    public List<CurrentTask> getTasksByEntity(String identity, Integer tyentity, List<String> tasksNames) {
        return currentTaskRepository.getTasksByEntity(identity, tyentity, tasksNames);
    }

    @Override
    public List<CurrentTask> getTasksByDates(Integer tyentity, List<String> tasksNames, Date startDate, Date endDate) {
        return currentTaskRepository.getTasksByDates(tyentity, tasksNames, startDate, endDate);
    }

    @Override
    public List<CurrentTask> getLimitedTasksByDates(int numOfRows, Date startDate, Date endDate) {
        return currentTaskRepository.getLimitedTasksByDates(numOfRows, startDate, endDate);
    }

    @Override
    public List<CurrentTask> getLimitedTasksByDatesAndTaskName(int numOfRows, int idtask, Date startDate, Date endDate) {
        return currentTaskRepository.getLimitedTasksByDatesAndTaskName(numOfRows, idtask, startDate, endDate);
    }

    @Override
    public List<CurrentTask> getTasksByDatesAndTaskName(Integer tyentity, String taskName, Date startDate, Date endDate) {
        return currentTaskRepository.getTasksByDatesAndTaskName(tyentity, taskName, startDate, endDate);
    }

}
