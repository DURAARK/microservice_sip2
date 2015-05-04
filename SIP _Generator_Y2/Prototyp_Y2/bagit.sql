CREATE TABLE `bagit` (
  `paketuid` varchar(255) CHARACTER SET latin1 NOT NULL,
  `dcCreatorDigital` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `dcTitleDigital` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `dcDateDigital` varchar(512) CHARACTER SET latin1 DEFAULT NULL,
  `dcTitlePhysical` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `dcCoverageLocation` varchar(512) CHARACTER SET latin1 DEFAULT NULL,
  `dcCoveragelatitude` varchar(512) CHARACTER SET latin1 DEFAULT NULL,
  `dcCoveragelongitude` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `dcIdentifierPhysical` varchar(512) CHARACTER SET latin1 DEFAULT NULL,
  `dcFormatDigital` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `dcTypeDigital` varchar(255) CHARACTER SET latin1 NOT NULL,
  `dcDescriptionDigital` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `streetAddress` varchar(512) CHARACTER SET latin1 DEFAULT NULL,
  `postalCodeStart` varchar(512) CHARACTER SET latin1 DEFAULT NULL,
  `postalCodeEnd` varchar(512) CHARACTER SET latin1 DEFAULT NULL,
  `postOfficeBoxNumber` varchar(512) CHARACTER SET latin1 DEFAULT NULL,
  `addressRegion` varchar(512) CHARACTER SET latin1 DEFAULT NULL,
  `addressCountry` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `addressLocality` varchar(512) CHARACTER SET latin1 DEFAULT NULL,
  `isPartOf` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `hasPart` varchar(512) CHARACTER SET latin1 DEFAULT NULL,
  `dcRightsDigital` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `dcCreatorPhysical` varchar(512) CHARACTER SET latin1 DEFAULT NULL,
  `dcContributorPhysical` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `dcCompletionDate` varchar(512) CHARACTER SET latin1 DEFAULT NULL,
  `dcStartDate` varchar(512) CHARACTER SET latin1 DEFAULT NULL,
  `dcConstructionTime` varchar(512) CHARACTER SET latin1 DEFAULT NULL,
  `dcRebuildingDate` varchar(512) CHARACTER SET latin1 DEFAULT NULL,
  `dcRightsPhysical` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `dcDescriptionPhysical` varchar(45) COLLATE utf8_swedish_ci DEFAULT NULL,
  `dcIdentifierDigital` varchar(45) COLLATE utf8_swedish_ci DEFAULT NULL,
  `hasFormatDetails` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `provenance` varchar(512) CHARACTER SET latin1 DEFAULT NULL,
  `unitCode` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `event` varchar(512) CHARACTER SET latin1 DEFAULT NULL,
  `owner` varchar(512) CHARACTER SET latin1 DEFAULT NULL,
  `modificationDetails` varchar(512) CHARACTER SET latin1 DEFAULT NULL,
  `buildingArea` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `function` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `architecturalStyle` varchar(512) CHARACTER SET latin1 DEFAULT NULL,
  `cost` varchar(512) CHARACTER SET latin1 DEFAULT NULL,
  `levelOfDetail` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `floorCount` varchar(512) CHARACTER SET latin1 DEFAULT NULL,
  `numberOfRooms` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `e57m` longtext  CHARACTER SET latin1 DEFAULT NULL,
  `ifcm` longtext  CHARACTER SET latin1 DEFAULT NULL,

  PRIMARY KEY (`paketuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;

INSERT INTO `sip_db`.`bagit` (`paketuid`) VALUES ('BagIT-SIP');