package CSS490;

import java.io.Serializable;
import java.util.Date;

public class Book implements Serializable{
	
	private int productId; 
	private String title;
	private int amount;
	private double cost;
	private double price;
	private String author;
	private String category;
	
	public Book(){
		
	}
	
	public void setProductId(int productId){
		this.productId = productId;
	}
	
	public int getProductId(){
		return productId;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setAmount(int amount){
		this.amount = amount;
	}
	
	public int getAmount(){
		return amount;
	}
	
	public void setCost(double cost){
		this.cost = cost;
	}
	
	public double getCost(){
		return cost;
	}
	
	public void setPrice(double price){
		this.price = price;
	}
	
	public double getPrice(){
		return price;
	}
	
	public void setAuthor(String author){
		this.author = author;
	}
	
	public String getAuthor(){
		return author;
	}
	
	public void setCategory(String category){
		this.category = category;
	}
	
	public String getCategory(){
		return category;
	}
}
