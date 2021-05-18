package BO1;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

class MyButton extends AbstractCellEditor
implements TableCellRenderer, TableCellEditor, ActionListener
{
JTable table;
JButton renderingButton;
JButton editingButton;
String myText;

public MyButton(JTable myTable, int column)
{
    super();
    this.table = myTable;
    renderingButton = new JButton();

    editingButton = new JButton();
    editingButton.setFocusPainted( false );
    editingButton.addActionListener( this );

    TableColumnModel columnModel = myTable.getColumnModel();
    columnModel.getColumn(column).setCellRenderer( this );
    columnModel.getColumn(column).setCellEditor( this );
}


public void actionPerformed(ActionEvent e)
{
    fireEditingStopped();
    System.out.println( e.getActionCommand() + " : "+ table.getSelectedRow());
}
public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
{
	if (hasFocus)
    {
        renderingButton.setForeground(table.getForeground());
        renderingButton.setBackground(UIManager.getColor("Button.background"));
    }
    else if (isSelected)
    {
        renderingButton.setForeground(table.getSelectionForeground());
         renderingButton.setBackground(table.getSelectionBackground());
    }
    else
    {
        renderingButton.setForeground(table.getForeground());
        renderingButton.setBackground(UIManager.getColor("Button.background"));
    }

    renderingButton.setText( (value == null) ? "Delete" : value.toString() );
    return renderingButton;
}
public Component getTableCellEditorComponent(
    JTable table, Object value, boolean isSelected, int row, int column)
{
    myText = (value == null) ? "Delete" : value.toString();
    editingButton.setText( myText );
    return editingButton;
}

public Object getCellEditorValue()
{
    return myText;
}


}
