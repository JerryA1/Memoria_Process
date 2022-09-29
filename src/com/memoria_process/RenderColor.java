package com.memoria_process;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class RenderColor extends DefaultTableCellRenderer{
    private final int columna_patron;

    public RenderColor(int Colpatron) {
        this.columna_patron = Colpatron;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        String val = Integer.toString(Methods.contP);
        if (table.getValueAt(row, columna_patron).toString().equals(val)) {
                setForeground(Methods.c);

        }
       super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);


        return this;

    }
}
