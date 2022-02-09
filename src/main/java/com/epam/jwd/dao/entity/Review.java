package com.epam.jwd.dao.entity;

import java.util.Objects;

/**
 * Review entity class which extends Entity class with Integer id field
 */
public class Review extends Entity<Integer> {
    /**
     * Integer field with course id which associated with current course
     */
    private Integer courseId;
    /**
     * Integer field with user id which associated with current user
     */
    private Integer studentId;
    /**
     * Integer field with review grade
     */
    private Integer grade;
    /**
     * Integer field with review attendance
     */
    private Integer attendance;
    /**
     * String field with review
     */
    private String review;

    /**
     * Constructor without arguments for creating empty Review object
     *
     * @see Review#Review(Integer, Integer, Integer, Integer, String)
     * @see Review#Review(Integer, Integer, Integer, Integer, Integer, String)
     */
    public Review() {

    }

    /**
     * Constructor with arguments and without id for creating Review Object
     *
     * @param courseId   - course id associated with current course
     * @param studentId  - student id associated with current user
     * @param grade      - student's grade
     * @param attendance - student's attendance
     * @param review     - student's review
     */
    public Review(Integer courseId, Integer studentId, Integer grade, Integer attendance, String review) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.grade = grade;
        this.attendance = attendance;
        this.review = review;
    }

    /**
     * Constructor with all amount of arguments for creating Review Object
     *
     * @param id         - review id
     * @param courseId   - course id associated with current course
     * @param studentId  - student id associated with current user
     * @param grade      - student's grade
     * @param attendance - student's attendance
     * @param review     - student's review
     */
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
