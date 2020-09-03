DROP TABLE IF EXISTS `attachment`;
DROP TABLE IF EXISTS `comment`;
DROP TABLE IF EXISTS `task`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `department`;

CREATE TABLE `department`
(
    `id`   BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `user`
(
    `id`            BIGINT NOT NULL AUTO_INCREMENT,
    `name`          VARCHAR(255),
    `department_id` BIGINT,
    KEY `idx_department_id` (`department_id`),
    FOREIGN KEY (`department_id`) REFERENCES `department` (`id`),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `task`
(
    `id`          BIGINT NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(255),
    `description` TEXT,
    `creator_id`  BIGINT,
    `created_at`  TIMESTAMP,
    `assignee_id` BIGINT,
    `status`      INT,
    KEY `idx_creator_id` (`creator_id`),
    KEY `idx_assignee_id` (`assignee_id`),
    FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`),
    FOREIGN KEY (`assignee_id`) REFERENCES `user` (`id`),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `comment`
(
    `id`         BIGINT NOT NULL AUTO_INCREMENT,
    `text`       TEXT,
    `creator_id` BIGINT,
    `created_at` TIMESTAMP,
    `task_id`    BIGINT,
    KEY `idx_creator_id` (`creator_id`),
    KEY `idx_task_id` (`task_id`),
    FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`),
    FOREIGN KEY (`task_id`) REFERENCES `task` (`id`),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `attachment`
(
    `id`         VARCHAR(255) NOT NULL,
    `name`       VARCHAR(255),
    `media_type` VARCHAR(255),
    `size`       BIGINT,
    `task_id`    BIGINT,
    KEY `idx_task_id` (`task_id`),
    FOREIGN KEY (`task_id`) REFERENCES `task` (`id`),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;
