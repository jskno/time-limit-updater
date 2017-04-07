package eu.euipo.tools.em.persistence.model;

import java.util.Date;

/**
 * Created by canogjo on 04/04/2017.
 */
public class CurrentTaskVO {

    private Integer idcurrenttask;
    private Date dtendtimelimit;
    private String identity;
    private String taskDesc;

    public Integer getIdcurrenttask() {
        return idcurrenttask;
    }

    public void setIdcurrenttask(Integer idcurrenttask) {
        this.idcurrenttask = idcurrenttask;
    }

    public Date getDtendtimelimit() {
        return dtendtimelimit;
    }

    public void setDtendtimelimit(Date dtendtimelimit) {
        this.dtendtimelimit = dtendtimelimit;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    @Override
    public String toString() {
        return "CurrentTaskVO{" +
                "idcurrenttask=" + idcurrenttask +
                ", dtendtimelimit=" + dtendtimelimit +
                ", identity='" + identity + '\'' +
                ", taskDesc='" + taskDesc + '\'' +
                '}';
    }
}
