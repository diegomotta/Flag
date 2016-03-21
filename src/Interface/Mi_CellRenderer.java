/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.awt.Component;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author garba
 */
public class Mi_CellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof Date) {
            JLabel lbl;
            Date val = (Date) value;
            lbl = new JLabel(Integer.valueOf(val.getDate()).toString()+"/"+Integer.valueOf(val.getMonth()+1).toString() +"/"+Integer.valueOf(val.getYear()).toString());
//            fillColor(table, lbl, isSelected);
            return lbl;
        } else {
            return super.getTableCellRendererComponent(table, value, hasFocus, hasFocus, row, row);
        }


    }
    
     public void fillColor(JTable t, JLabel l, boolean isSelected) {
            if (isSelected) {
                l.setForeground(t.getSelectionForeground());
                l.setBackground(t.getSelectionBackground());
//                l.setBackground(Color.yellow);
//                l.setForeground(Color.yellow);
                
            } else {
                l.setForeground(t.getForeground());
                l.setBackground(t.getBackground());
                
            }
        }
}
