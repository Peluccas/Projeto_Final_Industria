CREATE DATABASE Industria_db;
USE Industria_db;

CREATE TABLE produtos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    quantidade INT NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    localizacao VARCHAR(100) NOT NULL,
    categoria VARCHAR(100) NOT NULL
);

CREATE TABLE funcionarios(
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    matricula VARCHAR(30) NOT NULL,
    cargo VARCHAR(100) NOT NULL,
    salario VARCHAR(100) NOT NULL,
    setor VARCHAR(100) NOT NULL
);

CREATE TABLE usuarioRH(
    id SERIAL PRIMARY KEY NOT NULL,
    usuario VARCHAR(100) NOT NULL,
    senha VARCHAR(100) NOT NULL
);

CREATE TABLE usuarioFinanciero(
    id SERIAL PRIMARY KEY NOT NULL,
    usuario VARCHAR(100) NOT NULL,
    senha VARCHAR(100) NOT NULL
);

CREATE TABLE usuarioProducao(
    id SERIAL PRIMARY KEY NOT NULL,
    usuario VARCHAR(100) NOT NULL,
    senha VARCHAR(100) NOT NULL
);

CREATE TABLE usuarioEstoque(
    id SERIAL PRIMARY KEY NOT NULL,
    usuario VARCHAR(100) NOT NULL,
    senha VARCHAR(100) NOT NULL
);

CREATE TABLE usuarioQA(
    id SERIAL PRIMARY KEY NOT NULL,
    usuario VARCHAR(100) NOT NULL,
    senha VARCHAR(100) NOT NULL
);

CREATE TABLE usuarioAutomacao(
    id SERIAL PRIMARY KEY NOT NULL,
    usuario VARCHAR(100) NOT NULL,
    senha VARCHAR(100) NOT NULL
);

CREATE TABLE usuarioMaquinario(
    id SERIAL PRIMARY KEY NOT NULL,
    usuario VARCHAR(100) NOT NULL,
    senha VARCHAR(100) NOT NULL
);


INSERT INTO produtos (nome, quantidade, preco, localizacao, categoria) VALUES
('Laptop Dell XPS 13', 10, 1299.99, 'Estante A1', 'Eletrônicos'),
('Monitor LG 27 polegadas', 15, 249.50, 'Estante B2', 'Eletrônicos'),
('Teclado Mecânico Logitech', 25, 89.90, 'Estante C3', 'Periféricos'),
('Mouse Gamer Razer', 30, 59.99, 'Estante D4', 'Periféricos'),
('Impressora HP Laserjet', 8, 199.00, 'Estante E5', 'Escritório'),
('Papel A4 Branco', 100, 15.75, 'Estante F6', 'Escritório'),
('Caneta Esferográfica Preta', 200, 1.20, 'Estante G7', 'Escritório'),
('Caderno Universitário 10 matérias', 50, 8.50, 'Estante H8', 'Papelaria'),
('Mochila para Laptop', 12, 45.00, 'Estante I9', 'Acessórios'),
('Fone de Ouvido Bluetooth Sony', 18, 120.00, 'Estante J10', 'Eletrônicos'),
('HD Externo 1TB Seagate', 20, 79.99, 'Estante K11', 'Armazenamento'),
('SSD 500GB Samsung', 15, 65.00, 'Estante L12', 'Armazenamento'),
('Webcam Logitech HD', 22, 49.95, 'Estante M13', 'Periféricos'),
('Cadeira de Escritório Ergonômica', 5, 250.00, 'Estante N14', 'Mobiliário'),
('Luminária de Mesa LED', 35, 29.90, 'Estante O15', 'Iluminação'),
('Livro "Código Limpo"', 40, 35.00, 'Estante P16', 'Livros'),
('Roteador Wi-Fi TP-Link', 10, 60.00, 'Estante Q17', 'Rede'),
('Adaptador USB-C para HDMI', 30, 19.99, 'Estante R18', 'Acessórios'),
('Pasta Suspensa Azul', 80, 2.50, 'Estante S19', 'Escritório'),
('Calculadora Científica Casio', 25, 24.90, 'Estante T20', 'Papelaria');

INSERT INTO funcionarios (nome, matricula, cargo, salario, setor) VALUES
('Ana Silva', 'FUNC001', 'Gerente de Vendas', '5000.00', 'Vendas'),
('Carlos Pereira', 'FUNC002', 'Analista de Sistemas', '4500.00', 'TI'),
('Mariana Souza', 'FUNC003', 'Assistente Administrativo', '2500.00', 'Administrativo'),
('Ricardo Oliveira', 'FUNC004', 'Desenvolvedor Sênior', '6000.00', 'TI'),
('Laura Santos', 'FUNC005', 'Contador', '4000.00', 'Financeiro'),
('Pedro Almeida', 'FUNC006', 'Vendedor', '3000.00', 'Vendas'),
('Sofia Ferreira', 'FUNC007', 'Analista de RH', '3800.00', 'RH'),
('Lucas Costa', 'FUNC008', 'Técnico de Suporte', '2800.00', 'TI'),
('Isabela Rodrigues', 'FUNC009', 'Gerente de Marketing', '5500.00', 'Marketing'),
('Gustavo Martins', 'FUNC010', 'Designer Gráfico', '3500.00', 'Marketing'),
('Beatriz Rocha', 'FUNC011', 'Analista Financeiro', '4200.00', 'Financeiro'),
('Rafael Nunes', 'FUNC012', 'Programador Junior', '3200.00', 'TI'),
('Camila Barbosa', 'FUNC013', 'Recepcionista', '2000.00', 'Administrativo'),
('Eduardo Castro', 'FUNC014', 'Supervisor de Produção', '4800.00', 'Produção'),
('Juliana Gomes', 'FUNC015', 'Engenheiro de Software', '5800.00', 'TI'),
('Fernando Dias', 'FUNC016', 'Assistente de Vendas', '2700.00', 'Vendas'),
('Patricia Silva', 'FUNC017', 'Analista de Qualidade', '3900.00', 'Produção'),
('Rodrigo Alves', 'FUNC018', 'Estagiário de TI', '1500.00', 'TI'),
('Vanessa Lima', 'FUNC019', 'Coordenador de Projetos', '5200.00', 'Projetos'),
('Daniel Pereira', 'FUNC020', 'Analista de Dados', '4300.00', 'TI');

INSERT INTO usuarioRH (usuario, senha) VALUES ('rh', '123');
INSERT INTO usuarioFinanciero (usuario, senha) VALUES ('financiero', '123');
INSERT INTO usuarioProducao (usuario, senha) VALUES ('producao', '123');
INSERT INTO usuarioEstoque (usuario, senha) VALUES ('estoque', '123');
INSERT INTO usuarioQA (usuario, senha) VALUES ('qa', '123');
INSERT INTO usuarioAutomacao (usuario, senha) VALUES ('automacao', '123');
INSERT INTO usuarioMaquinario (usuario, senha) VALUES ('maquinario', '123');
