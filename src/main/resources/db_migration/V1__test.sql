CREATE TABLE board.user (
 id VARCHAR(100) NOT NULL PRIMARY KEY,
 password VARCHAR(100) NOT NULL,
 email VARCHAR(100) NOT NULL )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;