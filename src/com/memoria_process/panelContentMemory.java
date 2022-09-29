package com.memoria_process;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.border.Border;
import java.awt.Graphics;
import java.awt.Color;

public class panelContentMemory extends javax.swing.JPanel {
    TitledBorder title;
    static Proceso procesoMemory;
    static int y;
    static int x;
    static int widht = 150;
    static int height = ControllerIndex.memorySize;
    public panelContentMemory(){
        title = BorderFactory.createTitledBorder("Memoria " + ControllerIndex.memorySize + " MB");
        setBorder(title);
        setBounds(0, 0, widht, height);
        setBackground(Color.WHITE);
    }

    public void paint(Graphics g){
        super.paint(g);
        y = 20;
        x = 10;
        for (int i = 0; i < ControllerIndex.listaControl.size(); i++){
            procesoMemory = ControllerIndex.listaControl.get(i);
            g.setColor(procesoMemory.getColorP());
            g.fillRect(x, y, 240, procesoMemory.getTamaño());
            y += procesoMemory.getTamaño();
        }
        validate();
        repaint();
    }
}
