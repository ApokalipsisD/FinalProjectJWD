package com.epam.jwd.dao.entity;

public class Review extends Entity<Integer> {
    private Course course;
    private Account account;
    private int grade;
    private int attendance;
    private String review;

    public Review(Integer id, Course course, Account account, int grade, int attendance, String review) {
        this.id = id;
        this.course = course;
        this.account = account;
        this.grade = grade;
        this.attendance = attendance;
        this.review = review;
    }

    public Review(Course course, Account account, int grade, int attendance, String review) {
        this.course = course;
        this.account = account;
        this.grade = grade;
        this.attendance = attendance;
        this.review = review;
    }

    public Review() {

    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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
        if (!course.equals(review1.course)) return false;
        if (!account.equals(review1.account)) return false;
        return review.equals(review1.review);
    }

    @Override
    public int hashCode() {
        int result = course.hashCode();
        result = 31 * result + account.hashCode();
        result = 31 * result + grade;
        result = 31 * result + attendance;
        result = 31 * result + review.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", course=" + course +
                ", account=" + account +
                ", grade=" + grade +
                ", attendance=" + attendance +
                ", review='" + review + '\'' +
                '}';
    }
}
