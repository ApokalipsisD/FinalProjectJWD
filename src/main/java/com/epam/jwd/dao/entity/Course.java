package com.epam.jwd.dao.entity;

import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.impl.AccountDao;

import java.sql.Date;
import java.util.Objects;

public class Course extends Entity<Integer> {
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private Status courseStatus;
    private Integer teacherId;

    private static final Dao<Account, Integer> accountDao = new AccountDao();

    public Course(){

    }

    public Course(String title, String description, Date startDate, Date endDate, Integer courseStatus, Integer teacherId) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseStatus = Status.getById(courseStatus);
        this.teacherId = teacherId;
    }

    public Course(Integer id, String title, String description, Date startDate, Date endDate, Integer courseStatus, Integer teacherId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseStatus = Status.getById(courseStatus);
        this.teacherId = teacherId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Status getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(Status courseStatus) {
        this.courseStatus = courseStatus;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return title.equals(course.title) && description.equals(course.description) && startDate.equals(course.startDate) && endDate.equals(course.endDate) && courseStatus == course.courseStatus && Objects.equals(teacherId, course.teacherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, startDate, endDate, courseStatus, teacherId);
    }

    @Override
    public String toString() {
        return "Course{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", courseStatus=" + courseStatus +
                ", teacherId=" + teacherId +
                ", id=" + id +
                '}';
    }
}
