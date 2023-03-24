CREATE DATABASE  IF NOT EXISTS `safe_building` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `safe_building`;
-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: safe_building
-- ------------------------------------------------------
-- Server version	8.0.30

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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `id` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fullname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES ('0c624b3a-5a2f-4f3d-98e0-d9e4ae35fe17','safebuilding.swd@gmail.com','Safe Building admin','$2a$12$xOXUkpChPTfs/WmRvNS3s.Wqz2Wn9GdWO1Ttiea.2pRhGmYvVI5UK','0123456780','ACTIVE'),('241fe232-09dd-46a5-b77e-12d2087903ce','admin3@gmail.com','Admin 3','$2a$12$xOXUkpChPTfs/WmRvNS3s.Wqz2Wn9GdWO1Ttiea.2pRhGmYvVI5UK','0123456782','ACTIVE'),('475d345f-cf67-49a4-845e-5f13dfc530b3','admin9@gmail.com','Admin 9','$2a$12$xOXUkpChPTfs/WmRvNS3s.Wqz2Wn9GdWO1Ttiea.2pRhGmYvVI5UK','0123456788','ACTIVE'),('48bfbc4a-f5d9-4866-b284-4ca110681308','admin2@gmail.com','Admin 2','$2a$12$xOXUkpChPTfs/WmRvNS3s.Wqz2Wn9GdWO1Ttiea.2pRhGmYvVI5UK','0123456781','INACTIVE'),('58a7dd30-5e91-4ee5-9ad3-47a603580f8c','admin5@gmail.com','Admin 5','$2a$12$xOXUkpChPTfs/WmRvNS3s.Wqz2Wn9GdWO1Ttiea.2pRhGmYvVI5UK','0123456784','ACTIVE'),('61dc10dc-409e-458e-8524-4704e0410e17','admin8@gmail.com','Admin 8','$2a$12$xOXUkpChPTfs/WmRvNS3s.Wqz2Wn9GdWO1Ttiea.2pRhGmYvVI5UK','0123456787','ACTIVE'),('6f64c116-c037-4fab-b283-dbabfcb62bdb','admin10@gmail.com','Admin 10','$2a$12$xOXUkpChPTfs/WmRvNS3s.Wqz2Wn9GdWO1Ttiea.2pRhGmYvVI5UK','0123456789','ACTIVE'),('cf05e051-121c-44a0-892c-f9d8ffa92dbc','admin7@gmail.com','Admin 7','$2a$12$xOXUkpChPTfs/WmRvNS3s.Wqz2Wn9GdWO1Ttiea.2pRhGmYvVI5UK','0123456786','INACTIVE'),('dbb2b5bb-b594-4d73-9677-ea6847016ead','admin6@gmail.com','Admin 6','$2a$12$xOXUkpChPTfs/WmRvNS3s.Wqz2Wn9GdWO1Ttiea.2pRhGmYvVI5UK','0123456785','ACTIVE'),('f6256c7b-ac23-46ae-bae5-7cc573389e57','admin4@gmail.com','Admin 4','$2a$12$xOXUkpChPTfs/WmRvNS3s.Wqz2Wn9GdWO1Ttiea.2pRhGmYvVI5UK','0123456783','ACTIVE');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bill`
--

DROP TABLE IF EXISTS `bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bill` (
  `id` varchar(255) NOT NULL,
  `date` date DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `value` int NOT NULL,
  `rent_contract_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6ypgaebq9kfs5w5hya1qah2yh` (`rent_contract_id`),
  CONSTRAINT `FK6ypgaebq9kfs5w5hya1qah2yh` FOREIGN KEY (`rent_contract_id`) REFERENCES `rent_contract` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill`
--

LOCK TABLES `bill` WRITE;
/*!40000 ALTER TABLE `bill` DISABLE KEYS */;
INSERT INTO `bill` VALUES ('01ba2d84-220c-4a1d-b72c-c6a7cfb0aff6','2022-05-29','PAID',14285000,'a2253db2-9ff2-4631-9e43-4631f03aa6c0'),('0204875c-b7fd-44ed-91e0-991fc4cd1adf','2021-05-10','UNPAID',16877000,'23665534-56a4-49e0-b8c7-6b34424a8e1a'),('039df6bc-a94f-4f86-90da-6c2d748f2467','2022-01-10','PAID',14886000,'e0c4a94f-f96d-4836-a9d0-836fe1125dc0'),('06d3ac2c-a1a8-4f46-8ab7-aba27a1b2b38','2022-11-21','UNPAID',5362000,'e8559104-4db9-44b0-a500-22ebdea95ddf'),('0f484eb9-ae53-48a6-909c-4f800b2766ca','2022-12-13','PAID',9034000,'e8559104-4db9-44b0-a500-22ebdea95ddf'),('26b388be-bb65-4568-ac6d-9d9fcaeddd40','2021-02-03','PAID',19789000,'b1a91a9b-9d03-436c-a6e7-2cde9c11ffe6'),('351c118f-374a-4be5-9250-21e041068e8d','2022-02-02','PAID',8613000,'20ad926f-5131-43d7-af36-b5577b69b6d1'),('3f9d6283-ef69-4b87-bbbd-8c4e391f9a24','2021-11-29','UNPAID',5651000,'0acfdf72-5b35-455a-b41b-3026f1e7a95b'),('44bc91e2-d4ec-461e-9542-3b7b06269c23','2020-12-03','PAID',7285000,'b1a91a9b-9d03-436c-a6e7-2cde9c11ffe6'),('4f91bf66-0557-4910-b58a-70c35a2cf96a','2022-10-16','PAID',16060000,'02d7b954-3556-4f3e-87ae-04268b39e656'),('527f71ad-1561-4f19-9044-d0875ae03d05','2021-06-10','PAID',19939000,'20ad926f-5131-43d7-af36-b5577b69b6d1'),('53ed6852-77f4-42ab-a2a6-97927d77d72b','2021-08-19','PAID',6202000,'02d7b954-3556-4f3e-87ae-04268b39e656'),('59cbc85d-91e7-40b9-aa17-9d6d3f45b6ed','2021-06-16','PAID',5661000,'23665534-56a4-49e0-b8c7-6b34424a8e1a'),('5da21cf2-53e9-4c13-b76f-7bae623397b3','2022-04-25','PAID',8518000,'0acfdf72-5b35-455a-b41b-3026f1e7a95b'),('61c3377e-f290-4497-8979-51d98d6f626f','2022-05-22','UNPAID',10531000,'a2253db2-9ff2-4631-9e43-4631f03aa6c0'),('7e55405b-4781-4d86-a80c-8b966e84177e','2022-05-29','PAID',1788000,'02d7b954-3556-4f3e-87ae-04268b39e656'),('80c0bc7f-1978-455f-8c69-e37f1958e864','2022-06-17','PAID',12344000,'e8559104-4db9-44b0-a500-22ebdea95ddf'),('80e6ac33-c01f-4a30-9a75-818e19920543','2021-05-25','PAID',19571000,'c9378f01-9ef5-432d-b040-1833654544d6'),('82842539-0a5c-41e1-9b99-914e80febddc','2021-04-29','PAID',5835000,'0acfdf72-5b35-455a-b41b-3026f1e7a95b'),('839ed5ed-85c8-465e-8a43-852d423c0085','2022-02-21','PAID',16607000,'0acfdf72-5b35-455a-b41b-3026f1e7a95b'),('875f5e37-20cf-4c90-a22b-ce41fd84bb39','2022-03-12','PAID',14018000,'9d895db1-e36e-42d5-a655-fdbbb781c610'),('a3a2b879-6969-4e08-a245-5c85e3322e33','2022-12-20','UNPAID',12614000,'b1a91a9b-9d03-436c-a6e7-2cde9c11ffe6'),('b382c756-1ab5-4d38-9f7e-415e5889bf31','2021-10-15','PAID',16066000,'b1a91a9b-9d03-436c-a6e7-2cde9c11ffe6'),('b3b6141f-d8dc-4499-9734-013fff9ef828','2022-12-08','UNPAID',18563000,'e8559104-4db9-44b0-a500-22ebdea95ddf'),('bfd7b9d2-1ad8-4e42-8fc3-e5ed8de295a4','2022-03-04','PAID',18117000,'20ad926f-5131-43d7-af36-b5577b69b6d1'),('cabc1b81-42a5-4636-81e3-5176613847a4','2022-01-09','PAID',16548000,'a2253db2-9ff2-4631-9e43-4631f03aa6c0'),('da48ce91-428d-4862-9bc9-6138ccfd335e','2022-01-05','UNPAID',3149000,'23665534-56a4-49e0-b8c7-6b34424a8e1a'),('e3ce7b90-515c-4afc-835b-e58e4bb37b5f','2021-06-09','PAID',16345000,'a2253db2-9ff2-4631-9e43-4631f03aa6c0'),('ea1cb80d-1a0f-4b74-b462-c722aec59225','2020-12-16','PAID',1456000,'85839b86-9a89-4d6a-b247-82f7c5ec72a3'),('f10e6bca-66f4-421c-9f35-0648f01f795d','2021-08-03','UNPAID',13339000,'e8559104-4db9-44b0-a500-22ebdea95ddf'),('f1b843ee-aba7-4691-a780-6575a14bbafa','2021-02-06','UNPAID',12109000,'e0c4a94f-f96d-4836-a9d0-836fe1125dc0'),('fab966f1-00fe-494c-b668-2361174b97cf','2021-03-02','UNPAID',2094000,'b1a91a9b-9d03-436c-a6e7-2cde9c11ffe6');
/*!40000 ALTER TABLE `bill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bill_item`
--

DROP TABLE IF EXISTS `bill_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bill_item` (
  `id` varchar(255) NOT NULL,
  `quantity` int NOT NULL,
  `value` int NOT NULL,
  `bill_id` varchar(255) DEFAULT NULL,
  `service_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcf0kpt07c9onbtvnttajk3skx` (`bill_id`),
  KEY `FKn9c5cacfxqfx6pwhpdvokahil` (`service_id`),
  CONSTRAINT `FKcf0kpt07c9onbtvnttajk3skx` FOREIGN KEY (`bill_id`) REFERENCES `bill` (`id`),
  CONSTRAINT `FKn9c5cacfxqfx6pwhpdvokahil` FOREIGN KEY (`service_id`) REFERENCES `service` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill_item`
--

LOCK TABLES `bill_item` WRITE;
/*!40000 ALTER TABLE `bill_item` DISABLE KEYS */;
INSERT INTO `bill_item` VALUES ('01062493-f4be-4b19-8044-29e401d902c9',2,76000,'3f9d6283-ef69-4b87-bbbd-8c4e391f9a24','b4c93ebe-1048-4556-b552-a3f3990c06fe'),('043e072b-b0e5-4349-9576-66ee3a6bc59c',1,265000,'0204875c-b7fd-44ed-91e0-991fc4cd1adf','8fd0981e-c64f-4b4f-b5e1-63afe1841df0'),('0a542845-65a1-4507-a17d-c983c88955e6',4,222000,'7e55405b-4781-4d86-a80c-8b966e84177e','5aff5922-69bd-46dd-a84b-427ae9c24d41'),('0a6849ad-21e8-49af-8e50-49ed74ee5597',4,400000,'039df6bc-a94f-4f86-90da-6c2d748f2467','57b36703-287c-48d5-9d04-0a2debd48c53'),('0af7373c-3766-4597-99db-62a82cbd8e97',4,141000,'59cbc85d-91e7-40b9-aa17-9d6d3f45b6ed','b4c93ebe-1048-4556-b552-a3f3990c06fe'),('0ce62239-7db9-475e-969f-641272952a6d',4,456000,'351c118f-374a-4be5-9250-21e041068e8d','046b67ea-a216-4a6b-821b-e78f5a9e83ee'),('0dc4f8b9-049a-4b8c-9b35-fb8fc492cfdc',3,304000,'f1b843ee-aba7-4691-a780-6575a14bbafa','4ee0f880-34cf-4c99-b602-2ae01416096a'),('110b5d5c-b17e-40d9-8a85-a224b556ca1b',2,242000,'0204875c-b7fd-44ed-91e0-991fc4cd1adf','57b36703-287c-48d5-9d04-0a2debd48c53'),('1233c12c-8ab2-4a2e-8510-cee9f35132c4',2,210000,'4f91bf66-0557-4910-b58a-70c35a2cf96a','5aff5922-69bd-46dd-a84b-427ae9c24d41'),('18597bd0-ec34-4dc5-a48a-1667941a352a',2,276000,'ea1cb80d-1a0f-4b74-b462-c722aec59225','8fd0981e-c64f-4b4f-b5e1-63afe1841df0'),('1c607c75-02d4-497a-81c8-70616efd729c',3,189000,'53ed6852-77f4-42ab-a2a6-97927d77d72b','4ee0f880-34cf-4c99-b602-2ae01416096a'),('1d35b0bc-a0ca-4fc8-917c-264138afeba7',4,53000,'5da21cf2-53e9-4c13-b76f-7bae623397b3','74d6b423-8afc-40f1-8efb-475ab90880a8'),('23391328-13c5-49ff-bed6-d3dd514abf8b',4,54000,'61c3377e-f290-4497-8979-51d98d6f626f','4ee0f880-34cf-4c99-b602-2ae01416096a'),('2f692c11-ba0e-4867-b49d-49f8ce6cc241',2,261000,'7e55405b-4781-4d86-a80c-8b966e84177e','74d6b423-8afc-40f1-8efb-475ab90880a8'),('302ea7f6-97d5-4d76-8820-c08d837bc9e8',1,125000,'59cbc85d-91e7-40b9-aa17-9d6d3f45b6ed','d1a64dbe-589c-4298-a537-f9f695b60c4c'),('356eecc1-8ed7-40ee-93fa-1515020aa609',4,469000,'527f71ad-1561-4f19-9044-d0875ae03d05','5aff5922-69bd-46dd-a84b-427ae9c24d41'),('38c790bc-8f91-4f17-ae28-c16901052285',3,178000,'a3a2b879-6969-4e08-a245-5c85e3322e33','74d6b423-8afc-40f1-8efb-475ab90880a8'),('40136461-5047-41fa-b1c5-90f240f82660',4,219000,'01ba2d84-220c-4a1d-b72c-c6a7cfb0aff6','8d51ca04-eec4-4578-bcbe-21254d2f9051'),('404197e7-3b00-42db-ba71-815007522d76',3,123000,'0f484eb9-ae53-48a6-909c-4f800b2766ca','57b36703-287c-48d5-9d04-0a2debd48c53'),('4421e77d-3fcc-4d18-8560-fa1d4b252365',4,181000,'80c0bc7f-1978-455f-8c69-e37f1958e864','d1a64dbe-589c-4298-a537-f9f695b60c4c'),('48730c00-75dc-42bc-aef0-05175f693e0a',1,251000,'b3b6141f-d8dc-4499-9734-013fff9ef828','4ee0f880-34cf-4c99-b602-2ae01416096a'),('4c3540e4-b71c-4687-be3a-aeb4b2b22665',3,294000,'5da21cf2-53e9-4c13-b76f-7bae623397b3','5aff5922-69bd-46dd-a84b-427ae9c24d41'),('4db31ea0-7828-43e0-a35a-396b76ec0480',1,457000,'a3a2b879-6969-4e08-a245-5c85e3322e33','57b36703-287c-48d5-9d04-0a2debd48c53'),('513ad3ec-c189-4f99-8713-543104897c2f',3,375000,'3f9d6283-ef69-4b87-bbbd-8c4e391f9a24','4ee0f880-34cf-4c99-b602-2ae01416096a'),('52a19757-aac5-4a71-9fe9-1b21c9c4e591',2,119000,'44bc91e2-d4ec-461e-9542-3b7b06269c23','046b67ea-a216-4a6b-821b-e78f5a9e83ee'),('547940ec-3d9b-486e-a5ac-769c765034e7',1,312000,'59cbc85d-91e7-40b9-aa17-9d6d3f45b6ed','b4c93ebe-1048-4556-b552-a3f3990c06fe'),('54adc7b9-61fc-4443-8f55-6073dd64c875',1,272000,'80e6ac33-c01f-4a30-9a75-818e19920543','4ee0f880-34cf-4c99-b602-2ae01416096a'),('56b61272-8ee1-4053-a32c-a3674056f433',1,61000,'b382c756-1ab5-4d38-9f7e-415e5889bf31','57b36703-287c-48d5-9d04-0a2debd48c53'),('571e6ca0-3973-43e0-8f02-c6f457c04474',3,479000,'44bc91e2-d4ec-461e-9542-3b7b06269c23','74d6b423-8afc-40f1-8efb-475ab90880a8'),('5c68d59d-d567-4ca9-9d6f-f21f7953dff9',2,162000,'b3b6141f-d8dc-4499-9734-013fff9ef828','74d6b423-8afc-40f1-8efb-475ab90880a8'),('5db8677f-6702-499b-9993-e5085dbf645f',2,229000,'bfd7b9d2-1ad8-4e42-8fc3-e5ed8de295a4','4ee0f880-34cf-4c99-b602-2ae01416096a'),('5df589dd-5722-47db-b407-571cd2417794',1,233000,'4f91bf66-0557-4910-b58a-70c35a2cf96a','4ee0f880-34cf-4c99-b602-2ae01416096a'),('5f9ba936-fd47-40c3-865c-3c1a83f6200f',1,403000,'bfd7b9d2-1ad8-4e42-8fc3-e5ed8de295a4','d1a64dbe-589c-4298-a537-f9f695b60c4c'),('60454db5-e3d4-486c-ae57-5589f74cf83c',2,272000,'5da21cf2-53e9-4c13-b76f-7bae623397b3','8fd0981e-c64f-4b4f-b5e1-63afe1841df0'),('6165e776-2953-465f-94b4-7199f4b41fbd',3,291000,'839ed5ed-85c8-465e-8a43-852d423c0085','74d6b423-8afc-40f1-8efb-475ab90880a8'),('628557c9-3ff9-4766-8966-aac0b9331b45',4,213000,'06d3ac2c-a1a8-4f46-8ab7-aba27a1b2b38','d1a64dbe-589c-4298-a537-f9f695b60c4c'),('64503a3c-10d8-4fcf-9a9d-5a1976464181',4,364000,'da48ce91-428d-4862-9bc9-6138ccfd335e','8d51ca04-eec4-4578-bcbe-21254d2f9051'),('691e4a02-381c-41d4-bb4d-0889094041d6',2,283000,'b3b6141f-d8dc-4499-9734-013fff9ef828','b4c93ebe-1048-4556-b552-a3f3990c06fe'),('6948e89a-87c9-4819-9941-9fb689609cc6',1,467000,'61c3377e-f290-4497-8979-51d98d6f626f','d1a64dbe-589c-4298-a537-f9f695b60c4c'),('69e6d89f-b08f-4fcf-83b5-57159bab5010',2,491000,'e3ce7b90-515c-4afc-835b-e58e4bb37b5f','8fd0981e-c64f-4b4f-b5e1-63afe1841df0'),('6a74717f-c970-434b-9801-731218a15a20',2,293000,'527f71ad-1561-4f19-9044-d0875ae03d05','8d51ca04-eec4-4578-bcbe-21254d2f9051'),('6c96b491-2c6e-490a-a548-1b1853d28218',4,395000,'e3ce7b90-515c-4afc-835b-e58e4bb37b5f','8fd0981e-c64f-4b4f-b5e1-63afe1841df0'),('6ccba639-2794-45de-8fba-388e55feb349',4,484000,'f10e6bca-66f4-421c-9f35-0648f01f795d','046b67ea-a216-4a6b-821b-e78f5a9e83ee'),('6f195993-0cf9-4c02-8ba5-07d76fb169ad',1,364000,'351c118f-374a-4be5-9250-21e041068e8d','57b36703-287c-48d5-9d04-0a2debd48c53'),('73e7364d-f5b3-498a-90c6-2d7223d974ea',3,113000,'82842539-0a5c-41e1-9b99-914e80febddc','74d6b423-8afc-40f1-8efb-475ab90880a8'),('7936f2d1-c776-45d5-9c4b-128e5e30a853',4,325000,'01ba2d84-220c-4a1d-b72c-c6a7cfb0aff6','74d6b423-8afc-40f1-8efb-475ab90880a8'),('79b06428-7cb9-4147-af52-59d7534036a6',3,177000,'44bc91e2-d4ec-461e-9542-3b7b06269c23','5aff5922-69bd-46dd-a84b-427ae9c24d41'),('7a6a62a0-3371-4913-a142-cfaa01cf60ed',2,446000,'875f5e37-20cf-4c90-a22b-ce41fd84bb39','57b36703-287c-48d5-9d04-0a2debd48c53'),('7c6e41f2-686f-4c1d-9d15-e6a4b173bdd5',2,372000,'80c0bc7f-1978-455f-8c69-e37f1958e864','57b36703-287c-48d5-9d04-0a2debd48c53'),('7f06b2a5-d167-4de8-a6a7-61897df668ad',4,215000,'ea1cb80d-1a0f-4b74-b462-c722aec59225','046b67ea-a216-4a6b-821b-e78f5a9e83ee'),('846b3265-57c0-48c9-b489-4dc2e8ef6c21',2,128000,'44bc91e2-d4ec-461e-9542-3b7b06269c23','d1a64dbe-589c-4298-a537-f9f695b60c4c'),('881f01d9-9091-4222-be03-5684e9d30560',4,275000,'f1b843ee-aba7-4691-a780-6575a14bbafa','5aff5922-69bd-46dd-a84b-427ae9c24d41'),('8b5de773-6ddf-4465-a4bf-1448a825a4fe',2,336000,'f10e6bca-66f4-421c-9f35-0648f01f795d','4ee0f880-34cf-4c99-b602-2ae01416096a'),('900d0681-ee6c-47d2-b6aa-14589f6a6119',3,154000,'b382c756-1ab5-4d38-9f7e-415e5889bf31','5aff5922-69bd-46dd-a84b-427ae9c24d41'),('9370e0ae-4c96-45e7-982b-61de0b32fc15',4,348000,'06d3ac2c-a1a8-4f46-8ab7-aba27a1b2b38','57b36703-287c-48d5-9d04-0a2debd48c53'),('94f22b5b-d969-4fd0-b8f6-6e1466ec482c',3,466000,'527f71ad-1561-4f19-9044-d0875ae03d05','5aff5922-69bd-46dd-a84b-427ae9c24d41'),('964a9d4e-e6f8-4f8e-8be7-ce18b42ba12b',4,78000,'e3ce7b90-515c-4afc-835b-e58e4bb37b5f','74d6b423-8afc-40f1-8efb-475ab90880a8'),('995b6127-97b5-4d1a-9f5a-12fbbd3565f3',1,208000,'f10e6bca-66f4-421c-9f35-0648f01f795d','4ee0f880-34cf-4c99-b602-2ae01416096a'),('a0181ec2-69b2-445a-8703-49e7f2f75855',1,276000,'b382c756-1ab5-4d38-9f7e-415e5889bf31','8d51ca04-eec4-4578-bcbe-21254d2f9051'),('a116d83a-4d02-49d0-98c2-d6f23ca69608',4,141000,'cabc1b81-42a5-4636-81e3-5176613847a4','57b36703-287c-48d5-9d04-0a2debd48c53'),('a284f6cd-b926-402f-a928-34784ae34b9d',2,106000,'039df6bc-a94f-4f86-90da-6c2d748f2467','74d6b423-8afc-40f1-8efb-475ab90880a8'),('a3217b15-0970-4ab4-8aed-17768705fddd',1,390000,'839ed5ed-85c8-465e-8a43-852d423c0085','5aff5922-69bd-46dd-a84b-427ae9c24d41'),('a3821aba-2134-4332-aa65-3c19e80193cb',1,442000,'f10e6bca-66f4-421c-9f35-0648f01f795d','8d51ca04-eec4-4578-bcbe-21254d2f9051'),('a3957aa9-257b-43ee-a0bd-a33a8647dc04',3,261000,'0f484eb9-ae53-48a6-909c-4f800b2766ca','5aff5922-69bd-46dd-a84b-427ae9c24d41'),('a41c81d0-6122-49ed-9ac9-31dfbd4e11a2',3,186000,'839ed5ed-85c8-465e-8a43-852d423c0085','d1a64dbe-589c-4298-a537-f9f695b60c4c'),('a6f3ccec-68bc-483e-ad06-351171704e74',3,423000,'839ed5ed-85c8-465e-8a43-852d423c0085','4ee0f880-34cf-4c99-b602-2ae01416096a'),('a7cd42de-3f90-4748-8ca2-5eb9200b5874',4,181000,'59cbc85d-91e7-40b9-aa17-9d6d3f45b6ed','4ee0f880-34cf-4c99-b602-2ae01416096a'),('a839ab99-19e3-45c2-a8b6-db1a43ac9565',1,365000,'039df6bc-a94f-4f86-90da-6c2d748f2467','8d51ca04-eec4-4578-bcbe-21254d2f9051'),('a9a1371a-d466-4b2f-921b-3822ce0f9d1b',1,429000,'a3a2b879-6969-4e08-a245-5c85e3322e33','d1a64dbe-589c-4298-a537-f9f695b60c4c'),('b24a3bd5-25ae-4579-a342-773198d595e9',1,184000,'e3ce7b90-515c-4afc-835b-e58e4bb37b5f','8d51ca04-eec4-4578-bcbe-21254d2f9051'),('b26e21d2-8bce-4777-aafb-c1f6a911d9e9',3,226000,'ea1cb80d-1a0f-4b74-b462-c722aec59225','5aff5922-69bd-46dd-a84b-427ae9c24d41'),('b7c70fc7-5619-4c37-b6ac-d70ba6ecdec8',2,437000,'875f5e37-20cf-4c90-a22b-ce41fd84bb39','046b67ea-a216-4a6b-821b-e78f5a9e83ee'),('b992ca92-f886-4789-a961-8c01856d8cc5',1,369000,'4f91bf66-0557-4910-b58a-70c35a2cf96a','d1a64dbe-589c-4298-a537-f9f695b60c4c'),('c76b9454-b8e9-446b-bd75-3caa3436bc10',3,481000,'06d3ac2c-a1a8-4f46-8ab7-aba27a1b2b38','046b67ea-a216-4a6b-821b-e78f5a9e83ee'),('cb2ede7e-4bf5-4116-bfb5-fcae24d95202',2,272000,'06d3ac2c-a1a8-4f46-8ab7-aba27a1b2b38','74d6b423-8afc-40f1-8efb-475ab90880a8'),('d2b8dc5a-fd95-40e1-a421-ddc8cb6574e9',2,404000,'fab966f1-00fe-494c-b668-2361174b97cf','b4c93ebe-1048-4556-b552-a3f3990c06fe'),('d41945e5-7124-4d29-ac94-e33a63006ec9',3,89000,'fab966f1-00fe-494c-b668-2361174b97cf','74d6b423-8afc-40f1-8efb-475ab90880a8'),('db21dc0d-cd80-44b1-9621-33049c189025',3,93000,'875f5e37-20cf-4c90-a22b-ce41fd84bb39','5aff5922-69bd-46dd-a84b-427ae9c24d41'),('dde43d31-188e-4f26-aa15-5c9680725492',2,260000,'b3b6141f-d8dc-4499-9734-013fff9ef828','046b67ea-a216-4a6b-821b-e78f5a9e83ee'),('e1653325-ea7b-4ed7-bd03-145ca722711f',3,387000,'0f484eb9-ae53-48a6-909c-4f800b2766ca','8d51ca04-eec4-4578-bcbe-21254d2f9051'),('e5e18409-9ac7-407e-9b5a-a6cdcbe1227c',2,389000,'fab966f1-00fe-494c-b668-2361174b97cf','8fd0981e-c64f-4b4f-b5e1-63afe1841df0'),('e9291303-5e26-46d8-8e57-9b22874c04e8',1,311000,'26b388be-bb65-4568-ac6d-9d9fcaeddd40','d1a64dbe-589c-4298-a537-f9f695b60c4c'),('ea3b5023-7eb8-4ecb-9023-bc19ebd194f5',2,479000,'bfd7b9d2-1ad8-4e42-8fc3-e5ed8de295a4','4ee0f880-34cf-4c99-b602-2ae01416096a'),('ed51bc58-e43c-4120-83f4-3f4f066018fe',3,366000,'cabc1b81-42a5-4636-81e3-5176613847a4','5aff5922-69bd-46dd-a84b-427ae9c24d41'),('f210bae0-88cc-48a6-a2cf-ebb3316b3f29',3,329000,'b3b6141f-d8dc-4499-9734-013fff9ef828','4ee0f880-34cf-4c99-b602-2ae01416096a'),('f43cb35c-576a-41bd-9f03-8cde338b9855',3,96000,'ea1cb80d-1a0f-4b74-b462-c722aec59225','046b67ea-a216-4a6b-821b-e78f5a9e83ee'),('f9fa9cee-27bd-4bf6-9484-a6981e1c2f86',4,399000,'06d3ac2c-a1a8-4f46-8ab7-aba27a1b2b38','046b67ea-a216-4a6b-821b-e78f5a9e83ee'),('fdf07484-d03d-4865-b585-98d4e0243538',2,338000,'f10e6bca-66f4-421c-9f35-0648f01f795d','4ee0f880-34cf-4c99-b602-2ae01416096a');
/*!40000 ALTER TABLE `bill_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `building`
--

DROP TABLE IF EXISTS `building`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `building` (
  `id` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `capacity` int DEFAULT '-1',
  `name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `building`
--

LOCK TABLES `building` WRITE;
/*!40000 ALTER TABLE `building` DISABLE KEYS */;
INSERT INTO `building` VALUES ('2cce479a-b893-4343-8362-cc1624c6c3d4','Thu Duc city',50,'Building 4','AVAILABLE'),('2fce7af4-fb92-4faf-a8f0-3405591d476d','District 9',50,'Building 1','AVAILABLE'),('6aea7958-326f-415a-8f9f-61ce169ab7a0','District 9',50,'Building 2','AVAILABLE'),('d457dc9a-5004-425f-97c4-f267af1faa66','District 9',50,'Building 3','AVAILABLE');
/*!40000 ALTER TABLE `building` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `id` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `citizen_id` varchar(255) DEFAULT NULL,
  `date_join` date DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fullname` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_dwk6cx0afu8bs9o4t536v1j5v` (`email`),
  UNIQUE KEY `UK_o3uty20c6csmx5y4uk2tc5r4m` (`phone`),
  FULLTEXT KEY `fullname` (`fullname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES ('04f6588a-8567-408b-b434-0d7fd82495c2','Suite 763 85589 Paul Courts, Karleentown, TN 38341','1211-1221-1234-2201','2021-04-18','1974-04-01','customer24@gmail.com','Customer 24','FEMALE','$2a$12$xOXUkpChPTfs/WmRvNS3s.Wqz2Wn9GdWO1Ttiea.2pRhGmYvVI5UK','0123456724','INACTIVE'),('1844eccb-7ae1-49c5-842a-f052aea84aed','Suite 697 9437 Sanford Meadow, Shaneborough, SC 35198','1228-1221-1221-1431','2022-11-17','1968-11-30','customer23@gmail.com','Customer 23','FEMALE','$2a$12$xOXUkpChPTfs/WmRvNS3s.Wqz2Wn9GdWO1Ttiea.2pRhGmYvVI5UK','0123456723','INACTIVE'),('23032a4f-7c3d-4393-96d0-047ea0560483','Suite 299 164 Towne Knoll, Lake Isidra, AR 26848-2632','1228-1221-1221-1431','2021-04-06','1964-02-15','customer26@gmail.com','Customer 26','MALE','$2a$12$xOXUkpChPTfs/WmRvNS3s.Wqz2Wn9GdWO1Ttiea.2pRhGmYvVI5UK','34239126','INACTIVE'),('246faa9c-285b-4a2c-80fe-3ba28b163588','Apt. 653 33811 Marlys Circles, East Lucille, MD 87238','1212-1221-1121-1234','2022-06-04','1972-11-15','customer25@gmail.com','Customer 25','FEMALE','$2a$12$xOXUkpChPTfs/WmRvNS3s.Wqz2Wn9GdWO1Ttiea.2pRhGmYvVI5UK','0123456725','INACTIVE'),('31a57e90-3748-4088-b009-2108e6ca77cb','Suite 905 9065 Milo Common, Port Jaysonmouth, WV 72137','1234-2121-1221-1211','2021-01-27','2000-12-10','customer14@gmail.com','Customer 14','FEMALE','$2a$12$xOXUkpChPTfs/WmRvNS3s.Wqz2Wn9GdWO1Ttiea.2pRhGmYvVI5UK','0123456714','ACTIVE'),('3347dea0-9620-45d5-b0bc-3a1557014b48','254 Eldon Mountains, Louannton, MI 38967-5807','1234-2121-1221-1211','2021-10-07','1973-05-13','customer15@gmail.com','Customer 15','FEMALE','$2a$12$xOXUkpChPTfs/WmRvNS3s.Wqz2Wn9GdWO1Ttiea.2pRhGmYvVI5UK','0123456715','ACTIVE'),('38387425-86df-413b-85b0-0ed8a76cf5f5','Suite 746 25409 Greenholt Mountains, North Vernton, CA 88084','1234-2121-1221-1211','2022-07-18','1974-07-17','customer22@gmail.com','Customer 22','FEMALE','$2a$12$xOXUkpChPTfs/WmRvNS3s.Wqz2Wn9GdWO1Ttiea.2pRhGmYvVI5UK','0123456722','INACTIVE'),('3fcdac37-05d6-42ac-951c-5da1a2d52a1c','3597 Homenick Falls, West Shaneka, OK 83035-5302','1228-1221-1221-1431','2022-09-14','1999-07-23','customer21@gmail.com','Customer 21','FEMALE','$2a$12$xOXUkpChPTfs/WmRvNS3s.Wqz2Wn9GdWO1Ttiea.2pRhGmYvVI5UK','0123456721','INACTIVE'),('45097d28-9534-4c6c-87f8-e20800020ecc','59882 Cleo Greens, West Lorriville, OH 11999','1212-1221-1121-1234','2021-02-21','1989-08-22','customer27@gmail.com','Customer 27','MALE','$2a$12$xOXUkpChPTfs/WmRvNS3s.Wqz2Wn9GdWO1Ttiea.2pRhGmYvVI5UK','34239127','INACTIVE'),('6397dd86-b241-4d5c-87ff-eb5148980fdb','Apt. 884 2303 Lynch Vista, Watsicamouth, NJ 03604-1416','1212-1221-1121-1234','2022-04-06','1960-11-03','customer20@gmail.com','Customer 20','MALE','$2a$12$xOXUkpChPTfs/WmRvNS3s.Wqz2Wn9GdWO1Ttiea.2pRhGmYvVI5UK','0123456720','ACTIVE'),('6da1618d-b76f-4f6d-bfaf-9927f1dbe2c7','22243 Earlene Fords, New Pialand, MS 17959','1212-1221-1121-1234','2022-02-18','1970-04-13','customer30@gmail.com','Customer 30','MALE','$2a$12$xOXUkpChPTfs/WmRvNS3s.Wqz2Wn9GdWO1Ttiea.2pRhGmYvVI5UK','34239130','INACTIVE'),('6e44e05e-b1bc-4348-825d-237fcc37fc11','52513 Gutkowski Stream, Jacintofort, WI 41509-3013','1234-2121-1221-1211','2021-10-10','1977-11-16','customer16@gmail.com','Customer 16','MALE','$2a$12$xOXUkpChPTfs/WmRvNS3s.Wqz2Wn9GdWO1Ttiea.2pRhGmYvVI5UK','0123456716','ACTIVE'),('6eb48fc7-2d09-4206-9ec9-d28d193cd3e5','7564 Alissa Streets, Schinnerland, SD 18405','1212-1221-1121-1234','2020-12-31','1976-12-29','customer13@gmail.com','Customer 13','FEMALE','$2a$12$xOXUkpChPTfs/WmRvNS3s.Wqz2Wn9GdWO1Ttiea.2pRhGmYvVI5UK','0123456713','ACTIVE'),('898a95a3-d721-4b49-a350-f9fb4b0bbd9f','Suite 938 396 Deangelo Ridge, North Catherin, OR 18718','1228-1221-1221-1431','2021-04-25','1974-05-27','customer12@gmail.com','Customer 12','FEMALE','$2a$12$xOXUkpChPTfs/WmRvNS3s.Wqz2Wn9GdWO1Ttiea.2pRhGmYvVI5UK','0123456712','ACTIVE'),('9f61bd09-688e-4215-918f-7353b996c8b0','920 Armstrong Stravenue, East Norbertoton, KY 27693-7054','1228-1221-1221-1431','2021-04-27','1988-10-25','customer17@gmail.com','Customer 17','MALE','$2a$12$xOXUkpChPTfs/WmRvNS3s.Wqz2Wn9GdWO1Ttiea.2pRhGmYvVI5UK','0123456717','ACTIVE'),('a11a45fe-06bd-4f6f-ba2d-cd7a2eebf5c8','Apt. 529 94171 Williams Plains, North Wilfredomouth, AR 90266','1212-1221-1121-1234','2022-06-22','2004-03-18','customer11@gmail.com','Customer 11','FEMALE','$2a$12$xOXUkpChPTfs/WmRvNS3s.Wqz2Wn9GdWO1Ttiea.2pRhGmYvVI5UK','0123456711','ACTIVE'),('abf544bb-16f7-418c-b7c1-3378e2783810','48468 Dustin Square, South Madelineport, NY 70541-4605','1211-1221-1234-2201','2021-03-13','1990-04-11','customer29@gmail.com','Customer 29','MALE','$2a$12$xOXUkpChPTfs/WmRvNS3s.Wqz2Wn9GdWO1Ttiea.2pRhGmYvVI5UK','34239129','INACTIVE'),('d0dabea3-bb7f-4cb5-9e2b-3fca7a4841f8','Quận 9','112113114','2023-03-24','2002-07-18',NULL,'Lâm Duy','MALE','$2a$10$34X5jCvLm3C4HQJGUpuHuun9saAaAB5j3dSO9zIsdt01CYxAej/HO','0817415290','ACTIVE'),('d68a9d9c-b2ee-4fa0-83ec-e810fa1afac2','Suite 996 7777 Trantow Loop, Port Irma, AZ 80692-3212','1228-1221-1221-1431','2022-10-02','1968-07-30','customer19@gmail.com','Customer 19','MALE','$2a$12$xOXUkpChPTfs/WmRvNS3s.Wqz2Wn9GdWO1Ttiea.2pRhGmYvVI5UK','0123456719','ACTIVE'),('e100ce7a-c743-4ad9-9e7b-4eebc9989e95','393 Herbert Shore, Homenickfort, IA 36044','1234-2121-1221-1211','2021-07-22','1967-09-27','customer28@gmail.com','Customer 28','MALE','$2a$12$xOXUkpChPTfs/WmRvNS3s.Wqz2Wn9GdWO1Ttiea.2pRhGmYvVI5UK','34239128','INACTIVE'),('e479d7f8-0f83-41c7-b24f-e7a95c1fed0c','99932 Lakeesha Prairie, Hillsborough, ND 32504-4201','1212-1221-1121-1234','2021-11-16','1999-02-19','customer18@gmail.com','Customer 18','MALE','$2a$12$xOXUkpChPTfs/WmRvNS3s.Wqz2Wn9GdWO1Ttiea.2pRhGmYvVI5UK','0123456718','ACTIVE');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `device`
--

DROP TABLE IF EXISTS `device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `device` (
  `id` varchar(255) NOT NULL,
  `token` varchar(255) DEFAULT NULL,
  `customer_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsb31h8j4wshfi4dv2rxlk8emb` (`customer_id`),
  CONSTRAINT `FKsb31h8j4wshfi4dv2rxlk8emb` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device`
--

LOCK TABLES `device` WRITE;
/*!40000 ALTER TABLE `device` DISABLE KEYS */;
INSERT INTO `device` VALUES ('0a2fe0cf-77a6-430d-8985-5001f488dacc','e-KQYOLETMGDtwaCct1cNK:APA91bG4SyarlhuGIvTQUKYKWGtgtcRLSyYqBXD1qvO-czgjEpqMUY5fMnY5OBbfWbdP5Rjo25Ek8Gyq2H7TYow38pedbsGbWbXRQhHSi5N0TXbEFKArU-fWSzVZgin4jub7U454tgqs','6eb48fc7-2d09-4206-9ec9-d28d193cd3e5'),('15018485-7830-4e70-9476-af1379a208a2','e-KQYOLETMGDtwaCct1cNK:APA91bG4SyarlhuGIvTQUKYKWGtgtcRLSyYqBXD1qvO-czgjEpqMUY5fMnY5OBbfWbdP5Rjo25Ek8Gyq2H7TYow38pedbsGbWbXRQhHSi5N0TXbEFKArU-fWSzVZgin4jub7U454tgqs','31a57e90-3748-4088-b009-2108e6ca77cb'),('2995eb30-6ffc-47c9-a5cb-21d4018bc864','e-KQYOLETMGDtwaCct1cNK:APA91bG4SyarlhuGIvTQUKYKWGtgtcRLSyYqBXD1qvO-czgjEpqMUY5fMnY5OBbfWbdP5Rjo25Ek8Gyq2H7TYow38pedbsGbWbXRQhHSi5N0TXbEFKArU-fWSzVZgin4jub7U454tgqs','45097d28-9534-4c6c-87f8-e20800020ecc'),('34b9cc7e-895b-4206-8f94-3aa642a70482','e-KQYOLETMGDtwaCct1cNK:APA91bG4SyarlhuGIvTQUKYKWGtgtcRLSyYqBXD1qvO-czgjEpqMUY5fMnY5OBbfWbdP5Rjo25Ek8Gyq2H7TYow38pedbsGbWbXRQhHSi5N0TXbEFKArU-fWSzVZgin4jub7U454tgqs','9f61bd09-688e-4215-918f-7353b996c8b0'),('3e55b694-5227-4ab8-a842-4409f6a9bcce','e-KQYOLETMGDtwaCct1cNK:APA91bG4SyarlhuGIvTQUKYKWGtgtcRLSyYqBXD1qvO-czgjEpqMUY5fMnY5OBbfWbdP5Rjo25Ek8Gyq2H7TYow38pedbsGbWbXRQhHSi5N0TXbEFKArU-fWSzVZgin4jub7U454tgqs','6da1618d-b76f-4f6d-bfaf-9927f1dbe2c7'),('4a3d6a12-4745-4b0f-bf1d-5c4091d72553','e-KQYOLETMGDtwaCct1cNK:APA91bG4SyarlhuGIvTQUKYKWGtgtcRLSyYqBXD1qvO-czgjEpqMUY5fMnY5OBbfWbdP5Rjo25Ek8Gyq2H7TYow38pedbsGbWbXRQhHSi5N0TXbEFKArU-fWSzVZgin4jub7U454tgqs','3fcdac37-05d6-42ac-951c-5da1a2d52a1c'),('5ce36b41-3f40-43d4-a5c9-f9471128d33e','e-KQYOLETMGDtwaCct1cNK:APA91bG4SyarlhuGIvTQUKYKWGtgtcRLSyYqBXD1qvO-czgjEpqMUY5fMnY5OBbfWbdP5Rjo25Ek8Gyq2H7TYow38pedbsGbWbXRQhHSi5N0TXbEFKArU-fWSzVZgin4jub7U454tgqs','abf544bb-16f7-418c-b7c1-3378e2783810'),('636ed2cc-254c-4c95-8277-56056fd3593f','e-KQYOLETMGDtwaCct1cNK:APA91bG4SyarlhuGIvTQUKYKWGtgtcRLSyYqBXD1qvO-czgjEpqMUY5fMnY5OBbfWbdP5Rjo25Ek8Gyq2H7TYow38pedbsGbWbXRQhHSi5N0TXbEFKArU-fWSzVZgin4jub7U454tgqs','e479d7f8-0f83-41c7-b24f-e7a95c1fed0c'),('6ff2a802-6c58-4ce4-b116-8b8198ea9358','e-KQYOLETMGDtwaCct1cNK:APA91bG4SyarlhuGIvTQUKYKWGtgtcRLSyYqBXD1qvO-czgjEpqMUY5fMnY5OBbfWbdP5Rjo25Ek8Gyq2H7TYow38pedbsGbWbXRQhHSi5N0TXbEFKArU-fWSzVZgin4jub7U454tgqs','a11a45fe-06bd-4f6f-ba2d-cd7a2eebf5c8'),('78146fe1-6f14-4814-a2d5-1c5677f309d5','e-KQYOLETMGDtwaCct1cNK:APA91bG4SyarlhuGIvTQUKYKWGtgtcRLSyYqBXD1qvO-czgjEpqMUY5fMnY5OBbfWbdP5Rjo25Ek8Gyq2H7TYow38pedbsGbWbXRQhHSi5N0TXbEFKArU-fWSzVZgin4jub7U454tgqs','23032a4f-7c3d-4393-96d0-047ea0560483'),('79ca0dce-eff3-44e7-815c-4bd5ca6409d1','e-KQYOLETMGDtwaCct1cNK:APA91bG4SyarlhuGIvTQUKYKWGtgtcRLSyYqBXD1qvO-czgjEpqMUY5fMnY5OBbfWbdP5Rjo25Ek8Gyq2H7TYow38pedbsGbWbXRQhHSi5N0TXbEFKArU-fWSzVZgin4jub7U454tgqs','04f6588a-8567-408b-b434-0d7fd82495c2'),('98df0e40-60bb-45a9-8f80-31d338b6ef3a','e-KQYOLETMGDtwaCct1cNK:APA91bG4SyarlhuGIvTQUKYKWGtgtcRLSyYqBXD1qvO-czgjEpqMUY5fMnY5OBbfWbdP5Rjo25Ek8Gyq2H7TYow38pedbsGbWbXRQhHSi5N0TXbEFKArU-fWSzVZgin4jub7U454tgqs','6eb48fc7-2d09-4206-9ec9-d28d193cd3e5'),('a04e1ca5-da57-49c0-86f0-3e234cf88bcc','e-KQYOLETMGDtwaCct1cNK:APA91bG4SyarlhuGIvTQUKYKWGtgtcRLSyYqBXD1qvO-czgjEpqMUY5fMnY5OBbfWbdP5Rjo25Ek8Gyq2H7TYow38pedbsGbWbXRQhHSi5N0TXbEFKArU-fWSzVZgin4jub7U454tgqs','246faa9c-285b-4a2c-80fe-3ba28b163588'),('abe3fac1-411f-4712-a63b-d5011e4479cf','e-KQYOLETMGDtwaCct1cNK:APA91bG4SyarlhuGIvTQUKYKWGtgtcRLSyYqBXD1qvO-czgjEpqMUY5fMnY5OBbfWbdP5Rjo25Ek8Gyq2H7TYow38pedbsGbWbXRQhHSi5N0TXbEFKArU-fWSzVZgin4jub7U454tgqs','6e44e05e-b1bc-4348-825d-237fcc37fc11'),('af10a7eb-2da2-455f-8a49-f801aa8a37d2','e-KQYOLETMGDtwaCct1cNK:APA91bG4SyarlhuGIvTQUKYKWGtgtcRLSyYqBXD1qvO-czgjEpqMUY5fMnY5OBbfWbdP5Rjo25Ek8Gyq2H7TYow38pedbsGbWbXRQhHSi5N0TXbEFKArU-fWSzVZgin4jub7U454tgqs','1844eccb-7ae1-49c5-842a-f052aea84aed'),('bc8107cc-8a72-4459-bc13-e16f34325a76','e-KQYOLETMGDtwaCct1cNK:APA91bG4SyarlhuGIvTQUKYKWGtgtcRLSyYqBXD1qvO-czgjEpqMUY5fMnY5OBbfWbdP5Rjo25Ek8Gyq2H7TYow38pedbsGbWbXRQhHSi5N0TXbEFKArU-fWSzVZgin4jub7U454tgqs','246faa9c-285b-4a2c-80fe-3ba28b163588'),('c5f82261-f882-4f71-8964-6074d025f032','e-KQYOLETMGDtwaCct1cNK:APA91bG4SyarlhuGIvTQUKYKWGtgtcRLSyYqBXD1qvO-czgjEpqMUY5fMnY5OBbfWbdP5Rjo25Ek8Gyq2H7TYow38pedbsGbWbXRQhHSi5N0TXbEFKArU-fWSzVZgin4jub7U454tgqs','31a57e90-3748-4088-b009-2108e6ca77cb'),('d16fe98e-19f4-4595-9737-41e2617f47b4','e-KQYOLETMGDtwaCct1cNK:APA91bG4SyarlhuGIvTQUKYKWGtgtcRLSyYqBXD1qvO-czgjEpqMUY5fMnY5OBbfWbdP5Rjo25Ek8Gyq2H7TYow38pedbsGbWbXRQhHSi5N0TXbEFKArU-fWSzVZgin4jub7U454tgqs','6397dd86-b241-4d5c-87ff-eb5148980fdb'),('e29cef20-813c-41d8-a4db-be75853eadfb','e-KQYOLETMGDtwaCct1cNK:APA91bG4SyarlhuGIvTQUKYKWGtgtcRLSyYqBXD1qvO-czgjEpqMUY5fMnY5OBbfWbdP5Rjo25Ek8Gyq2H7TYow38pedbsGbWbXRQhHSi5N0TXbEFKArU-fWSzVZgin4jub7U454tgqs','abf544bb-16f7-418c-b7c1-3378e2783810'),('ed754efa-24bf-4068-8300-7005e725056e','e-KQYOLETMGDtwaCct1cNK:APA91bG4SyarlhuGIvTQUKYKWGtgtcRLSyYqBXD1qvO-czgjEpqMUY5fMnY5OBbfWbdP5Rjo25Ek8Gyq2H7TYow38pedbsGbWbXRQhHSi5N0TXbEFKArU-fWSzVZgin4jub7U454tgqs','a11a45fe-06bd-4f6f-ba2d-cd7a2eebf5c8'),('f71b5029-c343-4159-97d4-636c5e781afe','e-KQYOLETMGDtwaCct1cNK:APA91bG4SyarlhuGIvTQUKYKWGtgtcRLSyYqBXD1qvO-czgjEpqMUY5fMnY5OBbfWbdP5Rjo25Ek8Gyq2H7TYow38pedbsGbWbXRQhHSi5N0TXbEFKArU-fWSzVZgin4jub7U454tgqs','6397dd86-b241-4d5c-87ff-eb5148980fdb'),('f8fe479b-62d7-43cb-954d-0f1595426da3','e-KQYOLETMGDtwaCct1cNK:APA91bG4SyarlhuGIvTQUKYKWGtgtcRLSyYqBXD1qvO-czgjEpqMUY5fMnY5OBbfWbdP5Rjo25Ek8Gyq2H7TYow38pedbsGbWbXRQhHSi5N0TXbEFKArU-fWSzVZgin4jub7U454tgqs','04f6588a-8567-408b-b434-0d7fd82495c2');
/*!40000 ALTER TABLE `device` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `facility`
--

DROP TABLE IF EXISTS `facility`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `facility` (
  `id` varchar(255) NOT NULL,
  `item` varchar(255) DEFAULT NULL,
  `quantity` int NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facility`
--

LOCK TABLES `facility` WRITE;
/*!40000 ALTER TABLE `facility` DISABLE KEYS */;
INSERT INTO `facility` VALUES ('305c0f2f-78c7-4933-9f2d-1a778c1542a9','Facility 4',1,'AVAILABLE'),('6263cc15-8cdf-4242-8908-5b308573972e','Facility 5',4,'AVAILABLE'),('9713563a-89cd-425a-a81c-69ac0392db5f','Facility 6',2,'AVAILABLE'),('9e28fdb2-7db5-4b78-ae34-981534a30dd0','Facility 1',3,'AVAILABLE'),('a7db4835-a839-4d9e-837d-4cc9c8e80ea1','Facility 3',4,'AVAILABLE'),('f0285a34-162f-41e0-bec9-9eb50ac9c3ac','Facility 2',2,'AVAILABLE');
/*!40000 ALTER TABLE `facility` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flat`
--

DROP TABLE IF EXISTS `flat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flat` (
  `id` varchar(255) NOT NULL,
  `detail` varchar(255) DEFAULT NULL,
  `price` int NOT NULL,
  `room_number` int NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `building_id` varchar(255) DEFAULT NULL,
  `flat_type_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcuvibqssnlnlcvkphbl35qud1` (`building_id`),
  KEY `FKpyg3bde4wtav173p6punqlvee` (`flat_type_id`),
  CONSTRAINT `FKcuvibqssnlnlcvkphbl35qud1` FOREIGN KEY (`building_id`) REFERENCES `building` (`id`),
  CONSTRAINT `FKpyg3bde4wtav173p6punqlvee` FOREIGN KEY (`flat_type_id`) REFERENCES `flat_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flat`
--

LOCK TABLES `flat` WRITE;
/*!40000 ALTER TABLE `flat` DISABLE KEYS */;
INSERT INTO `flat` VALUES ('1b53b8fa-d1ea-4d76-b997-903be5657e16','Detail 5',4000000,5,'AVAILABLE','2cce479a-b893-4343-8362-cc1624c6c3d4','9e759a4d-f192-4101-8ec9-4807ea35cae3'),('2ae50195-8bb8-4674-a3a0-44a1a9b61e94','Detail 2',15000000,2,'AVAILABLE','2fce7af4-fb92-4faf-a8f0-3405591d476d','9e759a4d-f192-4101-8ec9-4807ea35cae3'),('37592f7a-3b4c-4fe4-bdbd-a11758664ac5','Detail 17',13000000,17,'AVAILABLE','2cce479a-b893-4343-8362-cc1624c6c3d4','bae02975-2bdd-4b5f-b211-eb62a4da2174'),('465ad517-b9e3-41dc-b46c-2a6db96e2e1c','Detail 9',8000000,9,'AVAILABLE','2fce7af4-fb92-4faf-a8f0-3405591d476d','4d7fce2d-9aa2-4124-b180-70a535424ecc'),('4ffbd084-c14f-40b5-9c0f-80747057efab','Detail 14',25000000,14,'AVAILABLE','2fce7af4-fb92-4faf-a8f0-3405591d476d','4d7fce2d-9aa2-4124-b180-70a535424ecc'),('5994bd7c-ce4f-4773-9a36-678650588bc9','Detail 13',21000000,13,'AVAILABLE','d457dc9a-5004-425f-97c4-f267af1faa66','bae02975-2bdd-4b5f-b211-eb62a4da2174'),('7d37c630-a580-494a-851a-8be44e466e12','Detail 18',5000000,18,'AVAILABLE','2cce479a-b893-4343-8362-cc1624c6c3d4','4d7fce2d-9aa2-4124-b180-70a535424ecc'),('7dfe53f6-545d-403d-a3d1-157cc0fd385c','Detail 8',22000000,8,'AVAILABLE','2fce7af4-fb92-4faf-a8f0-3405591d476d','4d7fce2d-9aa2-4124-b180-70a535424ecc'),('93411034-520c-4632-abf9-bf9984cadf1c','Detail 1',17000000,1,'AVAILABLE','2fce7af4-fb92-4faf-a8f0-3405591d476d','4d7fce2d-9aa2-4124-b180-70a535424ecc'),('a0453a40-ba13-4c5a-916b-c3909a3111b4','Detail 7',7000000,7,'AVAILABLE','2fce7af4-fb92-4faf-a8f0-3405591d476d','bae02975-2bdd-4b5f-b211-eb62a4da2174'),('a6f156da-f5ab-490f-8c26-316d1403a39b','Detail 19',5000000,19,'AVAILABLE','d457dc9a-5004-425f-97c4-f267af1faa66','bae02975-2bdd-4b5f-b211-eb62a4da2174'),('a7468cdf-248e-4121-9011-be34bc3c862d','Detail 6',5000000,6,'AVAILABLE','d457dc9a-5004-425f-97c4-f267af1faa66','bae02975-2bdd-4b5f-b211-eb62a4da2174'),('b42b62e0-ac40-47cf-a114-bcfc2b1bb0ab','Detail 12',24000000,12,'AVAILABLE','2fce7af4-fb92-4faf-a8f0-3405591d476d','4d7fce2d-9aa2-4124-b180-70a535424ecc'),('d79e5422-4b8e-44e8-9e8c-bf29c9eca4fb','Detail 4',10000000,4,'AVAILABLE','6aea7958-326f-415a-8f9f-61ce169ab7a0','4d7fce2d-9aa2-4124-b180-70a535424ecc'),('e43a0c51-bef6-4219-bc29-dce2dae3f5aa','Detail 0',22000000,0,'AVAILABLE','2cce479a-b893-4343-8362-cc1624c6c3d4','bae02975-2bdd-4b5f-b211-eb62a4da2174'),('e4486db8-8e9d-4d89-84b8-3ab7adcae637','Detail 15',16000000,15,'AVAILABLE','2cce479a-b893-4343-8362-cc1624c6c3d4','9e759a4d-f192-4101-8ec9-4807ea35cae3'),('f0af1872-adfa-43e5-86a0-9847f07ac769','Detail 11',6000000,11,'AVAILABLE','d457dc9a-5004-425f-97c4-f267af1faa66','4d7fce2d-9aa2-4124-b180-70a535424ecc'),('f37ae951-791a-47e1-9998-a344b94a417f','Detail 16',17000000,16,'AVAILABLE','2fce7af4-fb92-4faf-a8f0-3405591d476d','9e759a4d-f192-4101-8ec9-4807ea35cae3'),('fc0e0e80-fb0f-4ee5-8667-18ee5ffc583d','Detail 10',23000000,10,'AVAILABLE','2cce479a-b893-4343-8362-cc1624c6c3d4','bae02975-2bdd-4b5f-b211-eb62a4da2174'),('fc8fa3b7-6683-494f-8430-0b18c196ee90','Detail 3',27000000,3,'AVAILABLE','d457dc9a-5004-425f-97c4-f267af1faa66','9e759a4d-f192-4101-8ec9-4807ea35cae3');
/*!40000 ALTER TABLE `flat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flat_facility`
--

DROP TABLE IF EXISTS `flat_facility`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flat_facility` (
  `flat_id` varchar(255) NOT NULL,
  `facility_id` varchar(255) NOT NULL,
  KEY `FKn3pl38ks8vgl5cx24kr7fu633` (`facility_id`),
  KEY `FKgn03s51bdmobbxd6y4s8boct9` (`flat_id`),
  CONSTRAINT `FKgn03s51bdmobbxd6y4s8boct9` FOREIGN KEY (`flat_id`) REFERENCES `flat` (`id`),
  CONSTRAINT `FKn3pl38ks8vgl5cx24kr7fu633` FOREIGN KEY (`facility_id`) REFERENCES `facility` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flat_facility`
--

LOCK TABLES `flat_facility` WRITE;
/*!40000 ALTER TABLE `flat_facility` DISABLE KEYS */;
INSERT INTO `flat_facility` VALUES ('a7468cdf-248e-4121-9011-be34bc3c862d','9e28fdb2-7db5-4b78-ae34-981534a30dd0'),('a0453a40-ba13-4c5a-916b-c3909a3111b4','9e28fdb2-7db5-4b78-ae34-981534a30dd0'),('fc0e0e80-fb0f-4ee5-8667-18ee5ffc583d','9e28fdb2-7db5-4b78-ae34-981534a30dd0'),('b42b62e0-ac40-47cf-a114-bcfc2b1bb0ab','9e28fdb2-7db5-4b78-ae34-981534a30dd0'),('5994bd7c-ce4f-4773-9a36-678650588bc9','9e28fdb2-7db5-4b78-ae34-981534a30dd0'),('37592f7a-3b4c-4fe4-bdbd-a11758664ac5','9e28fdb2-7db5-4b78-ae34-981534a30dd0'),('a6f156da-f5ab-490f-8c26-316d1403a39b','9e28fdb2-7db5-4b78-ae34-981534a30dd0'),('93411034-520c-4632-abf9-bf9984cadf1c','f0285a34-162f-41e0-bec9-9eb50ac9c3ac'),('2ae50195-8bb8-4674-a3a0-44a1a9b61e94','f0285a34-162f-41e0-bec9-9eb50ac9c3ac'),('fc8fa3b7-6683-494f-8430-0b18c196ee90','f0285a34-162f-41e0-bec9-9eb50ac9c3ac'),('d79e5422-4b8e-44e8-9e8c-bf29c9eca4fb','f0285a34-162f-41e0-bec9-9eb50ac9c3ac'),('1b53b8fa-d1ea-4d76-b997-903be5657e16','f0285a34-162f-41e0-bec9-9eb50ac9c3ac'),('a7468cdf-248e-4121-9011-be34bc3c862d','f0285a34-162f-41e0-bec9-9eb50ac9c3ac'),('465ad517-b9e3-41dc-b46c-2a6db96e2e1c','f0285a34-162f-41e0-bec9-9eb50ac9c3ac'),('b42b62e0-ac40-47cf-a114-bcfc2b1bb0ab','f0285a34-162f-41e0-bec9-9eb50ac9c3ac'),('4ffbd084-c14f-40b5-9c0f-80747057efab','f0285a34-162f-41e0-bec9-9eb50ac9c3ac'),('37592f7a-3b4c-4fe4-bdbd-a11758664ac5','f0285a34-162f-41e0-bec9-9eb50ac9c3ac'),('a6f156da-f5ab-490f-8c26-316d1403a39b','f0285a34-162f-41e0-bec9-9eb50ac9c3ac'),('93411034-520c-4632-abf9-bf9984cadf1c','a7db4835-a839-4d9e-837d-4cc9c8e80ea1'),('d79e5422-4b8e-44e8-9e8c-bf29c9eca4fb','a7db4835-a839-4d9e-837d-4cc9c8e80ea1'),('1b53b8fa-d1ea-4d76-b997-903be5657e16','a7db4835-a839-4d9e-837d-4cc9c8e80ea1'),('a7468cdf-248e-4121-9011-be34bc3c862d','a7db4835-a839-4d9e-837d-4cc9c8e80ea1'),('fc0e0e80-fb0f-4ee5-8667-18ee5ffc583d','a7db4835-a839-4d9e-837d-4cc9c8e80ea1'),('e4486db8-8e9d-4d89-84b8-3ab7adcae637','a7db4835-a839-4d9e-837d-4cc9c8e80ea1'),('f37ae951-791a-47e1-9998-a344b94a417f','a7db4835-a839-4d9e-837d-4cc9c8e80ea1'),('7d37c630-a580-494a-851a-8be44e466e12','a7db4835-a839-4d9e-837d-4cc9c8e80ea1'),('e43a0c51-bef6-4219-bc29-dce2dae3f5aa','305c0f2f-78c7-4933-9f2d-1a778c1542a9'),('2ae50195-8bb8-4674-a3a0-44a1a9b61e94','305c0f2f-78c7-4933-9f2d-1a778c1542a9'),('fc8fa3b7-6683-494f-8430-0b18c196ee90','305c0f2f-78c7-4933-9f2d-1a778c1542a9'),('d79e5422-4b8e-44e8-9e8c-bf29c9eca4fb','305c0f2f-78c7-4933-9f2d-1a778c1542a9'),('1b53b8fa-d1ea-4d76-b997-903be5657e16','305c0f2f-78c7-4933-9f2d-1a778c1542a9'),('b42b62e0-ac40-47cf-a114-bcfc2b1bb0ab','305c0f2f-78c7-4933-9f2d-1a778c1542a9'),('e4486db8-8e9d-4d89-84b8-3ab7adcae637','305c0f2f-78c7-4933-9f2d-1a778c1542a9'),('f37ae951-791a-47e1-9998-a344b94a417f','305c0f2f-78c7-4933-9f2d-1a778c1542a9'),('7d37c630-a580-494a-851a-8be44e466e12','305c0f2f-78c7-4933-9f2d-1a778c1542a9'),('a6f156da-f5ab-490f-8c26-316d1403a39b','305c0f2f-78c7-4933-9f2d-1a778c1542a9'),('2ae50195-8bb8-4674-a3a0-44a1a9b61e94','6263cc15-8cdf-4242-8908-5b308573972e'),('fc8fa3b7-6683-494f-8430-0b18c196ee90','6263cc15-8cdf-4242-8908-5b308573972e'),('d79e5422-4b8e-44e8-9e8c-bf29c9eca4fb','6263cc15-8cdf-4242-8908-5b308573972e'),('1b53b8fa-d1ea-4d76-b997-903be5657e16','6263cc15-8cdf-4242-8908-5b308573972e'),('a7468cdf-248e-4121-9011-be34bc3c862d','6263cc15-8cdf-4242-8908-5b308573972e'),('a0453a40-ba13-4c5a-916b-c3909a3111b4','6263cc15-8cdf-4242-8908-5b308573972e'),('fc0e0e80-fb0f-4ee5-8667-18ee5ffc583d','6263cc15-8cdf-4242-8908-5b308573972e'),('b42b62e0-ac40-47cf-a114-bcfc2b1bb0ab','6263cc15-8cdf-4242-8908-5b308573972e'),('5994bd7c-ce4f-4773-9a36-678650588bc9','6263cc15-8cdf-4242-8908-5b308573972e'),('e4486db8-8e9d-4d89-84b8-3ab7adcae637','6263cc15-8cdf-4242-8908-5b308573972e'),('37592f7a-3b4c-4fe4-bdbd-a11758664ac5','6263cc15-8cdf-4242-8908-5b308573972e'),('7d37c630-a580-494a-851a-8be44e466e12','6263cc15-8cdf-4242-8908-5b308573972e'),('e43a0c51-bef6-4219-bc29-dce2dae3f5aa','9713563a-89cd-425a-a81c-69ac0392db5f'),('93411034-520c-4632-abf9-bf9984cadf1c','9713563a-89cd-425a-a81c-69ac0392db5f'),('2ae50195-8bb8-4674-a3a0-44a1a9b61e94','9713563a-89cd-425a-a81c-69ac0392db5f'),('fc8fa3b7-6683-494f-8430-0b18c196ee90','9713563a-89cd-425a-a81c-69ac0392db5f'),('a7468cdf-248e-4121-9011-be34bc3c862d','9713563a-89cd-425a-a81c-69ac0392db5f'),('a0453a40-ba13-4c5a-916b-c3909a3111b4','9713563a-89cd-425a-a81c-69ac0392db5f'),('465ad517-b9e3-41dc-b46c-2a6db96e2e1c','9713563a-89cd-425a-a81c-69ac0392db5f'),('f0af1872-adfa-43e5-86a0-9847f07ac769','9713563a-89cd-425a-a81c-69ac0392db5f'),('4ffbd084-c14f-40b5-9c0f-80747057efab','9713563a-89cd-425a-a81c-69ac0392db5f'),('f37ae951-791a-47e1-9998-a344b94a417f','9713563a-89cd-425a-a81c-69ac0392db5f');
/*!40000 ALTER TABLE `flat_facility` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flat_type`
--

DROP TABLE IF EXISTS `flat_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flat_type` (
  `id` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flat_type`
--

LOCK TABLES `flat_type` WRITE;
/*!40000 ALTER TABLE `flat_type` DISABLE KEYS */;
INSERT INTO `flat_type` VALUES ('4d7fce2d-9aa2-4124-b180-70a535424ecc','Description 1','Flat type 1'),('6a329c3f-ae5e-4452-8e16-7b7536043a4e','Description 3','Flat type 3'),('9e759a4d-f192-4101-8ec9-4807ea35cae3','Description 0','Flat type 0'),('bae02975-2bdd-4b5f-b211-eb62a4da2174','Description 2','Flat type 2');
/*!40000 ALTER TABLE `flat_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `money_transfer`
--

DROP TABLE IF EXISTS `money_transfer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `money_transfer` (
  `id` varchar(255) NOT NULL,
  `amount` int NOT NULL,
  `date` date DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `wallet_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2k3ku2wag2mjn22ww7is2ufm9` (`wallet_id`),
  CONSTRAINT `FK2k3ku2wag2mjn22ww7is2ufm9` FOREIGN KEY (`wallet_id`) REFERENCES `wallet` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `money_transfer`
--

LOCK TABLES `money_transfer` WRITE;
/*!40000 ALTER TABLE `money_transfer` DISABLE KEYS */;
/*!40000 ALTER TABLE `money_transfer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refresh_token`
--

DROP TABLE IF EXISTS `refresh_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refresh_token` (
  `id` varchar(255) NOT NULL,
  `expiry_date` datetime NOT NULL,
  `login_authorities` varchar(255) DEFAULT NULL,
  `token` varchar(255) NOT NULL,
  `admin_id` varchar(255) DEFAULT NULL,
  `customer_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_r4k4edos30bx9neoq81mdvwph` (`token`),
  KEY `FKa05i46xnn0y4yvc55h2dh06n1` (`admin_id`),
  KEY `FKthlbvd34s3un1d4pxxqv2ni6c` (`customer_id`),
  CONSTRAINT `FKa05i46xnn0y4yvc55h2dh06n1` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`id`),
  CONSTRAINT `FKthlbvd34s3un1d4pxxqv2ni6c` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refresh_token`
--

LOCK TABLES `refresh_token` WRITE;
/*!40000 ALTER TABLE `refresh_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `refresh_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rent_contract`
--

DROP TABLE IF EXISTS `rent_contract`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rent_contract` (
  `id` varchar(255) NOT NULL,
  `contract` varchar(255) DEFAULT NULL,
  `expiry_date` datetime DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `value` int NOT NULL,
  `customer_id` varchar(255) DEFAULT NULL,
  `flat_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8s6ylg2rnjswlkpfxrkvc12k` (`customer_id`),
  KEY `FKl3lgtukycyujhrn86lnacc5w0` (`flat_id`),
  CONSTRAINT `FK8s6ylg2rnjswlkpfxrkvc12k` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `FKl3lgtukycyujhrn86lnacc5w0` FOREIGN KEY (`flat_id`) REFERENCES `flat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rent_contract`
--

LOCK TABLES `rent_contract` WRITE;
/*!40000 ALTER TABLE `rent_contract` DISABLE KEYS */;
INSERT INTO `rent_contract` VALUES ('02d7b954-3556-4f3e-87ae-04268b39e656','04328983-819b-4859-ae1e-6782440d0e19pdf','2024-07-12 00:00:00','2022-02-07 00:00:00','VALID','Hợp đồng tháng',27000000,'04f6588a-8567-408b-b434-0d7fd82495c2','465ad517-b9e3-41dc-b46c-2a6db96e2e1c'),('0acfdf72-5b35-455a-b41b-3026f1e7a95b','04328983-819b-4859-ae1e-6782440d0e19pdf','2025-01-23 00:00:00','2022-11-18 00:00:00','VALID','Hợp đồng tháng',30000000,'d68a9d9c-b2ee-4fa0-83ec-e810fa1afac2','37592f7a-3b4c-4fe4-bdbd-a11758664ac5'),('20ad926f-5131-43d7-af36-b5577b69b6d1','04328983-819b-4859-ae1e-6782440d0e19pdf','2023-05-11 00:00:00','2022-08-17 00:00:00','VALID','Hợp đồng tháng',27000000,'04f6588a-8567-408b-b434-0d7fd82495c2','fc8fa3b7-6683-494f-8430-0b18c196ee90'),('23665534-56a4-49e0-b8c7-6b34424a8e1a','04328983-819b-4859-ae1e-6782440d0e19pdf','2023-07-01 00:00:00','2022-10-01 00:00:00','VALID','Hợp đồng tháng',17000000,'d68a9d9c-b2ee-4fa0-83ec-e810fa1afac2','a7468cdf-248e-4121-9011-be34bc3c862d'),('85839b86-9a89-4d6a-b247-82f7c5ec72a3','04328983-819b-4859-ae1e-6782440d0e19pdf','2023-09-30 00:00:00','2021-04-08 00:00:00','VALID','Hợp đồng tháng',19000000,'1844eccb-7ae1-49c5-842a-f052aea84aed','5994bd7c-ce4f-4773-9a36-678650588bc9'),('9d895db1-e36e-42d5-a655-fdbbb781c610','04328983-819b-4859-ae1e-6782440d0e19pdf','2025-02-22 00:00:00','2022-04-12 00:00:00','VALID','Hợp đồng tháng',20000000,'6397dd86-b241-4d5c-87ff-eb5148980fdb','a6f156da-f5ab-490f-8c26-316d1403a39b'),('a2253db2-9ff2-4631-9e43-4631f03aa6c0','04328983-819b-4859-ae1e-6782440d0e19pdf','2023-05-24 00:00:00','2021-03-16 00:00:00','VALID','Hợp đồng tháng',9000000,'9f61bd09-688e-4215-918f-7353b996c8b0','7dfe53f6-545d-403d-a3d1-157cc0fd385c'),('b1a91a9b-9d03-436c-a6e7-2cde9c11ffe6','04328983-819b-4859-ae1e-6782440d0e19pdf','2025-12-03 00:00:00','2021-10-10 00:00:00','VALID','Hợp đồng tháng',26000000,'a11a45fe-06bd-4f6f-ba2d-cd7a2eebf5c8','fc0e0e80-fb0f-4ee5-8667-18ee5ffc583d'),('c9378f01-9ef5-432d-b040-1833654544d6','04328983-819b-4859-ae1e-6782440d0e19pdf','2024-07-12 00:00:00','2021-08-26 00:00:00','VALID','Hợp đồng tháng',19000000,'e479d7f8-0f83-41c7-b24f-e7a95c1fed0c','93411034-520c-4632-abf9-bf9984cadf1c'),('e0c4a94f-f96d-4836-a9d0-836fe1125dc0','04328983-819b-4859-ae1e-6782440d0e19pdf','2024-06-02 00:00:00','2020-12-23 00:00:00','VALID','Hợp đồng tháng',8000000,'1844eccb-7ae1-49c5-842a-f052aea84aed','e43a0c51-bef6-4219-bc29-dce2dae3f5aa'),('e8559104-4db9-44b0-a500-22ebdea95ddf','04328983-819b-4859-ae1e-6782440d0e19pdf','2024-12-08 00:00:00','2022-12-11 00:00:00','VALID','Hợp đồng tháng',7000000,'246faa9c-285b-4a2c-80fe-3ba28b163588','a0453a40-ba13-4c5a-916b-c3909a3111b4');
/*!40000 ALTER TABLE `rent_contract` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service` (
  `id` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` int NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service`
--

LOCK TABLES `service` WRITE;
/*!40000 ALTER TABLE `service` DISABLE KEYS */;
INSERT INTO `service` VALUES ('046b67ea-a216-4a6b-821b-e78f5a9e83ee','Description 2','https://cdn-icons-png.flaticon.com/512/1086/1086470.png','Maintenance',300000,'ACTIVE'),('4ee0f880-34cf-4c99-b602-2ae01416096a','Description 7','https://cdn-icons-png.flaticon.com/512/3003/3003984.png','Laundry services',80000,'ACTIVE'),('57b36703-287c-48d5-9d04-0a2debd48c53','Description 1','https://cdn-icons-png.flaticon.com/512/2142/2142611.png','Animal services',200000,'ACTIVE'),('5aff5922-69bd-46dd-a84b-427ae9c24d41','Description 3','https://cdn-icons-png.flaticon.com/512/1023/1023346.png','Parking',150000,'ACTIVE'),('74d6b423-8afc-40f1-8efb-475ab90880a8','Description 9','https://cdn-icons-png.flaticon.com/512/7866/7866488.png','Electricity',3000,'ACTIVE'),('8d51ca04-eec4-4578-bcbe-21254d2f9051','Description 4','https://cdn-icons-png.flaticon.com/512/3125/3125596.png','Plumbing services',250000,'ACTIVE'),('8fd0981e-c64f-4b4f-b5e1-63afe1841df0','Description 8','https://cdn-icons-png.flaticon.com/512/4099/4099249.png','HVAC',350000,'ACTIVE'),('9f18d555-9fa9-42e8-92e1-d23762b21b61','Description 10','https://cdn-icons-png.flaticon.com/512/1558/1558896.png','Water',8000,'ACTIVE'),('b4c93ebe-1048-4556-b552-a3f3990c06fe','Description 6','https://cdn-icons-png.flaticon.com/512/2737/2737062.png','Cleaning services',100000,'ACTIVE'),('d1a64dbe-589c-4298-a537-f9f695b60c4c','Description 5','https://cdn-icons-png.flaticon.com/512/2175/2175443.png','Electrical services',345000,'ACTIVE');
/*!40000 ALTER TABLE `service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction` (
  `id` varchar(255) NOT NULL,
  `amount` int NOT NULL,
  `date` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `bill_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7qk27itww9dh5gq4fs3g6bxot` (`bill_id`),
  CONSTRAINT `FK7qk27itww9dh5gq4fs3g6bxot` FOREIGN KEY (`bill_id`) REFERENCES `bill` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wallet`
--

DROP TABLE IF EXISTS `wallet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wallet` (
  `id` varchar(255) NOT NULL,
  `amount` int NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `customer_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpb5ltxtks766lq2b9hgvnr2bq` (`customer_id`),
  CONSTRAINT `FKpb5ltxtks766lq2b9hgvnr2bq` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wallet`
--

LOCK TABLES `wallet` WRITE;
/*!40000 ALTER TABLE `wallet` DISABLE KEYS */;
INSERT INTO `wallet` VALUES ('abcadb60-4d9b-4bb0-b2d8-13122ae584e7',0,'ACTIVE','d0dabea3-bb7f-4cb5-9e2b-3fca7a4841f8');
/*!40000 ALTER TABLE `wallet` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-03-24 12:55:28
