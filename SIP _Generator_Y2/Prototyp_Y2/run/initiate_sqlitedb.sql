DROP TABLE IF EXISTS `rdf`;

CREATE TABLE `rdf` (
  `paketuid` varchar(255)  NOT NULL,
  `dcCreatorDigital` varchar(255)  DEFAULT NULL,
  `dcTitleDigital` varchar(255)  DEFAULT NULL,
  `dcDateDigital` varchar(512)  DEFAULT NULL,
  `dcTitlePhysical` varchar(255)  DEFAULT NULL,
  `dcCoverageLocation` varchar(512)  DEFAULT NULL,
  `dcCoveragelatitude` varchar(512)  DEFAULT NULL,
  `dcCoveragelongitude` varchar(255)  DEFAULT NULL,
  `dcIdentifierPhysical` varchar(512)  DEFAULT NULL,
  `dcFormatDigital` varchar(255)  DEFAULT NULL,
  `dcTypeDigital` varchar(255)  DEFAULT NULL,
  `dcDescriptionDigital` varchar(255)  DEFAULT NULL,
  `streetAddress` varchar(512)  DEFAULT NULL,
  `postalCodeStart` varchar(512)  DEFAULT NULL,
  `postalCodeEnd` varchar(512)  DEFAULT NULL,
  `postOfficeBoxNumber` varchar(512)  DEFAULT NULL,
  `addressRegion` varchar(512)  DEFAULT NULL,
  `addressCountry` varchar(255)  DEFAULT NULL,
  `addressLocality` varchar(512)  DEFAULT NULL,
  `isPartOf` varchar(255)  DEFAULT NULL,
  `hasPart` varchar(512)  DEFAULT NULL,
  `dcRightsDigital` varchar(255)  DEFAULT NULL,
  `dcCreatorPhysical` varchar(512)  DEFAULT NULL,
  `dcContributorPhysical` varchar(255)  DEFAULT NULL,
  `dcCompletionDate` varchar(512)  DEFAULT NULL,
  `dcStartDate` varchar(512)  DEFAULT NULL,
  `dcConstructionTime` varchar(512)  DEFAULT NULL,
  `dcRebuildingDate` varchar(512)  DEFAULT NULL,
  `dcRightsPhysical` varchar(255)  DEFAULT NULL,
  `dcDescriptionPhysical` varchar(45) DEFAULT NULL,
  `dcIdentifierDigital` varchar(45) DEFAULT NULL,
  `hasFormatDetails` varchar(255)  DEFAULT NULL,
  `provenance` varchar(512)  DEFAULT NULL,
  `unitCode` varchar(255)  DEFAULT NULL,
  `event` varchar(512)  DEFAULT NULL,
  `owner` varchar(512)  DEFAULT NULL,
  `modificationDetails` varchar(512)  DEFAULT NULL,
  `buildingArea` varchar(255)  DEFAULT NULL,
  `function` varchar(255)  DEFAULT NULL,
  `architecturalStyle` varchar(512)  DEFAULT NULL,
  `cost` varchar(512)  DEFAULT NULL,
  `levelOfDetail` varchar(255)  DEFAULT NULL,
  `floorCount` varchar(512)  DEFAULT NULL,
  `numberOfRooms` varchar(255)  DEFAULT NULL,
  `e57m` longtext   DEFAULT NULL,
  `ifcm` longtext   DEFAULT NULL,

PRIMARY KEY (`paketuid`)
);

INSERT INTO `rdf` (`paketuid`) VALUES ('Rosetta-SIP');

DROP TABLE IF EXISTS `droidinfo`;
CREATE TABLE  `droidinfo` (
  `paketuid` varchar(255) DEFAULT NULL,
  `filepath` varchar(1024) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `name` varchar(512) DEFAULT NULL,
  `version` varchar(255) DEFAULT NULL,
  `puid` varchar(50) DEFAULT NULL,
  `mimevalue` varchar(512) DEFAULT NULL,
  `idwarning` varchar(255) DEFAULT NULL
);

DROP TABLE IF EXISTS `mets`;
CREATE TABLE  `mets` (
  `paketuid` varchar(255) DEFAULT NULL,
  `filuid` varchar(255) DEFAULT NULL,
  `filnamn` varchar(512) DEFAULT NULL,
  `fildatum` varchar(255) DEFAULT NULL,
  `mime` varchar(255) DEFAULT NULL,
  `version` varchar(255) DEFAULT NULL,
  `filbyte` varchar(255) DEFAULT NULL,
  `hashsum` varchar(255) DEFAULT NULL,
  `hashsumtype` varchar(255) DEFAULT NULL,
  `path` varchar(1024) DEFAULT NULL
);




