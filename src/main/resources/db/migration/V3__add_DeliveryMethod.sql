CREATE TABLE DeliveryMethod (
    ID int NOT NULL AUTO_INCREMENT,
    Name varchar(255),
    Price int,
    FreeThreshold int,
    CONSTRAINT PK_Roles PRIMARY KEY (ID)
);