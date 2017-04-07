package eu.euipo.tools.em.service;

import eu.euipo.tools.em.persistence.model.WorkflowInfoVO;

import java.util.Set;

/**
 * Created by canogjo on 03/04/2017.
 */
public interface WorkflowManager {
    void getWorkflowByDossierId(WorkflowInfoVO workflowInfo, String dossierId);
    void getWorkflowByTaskName(WorkflowInfoVO workflowInfo, StringBuilder failedCasesBuilder, Set<String> dossierIds, String taskName);
    void getWorkflowInfo(WorkflowInfoVO workflowInfo, StringBuilder builder, Set<String> dossierIds);
}
