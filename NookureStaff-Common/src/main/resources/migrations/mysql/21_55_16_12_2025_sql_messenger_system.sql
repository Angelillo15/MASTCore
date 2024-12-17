CREATE TABLE IF NOT EXISTS `nookure_staff_messenger`
(
    `id`          bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `time`        timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `server_uuid` varchar(36)           NOT NULL,
    `data`        BLOB                  NOT NULL
);
