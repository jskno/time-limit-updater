package eu.euipo.tools.em.persistence.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by canogjo on 31/03/2017.
 */
@Entity
@Table(name = "dcurrenttask")
public class CurrentTask {

    @Id
    @Column(name = "idcurrenttask")
    private Integer idcurrenttask;

    @Column(name = "idappli")
    private String idappli;

    @Column(name = "wfidprocess")
    private String wfidprocess;

    @Column(name = "iscustomtask")
    private Boolean iscustomtask;

    @Column(name = "dtinsert")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtinsert;

    @Column(name = "logcreatedby")
    private String logcreatedby;

    @Column(name = "tyallocated")
    private Boolean tyallocated;

    @Column(name = "logallocatedto")
    private String logallocatedto;

    @Column(name = "dtstarttask")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtstarttask;

    @Column(name = "dtendtask")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtendtask;

    @Column(name = "stcurrenttask")
    private Integer stcurrenttask;

    @Column(name = "tytaskresult")
    private Integer tytaskresult;

    @Column(name = "dtvisibility")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtvisibility;

    @Column(name = "isvisible")
    private Boolean isvisible;

    @Column(name = "tyentity")
    private Integer tyentity;

    @Column(name = "identity")
    private String identity;

    @Column(name = "dsccurrenttask")
    private String dsccurrenttask;

    @Column(name = "idnotiftask")
    private String idnotiftask;

    @ManyToOne
    @JoinColumn(name = "idtask", referencedColumnName = "idtask", nullable = false)
    private TaskType taskType;

    @Column(name = "isread")
    private Boolean isread;

    @Column(name = "serviceresponse")
    private String serviceresponse;

    @Column(name = "tyrequestor")
    private Integer tyrequestor;

    @Column(name = "dtstarttimelimit")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtstarttimelimit;

    @Column(name = "dtendtimelimit")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtendtimelimit;

    @Column(name = "tysuspensionreason")
    private Integer tysuspensionreason;

    public Integer getIdcurrenttask() {
        return idcurrenttask;
    }

    public String getIdappli() {
        return idappli;
    }

    public String getWfidprocess() {
        return wfidprocess;
    }

    public Boolean getIscustomtask() {
        return iscustomtask;
    }

    public Date getDtinsert() {
        return dtinsert;
    }

    public String getLogcreatedby() {
        return logcreatedby;
    }

    public Boolean getTyallocated() {
        return tyallocated;
    }

    public String getLogallocatedto() {
        return logallocatedto;
    }

    public Date getDtstarttask() {
        return dtstarttask;
    }

    public Date getDtendtask() {
        return dtendtask;
    }

    public Integer getStcurrenttask() {
        return stcurrenttask;
    }

    public Integer getTytaskresult() {
        return tytaskresult;
    }

    public Date getDtvisibility() {
        return dtvisibility;
    }

    public Boolean getIsvisible() {
        return isvisible;
    }

    public Integer getTyentity() {
        return tyentity;
    }

    public String getIdentity() {
        return identity;
    }

    public String getDsccurrenttask() {
        return dsccurrenttask;
    }

    public String getIdnotiftask() {
        return idnotiftask;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public Boolean getIsread() {
        return isread;
    }

    public String getServiceresponse() {
        return serviceresponse;
    }

    public Integer getTyrequestor() {
        return tyrequestor;
    }

    public Date getDtstarttimelimit() {
        return dtstarttimelimit;
    }

    public Date getDtendtimelimit() {
        return dtendtimelimit;
    }

    public Integer getTysuspensionreason() {
        return tysuspensionreason;
    }

    @Override
    public String toString() {
        return "CurrentTaskEntity{" +
                "idcurrenttask=" + idcurrenttask +
                ", idappli='" + idappli + '\'' +
                ", dtinsert=" + dtinsert +
                ", dtstarttask=" + dtstarttask +
                ", dtendtask=" + dtendtask +
                ", stcurrenttask=" + stcurrenttask +
                ", tyentity=" + tyentity +
                ", identity='" + identity + '\'' +
                ", dsccurrenttask='" + dsccurrenttask + '\'' +
                ", taskType=" + taskType +
                ", dtstarttimelimit=" + dtstarttimelimit +
                ", dtendtimelimit=" + dtendtimelimit +
                '}';
    }
}
