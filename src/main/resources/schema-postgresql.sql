
--
-- Database: `scrum`
--

-- --------------------------------------------------------

--
-- Table structure for table `UserConnection`
--

CREATE TABLE IF NOT EXISTS UserConnection (
  userId varchar(255) NOT NULL,
  providerId varchar(255) NOT NULL,
  providerUserId varchar(255) NOT NULL DEFAULT '',
  rank int NOT NULL,
  displayName varchar(255) DEFAULT NULL,
  profileUrl varchar(512) DEFAULT NULL,
  imageUrl varchar(512) DEFAULT NULL,
  accessToken varchar(1024) NOT NULL,
  secret varchar(255) DEFAULT NULL,
  refreshToken varchar(255) DEFAULT NULL,
  expireTime bigint DEFAULT NULL,
  PRIMARY KEY (userId,providerId,providerUserId),
  CONSTRAINT UserConnectionRank UNIQUE (userId,providerId,rank)
) ;
