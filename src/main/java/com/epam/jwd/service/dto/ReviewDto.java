package com.epam.jwd.service.dto;

import java.util.Objects;

public class ReviewDto extends AbstractDto<Integer> {
    private Integer courseId;
    private Integer studentId;
    private Integer grade;
    private Integer attendance;
    private String review;

    public ReviewDto(){

    }

    public ReviewDto(Integer courseId, Integer studentId, Integer grade, Integer attendance, String review) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.grade = grade;
        this.attendance = attendance;
        this.review = review;
    }

    public ReviewDto(Integer id, Integer courseId, Integer studentId, Integer grade, Integer attendance, String review) {
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
        ReviewDto reviewDto = (ReviewDto) o;
        return courseId.equals(reviewDto.courseId) && studentId.equals(reviewDto.studentId) && Objects.equals(grade, reviewDto.grade) && Objects.equals(attendance, reviewDto.attendance) && Objects.equals(review, reviewDto.review);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, studentId, grade, attendance, review);
    }

    @Override
    public String toString() {
        return "ReviewDto{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", studentId=" + studentId +
                ", grade=" + grade +
                ", attendance=" + attendance +
                ", review='" + review + '\'' +
                '}';
    }
}
