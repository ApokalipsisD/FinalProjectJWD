package com.epam.jwd.dao.entity;

import java.sql.Date;
import java.util.Objects;

/**
 * Course entity class which extends Entity class with Integer id field
 */
public class Course extends Entity<Integer> {
    /**
     * String field with course title
     */
    private String title;
    /**
     * String field with course description
     */
    private String description;
    /**
     * Date field with course startDate
     */
    private Date startDate;
    /**
     * Date field with course endDate
     */
    private Date endDate;
    /**
     * Status field with course status
     *
     * @see Status
     */
    private Status courseStatus;
    /**
     * Integer field with course id which associated with teacher account
     */
    private Integer teacherId;

    /**
     * Constructor without arguments for creating empty Course object
     *
     * @see Course#Course(String, String, Date, Date, Integer, Integer)
     * @see Course#Course(Integer, String, String, Date, Date, Integer, Integer)
     */
    public Course() {

    }

    /**
     * Constructor with arguments and without id for creating Course Object
     *
     * @param title        - course title
     * @param description  - course description
     * @param startDate    - course start date
     * @param endDate      - course end date
     * @param courseStatus - course status
     * @param teacherId    - course teacher id which associated with teacher account
     */
    public Course(String title, String description, Date startDate, Date endDate, Integer courseStatus, Integer teacherId) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseStatus = Status.getById(courseStatus);
        this.teacherId = teacherId;
    }

    /**
     * Constructor with all amount of arguments for creating Course Object
     *
     * @param id           - course id
     * @param title        - course title
     * @param description  - course description
     * @param startDate    - course start date
     * @param endDate      - course end date
     * @param courseStatus - course status
     * @param teacherId    - course teacher id which associated with teacher account
     */
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
