package br.com.ies.service;

import br.com.ies.model.Curso;
import java.util.ArrayList;
import java.util.List;

public class CursoService {
    private static final List<Curso> cursos = new ArrayList<>();
    
    static {
        cursos.add(new Curso(1, "Administração"));
        cursos.add(new Curso(2, "Ciências Contábeis"));
        cursos.add(new Curso(3, "Direito"));
        cursos.add(new Curso(4, "Gestão Hospitalar"));
        cursos.add(new Curso(5, "Marketing"));
        cursos.add(new Curso(6, "Pedagogia"));
        cursos.add(new Curso(7, "Processos Gerenciais"));
        cursos.add(new Curso(8, "Psicologia"));
        cursos.add(new Curso(9, "Recursos Humanos"));
        cursos.add(new Curso(10, "Sistemas para Internet"));
    }
    
    public static List<Curso> getCursos() {
        return new ArrayList<>(cursos);
    }
    
    public static Curso getCursoById(int id) {
        return cursos.stream()
                .filter(curso -> curso.getId() == id)
                .findFirst()
                .orElse(null);
    }
} 