CREATE DATABASE Industria_db;
USE Industria_db;


CREATE TABLE usuarioRH(
    id SERIAL PRIMARY KEY NOT NULL,
    usuario VARCHAR(100) NOT NULL,
    senha VARCHAR(100) NOT NULL
);

CREATE TABLE `dadospessoais` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome_completo` VARCHAR(45) NOT NULL,
  `data_nascimento` VARCHAR(15) NOT NULL,
  `sexo` CHAR(20) NOT NULL,
  `estado_civil` VARCHAR(15) NULL,
  `conjuge` VARCHAR(45) NULL,
  `dependentes` VARCHAR(150) NULL,
  `nacionalidade` VARCHAR(45) NULL,
  `naturalidade` VARCHAR(45) NULL,
  `cpf` VARCHAR(15) NOT NULL,
  `rg` VARCHAR(15) NOT NULL,
  `endereco` VARCHAR(45) NOT NULL,
  `telefone` VARCHAR(20) NOT NULL,
  `email` VARCHAR(45) NULL,
  `filiacao` VARCHAR(150) NULL,
  `tipo_sanguineo` VARCHAR(3) NOT NULL,
  `contato_emergencia` VARCHAR(20) NULL,
  PRIMARY KEY (`cpf`),
  UNIQUE INDEX `nome_completo_UNIQUE` (`id` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  UNIQUE INDEX `telefone_UNIQUE` (`telefone` ASC),
  UNIQUE INDEX `rg_UNIQUE` (`rg` ASC),
  UNIQUE INDEX `cpf_UNIQUE` (`cpf` ASC));

CREATE TABLE `dadosprofissionais` (
  `idprof` INT NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
  `cargo` VARCHAR(30) NOT NULL,
  `departamento` VARCHAR(45) NOT NULL,
  `funcao` VARCHAR(150) NOT NULL,
  `maquinas` VARCHAR(45) NULL,
  `admissao` VARCHAR(15) NOT NULL,
  `salario` VARCHAR(45) NOT NULL,
  `dadosbancarios` VARCHAR(45) NOT NULL,
  `beneficios` VARCHAR(45) NULL,
  `escolaridade` VARCHAR(45) NULL,
  `ctps` VARCHAR(45) NOT NULL,
  `pisPasep` VARCHAR(45) NOT NULL,
  `contrato` VARCHAR(45) NOT NULL,
  `horario` VARCHAR(45) NOT NULL,
  `acidentes` VARCHAR(200) NOT NULL,
  `advertencias` VARCHAR(200) NOT NULL,
  `dados_pessoais` VARCHAR(15),
  
  FOREIGN KEY (dados_pessoais) REFERENCES dadospessoais(cpf),
  UNIQUE INDEX `ctps_UNIQUE` (`ctps` ASC),
  UNIQUE INDEX `pisPasep_UNIQUE` (`pisPasep` ASC),
  UNIQUE INDEX `dadosbancarios_UNIQUE` (`dadosbancarios` ASC));

ALTER TABLE `dadospessoais` 
DROP INDEX `email_UNIQUE` ,
DROP INDEX `cpf_UNIQUE` ,
DROP INDEX `rg_UNIQUE` ,
DROP INDEX `telefone_UNIQUE` ;

ALTER TABLE `dadosprofissionais` 
DROP INDEX `dadosbancarios_UNIQUE` ,
DROP INDEX `pisPasep_UNIQUE` ,
DROP INDEX `ctps_UNIQUE` ;


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
CREATE TABLE funcionario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    setor VARCHAR(50) NOT NULL
);

CREATE TABLE produto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    preco DECIMAL(10,2) NOT NULL
);

CREATE TABLE producao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    funcionario_id INT,
    maquina_id INT,
    produto_id INT,
    quantidade INT NOT NULL,
    data_producao DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (funcionario_id) REFERENCES funcionario(id),
    FOREIGN KEY (produto_id) REFERENCES produto(id)
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



-- Tabela de equipamentos
CREATE TABLE equipamentos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    categoria VARCHAR(50),
    modelo VARCHAR(100),
    numero_serie VARCHAR(50),
    setor VARCHAR(50),
    data_aquisicao DATE,
    valor_aquisicao DECIMAL(10,2),
    status VARCHAR(20) DEFAULT 'disponível',
    manutencao_periodica BOOLEAN DEFAULT FALSE
);

-- Tabela de manutenções
CREATE TABLE manutencoes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    equipamento_id INT,
    tipo_manutencao VARCHAR(50),
    data_inicio DATE,
    data_conclusao DATE,
    status VARCHAR(20),
    descricao_servico TEXT,
    FOREIGN KEY (equipamento_id) REFERENCES equipamentos(id)
);

-- Tabela de empréstimos
CREATE TABLE emprestimos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    equipamento_id INT,
    setor_solicitante VARCHAR(50),
    data_inicio DATE,
    data_devolucao DATE,
    status VARCHAR(20) DEFAULT 'no prazo',
    observacoes TEXT,
    FOREIGN KEY (equipamento_id) REFERENCES equipamentos(id)
);

