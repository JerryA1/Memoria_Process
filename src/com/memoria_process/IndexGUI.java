package com.memoria_process;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.Color;

public class IndexGUI extends JFrame{
    private JPanel panelWrap;
    private JPanel panelContentRight;
    private JPanel panelContentLeft;
    private JButton entrarButton;
    private JButton salirM;
    private JButton salirF;
    private JPanel panelContentStatus;
    private JTextArea TxtATerminal;
    private JPanel panelContentButtons;
    private JPanel panelContentTable;
    private JTable tableInfo;
    private JTextField txtSizeP;
    private JPanel panelButtons1;
    private JTextField txtNumberP;
    private JPanel panelButtons2;
    private JPanel panelContent;
    private JLabel labelSize;
    private JLabel labelProceso;
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    private DefaultTableModel modelData;
    JMenuBar mb = new JMenuBar();
    panelContentMemory pm = new panelContentMemory();
    JMenu pA;
    JMenu exit;
    JMenu about;

    public IndexGUI(){
        setTitle("Procesos en memoria");
        setSize(WIDTH, HEIGHT);
        setContentPane(panelWrap);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(255, 255, 255));
        pA = new JMenu("Primer ajuste");
        mb.add(pA);
        about = new JMenu("About");
        mb.add(about);
        exit = new JMenu("Salir");
        mb.add(exit);
        mb.setBackground(new Color(66, 66, 66));
        pA.setForeground(new Color(255, 255 ,255));
        about.setForeground(new Color(255, 255 ,255));
        exit. setForeground(new Color(255, 255, 255));
        setJMenuBar(mb);
        panelContentLeft.setLayout(new BoxLayout(panelContentLeft, BoxLayout.PAGE_AXIS));
        panelContentLeft.add(pm);
        panelContentRight.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelContentRight.setBackground(Colors.BlueR);
        panelContentButtons.setBackground(Colors.BlueR);
        panelButtons1.setBackground(Colors.BlueR);
        panelButtons2.setBackground(Colors.BlueR);
        labelSize.setForeground(Colors.White);
        labelProceso.setForeground(Colors.White);
        entrarButton.setBackground(Colors.CyanA700);
        entrarButton.setForeground(Colors.White);
        salirM.setBackground(Colors.CyanA700);
        salirM.setForeground(Colors.White);
        salirF.setBackground(Colors.LightBlueA700);
        salirF.setForeground(Colors.White);
        tableInfo.setBorder(null);
        tableInfo.setIntercellSpacing(new java.awt.Dimension(0, 5));
        tableInfo.setGridColor(Colors.White);
        tableInfo.setSelectionBackground(Colors.BlueA700);
        tableInfo.setSelectionForeground(Colors.White);
        tableInfo.setRowHeight(25);
        JTableHeader header = tableInfo.getTableHeader();
        header.setBackground(Colors.White);
        header.setForeground(Colors.Black);
        panelWrap.setBackground(Colors.BlueR);
        panelContent.setBackground(Colors.BlueR);

        String x[][] = {};
        String columnas[] = {"PID", "Tamaño", "Hora entrada", "Hora Salida", "Status", ""};
        modelData = new DefaultTableModel(x, columnas);
        tableInfo.setModel(modelData);

        initListeners();
    }

    private void initListeners() {
        exit.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                System.exit(0);
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
        });

        about.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                JOptionPane.showMessageDialog(null, "Proyecto Sistemas Operativos,\n Instituto Tecnológico de México en Celaya \n Copyright 2017 GAV");
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
        });

        pA.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                dispose();
                ControllerIndex controllerIndex = new ControllerIndex();
                controllerIndex.show();
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
        });
    }

    public JTextArea getTxtATerminal() {
        return TxtATerminal;
    }

    public JButton getEntrarButton() {
        return entrarButton;
    }

    public JTable getTableInfo() {
        return tableInfo;
    }

    public JTextField getTxtSizeP() {
        return txtSizeP;
    }

    public JTextField getTxtNumberP() {
        return txtNumberP;
    }

    public JButton getSalirM() {
        return salirM;
    }

    public JButton getSalirF() {
        return salirF;
    }

    public DefaultTableModel getModelData() {
        return modelData;
    }



}
