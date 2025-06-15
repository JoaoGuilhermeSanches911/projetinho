package br.com.ies.gui;

import br.com.ies.dao.PalestranteDAO;
import br.com.ies.model.Palestrante;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class PalestranteList extends JDialog {
    private JTable table;
    private DefaultTableModel tableModel;
    private List<Palestrante> palestrantes;
    
    public PalestranteList(Frame owner) {
        super(owner, "Lista de Palestrantes", true);
        initComponents();
        carregarPalestrantes();
        
        setSize(600, 400);
        setLocationRelativeTo(owner);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Tabela
        String[] colunas = {"Nome", "Email"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnNovo = new JButton("Novo");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnFechar = new JButton("Fechar");
        
        btnNovo.addActionListener(e -> {
            PalestranteForm form = new PalestranteForm((Frame) SwingUtilities.getWindowAncestor(this), null);
            form.setVisible(true);
            if (form.isConfirmado()) {
                carregarPalestrantes();
            }
        });
        
        btnEditar.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um palestrante para editar",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            PalestranteForm form = new PalestranteForm((Frame) SwingUtilities.getWindowAncestor(this), palestrantes.get(row));
            form.setVisible(true);
            if (form.isConfirmado()) {
                carregarPalestrantes();
            }
        });
        
        btnExcluir.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um palestrante para excluir",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int opcao = JOptionPane.showConfirmDialog(this,
                    "Deseja realmente excluir o palestrante selecionado?",
                    "Confirmação", JOptionPane.YES_NO_OPTION);
                    
            if (opcao == JOptionPane.YES_OPTION) {
                try {
                    new PalestranteDAO().excluir((long) palestrantes.get(row).getId());
                    carregarPalestrantes();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir palestrante: " + ex.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        btnFechar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnNovo);
        buttonPanel.add(btnEditar);
        buttonPanel.add(btnExcluir);
        buttonPanel.add(btnFechar);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void carregarPalestrantes() {
        tableModel.setRowCount(0);
        try {
            palestrantes = new PalestranteDAO().listarTodos();
            
            for (Palestrante palestrante : palestrantes) {
                Object[] row = {
                    palestrante.getNome(),
                    palestrante.getEmail()
                };
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar palestrantes: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
} 