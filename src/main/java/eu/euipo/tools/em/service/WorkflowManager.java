package eu.euipo.tools.em.service;

import eu.euipo.tools.em.constants.TaskNameVariablesMapping;
import eu.euipo.tools.em.persistence.model.PairFromWFW;

import java.util.List;
import java.util.Map;

/**
 * Created by canogjo on 03/04/2017.
 */
public interface WorkflowManager {

    void getWFWByDossierId(String dossierId);
    void repeatAllByName(String wfwName);
    void getWFWByWobNum(String workflowId);
    void getWorkflow(Map<String, String> mapFromWFW, TaskNameVariablesMapping mapping, StringBuilder builder, List<String> tasksIds);
}
