package eu.euipo.tools.em.service.impl;

import eu.euipo.tools.em.constants.TaskNameVariablesMapping;
import eu.euipo.tools.em.persistence.entity.CurrentTask;
import eu.euipo.tools.em.persistence.model.CurrentTaskVO;
import eu.euipo.tools.em.persistence.model.WorkflowTask;
import eu.euipo.tools.em.service.ScriptWriter;
import eu.euipo.tools.em.utils.DateUtil;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Component
public class ScriptWriterImpl implements ScriptWriter{

	private static final String UPDATE_TEMPLATE = "UPDATE dcurrenttask SET dtendtimelimit=? WHERE idcurrenttask=?; ";
	private static final String FILE_PATH = "D\\devel\\Projects\\TimeLimitsTools\\time-limit-updater\\src\\main\\resources\\script\\updateScript.sql";
	private static final String UPDATED_SCRIPT = "updateScript.sql";
	private static final String NOT_UPDATED_SCRIPT = "notUpdateScript.txt";

	public ScriptWriterImpl() { }

	@Override
    public void writeUpdatedScript(List<CurrentTaskVO> tasks) {
        StringBuilder updateEntriesBuilder = new StringBuilder();
        for (CurrentTaskVO task : tasks) {
            writeUpdateElemet(updateEntriesBuilder, task);
        }
        try {
            stringToFile(UPDATED_SCRIPT, updateEntriesBuilder.toString());
        } catch (IOException e) {

        }
    }

    @Override
    public void writeNotUpdatedScript(List<CurrentTaskVO> tasks) {
        StringBuilder notUpdateEntriesBuilder = new StringBuilder();
        for (CurrentTaskVO task : tasks) {
			writeNotUpdatedElement(notUpdateEntriesBuilder, task);
        }
        try {
            stringToFile(NOT_UPDATED_SCRIPT, notUpdateEntriesBuilder.toString());
        } catch (IOException e) {

        }
    }

	@Override
	public void writeScript(StringBuilder builder, String fileName) {
		try {
			stringToFile(fileName, builder.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void writeDossierNotFound(List<String> dossiersNotFound) {
		StringBuilder builder = new StringBuilder();
		for(String dossierId : dossiersNotFound) {
			builder.append("Dossier not found in workflow : " + dossierId);
			builder.append(System.getProperty("line.separator"));
		}
		writeScript(builder, "dossiersNotFoundInWFW.txt");
	}

	@Override
	public void writeFieldNotFound(List<WorkflowTask> matchingFailedTasks) {
		StringBuilder builder = new StringBuilder();
		for(WorkflowTask task : matchingFailedTasks) {
			builder.append("CurrentTaskId with no time limit : ");
			builder.append("TaskId: " + task.getCurrentTaskId() + " dossierId: " + task.getDossierId() +
                    " TaskName: " + TaskNameVariablesMapping.fromValue(task.getTaskName()).getTaskDesc());
			builder.append(System.getProperty("line.separator"));
		}
		writeScript(builder, "tasksWithNoTimeLimit.txt");
	}

	private void writeUpdateElemet(StringBuilder builder, CurrentTaskVO task) {
		
		builder.append(UPDATE_TEMPLATE);
		int index = builder.indexOf("?", 0);
		builder.replace(index, index + 1, " TO_DATE('" + DateUtil.getCurrentYear(task.getDtendtimelimit()) + "-" +
														 DateUtil.getCurrentMonth(task.getDtendtimelimit()) + "-" +
														 DateUtil.getCurrentDay(task.getDtendtimelimit()) + "', '%Y-%m-%d')");
        index = builder.indexOf("?", 0);
        builder.replace(index, index + 1, " \"" + task.getIdcurrenttask() + "\"");

        builder.append(System.getProperty("line.separator"));
	}


    private void writeNotUpdatedElement(StringBuilder builder, CurrentTaskVO task) {

        builder.append("CurrentTaskId with no time limit : ");
        builder.append("TaskId: " + task.getIdcurrenttask() + " dossierId: " + task.getIdentity() +
                " TaskName: " + task.getTaskDesc());
        builder.append(System.getProperty("line.separator"));

    }

	private void stringToFile(String fileName, String script) throws IOException {
		Files.write(Paths.get("./" + fileName), script.getBytes());
	}

}
