
CREATE TABLE `objects` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `sells` (
  `id` int(11) NOT NULL,
  `seller` varchar(255) NOT NULL,
  `objectId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `objects`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `sells`
  ADD PRIMARY KEY (`id`),
  ADD KEY `objectId` (`objectId`);

ALTER TABLE `objects`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `sells`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `sells`
  ADD CONSTRAINT `sells_ibfk_1` FOREIGN KEY (`objectId`) REFERENCES `objects` (`id`);

ALTER TABLE `sells` DROP FOREIGN KEY `sells_ibfk_1`;
ALTER TABLE `sells`
  ADD CONSTRAINT `sells_ibfk_1` FOREIGN KEY (`objectId`) REFERENCES `objects`(`id`) ON DELETE CASCADE ON UPDATE RESTRICT;

COMMIT;