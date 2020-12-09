
CREATE USER 'studentDb'@'localhost' IDENTIFIED BY 'studentDb';

CREATE DATABASE students
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;
  
GRANT SELECT, INSERT, UPDATE, DELETE ON students.* TO 'studentDb'@'localhost';

USE students;

CREATE TABLE `student` (
  `id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT ,
  `nif` varchar(32) NOT NULL,
  `name` varchar(32) NOT NULL,
  `age` int(11) NOT NULL,
  `minor` tinyint(1) NOT NULL
) ENGINE=InnoDB;




INSERT INTO `student` (`id`, `nif`, `name`, `age`, `minor`) VALUES
(1, '49490724Z', 'Student01', 16, 1),
(3, '42133913Y', 'Student03', 19, 0),
(4, '24165550W', 'Student04', 17, 1),
(5, '95049856X', 'Student05', 16, 1);
