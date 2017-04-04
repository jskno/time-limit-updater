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
@Service()
public class CurrentTaskServiceImpl implements CurrentTaskService {

    @Autowired
    private CurrentTaskRepository currentTaskRepository;

    @Override
    public List<CurrentTask> getTasksByEntity(String identity, Integer tyentity, List<String> tasksNames) {
        return currentTaskRepository.getTasksByEntity(identity, tyentity, tasksNames);
    }

    @Override
    public List<CurrentTask> getTasksByDates(Integer tyentity, List<String> tasksNames, Date startDate, Date endDate) {
        return currentTaskRepository.getTasksByDates(tyentity, tasksNames, startDate, endDate);
    }

    @Override
    public List<CurrentTask> get100TasksByDates(Date startDate, Date endDate) {
        return currentTaskRepository.get100TasksByDates(startDate, endDate);
    }

}
