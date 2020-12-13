CREATE TABLE Supplier (
    ID int NOT NULL AUTO_INCREMENT,
    name varchar(255),
    phone int,
    CONSTRAINT PK_Supplier PRIMARY KEY (ID)
);

CREATE TABLE Supplier_Product (
    productID int NOT NULL,
    supplierID int NOT NULL,
    CONSTRAINT FK_Supplier_Product FOREIGN KEY (supplierID) REFERENCES Supplier(ID),
    CONSTRAINT FK_Product_Supplier FOREIGN KEY (productID) REFERENCES Product(ID),
    CONSTRAINT UC_Supplier_Product UNIQUE (productID, supplierID)
);
