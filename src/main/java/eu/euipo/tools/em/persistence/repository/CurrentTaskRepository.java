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
            @Param("startdate") Date startdate,
            @Param("enddate") Date enddate);

    @Query(value = "SELECT FIRST ?1 * FROM dcurrenttask d WHERE d.tyentity = 3 AND d.idtask IN (26,30,48,51,83,86,98,139,147,155,194,198,275) AND d.dtinsert BETWEEN ?2 AND ?3",
            nativeQuery = true)
    List<CurrentTask> getLimitedTasksByDates(
            @Param("numOfRows") int numOfRows,
            @Param("startdate") Date startdate,
            @Param("enddate") Date enddate);

    @Query(value = "SELECT FIRST ?1 * FROM dcurrenttask d WHERE d.tyentity = 3 AND d.idtask = ?2 AND d.dtinsert BETWEEN ?3 AND ?4",
            nativeQuery = true)
    List<CurrentTask> getLimitedTasksByDatesAndTaskName(
            @Param("numOfRows") int numOfRows,
            @Param("idtask") int idtask,
            @Param("startdate") Date startdate,
            @Param("enddate") Date enddate);

    @Query("SELECT d FROM CurrentTask d WHERE d.tyentity = :tyentity AND d.taskType.nmtask = :taskName AND d.dtinsert BETWEEN :startdate AND :enddate")
    List<CurrentTask> getTasksByDatesAndTaskName(
            @Param("tyentity") Integer tyentity,
            @Param("taskName") String nmtask,
            @Param("startdate") Date startdate,
            @Param("enddate") Date enddate);

}
