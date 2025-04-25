-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: mymall
-- ------------------------------------------------------
-- Server version	8.0.41

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
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `pid` int NOT NULL,
  `price` int DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `pname` varchar(255) DEFAULT NULL,
  `size` varchar(255) DEFAULT NULL,
  `brief` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,390,'M001.jpg','新世紀福音戰士','S','正面印有NERV標誌，背面採用EVA初號機插畫與第一集副標題設計。圖案經“磨損加工”，呈現復古風格。'),(2,390,'M001.jpg','新世紀福音戰士','M','正面印有NERV標誌，背面採用EVA初號機插畫與第一集副標題設計。圖案經“磨損加工”，呈現復古風格。'),(3,390,'M001.jpg','新世紀福音戰士','L','正面印有NERV標誌，背面採用EVA初號機插畫與第一集副標題設計。圖案經“磨損加工”，呈現復古風格。'),(4,690,'P001.jpg','寶可夢','S','寶可夢聯名，融合8-bit風格，打造復古像素潮流，快選擇你的寶可夢，踏上成為時尚大師的旅途。'),(5,690,'P001.jpg','寶可夢','M','寶可夢聯名，融合8-bit風格，打造復古像素潮流，快選擇你的寶可夢，踏上成為時尚大師的旅途。'),(6,690,'P001.jpg','寶可夢','L','寶可夢聯名，融合8-bit風格，打造復古像素潮流，快選擇你的寶可夢，踏上成為時尚大師的旅途。'),(7,290,'D001.jpg','七龍珠','S','​採用變小的孫悟空(迷你)揮舞如意棒的活力插畫印花設計。推薦搭配本系列襪款一同穿搭。​'),(8,290,'D001.jpg','七龍珠','M','​採用變小的孫悟空(迷你)揮舞如意棒的活力插畫印花設計。推薦搭配本系列襪款一同穿搭。​'),(9,290,'D001.jpg','七龍珠','L','​採用變小的孫悟空(迷你)揮舞如意棒的活力插畫印花設計。推薦搭配本系列襪款一同穿搭。​');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-25 16:27:50
