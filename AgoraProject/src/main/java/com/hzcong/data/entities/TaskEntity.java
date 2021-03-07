package com.hzcong.data.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "task")
public class TaskEntity  implements Serializable {
    private String taskId;
    private String stuId;
    private String secId;
    private String teaId;
    private String courId;
    private String roomNumber;
    private String title;
    private Date startDate;
    private String classtime;
    private String teaName;
    private String stuName;

    @Id
    @Column(name = "task_id", columnDefinition = "char(32)")
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Basic
    @Column(name = "stu_id", columnDefinition = "char(32)")
    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    @Basic
    @Column(name = "sec_id", columnDefinition = "char(32)")
    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    @Basic
    @Column(name = "tea_id", columnDefinition = "char(32)")
    public String getTeaId() {
        return teaId;
    }

    public void setTeaId(String teaId) {
        this.teaId = teaId;
    }

    @Basic
    @Column(name = "cour_id", columnDefinition = "char(32)")
    public String getCourId() {
        return courId;
    }

    public void setCourId(String courId) {
        this.courId = courId;
    }

    @Basic
    @Column(name = "room_number", columnDefinition = "char(32)")
    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "start_date")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "classtime")
    public String getClasstime() {
        return classtime;
    }

    public void setClasstime(String classtime) {
        this.classtime = classtime;
    }

    @Basic
    @Column(name = "tea_name")
    public String getTeaName() {
        return teaName;
    }

    public void setTeaName(String teaName) {
        this.teaName = teaName;
    }

    @Basic
    @Column(name = "stu_name")
    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskEntity that = (TaskEntity) o;

        if (taskId != null ? !taskId.equals(that.taskId) : that.taskId != null) return false;
        if (stuId != null ? !stuId.equals(that.stuId) : that.stuId != null) return false;
        if (secId != null ? !secId.equals(that.secId) : that.secId != null) return false;
        if (teaId != null ? !teaId.equals(that.teaId) : that.teaId != null) return false;
        if (courId != null ? !courId.equals(that.courId) : that.courId != null) return false;
        if (roomNumber != null ? !roomNumber.equals(that.roomNumber) : that.roomNumber != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (classtime != null ? !classtime.equals(that.classtime) : that.classtime != null) return false;
        if (teaName != null ? !teaName.equals(that.teaName) : that.teaName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = taskId != null ? taskId.hashCode() : 0;
        result = 31 * result + (stuId != null ? stuId.hashCode() : 0);
        result = 31 * result + (secId != null ? secId.hashCode() : 0);
        result = 31 * result + (teaId != null ? teaId.hashCode() : 0);
        result = 31 * result + (courId != null ? courId.hashCode() : 0);
        result = 31 * result + (roomNumber != null ? roomNumber.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (classtime != null ? classtime.hashCode() : 0);
        result = 31 * result + (teaName != null ? teaName.hashCode() : 0);
        return result;
    }
}
