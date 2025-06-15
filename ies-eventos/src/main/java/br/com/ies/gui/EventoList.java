package br.com.ies.gui;

import br.com.ies.dao.EventoDAO;
import br.com.ies.dao.PalestranteDAO;
import br.com.ies.model.Curso;
import br.com.ies.model.Evento;
import br.com.ies.model.Palestrante;
import br.com.ies.service.CursoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EventoList extends JDialog {
    private JTable table;
    private DefaultTableModel tableModel;
    private List<Evento> eventos;
    
    public EventoList(Frame owner) {
        super(owner, "Lista de Eventos", true);
        initComponents();
        carregarEventos();
        
        setSize(1000, 400);
        setLocationRelativeTo(owner);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Tabela
        String[] colunas = {"Título", "Descrição", "Data", "Hora", "Curso", "Palestrante"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Ajusta a largura das colunas
        table.getColumnModel().getColumn(0).setPreferredWidth(150); // Título
        table.getColumnModel().getColumn(1).setPreferredWidth(300); // Descrição
        table.getColumnModel().getColumn(2).setPreferredWidth(100); // Data
        table.getColumnModel().getColumn(3).setPreferredWidth(100); // Hora
        table.getColumnModel().getColumn(4).setPreferredWidth(150); // Curso
        table.getColumnModel().getColumn(5).setPreferredWidth(150); // Palestrante
        
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnNovo = new JButton("Novo");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnVisualizar = new JButton("Visualizar");
        JButton btnFechar = new JButton("Fechar");
        
        btnNovo.addActionListener(e -> {
            EventoForm form = new EventoForm((Frame) SwingUtilities.getWindowAncestor(this), null);
            form.setVisible(true);
            if (form.isConfirmado()) {
                carregarEventos();
            }
        });
        
        btnEditar.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um evento para editar",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            EventoForm form = new EventoForm((Frame) SwingUtilities.getWindowAncestor(this), eventos.get(row));
            form.setVisible(true);
            if (form.isConfirmado()) {
                carregarEventos();
            }
        });
        
        btnExcluir.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um evento para excluir",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int opcao = JOptionPane.showConfirmDialog(this,
                    "Deseja realmente excluir o evento selecionado?",
                    "Confirmação", JOptionPane.YES_NO_OPTION);
                    
            if (opcao == JOptionPane.YES_OPTION) {
                try {
                    new EventoDAO().excluir(eventos.get(row).getId());
                    carregarEventos();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir evento: " + ex.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        btnVisualizar.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um evento para visualizar",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            mostrarDetalhesEvento(eventos.get(row));
        });
        
        btnFechar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnNovo);
        buttonPanel.add(btnEditar);
        buttonPanel.add(btnExcluir);
        buttonPanel.add(btnVisualizar);
        buttonPanel.add(btnFechar);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void mostrarDetalhesEvento(Evento evento) {
        JDialog dialog = new JDialog(this, "Detalhes do Evento", true);
        dialog.setLayout(new BorderLayout());
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter horaFormatter = DateTimeFormatter.ofPattern("HH:mm");
        
        try {
            Curso curso = CursoService.getCursoById(evento.getIdCurso());
            Palestrante palestrante = new PalestranteDAO().buscarPorId((long) evento.getIdPalestrante());
            
            // Título
            gbc.gridx = 0;
            gbc.gridy = 0;
            panel.add(new JLabel("Título:"), gbc);
            
            gbc.gridx = 1;
            gbc.weightx = 1.0;
            panel.add(new JLabel(evento.getTitulo()), gbc);
            
            // Descrição
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.weightx = 0.0;
            panel.add(new JLabel("Descrição:"), gbc);
            
            gbc.gridx = 1;
            gbc.weightx = 1.0;
            JTextArea txtDescricao = new JTextArea(evento.getDescricao());
            txtDescricao.setLineWrap(true);
            txtDescricao.setWrapStyleWord(true);
            txtDescricao.setEditable(false);
            txtDescricao.setBackground(panel.getBackground());
            txtDescricao.setFont(new JLabel().getFont());
            JScrollPane scrollDescricao = new JScrollPane(txtDescricao);
            scrollDescricao.setPreferredSize(new Dimension(300, 50));
            panel.add(scrollDescricao, gbc);
            
            // Data
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.weightx = 0.0;
            panel.add(new JLabel("Data:"), gbc);
            
            gbc.gridx = 1;
            gbc.weightx = 1.0;
            panel.add(new JLabel(evento.getData().format(dataFormatter)), gbc);
            
            // Hora
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.weightx = 0.0;
            panel.add(new JLabel("Hora:"), gbc);
            
            gbc.gridx = 1;
            gbc.weightx = 1.0;
            panel.add(new JLabel(evento.getHora().format(horaFormatter)), gbc);
            
            // Curso
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.weightx = 0.0;
            panel.add(new JLabel("Curso:"), gbc);
            
            gbc.gridx = 1;
            gbc.weightx = 1.0;
            panel.add(new JLabel(curso != null ? curso.getNome() : "Curso não encontrado"), gbc);
            
            // Palestrante
            gbc.gridx = 0;
            gbc.gridy = 5;
            gbc.weightx = 0.0;
            panel.add(new JLabel("Palestrante:"), gbc);
            
            gbc.gridx = 1;
            gbc.weightx = 1.0;
            panel.add(new JLabel(palestrante != null ? palestrante.getNome() : "Palestrante não encontrado"), gbc);
            
            // Email do Palestrante
            gbc.gridx = 0;
            gbc.gridy = 6;
            gbc.weightx = 0.0;
            panel.add(new JLabel("Email do Palestrante:"), gbc);
            
            gbc.gridx = 1;
            gbc.weightx = 1.0;
            panel.add(new JLabel(palestrante != null ? palestrante.getEmail() : "Email não encontrado"), gbc);
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(dialog, "Erro ao carregar detalhes: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        dialog.add(panel, BorderLayout.CENTER);
        
        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dialog.dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnFechar);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    private void carregarEventos() {
        tableModel.setRowCount(0);
        try {
            eventos = new EventoDAO().listarTodos();
            DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter horaFormatter = DateTimeFormatter.ofPattern("HH:mm");
            
            for (Evento evento : eventos) {
                Curso curso = CursoService.getCursoById(evento.getIdCurso());
                Palestrante palestrante = new PalestranteDAO().buscarPorId((long) evento.getIdPalestrante());
                
                Object[] row = {
                    evento.getTitulo(),
                    evento.getDescricao(),
                    evento.getData().format(dataFormatter),
                    evento.getHora().format(horaFormatter),
                    curso != null ? curso.getNome() : "Curso não encontrado",
                    palestrante != null ? palestrante.getNome() : "Palestrante não encontrado"
                };
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar eventos: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
} 