package br.com.ies.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Evento {
    private int id;
    private String titulo;
    private String descricao;
    private LocalDate data;
    private LocalTime hora;
    private int idCurso;
    private int idPalestrante;
    private String slug;

    public Evento() {
    }

    public Evento(int id, String titulo, String descricao, LocalDate data, LocalTime hora, 
                 int idCurso, int idPalestrante, String slug) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
        this.hora = hora;
        this.idCurso = idCurso;
        this.idPalestrante = idPalestrante;
        this.slug = slug;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public int getIdPalestrante() {
        return idPalestrante;
    }

    public void setIdPalestrante(int idPalestrante) {
        this.idPalestrante = idPalestrante;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    @Override
    public String toString() {
        return titulo;
    }
} 