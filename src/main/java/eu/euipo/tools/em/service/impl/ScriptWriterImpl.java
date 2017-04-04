package eu.euipo.tools.em.service.impl;

import eu.euipo.tools.em.persistence.entity.CurrentTask;
import eu.euipo.tools.em.persistence.model.CurrentTaskVO;
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
	private static final String FILE_PATH2 = "updateScript.sql";

	private StringBuilder updateEntriesBuilder;

	public ScriptWriterImpl() { }

	@Override
	public void writeUpdateScript(List<CurrentTaskVO> tasks) {
		updateEntriesBuilder = new StringBuilder();
		for (CurrentTaskVO task : tasks) {
			writeUpdateElemet(updateEntriesBuilder, task);
        }
		try {
            stringToFile(FILE_PATH2, updateEntriesBuilder.toString());
        } catch (IOException e) {

        }
	}

	@Override
	public void writeFailScript(StringBuilder failedCases) {
		try {
			stringToFile("failedCases.sql", failedCases.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	private void stringToFile(String fileName, String script) throws IOException {
		Files.write(Paths.get("./" + fileName), script.getBytes());
	}

}
