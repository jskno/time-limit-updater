package eu.euipo.tools.em.persistence.repository;

import eu.euipo.tools.em.persistence.entity.CurrentTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by canogjo on 31/03/2017.
 */
@Repository
public interface CurrentTaskRepository extends JpaRepository<CurrentTask, String> {

    @Query("SELECT d FROM CurrentTask d WHERE d.identity = :identity AND d.tyentity = :tyentity AND d.taskType.nmtask IN (:tasksNames)")
    List<CurrentTask> getTasksByEntity(
            @Param("identity") String identity,
            @Param("tyentity") Integer tyentity,
            @Param("tasksNames") List<String> nmtask);

    @Query("SELECT d FROM CurrentTask d WHERE d.tyentity = :tyentity AND d.taskType.nmtask IN (:tasksNames) AND d.dtinsert BETWEEN :startdate AND :enddate")
    List<CurrentTask> getTasksByDates(
            @Param("tyentity") Integer tyentity,
            @Param("tasksNames") List<String> nmtask,
            @Param("startdate")Date startdate,
            @Param("enddate") Date enddate);

    @Query(value = "SELECT FIRST 100 * FROM dcurrenttask d WHERE d.tyentity = 3 AND d.taskType.nmtask IN ('T_OPPO_DLN_COP_PERIOD', 'T_OPPO_WFR_INEX_AO', 'T_OPPO_WFR_INEX_FFEA') AND d.dtinsert BETWEEN ?1 AND ?2",
            nativeQuery = true)
    List<CurrentTask> get100TasksByDates(
            @Param("startdate")Date startdate,
            @Param("enddate") Date enddate);

}
