package br.com.ies.gui;

import br.com.ies.dao.PalestranteDAO;
import br.com.ies.model.Palestrante;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class PalestranteForm extends JDialog {
    private JTextField txtNome;
    private JTextField txtEmail;
    private JButton btnSalvar;
    private JButton btnCancelar;
    
    private Palestrante palestrante;
    private boolean confirmado = false;
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@(.+)$"
    );
    
    public PalestranteForm(Frame owner, Palestrante palestrante) {
        super(owner, palestrante == null ? "Novo Palestrante" : "Editar Palestrante", true);
        this.palestrante = palestrante;
        
        initComponents();
        carregarDados();
        
        setSize(400, 200);
        setLocationRelativeTo(owner);
    }

    public PalestranteForm(PalestranteList palestranteList, Object palestrante) {

    }

    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Painel de formulário
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Nome
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Nome:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtNome = new JTextField(20);
        formPanel.add(txtNome, gbc);
        
        // Email
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Email:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtEmail = new JTextField(20);
        formPanel.add(txtEmail, gbc);
        
        add(formPanel, BorderLayout.CENTER);
        
        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");
        
        btnSalvar.addActionListener(e -> salvar());
        btnCancelar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnCancelar);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void carregarDados() {
        if (palestrante != null) {
            txtNome.setText(palestrante.getNome());
            txtEmail.setText(palestrante.getEmail());
        }
    }
    
    private boolean validarCampos() {
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O nome é obrigatório",
                    "Erro de validação", JOptionPane.ERROR_MESSAGE);
            txtNome.requestFocus();
            return false;
        }
        
        if (txtEmail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O email é obrigatório",
                    "Erro de validação", JOptionPane.ERROR_MESSAGE);
            txtEmail.requestFocus();
            return false;
        }
        
        if (!EMAIL_PATTERN.matcher(txtEmail.getText().trim()).matches()) {
            JOptionPane.showMessageDialog(this, "Email inválido",
                    "Erro de validação", JOptionPane.ERROR_MESSAGE);
            txtEmail.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void salvar() {
        // Validação dos campos
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O nome é obrigatório",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (txtEmail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O email é obrigatório",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            // Cria ou atualiza o palestrante
            Palestrante novoPalestrante = new Palestrante();
            if (palestrante != null) {
                novoPalestrante.setId(palestrante.getId());
            }
            novoPalestrante.setNome(txtNome.getText().trim());
            novoPalestrante.setEmail(txtEmail.getText().trim());
            
            PalestranteDAO palestranteDAO = new PalestranteDAO();
            if (palestrante == null) {
                palestranteDAO.inserir(novoPalestrante);
            } else {
                palestranteDAO.atualizar(novoPalestrante);
            }
            
            confirmado = true;
            dispose();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar palestrante: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean isConfirmado() {
        return confirmado;
    }
    
    public Palestrante getPalestrante() {
        return palestrante;
    }
} 