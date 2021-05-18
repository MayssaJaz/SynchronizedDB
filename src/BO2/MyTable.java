package BO2;
import java.util.ArrayList;
import java.awt.Button;
import java.awt.Component;
import java.lang.Object;
import java.util.Optional;
import javax.swing.table.*;
import javax.swing.event.TableModelEvent;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.util.List;


public class MyTable extends AbstractTableModel implements TableModel {
    
	public List<Sale> mySaless;
    private GestionSales gs=new GestionSales();

    private void setData(List<Sale> list, Sale data) {
        int rows = getRowCount();
        int row = list.size();
        list.add(data);

        if(row < rows) {
            fireTableRowsUpdated(row, row);
        }
        else {
            fireTableRowsInserted(row, row);
        }
    }

    public void setIncomeData(Sale sale) {
        if(mySaless == null) {
            mySaless = new ArrayList<>();
        }

        setData(mySaless, sale);
    }
    @Override
    public int getColumnCount() {
        return 10;
    }

    public List<Sale> getIncomeData() {
        return mySaless;
    }



    @Override
    public int getRowCount() {
            if(mySaless != null) 
                return mySaless.size();
            return 0;
    }
    @Override
    public String getColumnName(int column) {
        switch (column) {
        	case 0:  return "ID";
            case 1:return "Date";
            case 2: return "Product";
            case 3: return "Amount";
            case 4:return "Cost";
            case 5:return "Quantite";
            case 6:return "Region";
            case 7:return "Tax";
            case 8:return "Total";
            case 9: return "Delete";
            default:
                return super.getColumnName(column);
        }
    }

    
    @Override
    public Object getValueAt(int row, int column) {
        Sale inclome = null;

        if(mySaless != null && mySaless.size() > row) {
            inclome = mySaless.get(row);
        }

        switch (column) {
 
            case 0: return inclome != null ? inclome.getId() : null;
            case 1: return inclome != null ? inclome.getDate() : null;
            case 2: return inclome != null ? inclome.getProduct() : null;
            case 3: return inclome != null ? inclome.getAmt() : null;
            case 4: return inclome != null ? inclome.getCost() : null;
            case 5: return inclome  != null ? inclome.getQuantite() : null;
            case 6: return inclome != null ? inclome.getRegion() : null;
            case 7: return inclome  != null ? inclome.getTax() : null;
            case 8: return inclome != null ? inclome.getTotal() : null;

        }

        return null;
    }

@Override
public void setValueAt(Object value, int row, int col)
{
   Sale sale =mySaless.get(row);
   if (col==1)
	    sale.setDate((String)value);
    if (col==2)
    	sale.setProduct((String)value);
    else if (col==3)
    	sale.setAmt((double)value);
    if (col==4)
	    sale.setCost((double)value);

    if (col==5)
    	sale.setQuantite((int)value);
    else if (col==6)
    	sale.setRegion((String)value);
    if (col==7)
	    sale.setTax((double)value);

    if (col==8)
    	sale.setTotal((double)value);
    if (col==9)
    	
    {  
    	gs.deleteSale(sale.getId());
    	mySaless.remove(row);
    	}
    else {
    if (row>=mySaless.size()-1 && sale.getId() == 0)
    {
    	System.out.println(mySaless.size());
   sale.setId(mySaless.get(mySaless.size()-2).getId()+1);
  gs.addSale(sale.getId(),Optional.ofNullable(sale.getProduct()).orElse(""),Optional.ofNullable(sale.getRegion()).orElse(""),
		
		  Optional.ofNullable(sale.getDate()).orElse(null),sale.getQuantite(), sale.getCost(),sale.getAmt(),sale.getTax(),sale.getTotal());

    }
  else {
	  System.out.println(mySaless.size());
    	gs.updateSale(sale);
    	
    }
    }
    fireTableCellUpdated(row,col);
    fireTableDataChanged();

}

    public Class getColumnClass(int c)
    {
        return getValueAt(0, c).getClass();
    }
  
    @Override
    public boolean isCellEditable(int row, int col)
    {
        return true;
    }


    public void update() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
            	mySaless = gs.selectAll();
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    fireTableDataChanged();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        worker.execute();
    }
}