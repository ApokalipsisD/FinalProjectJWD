package com.epam.jwd.dao.entity;

import java.sql.Date;

public class Course extends Entity<Integer> {
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private Integer courseStatus;
    private Integer teacherId;

    public Course(){

    }

    public Course(String title, String description, Date startDate, Date endDate, Integer courseStatus, Integer teacherId) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseStatus = courseStatus;
        this.teacherId = teacherId;
    }

    public Course(Integer id, String title, String description, Date startDate, Date endDate, Integer courseStatus, Integer teacherId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseStatus = courseStatus;
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

    public Integer getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(Integer courseStatus) {
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

        if (!title.equals(course.title)) return false;
        if (!description.equals(course.description)) return false;
        if (!startDate.equals(course.startDate)) return false;
        if (!endDate.equals(course.endDate)) return false;
        if (!courseStatus.equals(course.courseStatus)) return false;
        return teacherId.equals(course.teacherId);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + courseStatus.hashCode();
        result = 31 * result + teacherId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", courseStatus=" + courseStatus +
                ", teacherId=" + teacherId +
                '}';
    }
}
