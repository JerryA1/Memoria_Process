package com.memoria_process;

import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.util.LinkedList;
import java.util.List;
import javax.swing.SwingConstants;

//////// ---------------------- CONTROLLER ---------------------- /////////

public class ControllerIndex {
    private IndexGUI indexGUI;
    static JTextArea TxtATerminal;
    private JButton entrarButton;
    static JTable tableInfo;
    static DefaultTableModel modelData;
    static JTextField txtSizeP;
    static JTextField txtNumberP;
    private JButton salirM;
    private JButton salirF;

    static int memorySize;
    static String politica;
    /*
    //Lista de control es una array de tipo Proceso de 4 valores, que son:
    // 0: Estado (0 = libre,1 = ocupado)
    // 1: Dirección inicial del bloque
    // 2: Tamaño (En ticks)
    // 3: Pid del proceso al que está asignado(Id del proceso)
    */
    public static List<Proceso> listaControl;
    public static int[] memoria;

    public ControllerIndex(){
        initiMemory();

        indexGUI = new IndexGUI();
        initComponents();
        initListeners();
        modelData = (DefaultTableModel) tableInfo.getModel();
    }

    private void initiMemory() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255));
        panel.setPreferredSize(new Dimension(180,150));
        panel.setLayout(null);

        JLabel label = new JLabel("Tamaño de la memoria");
        label.setBounds(0, 0, 240, 64);
        label.setFont(new Font("Arial", Font.BOLD, 11));
        label.setHorizontalAlignment(SwingConstants.LEFT);

        JTextField txtT = new JTextField();
        txtT.setBounds(0,40, 180,30);
        txtT.setFocusable(true);

        JLabel lblPa = new JLabel("Primer ajuste");
        lblPa.setBounds(0, 50, 200, 64);
        lblPa.setFont(new Font("Arial", Font.BOLD, 11));
        lblPa.setHorizontalAlignment(SwingConstants.LEFT);

        String[] primerAjsute= {"FIFO","MENOR"};
        JComboBox jcp = new JComboBox(primerAjsute);
        jcp.setBounds(0,90, 180,30);

        panel.add(label);
        panel.add(txtT);
        panel.add(lblPa);
        panel.add(jcp);

        JOptionPane jop = new JOptionPane();
        //UIManager.put("OptionPane.minimumSize",new Dimension(240, 200));
        jop.showMessageDialog(null, panel, "Bienvenido", JOptionPane.PLAIN_MESSAGE);

        //Se inicia el tamaño de memoria
        memorySize = Integer.parseInt(txtT.getText());
        //Se indica la politica a trabajar para sacar de la fila
        politica = (String) jcp.getSelectedItem();

        listaControl = new LinkedList<Proceso>();
        //El array de memoria se divide en n ticks, donde n = memorySize
        memoria = new int[memorySize];
        // Se añade a la lista una primera entrada, con un hueco del total de la memoria
        Proceso hueco = new Proceso(0,0, memorySize, 0, Methods.vacio);
        listaControl.add(hueco);
    }

    public void show(){
        indexGUI.setVisible(true);
    }

    private void initComponents() {
        TxtATerminal = indexGUI.getTxtATerminal();
        entrarButton = indexGUI.getEntrarButton();
        tableInfo = indexGUI.getTableInfo();
        txtSizeP = indexGUI.getTxtSizeP();
        txtNumberP = indexGUI.getTxtNumberP();
        salirM = indexGUI.getSalirM();
        salirF = indexGUI.getSalirF();
        tableInfo.setDefaultRenderer(Object.class, new statusCheck());
        TxtATerminal.append(" -> Tamaño de memoria " + memorySize + "\n");
        TxtATerminal.append(" -> Politica " + politica + "\n");
    }

    private void initListeners(){
        entrarButton.addActionListener(new EntrarButtonListener());
        txtSizeP.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                TxtSizePKeyTyped(e);
            }
        });

        salirM.addActionListener(new SalirMListener());
        salirF.addActionListener(new SalirFListener());
    }

    /* solo caracteres numéricos metodo */
    private void TxtSizePKeyTyped(KeyEvent evt){
        int k = (int) evt.getKeyChar();
        if (k >= 33 && k <= 47 || k >= 58 && k <= 97) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(null, "Solo se permiten caracteres Númericos", "Error de ingreso", JOptionPane.ERROR_MESSAGE);
        } else if (k >= 58) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(null, "Solo se permiten caracteres Númericos", "Error de ingreso", JOptionPane.ERROR_MESSAGE);
        } else if (k == 10) {
            txtSizeP.transferFocus();
        }
    }

    private class EntrarButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(txtSizeP.getText().equals("0")){
                JOptionPane.showMessageDialog(null, "Wow, checa tu cantidad antes de continuar");
            } else if (txtSizeP.getText().length() != 0){
                Methods.Reserve();
                txtSizeP.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Pst, olvidas poner la cantidad");
            }
        }
    }

    private class SalirMListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (txtNumberP.getText().equals("0")){
                JOptionPane.showMessageDialog(null, "Este proceso no existe");
            } else if (txtNumberP.getText().length() != 0) {
                Methods.Libere();
                txtNumberP.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Hey, aún no escribes el número de proceso");
            }
        }
    }

    private class SalirFListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (politica) {
                case "FIFO":
                    Methods.LibereFiFo();
                    break;
                case "MENOR":
                    Methods.LibereMenor();
                    break;
            }
        }
    }
}
