package eu.euipo.tools.em.service;

import eu.euipo.tools.em.persistence.model.CurrentTaskVO;
import eu.euipo.tools.em.persistence.model.WorkflowTask;

import java.util.List;

/**
 * Created by canogjo on 03/04/2017.
 */
public interface ScriptWriter {

    void writeUpdatedScript(List<CurrentTaskVO> tasks);

    void writeNotUpdatedScript(List<CurrentTaskVO> tasks);

    void writeScript(StringBuilder builder, String filename);

    void writeDossierNotFound(List<String> dossiersNotFound);

    void writeFieldNotFound(List<WorkflowTask> matchingFailedTasks);
}
