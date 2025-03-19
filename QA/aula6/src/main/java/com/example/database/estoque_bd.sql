CREATE DATABASE ControleQualidade;
USE ControleQualidade;

CREATE TABLE Produto (
    produto_id INT AUTO_INCREMENT PRIMARY KEY,
    produto_nome VARCHAR(255) NOT NULL,
    produto_quantidade INT,
    produto_preco DOUBLE,
    produto_localizacao VARCHAR(100),
    produto_categoria VARCHAR (100) NOT NULL
);

CREATE TABLE Producao (
    producao_id INT AUTO_INCREMENT PRIMARY KEY,
    producao_nome VARCHAR(255) NOT NULL,
    producao_quantidade INT,
    producao_preco DOUBLE,
    producao_localizacao VARCHAR(100),
    producao_categoria VARCHAR (100) NOT NULL,
    data_criacao DATE
);

CREATE TABLE Peca (
    peca_id INT AUTO_INCREMENT PRIMARY KEY,
    peca_nome VARCHAR(255) NOT NULL,
    peca_quantidade INT,
    peca_preco DOUBLE,
    peca_localizacao VARCHAR(100),
    peca_categoria VARCHAR (100) NOT NULL
    
);

CREATE TABLE Automocao (
    automocao_id INT AUTO_INCREMENT PRIMARY KEY,
    automocao_nome VARCHAR(255) NOT NULL,
    automocao_quantidade INT,
    automocao_preco DOUBLE,
    automocao_localizacao VARCHAR(100),
    automocao_categoria VARCHAR (100) NOT NULL   

);

CREATE TABLE Funcionarios(
    funcionarios_id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    matricula VARCHAR(30) NOT NULL,
    cargo VARCHAR(100) NOT NULL,
    salario VARCHAR(100) NOT NULL,
    setor VARCHAR(100) NOT NULL
);
