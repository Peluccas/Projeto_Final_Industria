-- Criar o banco de dados
CREATE DATABASE monitoramento;
USE monitoramento;

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
