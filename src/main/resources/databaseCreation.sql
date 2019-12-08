-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema expositions_calendar_spring
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `expositions_calendar_spring` ;

-- -----------------------------------------------------
-- Schema expositions_calendar_spring
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `expositions_calendar_spring` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;
USE `expositions_calendar_spring` ;

-- -----------------------------------------------------
-- Table `expositions_calendar_spring`.`halls`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `expositions_calendar_spring`.`halls` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `city` VARCHAR(50) NOT NULL,
  `house_number` INT(11) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `street` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_sadg6dwhsocpkayx2i7dy032t` (`name` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `expositions_calendar_spring`.`expositions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `expositions_calendar_spring`.`expositions` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(500) NOT NULL,
  `end_date` DATE NOT NULL,
  `start_date` DATE NOT NULL,
  `theme` VARCHAR(100) NOT NULL,
  `ticket_price` DECIMAL(6,2) NOT NULL,
  `title` VARCHAR(100) NOT NULL,
  `hall_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_mmmgdvaumhebtb56kbtjivqxx` (`title` ASC) VISIBLE,
  INDEX `FKcy4x4bjc5jobyubpwiogscgro` (`hall_id` ASC) VISIBLE,
  CONSTRAINT `FKcy4x4bjc5jobyubpwiogscgro`
    FOREIGN KEY (`hall_id`)
    REFERENCES `expositions_calendar_spring`.`halls` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `expositions_calendar_spring`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `expositions_calendar_spring`.`users` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `name` VARCHAR(55) NOT NULL,
  `password` VARCHAR(60) NOT NULL,
  `role` VARCHAR(50) NOT NULL,
  `surname` VARCHAR(55) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_6dotkott2kjsp8vw4d0m25fb7` (`email` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 101
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `expositions_calendar_spring`.`payments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `expositions_calendar_spring`.`payments` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `payment_time` DATETIME(6) NOT NULL,
  `price` DECIMAL(11,2) NOT NULL,
  `status` VARCHAR(50) NOT NULL,
  `tickets_amount` INT(11) NOT NULL,
  `exposition_id` BIGINT(20) NOT NULL,
  `user_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKnjoc7djdwp4ddejplw2rfn4lc` (`exposition_id` ASC) VISIBLE,
  INDEX `FKj94hgy9v5fw1munb90tar2eje` (`user_id` ASC) VISIBLE,
  CONSTRAINT `FKj94hgy9v5fw1munb90tar2eje`
    FOREIGN KEY (`user_id`)
    REFERENCES `expositions_calendar_spring`.`users` (`id`),
  CONSTRAINT `FKnjoc7djdwp4ddejplw2rfn4lc`
    FOREIGN KEY (`exposition_id`)
    REFERENCES `expositions_calendar_spring`.`expositions` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `expositions_calendar_spring`.`tickets`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `expositions_calendar_spring`.`tickets` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `valid_date` DATE NOT NULL,
  `exposition_id` BIGINT(20) NOT NULL,
  `payment_id` BIGINT(20) NOT NULL,
  `user_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK86gks79ltyaxxy0au4rolhxus` (`exposition_id` ASC) VISIBLE,
  INDEX `FKryn3ko9bmcvl6ulh796fjsw9m` (`payment_id` ASC) VISIBLE,
  INDEX `FK4eqsebpimnjen0q46ja6fl2hl` (`user_id` ASC) VISIBLE,
  CONSTRAINT `FK4eqsebpimnjen0q46ja6fl2hl`
    FOREIGN KEY (`user_id`)
    REFERENCES `expositions_calendar_spring`.`users` (`id`),
  CONSTRAINT `FK86gks79ltyaxxy0au4rolhxus`
    FOREIGN KEY (`exposition_id`)
    REFERENCES `expositions_calendar_spring`.`expositions` (`id`),
  CONSTRAINT `FKryn3ko9bmcvl6ulh796fjsw9m`
    FOREIGN KEY (`payment_id`)
    REFERENCES `expositions_calendar_spring`.`payments` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 16
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
