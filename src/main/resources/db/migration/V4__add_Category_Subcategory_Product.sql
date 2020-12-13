CREATE TABLE Category (
    ID int NOT NULL AUTO_INCREMENT,
    name varchar(255),
    CONSTRAINT PK_Category PRIMARY KEY (ID)
);

CREATE TABLE Subcategory (
    ID int NOT NULL AUTO_INCREMENT,
    name varchar(255),
    categoryID int NOT NULL,
    CONSTRAINT PK_Subcategory PRIMARY KEY (ID),
    CONSTRAINT FK_Subcategory_Category FOREIGN KEY (categoryID) REFERENCES Category(ID)
);

CREATE TABLE Product (
    ID int NOT NULL AUTO_INCREMENT,
    name varchar(255),
    price int,
    numberAvailable int,
    description varchar(255),
    salePrice int,
    imageSrc varchar(255),
    taxCategoryID int NOT NULL,
    CONSTRAINT PK_Product PRIMARY KEY (ID),
    CONSTRAINT FK_Product_TaxCategory FOREIGN KEY (taxCategoryID) REFERENCES TaxCategory(ID)
);

CREATE TABLE Subcategory_Product (
    productID int NOT NULL,
    subcategoryID int NOT NULL,
    CONSTRAINT FK_Subcategory_Product FOREIGN KEY (subcategoryID) REFERENCES Subcategory(ID),
    CONSTRAINT FK_Product_Subcategory FOREIGN KEY (productID) REFERENCES Product(ID),
    CONSTRAINT UC_Subcategory_Product UNIQUE (productID, subcategoryID)
);


CREATE TABLE Category_Product (
    productID int NOT NULL,
    categoryID int NOT NULL,
    CONSTRAINT FK_Category_Product FOREIGN KEY (categoryID) REFERENCES Category(ID),
    CONSTRAINT FK_Product_Category FOREIGN KEY (productID) REFERENCES Product(ID),
    CONSTRAINT UC_Category_Product UNIQUE (productID, categoryID)
);


