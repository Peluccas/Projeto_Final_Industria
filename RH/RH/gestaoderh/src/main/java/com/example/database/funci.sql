CREATE DATABASE GestaoFuncionarios;
USE GestaoFuncionarios;

CREATE TABLE `gestaofuncionarios`.`dadospessoais` (
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

CREATE TABLE `gestaofuncionarios`.`dadosprofissionais` (
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


  SELECT 
    dp.nome_completo,
    dpf.cargo,
    dpf.departamento,
    dpf.funcao,
    dpf.maquinas,
    dpf.admissao,
    dpf.salario,
    dpf.dadosbancarios,
    dpf.beneficios,
    dpf.escolaridade,
    dpf.ctps,
    dpf.pisPasep,
    dpf.contrato,
    dpf.horario,
    dpf.acidentes,
    dpf.advertencias
FROM 
    gestaofuncionarios.dadospessoais dp
JOIN 
    gestaofuncionarios.dadosprofissionais dpf
ON 
    dp.cpf = dpf.dados_pessoais;
    
    
ALTER TABLE `gestaofuncionarios`.`dadospessoais` 
DROP INDEX `email_UNIQUE` ,
DROP INDEX `cpf_UNIQUE` ,
DROP INDEX `rg_UNIQUE` ,
DROP INDEX `telefone_UNIQUE` ;

ALTER TABLE `gestaofuncionarios`.`dadosprofissionais` 
DROP INDEX `dadosbancarios_UNIQUE` ,
DROP INDEX `pisPasep_UNIQUE` ,
DROP INDEX `ctps_UNIQUE` ;
