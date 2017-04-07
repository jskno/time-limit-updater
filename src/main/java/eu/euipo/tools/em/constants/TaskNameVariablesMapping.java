package eu.euipo.tools.em.constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by canogjo on 03/04/2017.
 */
public enum TaskNameVariablesMapping {
	T_OPPO_DLN_COP_PERIOD		("T_OPPO_DLN_COP_PERIOD","ret_letterDueDateCOP","unusedValue", "Deadline: End of COP", 26),
	T_OPPO_WFR_INEX_FFEA      	("T_OPPO_WFR_INEX_FFEA","ret_letterDueDateFFEA","unusedValue", "Check Substantiation", 51),
	T_OPPO_WFR_INEX_AO        	("T_OPPO_WFR_INEX_AO","ret_letterDueDateAO","unusedValue", "Check for Applicant's Observations", 86),
	T_OPPO_WFR_MAINTAIN_OPPO  	("T_OPPO_WFR_MAINTAIN_OPPO","ret_letterDueDate","ret_WFR_MAINT_taskId", "Opposition maintained?", 83),
	T_OPPO_DLN_CHECK_SUSP     	("T_OPPO_DLN_CHECK_SUSP","timeLimitEndDate","unusedValue", "Check Suspension", 30),
	T_OPPO_DLN_END_SUSP       	("T_OPPO_DLN_END_SUSP","timeLimitEndDate","unusedValue", "End Suspension", 147),
	T_OPPO_WFR_INEX_OO        	("T_OPPO_WFR_INEX_OO","timeLimitEndDate","unusedValue", "Check for Opponent's Reply", 275),
	T_OPPO_WAIT_DEC_LACK_PAYM 	("T_OPPO_WAIT_DEC_LACK_PAYM","ret_letterDueDate","ret_WFRTaskId", "WFR: Decision on lack of payment", 194), //MAIN
	T_OPPO_WFR_LATE_PAYM      	("T_OPPO_WFR_LATE_PAYM","ret_letterDueDate","ret_WFRTaskId", "WFR: Late Payment", 198), //MAIN
	T_OPPO_WFR_RMD_ADM_DEF    	("T_OPPO_WFR_RMD_ADM_DEF","ret_letterDueDate","ret_ADM_DEF_taskId", "Admissibility Deficiencies Response", 155), //MAIN
	T_OPPO_WAIT_DEC_ON_ADM    	("T_OPPO_WAIT_DEC_ON_ADM", "ret_letterDueDate", "ret_ADM_DEF_taskId", "Admissibility Decision to be taken", 98), //MAIN
	T_OPPO_VALIDATE_OPPO_FEES 	("T_OPPO_VALIDATE_OPPO_FEES","ret_FeeTimeLimit","ret_ValFeesTaskId", "Validate Opposition Fee", 48), //MAIN
	T_OPPO_WFR_APT_IR_REP     	("T_OPPO_WFR_APT_IR_REP","timeLimitEndDate","ret_WFR_taskId", "Check Appointment of IR Representative", 139); //MAIN

	private String taskName;
	private String timeLimitVariable;
	private String currentTaskIdVariable;
	private String taskDesc;
	private int idtask;


	TaskNameVariablesMapping(String taskName, String currentTaskVariable, String dateNeeded, String taskDesc, int idtask) {
		this.taskName = taskName;
		this.timeLimitVariable = currentTaskVariable;
		this.currentTaskIdVariable = dateNeeded;
		this.taskDesc = taskDesc;
		this.idtask = idtask;
	}

	public String getTaskName() {
		return taskName;
	}

	public String getTimeLimitVariable() {
		return timeLimitVariable;
	}

	public String getCurrentTaskIdVariable() {
		return currentTaskIdVariable;
	}

	public String getTaskDesc() {
		return taskDesc;
	}

	public int getIdtask() {
		return idtask;
	}

	public static TaskNameVariablesMapping fromValue(String taskName) {
		TaskNameVariablesMapping result = null;
		for (TaskNameVariablesMapping keyEnum : TaskNameVariablesMapping.values()) {
			if (keyEnum.getTaskName().equals(taskName)) {
				result = keyEnum;
				break;
			}
		}
		return result;
	}

	public static int getSize() {
		return TaskNameVariablesMapping.values().length;
	}

	public static List<String> getNames() {
		List<String> names = new ArrayList<>();
		for(TaskNameVariablesMapping mapping : TaskNameVariablesMapping.values()) {
			names.add(mapping.getTaskName());
		}
		return names;

	}
}
