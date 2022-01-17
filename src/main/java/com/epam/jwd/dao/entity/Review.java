package com.epam.jwd.dao.entity;

import java.util.Objects;

public class Review extends Entity<Integer> {
    private Integer courseId;
    private Integer studentId;
    private Integer grade;
    private Integer attendance;
    private String review;

    public Review(){

    }

    public Review(Integer courseId, Integer studentId, Integer grade, Integer attendance, String review) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.grade = grade;
        this.attendance = attendance;
        this.review = review;
    }

    public Review(Integer id, Integer courseId, Integer studentId, Integer grade, Integer attendance, String review) {
        this.id = id;
        this.courseId = courseId;
        this.studentId = studentId;
        this.grade = grade;
        this.attendance = attendance;
        this.review = review;
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

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Integer getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review1 = (Review) o;
        return courseId.equals(review1.courseId) && studentId.equals(review1.studentId) && Objects.equals(grade, review1.grade) && Objects.equals(attendance, review1.attendance) && Objects.equals(review, review1.review);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, studentId, grade, attendance, review);
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", accountId=" + studentId +
                ", grade=" + grade +
                ", attendance=" + attendance +
                ", review='" + review + '\'' +
                '}';
    }
}
