CREATE TABLE Roles (
    ID int NOT NULL AUTO_INCREMENT,
    Name varchar(255),
    CONSTRAINT PK_Roles PRIMARY KEY (ID)
);

INSERT INTO Roles (Name) VALUES ("Worker");
INSERT INTO Roles (Name) VALUES ("Client");
INSERT INTO Roles (Name) VALUES ("Admin");