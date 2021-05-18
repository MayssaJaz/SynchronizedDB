package BO2;

import java.sql.*;
import java.util.Vector;


public class GestionSales {
	public Sale find(int id) {
		Connection conn=Utilitaire.getConnection();
		Sale mySale=null;
		try {
			PreparedStatement request=conn.prepareStatement("select * from `sales` where ID=?");
			request.setInt(1,id);
			ResultSet rsUser=request.executeQuery();
			if(rsUser.next()){
				mySale=new Sale();
				mySale.setId(new Integer(rsUser.getInt("ID")));
				mySale.setProduct(rsUser.getString("product"));
				mySale.setRegion(rsUser.getString("region"));
				mySale.setDate(rsUser.getString("date"));
				mySale.setQuantite(new Integer(rsUser.getString("quantite")));
				mySale.setCost(Double.parseDouble(rsUser.getString("cost")));
				mySale.setAmt(Double.parseDouble(rsUser.getString("amt")));
				mySale.setTax(Double.parseDouble(rsUser.getString("tax")));
				mySale.setTotal(Double.parseDouble(rsUser.getString("total")));
				
				}
			} catch (SQLException e) { e.printStackTrace();
			
			}
		return mySale;
		}

	public void addSale(int id , String product,String region,String date,int quantite,double cost, double amt,double tax,double total )
	{ 
		Connection conn=Utilitaire.getConnection();
		Sale s=null;
		try {
			
			PreparedStatement request=conn.prepareStatement("INSERT INTO `sales`(`ID`, `product`, `region`, `date`, `quantite`, `cost`, `amt`, `tax`, `total`) VALUES (?,?,?,?,?,?,?,?,?)");
			request.setInt(1,id); 
			request.setString(2,product);
			request.setString(3,region); 
			request.setString(4, date);
			request.setInt(5,quantite);
			request.setDouble(6,cost);
			request.setDouble(7,amt);
			request.setDouble(8,tax); 
			request.setDouble(9, total); 
			request.executeUpdate();
			

			} catch (SQLException e2) { e2.printStackTrace();
			}
		}
	public void updateSale(Sale s) {
		Connection conn=Utilitaire.getConnection();
		try {
			PreparedStatement ps= conn.prepareStatement("update `sales` set product=?,region=? , date=? , quantite=? , cost=? ,amt=?,tax=?,total=? where ID=? ");
			ps.setString(1, s.getProduct());
			ps.setString(2, s.getRegion());
			ps.setString(3, s.getDate());
			ps.setInt(4, s.getQuantite());
			ps.setDouble(5, s.getCost());
			ps.setDouble(6, s.getAmt());
			ps.setDouble(7, s.getTax());
			ps.setDouble(8, s.getTotal());
			ps.setInt(9,s.getId());
			ps.executeUpdate();
		} catch (SQLException e) { e.printStackTrace();
		}
		}
	
	public Vector<Sale> selectAll(){
		Connection conn=Utilitaire.getConnection();
		Vector<Sale> sales=new Vector<Sale>();
		Sale s=null; 
		try {
			PreparedStatement ps=conn.prepareStatement("select * from `sales` ");
			ResultSet rsUser=ps.executeQuery();
			while(rsUser.next()){
				s=new Sale();
				s.setId(new Integer(rsUser.getInt("ID")));
				s.setProduct(rsUser.getString("product"));
				s.setRegion(rsUser.getString("region"));
				s.setDate(rsUser.getString("date"));
				s.setQuantite(new Integer(rsUser.getString("quantite")));
				s.setCost(Double.parseDouble(rsUser.getString("cost")));
				s.setAmt(Double.parseDouble(rsUser.getString("amt")));
				s.setTax(Double.parseDouble(rsUser.getString("tax")));
				s.setTotal(Double.parseDouble(rsUser.getString("total")));
				sales.add(s);
				
				
				}
			} catch (SQLException e) { e.printStackTrace();
			}
		return sales;
		}
	
	
	



	public void deleteSale(int id) {
		Connection conn=Utilitaire.getConnection();
		try {
			PreparedStatement ps=conn.prepareStatement("delete from `sales` where ID=? ");
			ps.setInt(1,id);
			ps.executeUpdate();
			} catch (SQLException e) { e.printStackTrace();
			}				}
	
	}
