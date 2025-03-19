create database financeiro;
use financeiro;

CREATE TABLE setores (
    id_setores int auto_increment primary key,
    nome_setor varchar(50)
);

CREATE TABLE funcionarios (
    id_funcionarios int auto_increment primary key,
    nome varchar(50)
);

CREATE TABLE funcionario_setor (
    id_funcionario_setor int auto_increment primary key,
    fk_funcionario int,
    fk_setor int,
    FOREIGN KEY (fk_funcionario) REFERENCES funcionarios(id_funcionarios),
    FOREIGN KEY (fk_setor) REFERENCES setores(id_setores)
);

create table fluxo(
id_fluxo int auto_increment primary key,
data_transacao date,
fk_setor int,
descricao varchar(50),
valor DECIMAL(10,2),
categoria varchar(50),
forma_pagto varchar(50),
vencimento date,
status boolean,
foreign key (fk_setor) references setores(id_setores));


create table solicitacoes(
id_solicitacoes int auto_increment primary key,
data_solicitacao date, 
fk_setor int,
descricao varchar(50),
quantidade varchar(50),
valor DECIMAL(10,2),
prazo date,
status boolean,
foreign key (fk_setor) references setores(id_setores));


create table pagfuncionarios(
id_pagfuncionarios int auto_increment primary key,
fk_funcionarios int,
fk_setor int,
data_pagto date,
salario_base DECIMAL(10,2),
descontos DECIMAL(10,2) ,
valor_liquido DECIMAL(10,2),
status boolean,
foreign key (fk_setor) references setores(id_setores));



INSERT INTO setores (nome_setor) VALUES
('RH'),
('AUTOMAÇÃO'),
('PRODUÇÃO'),
('ESTOQUE'),
('CONTROLE DE QUALIDADE');


INSERT INTO fluxo (data_transacao, fk_setor, descricao, valor, categoria, forma_pagto, vencimento, status) VALUES
('2025-01-15', 1, 'Compra de componentes', 5000.00, 'Compra', 'Cartão de Crédito', '2025-02-15', true),
('2025-01-20', 2, 'Automação de processos', 1500.00, 'Serviço', 'Transferência', '2025-03-01', true),
('2025-02-01', 3, 'Produção de jogos', 10000.00, 'Venda', 'Boleto', '2025-03-05', true),
('2025-02-10', 4, 'Estoque de materiais', 3000.00, 'Compra', 'Cartão de Débito', '2025-02-20', true),
('2025-02-15', 5, 'Teste de qualidade', 2000.00, 'Serviço', 'Transferência', '2025-03-10', false),
('2025-02-18', 1, 'Salário do mês', 7000.00, 'Salário', 'Transferência', '2025-02-28', true),
('2025-02-20', 2, 'Manutenção de equipamentos', 2500.00, 'Serviço', 'Boleto', '2025-03-15', false),
('2025-03-01', 3, 'Lançamento de novo jogo', 12000.00, 'Venda', 'Cartão de Crédito', '2025-04-01', true),
('2025-03-05', 4, 'Ajustes no estoque', 800.00, 'Serviço', 'Cartão de Débito', '2025-03-20', true),
('2025-03-10', 5, 'Relatório de qualidade', 1500.00, 'Serviço', 'Transferência', '2025-03-25', true);

INSERT INTO funcionarios (nome) VALUES
('Ana Silva'),
('Carlos Pereira'),
('Fernanda Costa'),
('João Souza'),
('Mariana Lima'),
('Pedro Oliveira'),
('Luciana Santos'),
('Rafael Gomes'),
('Juliana Alves'),
('Gabriel Martins');

INSERT INTO solicitacoes (data_solicitacao, fk_setor, descricao, quantidade, valor, prazo, status) VALUES
('2025-01-10', 1, 'Solicitação de material de escritório', '10 unidades', 150.00, '2025-01-20', true),
('2025-01-12', 2, 'Solicitação de software de automação', '5 licenças', 3000.00, '2025-01-25', false),
('2025-01-15', 3, 'Solicitação para produção de novos jogos', '100 unidades', 50000.00, '2025-02-15', true),
('2025-01-20', 4, 'Solicitação de manutenção de equipamentos', '2 serviços', 1200.00, '2025-02-05', true),
('2025-01-25', 5, 'Solicitação de auditoria de qualidade', '1 serviço', 4500.00, '2025-02-10', false),
('2025-02-01', 1, 'Solicitação de treinamento para equipe', '1 curso', 2500.00, '2025-02-20', true),
('2025-02-05', 2, 'Solicitação de novos PCs', '10 unidades', 8000.00, '2025-02-15', false),
('2025-02-10', 3, 'Solicitação para compra de jogos antigos', '20 unidades', 3000.00, '2025-02-25', true),
('2025-02-15', 4, 'Solicitação de novos móveis para escritório', '5 itens', 2000.00, '2025-03-01', true),
('2025-02-20', 5, 'Solicitação de campanha publicitária', '1 serviço', 7000.00, '2025-03-10', false);

INSERT INTO pagfuncionarios (fk_funcionarios, fk_setor, data_pagto, salario_base, descontos, valor_liquido, status) VALUES
(1, 1, '2025-01-31', 5000.00, 500.00, 4500.00, true), 
(2, 2, '2025-01-31', 6000.00, 600.00, 5400.00, true), 
(3, 3, '2025-01-31', 7000.00, 700.00, 6300.00, false), 
(4, 4, '2025-01-31', 5500.00, 550.00, 4950.00, true), 
(5, 5, '2025-01-31', 5800.00, 580.00, 5220.00, true), 
(6, 1, '2025-01-31', 5000.00, 500.00, 4500.00, true),  
(7, 2, '2025-01-31', 6000.00, 600.00, 5400.00, false),  
(8, 3, '2025-01-31', 7000.00, 700.00, 6300.00, true),
(9, 4, '2025-01-31', 5500.00, 550.00, 4950.00, true), 
(10, 5, '2025-01-31', 5800.00, 580.00, 5220.00, true);  

INSERT INTO funcionario_setor (fk_funcionario, fk_setor) VALUES
(1, 1),  
(2, 2),  
(3, 3),  
(4, 4),  
(5, 5),  
(6, 1), 
(7, 2), 
(8, 3), 
(9, 4),
(10, 5); 






