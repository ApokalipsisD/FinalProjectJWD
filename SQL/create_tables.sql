CREATE TABLE IF NOT EXISTS `role` (
    `id` int NOT NULL AUTO_INCREMENT,
    `role_name` varchar(15) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `user` (
    `id` int NOT NULL AUTO_INCREMENT,
    `login` varchar(30) NOT NULL,
    `password` varchar(30) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `login` (`login`)
);

CREATE TABLE IF NOT EXISTS `account` (
   `id` int NOT NULL AUTO_INCREMENT,
   `first_name` varchar(20) DEFAULT NULL,
   `last_name` varchar(20) DEFAULT NULL,
   `email` varchar(30) DEFAULT NULL,
   `birth_date` date DEFAULT NULL,
   `role_id` int NOT NULL DEFAULT '4',
   `user_id` int NOT NULL,
   PRIMARY KEY (`id`),
   KEY `FK_ACCOUNT_TO_USER` (`user_id`),
   KEY `FK_ACCOUNT_ROLE` (`role_id`),
   CONSTRAINT `FK_ACCOUNT_ROLE` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
   CONSTRAINT `FK_ACCOUNT_TO_USER` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

CREATE TABLE IF NOT EXISTS `status` (
    `id` int NOT NULL AUTO_INCREMENT,
    `course_status` varchar(30) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `course_status` (`course_status`)
);

CREATE TABLE IF NOT EXISTS `course` (
    `id` int NOT NULL AUTO_INCREMENT,
    `title` varchar(50) NOT NULL,
    `description` text NOT NULL,
    `start_date` date NOT NULL,
    `end_date` date NOT NULL,
    `course_status` int NOT NULL,
    `teacher_id` int DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `title` (`title`),
    KEY `fk_course_status_to_status` (`course_status`),
    KEY `fk_course_teacher` (`teacher_id`),
    CONSTRAINT `fk_course_status_to_status` FOREIGN KEY (`course_status`) REFERENCES `status` (`id`),
    CONSTRAINT `fk_course_teacher` FOREIGN KEY (`teacher_id`) REFERENCES `account` (`id`)
);

CREATE TABLE IF NOT EXISTS `student_has_course` (
    `id` int NOT NULL AUTO_INCREMENT,
    `course_id` int NOT NULL,
    `student_id` int NOT NULL,
    `application_date` date NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_STUDENT_ON_COURSE` (`course_id`),
    KEY `FK_STUDENT_USER` (`student_id`),
    CONSTRAINT `FK_STUDENT_ON_COURSE` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`),
    CONSTRAINT `FK_STUDENT_USER` FOREIGN KEY (`student_id`) REFERENCES `user` (`id`)
);

CREATE TABLE IF NOT EXISTS `review` (
    `id` int NOT NULL AUTO_INCREMENT,
    `course_id` int NOT NULL,
    `student_id` int NOT NULL,
    `grade` int DEFAULT NULL,
    `attendance` int NOT NULL DEFAULT '0',
    `review` text,
    PRIMARY KEY (`id`),
    KEY `FK_REVIEW_STUDENT` (`student_id`),
    KEY `FK_REVIEW_STUDENT_ON_COURSE` (`course_id`),
    CONSTRAINT `FK_REVIEW_STUDENT` FOREIGN KEY (`student_id`) REFERENCES `user` (`id`),
    CONSTRAINT `FK_REVIEW_STUDENT_ON_COURSE` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
);

INSERT INTO role (`role_name`) VALUES
       ('ADMIN'),
       ('TEACHER'),
       ('STUDENT'),
       ('USER');

INSERT INTO `status`(`course_status`) VALUES
      ('Coming'),
      ('Started'),
      ('Finished');



