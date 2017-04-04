package eu.euipo.tools.em.persistence.entity;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by canogjo on 31/03/2017.
 */
@Entity
@Table(name = "ptask")
public class TaskType {


    @Id
    @Column(name = "idtask")
    private Integer idtask;

    @Column(name = "nmtask")
    private String nmtask;

    @Column(name = "dsctask")
    private String dsctask;

    @Column(name = "isregsuspender")
    private Boolean isregsuspender;

    @Column(name = "tyctmmode")
    private Integer tyctmmode;

    @Column(name = "tyrecipient")
    private Integer tyrecipient;

    public Integer getIdtask() {
        return idtask;
    }

    public void setIdtask(Integer idtask) {
        this.idtask = idtask;
    }

    public String getNmtask() {
        return nmtask;
    }

    public void setNmtask(String nmtask) {
        this.nmtask = nmtask;
    }

    public String getDsctask() {
        return dsctask;
    }

    public void setDsctask(String dsctask) {
        this.dsctask = dsctask;
    }

    public Boolean getIsregsuspender() {
        return isregsuspender;
    }

    public void setIsregsuspender(Boolean isregsuspender) {
        this.isregsuspender = isregsuspender;
    }

    public Integer getTyctmmode() {
        return tyctmmode;
    }

    public void setTyctmmode(Integer tyctmmode) {
        this.tyctmmode = tyctmmode;
    }

    public Integer getTyrecipient() {
        return tyrecipient;
    }

    public void setTyrecipient(Integer tyrecipient) {
        this.tyrecipient = tyrecipient;
    }

    @Override
    public String toString() {
        return "TaskType{" +
                "idtask=" + idtask +
                ", nmtask='" + nmtask + '\'' +
                '}';
    }
}
