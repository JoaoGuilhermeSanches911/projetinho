CREATE DATABASE IF NOT EXISTS ies_eventos;
USE ies_eventos;

CREATE TABLE IF NOT EXISTS palestrantes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS eventos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descricao TEXT,
    data DATE NOT NULL,
    hora TIME NOT NULL,
    id_curso INT NOT NULL,
    id_palestrante INT NOT NULL,
    slug VARCHAR(100) NOT NULL,
    FOREIGN KEY (id_palestrante) REFERENCES palestrantes(id)
); 