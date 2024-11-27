 CREATE TABLE IF NOT EXISTS `nookure_staff_data` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `UUID` VARCHAR(40) COLLATE 'utf8mb4_general_ci' DEFAULT NULL,
    `staff_mode` TINYINT(1) NOT NULL DEFAULT 0,
    `vanished` TINYINT(1) NOT NULL DEFAULT 0,
    `staff_chat_enabled` TINYINT(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLTE=utf8mb4_general_ci;