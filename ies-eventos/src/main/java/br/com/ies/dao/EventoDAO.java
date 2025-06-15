package br.com.ies.dao;

import br.com.ies.model.Evento;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EventoDAO extends ConexaoDAO {
    
    public void inserir(Evento evento) throws SQLException {
        String sql = "INSERT INTO eventos (titulo, slug, descricao, data, hora, id_curso, id_palestrante) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, evento.getTitulo());
            stmt.setString(2, evento.getSlug());
            stmt.setString(3, evento.getDescricao());
            stmt.setObject(4, evento.getData());
            stmt.setObject(5, evento.getHora());
            stmt.setInt(6, evento.getIdCurso());
            stmt.setInt(7, evento.getIdPalestrante());
            stmt.execute();
        }
    }
    
    public void atualizar(Evento evento) throws SQLException {
        String sql = "UPDATE eventos SET titulo = ?, slug = ?, descricao = ?, data = ?, hora = ?, id_curso = ?, id_palestrante = ? WHERE id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, evento.getTitulo());
            stmt.setString(2, evento.getSlug());
            stmt.setString(3, evento.getDescricao());
            stmt.setObject(4, evento.getData());
            stmt.setObject(5, evento.getHora());
            stmt.setInt(6, evento.getIdCurso());
            stmt.setInt(7, evento.getIdPalestrante());
            stmt.setInt(8, evento.getId());
            stmt.execute();
        }
    }
    
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM eventos WHERE id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
        }
    }
    
    public Evento buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM eventos WHERE id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return criarEvento(rs);
            }
        }
        return null;
    }
    
    public List<Evento> listarTodos() throws SQLException {
        List<Evento> eventos = new ArrayList<>();
        String sql = "SELECT * FROM eventos ORDER BY data, hora";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                eventos.add(criarEvento(rs));
            }
        }
        return eventos;
    }
    
    private Evento criarEvento(ResultSet rs) throws SQLException {
        Evento evento = new Evento();
        evento.setId(rs.getInt("id"));
        evento.setTitulo(rs.getString("titulo"));
        evento.setSlug(rs.getString("slug"));
        evento.setDescricao(rs.getString("descricao"));
        evento.setData(rs.getObject("data", LocalDate.class));
        evento.setHora(rs.getObject("hora", LocalTime.class));
        evento.setIdCurso(rs.getInt("id_curso"));
        evento.setIdPalestrante(rs.getInt("id_palestrante"));
        return evento;
    }
} 