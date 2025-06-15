package br.com.ies.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ConexaoDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/neww?useTimezone=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";
    
    private Connection connection;
    
    public ConexaoDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Conecta ao banco específico
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            
            // Cria as tabelas se não existirem
            criarTabelas();
            
        } catch (Exception e) {
            System.out.println("[DAO Connection] " + e.getMessage());
        }
    }
    
    private void criarTabelas() {
        try (Statement stmt = connection.createStatement()) {
            // Tabela de palestrantes
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS palestrantes (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nome VARCHAR(100) NOT NULL,
                    email VARCHAR(100) NOT NULL
                )
            """);
            
            // Tabela de eventos
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS eventos (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    titulo VARCHAR(100) NOT NULL,
                    slug VARCHAR(100) NOT NULL,
                    descricao TEXT,
                    data DATE NOT NULL,
                    hora TIME NOT NULL,
                    id_curso INT NOT NULL,
                    id_palestrante INT NOT NULL,
                    FOREIGN KEY (id_palestrante) REFERENCES palestrantes(id)
                )
            """);
            
        } catch (Exception e) {
            System.out.println("[DAO Create Tables] " + e.getMessage());
        }
    }
    
    public Connection getConnection() {
        return connection;
    }
} 