CREATE DATABASE almoxarifado_db;
USE almoxarifado_db;

CREATE TABLE estoque (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    quantidade INT NOT NULL,
    marca VARCHAR(100),
    fornecedor VARCHAR(255),
    localizacao VARCHAR(255),
    codigo VARCHAR(50),
    preco_de_custo DECIMAL(10, 2),
    categoria VARCHAR(100)
);
