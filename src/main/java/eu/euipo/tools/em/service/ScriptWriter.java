package eu.euipo.tools.em.service;

import eu.euipo.tools.em.persistence.model.CurrentTaskVO;

import java.util.List;

/**
 * Created by canogjo on 03/04/2017.
 */
public interface ScriptWriter {

    void writeUpdateScript(List<CurrentTaskVO> tasks);

    void writeFailScript(StringBuilder failedCases);
}
