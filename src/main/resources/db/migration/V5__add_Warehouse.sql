CREATE TABLE Warehouse (
    ID int NOT NULL AUTO_INCREMENT,
    name varchar(255),
    adress varchar(255),
    CONSTRAINT PK_Warehouse PRIMARY KEY (ID)
);

CREATE TABLE Warehouse_Product (
    productID int NOT NULL,
    warehouseID int NOT NULL,
    CONSTRAINT FK_Warehouse_Product FOREIGN KEY (warehouseID) REFERENCES Warehouse(ID),
    CONSTRAINT FK_Product_Warehouse FOREIGN KEY (productID) REFERENCES Product(ID),
    CONSTRAINT UC_Warehouse_Product UNIQUE (productID, warehouseID)
);
