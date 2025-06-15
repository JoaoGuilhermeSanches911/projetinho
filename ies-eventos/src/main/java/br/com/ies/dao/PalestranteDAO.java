package br.com.ies.dao;

import br.com.ies.model.Palestrante;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PalestranteDAO extends ConexaoDAO {
    
    public void inserir(Palestrante palestrante) throws SQLException {
        String sql = "INSERT INTO palestrantes (nome, email) VALUES (?, ?)";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, palestrante.getNome());
            stmt.setString(2, palestrante.getEmail());
            stmt.execute();
        }
    }
    
    public void atualizar(Palestrante palestrante) throws SQLException {
        String sql = "UPDATE palestrantes SET nome = ?, email = ? WHERE id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, palestrante.getNome());
            stmt.setString(2, palestrante.getEmail());
            stmt.setInt(3, palestrante.getId());
            stmt.execute();
        }
    }
    
    public void excluir(Long id) throws SQLException {
        String sql = "DELETE FROM palestrantes WHERE id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.execute();
        }
    }
    
    public Palestrante buscarPorId(Long id) throws SQLException {
        String sql = "SELECT * FROM palestrantes WHERE id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return criarPalestrante(rs);
            }
        }
        return null;
    }
    
    public List<Palestrante> listarTodos() throws SQLException {
        List<Palestrante> palestrantes = new ArrayList<>();
        String sql = "SELECT * FROM palestrantes ORDER BY nome";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                palestrantes.add(criarPalestrante(rs));
            }
        }
        return palestrantes;
    }
    
    private Palestrante criarPalestrante(ResultSet rs) throws SQLException {
        Palestrante palestrante = new Palestrante();
        palestrante.setId((int) rs.getLong("id"));
        palestrante.setNome(rs.getString("nome"));
        palestrante.setEmail(rs.getString("email"));
        return palestrante;
    }
} 