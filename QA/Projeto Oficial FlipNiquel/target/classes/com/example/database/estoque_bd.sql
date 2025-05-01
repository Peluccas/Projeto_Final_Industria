
CREATE DATABASE ControleQualidade;
USE ControleQualidade;



CREATE TABLE tableDash (
    id INT AUTO_INCREMENT PRIMARY KEY,  
    total INT NOT NULL,                 
    aprovados INT NOT NULL,              
    reprovados INT NOT NULL,             
    concertos INT NOT NULL               
);

CREATE TABLE barChart (
    id INT AUTO_INCREMENT PRIMARY KEY,
    mes VARCHAR(20) NOT NULL,
    quantidade INT NOT NULL
);

CREATE TABLE barChart1 (
    id INT AUTO_INCREMENT PRIMARY KEY,
    mes VARCHAR(20) NOT NULL,
    quantidade INT NOT NULL
);

CREATE TABLE lineChart (
    dia VARCHAR(10),
    quantidade INT
);

CREATE TABLE tableRevisao (
    id INT AUTO_INCREMENT PRIMARY KEY,  
    selo VARCHAR (10)NOT NULL,                  
    descricao VARCHAR (100) NOT NULL,              
    status VARCHAR (10) NOT NULL,             
    produtos VARCHAR (20) NOT NULL,
    lote INT NOT NULL,
    chegada DATE NOT NULL,
    saida DATE NOT NULL 
);

CREATE TABLE tableConferidos (
    id INT AUTO_INCREMENT PRIMARY KEY,  
    selo VARCHAR (10)NOT NULL,                  
    descricao VARCHAR (100) NOT NULL,              
    status VARCHAR (10) NOT NULL,             
    produtos VARCHAR (20) NOT NULL,
    lote INT NOT NULL,
    chegada DATE NOT NULL,
    saida DATE NOT NULL 
);

CREATE TABLE tableRecusados (
    id INT AUTO_INCREMENT PRIMARY KEY, 
    selo VARCHAR (12) NOT NULL,                   
    descricao VARCHAR (100) NOT NULL,              
    status VARCHAR (10) NOT NULL,             
    produtos VARCHAR (20) NOT NULL,
    lote INT NOT NULL,
    chegada DATE NOT NULL,
    saida DATE NOT NULL 
);

CREATE TABLE tableProducao (
    id INT AUTO_INCREMENT PRIMARY KEY,                    
    descricao VARCHAR (100) NOT NULL,              
    status VARCHAR (10) NOT NULL,             
    produtos VARCHAR (20) NOT NULL,
    lote INT NOT NULL,
    chegada DATE NOT NULL
);


INSERT INTO tableDash (id, total, aprovados, reprovados, concertos) VALUES (0, 0, 0, 0, 0);

INSERT INTO barChart (mes, quantidade) VALUES('Jan', 2),('Feb', 3),('Mar', 1),('Apr', 0),('Mai', 3),('Jun', 4);

INSERT INTO barChart1 (mes, quantidade) VALUES('Jan', 1),('Feb', 2),('Mar', 1),('Apr', 0),('Mai', 3),('Jun', 1);
