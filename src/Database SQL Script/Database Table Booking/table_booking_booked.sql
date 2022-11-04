-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: table_booking
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `booked`
--

DROP TABLE IF EXISTS `booked`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booked` (
  `User_id` int NOT NULL,
  `Table_id` int NOT NULL,
  `date` varchar(45) NOT NULL,
  `time` int NOT NULL,
  `status` enum('APPROVED','PENDING','DECLINE') NOT NULL,
  KEY `fk_User_has_Table_Table1_idx` (`Table_id`),
  KEY `fk_User_has_Table_User_idx` (`User_id`),
  CONSTRAINT `fk_User_has_Table_Table1` FOREIGN KEY (`Table_id`) REFERENCES `table` (`id`),
  CONSTRAINT `fk_User_has_Table_User` FOREIGN KEY (`User_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booked`
--

LOCK TABLES `booked` WRITE;
/*!40000 ALTER TABLE `booked` DISABLE KEYS */;
INSERT INTO `booked` VALUES (2,1,'5-OCT',4,'APPROVED'),(2,2,'8-NOV',6,'APPROVED'),(3,5,'9-DEC',7,'APPROVED'),(2,4,'8-NOV',9,'APPROVED'),(2,3,'9-NOV',7,'APPROVED'),(2,3,'9-NOV',6,'APPROVED'),(2,3,'9-NOV',2,'APPROVED'),(2,3,'9-NOV',14,'APPROVED'),(2,3,'9-NOV',22,'APPROVED'),(2,3,'3-NOV',2,'APPROVED'),(2,3,'3-NOV',4,'APPROVED'),(2,3,'4-NOV',5,'APPROVED'),(2,3,'4-NOV',6,'APPROVED');
/*!40000 ALTER TABLE `booked` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-05  0:54:02
