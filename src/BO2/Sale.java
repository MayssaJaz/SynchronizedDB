package BO2;

import java.util.Map;

public class Sale {
	private int id;
	private String product;
	private String region;
	private String date;
	private int quantite;
	private double cost;
	private double amt;
	private double tax;
	private double total;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getQuantite() {
		return quantite;
	}
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public double getAmt() {
		return amt;
	}
	public void setAmt(double amt) {
		this.amt = amt;
	}
	public double getTax() {
		return tax;
	}
	public void setTax(double tax) {
		this.tax = tax;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
	public boolean hasChanged(Sale s) {
		if(s.getAmt()==amt && s.getCost()==cost && s.getDate()==date && s.getProduct()==product&& s.getQuantite()==quantite && s.getRegion()==region && s.getTax()==tax && s.getTotal()==total)
		return false;
		
		return true;
	}
	
	
}
