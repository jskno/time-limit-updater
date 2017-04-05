package eu.euipo.tools.em.service.impl;

import com.sun.net.httpserver.Authenticator;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import eu.euipo.tools.em.config.filenet.P8Connection;
import eu.euipo.tools.em.constants.FSRConstants;
import eu.euipo.tools.em.constants.TaskNameVariablesMapping;
import eu.euipo.tools.em.persistence.model.PairFromWFW;
import eu.euipo.tools.em.persistence.model.WorkflowTask;
import eu.euipo.tools.em.service.WorkflowManager;
import filenet.vw.api.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class WorkflowManagerImpl implements WorkflowManager{

	private static Logger log = Logger.getLogger(WorkflowManagerImpl.class);
	
	private VWSession peSession;

	@Autowired
	public WorkflowManagerImpl(final P8Connection p8Connection) {
		log.info("------------Instantiating WorkflowManager--------------");
		this.peSession = p8Connection.getPESession();
	}
	

	public void repeatAllByName(String wfwName) {
		try {
			VWQueue theQueue 		= peSession.getQueue(FSRConstants.USER_WORK_QUEUE_NAME);
			VWQueueQuery queueQuery = theQueue.createQuery(null, null, null,
					0, "F_SUBJECT like '" + wfwName + "'", null, VWFetchType.FETCH_TYPE_WORKOBJECT);
			int count = 0;
			//Iterate The Found Objects
			while (queueQuery.hasNext()) {
				VWWorkObject obj = (VWWorkObject) queueQuery.next();
				obj.doLock(true);
				obj.getFieldValue(TaskNameVariablesMapping.T_OPPO_DLN_COP_PERIOD.getTimeLimitVariable());
			}
			log.debug("Completed Steps = " + count);
		} catch (VWException e) {
			log.error(e);
		}
	}

	public void getWFWByDossierId(String dossierId) {
		try {
			VWQueue theQueue = peSession.getQueue(FSRConstants.WORK_QUEUE_NAME);
			VWQueueQuery queueQuery = theQueue.createQuery(null, null, null,
					0, "dossierId like '" + dossierId + "'", null, VWFetchType.FETCH_TYPE_WORKOBJECT);
			int count = 0;
			//Iterate The Found Objects
			while (queueQuery.hasNext()) {
				VWWorkObject obj = (VWWorkObject) queueQuery.next();
//				obj.doLock(true);
			}
			log.debug("Completed Steps = " + count);
		} catch (VWException e) {
			log.error(e);
		}
	}

	public void getWFWByWorkFlowNumber(String workflowId) {
		try {
			VWQueue theQueue 		= peSession.getQueue(FSRConstants.WORK_QUEUE_NAME);
			VWQueueQuery queueQuery = theQueue.createQuery(null, null, null,
					0, "F_WorkFlowNumber = '50905C585D7A694EAE14AB9BA985AF3B' ", null, VWFetchType.FETCH_TYPE_WORKOBJECT);
			int count = 0;
			//Iterate The Found Objects
			while (queueQuery.hasNext()) {
				VWWorkObject obj = (VWWorkObject) queueQuery.next();
				log.info(obj.getAuthoredFieldNames());
				log.info(obj.getStepName());
			}
			log.debug("Completed Steps = " + count);
		} catch (VWException e) {
			log.error(e);
		}
	}

	public void getWFWByWobNum(String workflowId) {
		try {
			VWQueue theQueue 		= peSession.getQueue(FSRConstants.WORK_QUEUE_NAME);
			VWQueueQuery queueQuery = theQueue.createQuery(null, null, null,
					0, "F_WobNum = '" + workflowId + "'", null, VWFetchType.FETCH_TYPE_WORKOBJECT);
			int count = 0;
			//Iterate The Found Objects
			while (queueQuery.hasNext()) {
				VWWorkObject obj = (VWWorkObject) queueQuery.next();
				log.info(obj.getAuthoredFieldNames());
				log.info(obj.getStepName());
////				obj.doLock(true);
//				obj.setSelectedResponse(FSRConstants.RESPONSE_REPEAT);
//				obj.doDispatch();
			}
			log.debug("Completed Steps = " + count);
		} catch (VWException e) {
			log.error(e);
		}
	}

	public void getAll() {
		try {
			VWQueue theQueue = peSession.getQueue(FSRConstants.WORK_QUEUE_NAME);
			VWQueueQuery queueQuery = theQueue.createQuery(null, null, null,
					0, null , null, VWFetchType.FETCH_TYPE_WORKOBJECT);
			int count = 0;
			//Iterate The Found Objects
			while (queueQuery.hasNext()) {
				VWWorkObject obj = (VWWorkObject) queueQuery.next();
				log.info(obj.getStepName());
				log.info(obj.getFieldNames());
			}
			log.debug("Completed Steps = " + count);
		} catch (VWException e) {
			log.error(e);
		}
	}

	public void getWorkflow(Map<String, String> mapFromWFW, TaskNameVariablesMapping mapping, StringBuilder builder, List<String> tasksIds) {
		try {
			VWQueue theQueue = peSession.getQueue(FSRConstants.WORK_QUEUE_NAME);
			VWQueueQuery queueQuery = theQueue.createQuery(null, null, null,
					0, "F_StepName = '" + mapping.getTaskName() + "'" ,
					null, VWFetchType.FETCH_TYPE_WORKOBJECT);

			log.info("WORKFLOWS RETRIEVED FOR " + mapping.getTaskName() + " " + queueQuery.fetchCount());
			int failed = 0;
			int failedAndAdded = 0;
			int success = 0;
			//Iterate The Found Objects
			while (queueQuery.hasNext()) {
				VWWorkObject obj = (VWWorkObject) queueQuery.next();
				WorkflowTask workflowTask = new WorkflowTask();
				try {
					mapToWorkflowTask(obj, workflowTask, mapping);
				} catch (VWException e) {
					failed++;
					if(tasksIds.contains(workflowTask.getCurrentTaskId())) {
						e.printStackTrace();
						log.info(e);
						addFailedElementToBuilder(workflowTask, builder);
						failedAndAdded++;
					}
					continue;
				}
				if(tasksIds.contains(workflowTask.getCurrentTaskId())) {
					if(!workflowTask.getTimeLimit().isEmpty()) {
						success++;
						mapFromWFW.put(workflowTask.getCurrentTaskId(), workflowTask.getTimeLimit());
					} else {
						addFailedElementToBuilder(workflowTask, builder);
					}
					log.info(workflowTask);
				}
			}
			log.info("CASES FAILED BECAUSE DON'T HAVE VARIABLE : " + failed);
			log.info("CASES FAILED BECAUSE DON'T HAVE VARIABLE AND ADDED TO THE FILE : " + failedAndAdded);
			log.info("CASES SUCCESFULLY UPDATED " + success);
		} catch (VWException e) {
			log.error(e);
		}

	}

	private WorkflowTask mapToWorkflowTask(VWWorkObject obj, WorkflowTask workflowTask, TaskNameVariablesMapping mapping) throws VWException {

		workflowTask.setWorkflowName(obj.getSubject());
		workflowTask.setTaskName(obj.getStepName());
		workflowTask.setWorkflowNumber(obj.getWorkflowNumber());
		workflowTask.setTimeLimit((String) obj.getFieldValue(mapping.getTimeLimitVariable()));
		workflowTask.setCurrentTaskId((String) obj.getFieldValue(mapping.getCurrentTaskIdVariable()));

		return workflowTask;
	}

	private void addFailedElementToBuilder(WorkflowTask workflowTask, StringBuilder builder) {
		builder.append(workflowTask);
		builder.append(System.getProperty("line.separator"));
	}


//				for(String field : obj.getFieldNames()) {
//					log.info(field);
//				}

//			VWQueueQuery queueQuery = theQueue.createQuery(null, null, null,
//					0, "F_SUBJECT = '" + mapping.getWorkFlowId() + "' and F_StepName = '" + mapping.getTaskName() + "'" ,
//					null, VWFetchType.FETCH_TYPE_WORKOBJECT);

}
