package com.epam.jwd.dao.entity;

public class Review extends Entity<Integer> {
    private Integer courseId;
    private Integer studentId;
    private int grade;
    private int attendance;
    private String review;

    public Review(){

    }

    public Review(Integer courseId, Integer studentId, int grade, int attendance, String review) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.grade = grade;
        this.attendance = attendance;
        this.review = review;
    }

    public Review(Integer id, Integer courseId, Integer studentId, int grade, int attendance, String review) {
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

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getAttendance() {
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

        if (grade != review1.grade) return false;
        if (attendance != review1.attendance) return false;
        if (!courseId.equals(review1.courseId)) return false;
        if (!studentId.equals(review1.studentId)) return false;
        return review.equals(review1.review);
    }

    @Override
    public int hashCode() {
        int result = courseId.hashCode();
        result = 31 * result + studentId.hashCode();
        result = 31 * result + grade;
        result = 31 * result + attendance;
        result = 31 * result + review.hashCode();
        return result;
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
