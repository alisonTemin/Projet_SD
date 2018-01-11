SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


CREATE TABLE `objects` (
  `id` int(11) NOT NULL,
  `name` int(11) NOT NULL,
  `price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `sells` (
  `id` int(11) NOT NULL,
  `seller` int(11) NOT NULL,
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
COMMIT;