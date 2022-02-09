package com.epam.jwd.dao.entity;

import java.sql.Date;

/**
 * StudentHasCourse entity class which extends Entity class with Integer id field
 */
public class StudentHasCourse extends Entity<Integer> {
    /**
     * Integer field with course id which associated with current course
     */
    private Integer courseId;
    /**
     * Integer field with user id which associated with current user
     */
    private Integer studentId;
    /**
     * Date field with record application date
     */
    private Date applicationDate;

    /**
     * Constructor without arguments for creating empty StudentHasCourse object
     *
     * @see StudentHasCourse#StudentHasCourse(Integer, Integer, Date)
     * @see StudentHasCourse#StudentHasCourse(Integer, Integer, Integer, Date)
     */
    public StudentHasCourse() {

    }

    /**
     * Constructor with arguments and without id for creating StudentHasCourse Object
     *
     * @param courseId        - course id associated with current course
     * @param studentId       - student id associated with current user
     * @param applicationDate - record application date
     */
    public StudentHasCourse(Integer courseId, Integer studentId, Date applicationDate) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.applicationDate = applicationDate;
    }

    /**
     * Constructor with all amount of arguments for creating StudentHasCourse Object
     *
     * @param id              - record id
     * @param courseId        - course id associated with current course
     * @param studentId       - student id associated with current user
     * @param applicationDate - record application date
     */
    public StudentHasCourse(Integer id, Integer courseId, Integer studentId, Date applicationDate) {
        this.id = id;
        this.courseId = courseId;
        this.studentId = studentId;
        this.applicationDate = applicationDate;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentHasCourse that = (StudentHasCourse) o;

        if (!courseId.equals(that.courseId)) return false;
        if (!studentId.equals(that.studentId)) return false;
        return applicationDate.equals(that.applicationDate);
    }

    @Override
    public int hashCode() {
        int result = courseId.hashCode();
        result = 31 * result + studentId.hashCode();
        result = 31 * result + applicationDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "StudentHasCourse{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", accountId=" + studentId +
                ", applicationDate=" + applicationDate +
                '}';
    }
}
