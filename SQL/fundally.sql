-- MySQL dump 10.13  Distrib 5.7.22, for Linux (x86_64)
--
-- Host: localhost    Database: fundally
-- ------------------------------------------------------
-- Server version	5.7.22-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `InstituteAccount`
--

DROP TABLE IF EXISTS `InstituteAccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `InstituteAccount` (
  `AccountNo` varchar(200) NOT NULL,
  `UserId` varchar(200) NOT NULL,
  `AccountTypeId` varchar(200) NOT NULL,
  `Balance` float(11,2) NOT NULL DEFAULT '0.00',
  `OpeningDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ClosingDate` datetime DEFAULT NULL,
  `Status` enum('ACTIVE','INACTIVE') NOT NULL DEFAULT 'ACTIVE',
  PRIMARY KEY (`AccountNo`),
  UNIQUE KEY `UserId` (`UserId`,`AccountTypeId`),
  KEY `fk_11` (`AccountTypeId`),
  CONSTRAINT `fk_10` FOREIGN KEY (`UserId`) REFERENCES `User` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_11` FOREIGN KEY (`AccountTypeId`) REFERENCES `InstituteAccountType` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `InstituteAccount`
--

--
-- Table structure for table `InstituteAccountType`
--

DROP TABLE IF EXISTS `InstituteAccountType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `InstituteAccountType` (
  `Id` varchar(20) NOT NULL,
  `Name` varchar(200) NOT NULL,
  `Description` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `InstituteAccountType`
--

--
-- Table structure for table `InstituteQuotation`
--

DROP TABLE IF EXISTS `InstituteQuotation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `InstituteQuotation` (
  `Id` varchar(200) NOT NULL,
  `RequestId` varchar(200) NOT NULL,
  `Path` varchar(2000) NOT NULL,
  `Date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Status` enum('PENDING','APPROVED','DENIED') NOT NULL DEFAULT 'PENDING',
  PRIMARY KEY (`Id`),
  KEY `RequestId` (`RequestId`),
  CONSTRAINT `fk_14` FOREIGN KEY (`RequestId`) REFERENCES `InstituteRequest` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `InstituteQuotation`
--

--
-- Table structure for table `InstituteRequest`
--

DROP TABLE IF EXISTS `InstituteRequest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `InstituteRequest` (
  `Id` varchar(200) NOT NULL,
  `ResourceId` varchar(200) NOT NULL,
  `AccountNo` varchar(200) NOT NULL,
  `Description` varchar(2000) NOT NULL,
  `StartDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `EndDate` datetime DEFAULT NULL,
  `StageId` varchar(20) NOT NULL,
  `Status` enum('APPROVED','DENIED','PENDING','FINISHED') NOT NULL DEFAULT 'PENDING',
  PRIMARY KEY (`Id`),
  KEY `AccountNo` (`AccountNo`),
  KEY `StageId` (`StageId`),
  KEY `ResourceId` (`ResourceId`),
  CONSTRAINT `fk_2` FOREIGN KEY (`ResourceId`) REFERENCES `InstituteResource` (`Id`),
  CONSTRAINT `fk_3` FOREIGN KEY (`AccountNo`) REFERENCES `InstituteAccount` (`AccountNo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_4` FOREIGN KEY (`StageId`) REFERENCES `InstituteRequestStageType` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `InstituteRequest`
--


--
-- Table structure for table `InstituteRequestStage`
--

DROP TABLE IF EXISTS `InstituteRequestStage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `InstituteRequestStage` (
  `RequestId` varchar(200) NOT NULL,
  `StageId` varchar(20) NOT NULL,
  `Date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY `RequestId` (`RequestId`,`StageId`),
  KEY `fk_8` (`StageId`),
  CONSTRAINT `fk_7` FOREIGN KEY (`RequestId`) REFERENCES `InstituteRequest` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_8` FOREIGN KEY (`StageId`) REFERENCES `InstituteRequestStageType` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `InstituteRequestStage`
--


--
-- Table structure for table `InstituteRequestStageType`
--

DROP TABLE IF EXISTS `InstituteRequestStageType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `InstituteRequestStageType` (
  `Id` varchar(20) NOT NULL,
  `Name` varchar(200) NOT NULL,
  `Description` varchar(2000) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `InstituteRequestStageType`
--

--
-- Table structure for table `InstituteRequestTransactionLink`
--

DROP TABLE IF EXISTS `InstituteRequestTransactionLink`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `InstituteRequestTransactionLink` (
  `RequestId` varchar(200) NOT NULL,
  `TransactionId` varchar(200) NOT NULL,
  UNIQUE KEY `RequestId` (`RequestId`),
  UNIQUE KEY `TransactionId` (`TransactionId`),
  CONSTRAINT `fk_5` FOREIGN KEY (`RequestId`) REFERENCES `InstituteRequest` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_6` FOREIGN KEY (`TransactionId`) REFERENCES `InstituteTransaction` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `InstituteRequestTransactionLink`
--


--
-- Table structure for table `InstituteResource`
--

DROP TABLE IF EXISTS `InstituteResource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `InstituteResource` (
  `Id` varchar(200) NOT NULL,
  `Name` varchar(200) NOT NULL,
  `Description` varchar(2000) NOT NULL,
  `Type` enum('TANGIBLE','INTANGIBLE') NOT NULL,
  `Amount` float(11,2) NOT NULL,
  `DeadlineDate` date NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `InstituteResource`
--

--
-- Table structure for table `InstituteResourceDocument`
--

DROP TABLE IF EXISTS `InstituteResourceDocument`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `InstituteResourceDocument` (
  `Id` varchar(200) NOT NULL,
  `ResourceId` varchar(200) NOT NULL,
  `Date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Description` varchar(2000) DEFAULT NULL,
  `Path` varchar(2000) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `ResourceId` (`ResourceId`) USING BTREE,
  CONSTRAINT `fk_13` FOREIGN KEY (`ResourceId`) REFERENCES `InstituteResource` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `InstituteResourceDocument`
--

--
-- Table structure for table `InstituteTransaction`
--

DROP TABLE IF EXISTS `InstituteTransaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `InstituteTransaction` (
  `Id` varchar(200) NOT NULL,
  `AccountNo` varchar(200) NOT NULL,
  `OpeningBalance` float(11,2) NOT NULL DEFAULT '0.00',
  `ClosingBalance` float(11,2) NOT NULL DEFAULT '0.00',
  `Type` enum('DEBIT','CREDIT','REFUND') NOT NULL,
  `Date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`Id`),
  KEY `fk_12` (`AccountNo`),
  CONSTRAINT `fk_12` FOREIGN KEY (`AccountNo`) REFERENCES `InstituteAccount` (`AccountNo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `InstituteTransaction`
--


--
-- Table structure for table `Project`
--

DROP TABLE IF EXISTS `Project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Project` (
  `Id` varchar(200) NOT NULL,
  `Name` varchar(2000) NOT NULL,
  `SanctionedAmount` float(11,2) NOT NULL,
  `UserId` varchar(200) NOT NULL,
  `OpeningDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ClosingDate` date NOT NULL,
  `Description` varchar(2000) NOT NULL,
  `Status` enum('ACTIVE','INACTIVE') NOT NULL DEFAULT 'ACTIVE',
  PRIMARY KEY (`Id`),
  KEY `UserId` (`UserId`),
  CONSTRAINT `fk_9` FOREIGN KEY (`UserId`) REFERENCES `User` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Project`
--

--
-- Table structure for table `ProjectAccount`
--

DROP TABLE IF EXISTS `ProjectAccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProjectAccount` (
  `AccountNo` varchar(200) NOT NULL,
  `ProjectId` varchar(200) NOT NULL,
  `AccountTypeId` varchar(20) NOT NULL,
  `Balance` float(11,2) NOT NULL DEFAULT '0.00',
  `OpeningDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ClosingDate` datetime DEFAULT NULL,
  `Status` enum('ACTIVE','INACTIVE') NOT NULL DEFAULT 'ACTIVE',
  PRIMARY KEY (`AccountNo`),
  UNIQUE KEY `ProjectId` (`ProjectId`,`AccountTypeId`),
  KEY `fk_24` (`AccountTypeId`),
  CONSTRAINT `fk_23` FOREIGN KEY (`ProjectId`) REFERENCES `Project` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_24` FOREIGN KEY (`AccountTypeId`) REFERENCES `ProjectAccountType` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProjectAccount`
--


--
-- Table structure for table `ProjectAccountType`
--

DROP TABLE IF EXISTS `ProjectAccountType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProjectAccountType` (
  `Id` varchar(20) NOT NULL,
  `Name` varchar(200) NOT NULL,
  `Description` varchar(2000) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProjectAccountType`
--


--
-- Table structure for table `ProjectFacultyLink`
--

DROP TABLE IF EXISTS `ProjectFacultyLink`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProjectFacultyLink` (
  `ProjectId` varchar(200) NOT NULL,
  `UserId` varchar(200) NOT NULL,
  UNIQUE KEY `ProjectId_2` (`ProjectId`,`UserId`),
  KEY `ProjectId` (`ProjectId`),
  KEY `UserId` (`UserId`) USING BTREE,
  CONSTRAINT `fk_28` FOREIGN KEY (`ProjectId`) REFERENCES `Project` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_29` FOREIGN KEY (`UserId`) REFERENCES `User` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProjectFacultyLink`
--
--
-- Table structure for table `ProjectQuotation`
--

DROP TABLE IF EXISTS `ProjectQuotation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProjectQuotation` (
  `Id` varchar(200) NOT NULL,
  `RequestId` varchar(200) NOT NULL,
  `Path` varchar(2000) NOT NULL,
  `Date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Status` enum('PENDING','APPROVED','DENIED') NOT NULL DEFAULT 'PENDING',
  PRIMARY KEY (`Id`),
  KEY `RequestId` (`RequestId`),
  CONSTRAINT `fk_27` FOREIGN KEY (`RequestId`) REFERENCES `ProjectRequest` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProjectQuotation`
--
--
-- Table structure for table `ProjectRequest`
--

DROP TABLE IF EXISTS `ProjectRequest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProjectRequest` (
  `Id` varchar(200) NOT NULL,
  `ResourceId` varchar(200) NOT NULL,
  `UserId` varchar(200) NOT NULL,
  `AccountNo` varchar(200) NOT NULL,
  `Description` varchar(2000) NOT NULL,
  `StartDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `EndDate` datetime DEFAULT CURRENT_TIMESTAMP,
  `StageId` varchar(20) NOT NULL DEFAULT '0',
  `Status` enum('APPROVED','DENIED','PENDING','FINISHED') NOT NULL DEFAULT 'PENDING',
  PRIMARY KEY (`Id`),
  KEY `ResourceId` (`ResourceId`),
  KEY `UserId` (`UserId`),
  KEY `AccountNo` (`AccountNo`),
  KEY `StageId` (`StageId`),
  CONSTRAINT `fk_15` FOREIGN KEY (`ResourceId`) REFERENCES `ProjectResource` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_16` FOREIGN KEY (`UserId`) REFERENCES `User` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_17` FOREIGN KEY (`AccountNo`) REFERENCES `ProjectAccount` (`AccountNo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_18` FOREIGN KEY (`StageId`) REFERENCES `InstituteRequestStageType` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProjectRequest`
--

--
-- Table structure for table `ProjectRequestStage`
--

DROP TABLE IF EXISTS `ProjectRequestStage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProjectRequestStage` (
  `RequestId` varchar(200) NOT NULL,
  `StageId` varchar(20) NOT NULL,
  `Date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY `RequestId` (`RequestId`,`StageId`),
  KEY `fk_22` (`StageId`),
  CONSTRAINT `fk_21` FOREIGN KEY (`RequestId`) REFERENCES `ProjectRequest` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_22` FOREIGN KEY (`StageId`) REFERENCES `InstituteRequestStageType` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProjectRequestStage`
--
--
-- Table structure for table `ProjectRequestTransactionLink`
--

DROP TABLE IF EXISTS `ProjectRequestTransactionLink`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProjectRequestTransactionLink` (
  `RequestId` varchar(200) NOT NULL,
  `TransactionId` varchar(200) NOT NULL,
  UNIQUE KEY `RequestId` (`RequestId`),
  UNIQUE KEY `TransactionId` (`TransactionId`),
  CONSTRAINT `fk_19` FOREIGN KEY (`RequestId`) REFERENCES `ProjectRequest` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_20` FOREIGN KEY (`TransactionId`) REFERENCES `ProjectTransaction` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProjectRequestTransactionLink`
--
--
-- Table structure for table `ProjectResource`
--

DROP TABLE IF EXISTS `ProjectResource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProjectResource` (
  `Id` varchar(200) NOT NULL,
  `Name` varchar(200) NOT NULL,
  `Description` varchar(2000) DEFAULT NULL,
  `Type` enum('TANGIBLE','INTANGIBLE') NOT NULL,
  `Amount` float(11,2) NOT NULL DEFAULT '0.00',
  `DeadlineDate` date NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProjectResource`
--
--
-- Table structure for table `ProjectResourceDocument`
--

DROP TABLE IF EXISTS `ProjectResourceDocument`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProjectResourceDocument` (
  `Id` varchar(200) NOT NULL,
  `ResourceId` varchar(200) NOT NULL,
  `Date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Description` varchar(2000) DEFAULT NULL,
  `Path` varchar(2000) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `ResourceId` (`ResourceId`),
  CONSTRAINT `fk_26` FOREIGN KEY (`ResourceId`) REFERENCES `ProjectResource` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProjectResourceDocument`
--
--
-- Table structure for table `ProjectTransaction`
--

DROP TABLE IF EXISTS `ProjectTransaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProjectTransaction` (
  `Id` varchar(200) NOT NULL,
  `AccountNo` varchar(200) NOT NULL,
  `OpeningBalance` float(11,2) NOT NULL DEFAULT '0.00',
  `ClosingBalance` float(11,2) NOT NULL DEFAULT '0.00',
  `Date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Comment` varchar(2000) DEFAULT NULL,
  `Type` enum('CREDIT','DEBIT','REFUND') NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `AccountNo` (`AccountNo`),
  CONSTRAINT `fk_25` FOREIGN KEY (`AccountNo`) REFERENCES `ProjectAccount` (`AccountNo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProjectTransaction`
--

--
-- Table structure for table `Session`
--

DROP TABLE IF EXISTS `Session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Session` (
  `Id` varchar(200) NOT NULL,
  `UserId` varchar(200) NOT NULL,
  `Time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Status` enum('ACTIVE','INACTIVE') NOT NULL DEFAULT 'ACTIVE',
  PRIMARY KEY (`Id`),
  KEY `UserId` (`UserId`),
  CONSTRAINT `fk_1` FOREIGN KEY (`UserId`) REFERENCES `User` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Session`
--

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `Id` varchar(200) NOT NULL,
  `FirstName` varchar(100) NOT NULL,
  `LastName` varchar(100) DEFAULT NULL,
  `EmailId` varchar(250) NOT NULL,
  `Password` varchar(15) NOT NULL,
  `Department` enum('CSE','ECE','NA') NOT NULL,
  `Type` enum('admin','chiefadmin','faculty','finance','procurement') NOT NULL DEFAULT 'faculty',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-06-24 22:49:18
