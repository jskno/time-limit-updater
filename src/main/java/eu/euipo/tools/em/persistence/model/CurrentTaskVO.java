package eu.euipo.tools.em.persistence.model;

import java.util.Date;

/**
 * Created by canogjo on 04/04/2017.
 */
public class CurrentTaskVO {

    private Integer idcurrenttask;
    private Date dtendtimelimit;

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
}
