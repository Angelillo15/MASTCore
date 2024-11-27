CREATE TABLE IF NOT EXISTS `nookure_staff_data`
(
    `id`                 INTEGER PRIMARY KEY AUTOINCREMENT,
    `UUID`               TEXT,
    `staff_mode`         INTEGER NOT NULL DEFAULT 0,
    `vanished`           INTEGER NOT NULL DEFAULT 0,
    `staff_chat_enabled` INTEGER NOT NULL DEFAULT 0
);