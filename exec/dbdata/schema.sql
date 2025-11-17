DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
                          `id` int(11) NOT NULL AUTO_INCREMENT,
                          `created_at` datetime(6) NOT NULL,
                          `email` varchar(100) NOT NULL,
                          `password` varchar(100) NOT NULL,
                          `username` varchar(50) NOT NULL,
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `UKmbmcqelty0fbrvxp1q58dn57t` (`email`),
                          UNIQUE KEY `UKgc3jmn7c2abyo3wf6syln5t2i` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;