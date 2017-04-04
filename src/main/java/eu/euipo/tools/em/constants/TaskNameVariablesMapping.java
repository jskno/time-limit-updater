package eu.euipo.tools.em.constants;

/**
 * Created by canogjo on 03/04/2017.
 */
public enum TaskNameVariablesMapping {
        T_OPPO_DLN_COP_PERIOD				("T_OPPO_DLN_COP_PERIOD","ret_letterDueDateCOP","unusedValue"),
        T_OPPO_WFR_INEX_FFEA                ("T_OPPO_WFR_INEX_FFEA","ret_letterDueDateFFEA","unusedValue"),
        T_OPPO_WFR_INEX_AO                  ("T_OPPO_WFR_INEX_AO","ret_letterDueDateAO","unusedValue"),
//        T_OPPO_DLN_CHECK_SUSP               ("T_OPPO_DLN_CHECK_SUSP","","unusedValue"),
//        T_OPPO_VALIDATE_OPPO_FEES           ("T_OPPO_VALIDATE_OPPO_FEES","","unusedValue"),
//        T_OPPO_WFR_MAINTAIN_OPPO            ("T_OPPO_WFR_MAINTAIN_OPPO","","unusedValue"),
//        T_OPPO_WAIT_DEC_ON_ADM              ("T_OPPO_WAIT_DEC_ON_ADM", "", "unusedValue"),
//        T_OPPO_WFR_APT_IR_REP               ("T_OPPO_WFR_APT_IR_REP","","unusedValue"),
//        T_OPPO_DLN_END_SUSP                 ("T_OPPO_DLN_END_SUSP","","unusedValue"),
//        T_OPPO_WFR_RMD_ADM_DEF              ("T_OPPO_WFR_RMD_ADM_DEF","","unusedValue"),
//        T_OPPO_WAIT_DEC_LACK_PAYM           ("T_OPPO_WAIT_DEC_LACK_PAYM","","unusedValue"),
//        T_OPPO_WFR_LATE_PAYM                ("T_OPPO_WFR_LATE_PAYM","","unusedValue"),
//        T_OPPO_WFR_INEX_OO                  ("T_OPPO_WFR_INEX_OO","","unusedValue")
        ;

        private String taskName;
        private String timeLimitVariable;
        private String currentTaskIdVariable;

        TaskNameVariablesMapping(String taskName, String currentTaskVariable, String dateNeeded) {
                this.taskName = taskName;
                this.timeLimitVariable = currentTaskVariable;
                this.currentTaskIdVariable = dateNeeded;
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
}
