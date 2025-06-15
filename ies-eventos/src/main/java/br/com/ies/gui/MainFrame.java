package br.com.ies.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    
    public MainFrame() {
        setTitle("IES Eventos - Back Office");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        initComponents();
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menuEventos = new JMenu("Eventos");
        JMenu menuPalestrantes = new JMenu("Palestrantes");
        
        JMenuItem itemNovoEvento = new JMenuItem("Novo Evento");
        JMenuItem itemListarEventos = new JMenuItem("Listar Eventos");
        JMenuItem itemNovoPalestrante = new JMenuItem("Novo Palestrante");
        JMenuItem itemListarPalestrantes = new JMenuItem("Listar Palestrantes");
        
        menuEventos.add(itemNovoEvento);
        menuEventos.add(itemListarEventos);
        menuPalestrantes.add(itemNovoPalestrante);
        menuPalestrantes.add(itemListarPalestrantes);
        
        menuBar.add(menuEventos);
        menuBar.add(menuPalestrantes);
        
        setJMenuBar(menuBar);
        
        // Painel central
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JLabel lblTitulo = new JLabel("Bem-vindo ao Sistema de Gestão de Eventos");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        centerPanel.add(lblTitulo, gbc);
        

        
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Adiciona o painel principal à janela
        add(mainPanel);
        
        // Adiciona os listeners dos menus
        itemNovoEvento.addActionListener(e -> {
            EventoForm form = new EventoForm(this, null);
            form.setVisible(true);
        });
        
        itemListarEventos.addActionListener(e -> {
            EventoList list = new EventoList(this);
            list.setVisible(true);
        });
        
        itemNovoPalestrante.addActionListener(e -> {
            PalestranteForm form = new PalestranteForm(this, null);
            form.setVisible(true);
        });
        
        itemListarPalestrantes.addActionListener(e -> {
            PalestranteList list = new PalestranteList(this);
            list.setVisible(true);
        });
    }
} 