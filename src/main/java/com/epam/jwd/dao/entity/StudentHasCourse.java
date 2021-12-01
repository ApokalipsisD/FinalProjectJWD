package com.epam.jwd.dao.entity;

import java.sql.Date;

public class StudentHasCourse extends Entity<Integer> {
    private Integer courseId;
    private Integer studentId;
    private Date applicationDate;

    public StudentHasCourse() {

    }

    public StudentHasCourse(Integer courseId, Integer studentId, Date applicationDate) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.applicationDate = applicationDate;
    }

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
