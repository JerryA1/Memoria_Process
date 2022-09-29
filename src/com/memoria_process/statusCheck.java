package com.memoria_process;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class statusCheck extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if(table.getValueAt(row, 4).toString().equals("Finalizado")){
            if (column == 4) {
                setForeground(Colors.Green800);
            }
        }
        if (table.getValueAt(row, 4).toString().equals("En memoria")){
            if (column == 4) {
                setForeground(Colors.Yellow600);
            }
        }
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}
