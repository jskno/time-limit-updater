package eu.euipo.tools.em.service.impl;

import eu.euipo.tools.em.config.filenet.P8Connection;
import eu.euipo.tools.em.constants.FSRConstants;
import eu.euipo.tools.em.constants.TaskNameVariablesMapping;
import eu.euipo.tools.em.persistence.model.WorkflowInfoVO;
import eu.euipo.tools.em.persistence.model.WorkflowTask;
import eu.euipo.tools.em.service.WorkflowManager;
import filenet.vw.api.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class WorkflowManagerImpl implements WorkflowManager{

	private static Logger log = Logger.getLogger(WorkflowManagerImpl.class);
	
	private VWSession peSession;
	private int failed;
	private int success;
	private int notFound;

	@Autowired
	public WorkflowManagerImpl(final P8Connection p8Connection) {
		log.info("------------Instantiating WorkflowManager--------------");
		this.peSession = p8Connection.getPESession();
	}

	@Override
	public void getWorkflowInfo(WorkflowInfoVO workflowInfo, StringBuilder builder, Set<String> dossierIds) {
		for(String dossierId : dossierIds) {
			getWorkflowByDossierId(workflowInfo, dossierId);
		}
		logResume(workflowInfo);
	}

	@Override
	public void getWorkflowByTaskName(WorkflowInfoVO workflowInfo, StringBuilder builder, Set<String> dossierIds, String taskName) {
		TaskNameVariablesMapping mapping = TaskNameVariablesMapping.fromValue(taskName);
		for(String dossierId : dossierIds) {
			getWorkflowByDossierAndTaskName(workflowInfo, dossierId, mapping);
		}
		logResume(workflowInfo);
	}

	private void logResume(WorkflowInfoVO workflowInfo) {
		log.info("*************** WORKFLOW INFO ************************");
		log.info("DossierIds not found in Filenet : " + notFound);
		log.info(workflowInfo.getDossiersNotFound());
		log.info("Tasks failed because don't have the defined variable with the time limit : " + failed);
		log.info(workflowInfo.getWorkflowTaskWithNoTimeLimitField());
		log.info("Tasks retrieved and added to the workflow data structure " + success);
		log.info(workflowInfo.getTaskIdTimeLimitMap().keySet());
	}

	@Override
	public void getWorkflowByDossierId(WorkflowInfoVO workflowInfo, String dossierId) {
		try {
			VWQueue theQueue = peSession.getQueue(FSRConstants.WORK_QUEUE_NAME);
			VWQueueQuery queueQuery = theQueue.createQuery(null, null, null,
					0, "dossierId = '" + dossierId + "' AND (" + getTaskNamesWhereCondition() + ")",
					null, VWFetchType.FETCH_TYPE_WORKOBJECT);

			getDataFromQueryObject(workflowInfo, dossierId, queueQuery);


		} catch (VWException e) {
			log.error(e);
		}
	}

	private void getDataFromQueryObject(WorkflowInfoVO workflowInfo, String dossierId, VWQueueQuery queueQuery) throws VWException {
		//Iterate The Found Objects
		int count = 0;
		while (queueQuery.hasNext()) {
			count++;
            VWWorkObject obj = (VWWorkObject) queueQuery.next();
            WorkflowTask workflowTask = new WorkflowTask();
            try {
                mapToWorkflowTaskFromDossier(obj, workflowTask);
            } catch (VWException e) {
				failed++;
                workflowInfo.getWorkflowTaskWithNoTimeLimitField().add(workflowTask);
                continue;
            }
			processWorkflowTask(workflowInfo, workflowTask);
        }
		if(count == 0){
            notFound++;
            workflowInfo.getDossiersNotFound().add(dossierId);
        }
	}

	private void getWorkflowByDossierAndTaskName(WorkflowInfoVO workflowInfo, String dossierId, TaskNameVariablesMapping mapping) {
		try {
			VWQueue theQueue = peSession.getQueue(FSRConstants.WORK_QUEUE_NAME);
			VWQueueQuery queueQuery = theQueue.createQuery(null, null, null,
					0, "F_StepName = '" + mapping.getTaskName() + "'"
							+ " and dossierId = '" + dossierId + "'",
					null, VWFetchType.FETCH_TYPE_WORKOBJECT);
			getDataFromQueryObjectWithTaskName(workflowInfo, dossierId, mapping, queueQuery);


		} catch (VWException e) {
			log.error(e);
		}
	}

	private void getDataFromQueryObjectWithTaskName(WorkflowInfoVO workflowInfo, String dossierId, TaskNameVariablesMapping mapping, VWQueueQuery queueQuery) throws VWException {
		//Iterate The Found Objects
		int count = 0;
		while (queueQuery.hasNext()) {
            count++;
            VWWorkObject obj = (VWWorkObject) queueQuery.next();
            WorkflowTask workflowTask = new WorkflowTask();
            try {
                mapToWorkflowTask(obj, workflowTask, mapping);
            } catch (VWException e) {
				failed++;
                workflowInfo.getWorkflowTaskWithNoTimeLimitField().add(workflowTask);
				continue;
            }
			processWorkflowTask(workflowInfo, workflowTask);
		}
		if(count == 0){
            notFound++;
            workflowInfo.getDossiersNotFound().add(dossierId);
        }
	}

	private void processWorkflowTask(WorkflowInfoVO workflowInfo, WorkflowTask workflowTask) {
		if(!workflowTask.getTimeLimit().isEmpty()) {
            success++;
            workflowInfo.getTaskIdTimeLimitMap().put(workflowTask.getCurrentTaskId(), workflowTask.getTimeLimit());
        } else {
            workflowInfo.getWorkflowTaskWithNoTimeLimitField().add(workflowTask);
            failed++;
        }
	}

	private WorkflowTask mapToWorkflowTask(VWWorkObject obj, WorkflowTask workflowTask, TaskNameVariablesMapping mapping) throws VWException {

		workflowTask.setWorkflowName(obj.getSubject());
		workflowTask.setTaskName(obj.getStepName());
		workflowTask.setWorkflowNumber(obj.getWorkflowNumber());
		workflowTask.setDossierId((String) obj.getFieldValue("dossierId"));
		String taskIdStr = (String) obj.getFieldValue(mapping.getCurrentTaskIdVariable());
		workflowTask.setCurrentTaskId(Integer.valueOf(taskIdStr));
		workflowTask.setTimeLimit((String) obj.getFieldValue(mapping.getTimeLimitVariable()));

		return workflowTask;
	}

	private WorkflowTask mapToWorkflowTaskFromDossier(VWWorkObject obj, WorkflowTask workflowTask) throws VWException {

		workflowTask.setWorkflowName(obj.getSubject());
		workflowTask.setTaskName(obj.getStepName());
		workflowTask.setWorkflowNumber(obj.getWorkflowNumber());
		workflowTask.setDossierId((String) obj.getFieldValue("dossierId"));
		TaskNameVariablesMapping mapping = TaskNameVariablesMapping.fromValue(workflowTask.getTaskName());
		String taskIdStr = (String) obj.getFieldValue(mapping.getCurrentTaskIdVariable());
		if(!taskIdStr.isEmpty()) {
			workflowTask.setCurrentTaskId(Integer.valueOf(taskIdStr));
		} else {
			System.out.println(taskIdStr);
		}

		workflowTask.setTimeLimit((String) obj.getFieldValue(mapping.getTimeLimitVariable()));

		return workflowTask;
	}

	private String getTaskNamesWhereCondition() {
		StringBuilder inTaskNamesCondition = new StringBuilder();
		TaskNameVariablesMapping[] mapping = TaskNameVariablesMapping.values();
		int mappingLength = TaskNameVariablesMapping.getSize();
		for(int i = 0; i < mappingLength; i++) {
			inTaskNamesCondition.append(" F_StepName = '" + mapping[i].getTaskName() + "'");
			if(i < mappingLength - 1) {
				inTaskNamesCondition.append(" OR");
			}
		}
		return inTaskNamesCondition.toString();
	}



//				for(String field : obj.getFieldNames()) {
//					log.info(field);
//				}

//			VWQueueQuery queueQuery = theQueue.createQuery(null, null, null,
//					0, "F_SUBJECT = '" + mapping.getWorkFlowId() + "' and F_StepName = '" + mapping.getTaskName() + "'" ,
//					null, VWFetchType.FETCH_TYPE_WORKOBJECT);

}
