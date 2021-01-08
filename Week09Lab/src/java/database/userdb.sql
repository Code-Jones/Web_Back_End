/*
    This is our userdb, here's role table and user table
*/

DROP SCHEMA IF EXISTS `userdb`;
CREATE SCHEMA IF NOT EXISTS `userdb` DEFAULT CHARACTER SET latin1;
USE `userdb`;

-- -----------------------------------------------------
-- Table `userdb`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `userdb`.`role` (
  `role_id` INT(11) NOT NULL,
  `role_name` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`role_id`));

-- -----------------------------------------------------
-- Table `userdb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `userdb`.`user` (
  `email` VARCHAR(40) NOT NULL,
  `active` TINYINT(1) NOT NULL DEFAULT '1',
  `first_name` VARCHAR(20) NOT NULL,
  `last_name` VARCHAR(20) NOT NULL,
  `password` VARCHAR(20) NOT NULL,
  `role_id` INT(11) NOT NULL,
  PRIMARY KEY (`email`),
  CONSTRAINT `fk_user_role_id`
    FOREIGN KEY (`role_id`)
    REFERENCES `userdb`.`role` (`role_id`));


INSERT INTO `role` VALUES (1, 'System Admin');
INSERT INTO `role` VALUES (2, 'Regular User');
INSERT INTO `role` VALUES (3, 'Company Admin');


INSERT INTO `user` (`email`,`active`,`first_name`,`last_name`,`password`,`role_id`)
	VALUES ('denise.lee@calgary.ca', true, 'Denise','Lee', 'password', 1);
INSERT INTO `user` (`email`,`active`,`first_name`,`last_name`,`password`,`role_id`)
	VALUES ('jack.graver@calgary.ca', true, 'Jack','Graver', 'password', 1);
INSERT INTO `user` (`email`,`active`,`first_name`,`last_name`,`password`,`role_id`)
	VALUES ('sean.chen@calgary.ca', true, 'Sean','Chen', 'password', 2);
INSERT INTO `user` (`email`,`active`,`first_name`,`last_name`,`password`,`role_id`)
	VALUES ('jones.matthew@calgary.ca', true, 'Jones','Matthew', 'password', 2);
INSERT INTO `user` (`email`,`active`,`first_name`,`last_name`,`password`,`role_id`)
	VALUES ('ethan.gervais@calgary.ca', true, 'Ethan','Gervais', 'password', 3);
INSERT INTO `user` (`email`,`active`,`first_name`,`last_name`,`password`,`role_id`)
	VALUES ('naeun.kim@calgary.ca', false, 'Naeun','Kim', 'password', 3);
INSERT INTO `user` (`email`,`active`,`first_name`,`last_name`,`password`,`role_id`)
	VALUES ('jaeyoung.kim@calgary.ca', false, 'Jaeyoung','Kim', 'password', 1);
INSERT INTO `user` (`email`,`active`,`first_name`,`last_name`,`password`,`role_id`)
	VALUES ('aaron.warsylewicz@calgary.ca', false, 'Aaron','Warsylewicz', 'password', 2);