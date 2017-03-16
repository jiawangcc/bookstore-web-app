package CSS490;

import java.io.Serializable;
import java.util.Date;

public class Rating implements Serializable{
	
	private int productId; 
	private int customerId;
	private int rating;
	private Date date;
	
	public Rating(){
		
	}
	
	public void setProductId(int productId){
		this.productId = productId;
	}
	
	public int getProductId(){
		return productId;
	}
	
	public void setCustomerId(int customerId){
		this.customerId = customerId;
	}
	
	public int getCustomerId(){
		return customerId;
	}
	
	public void setRating(int rating){
		this.rating = rating;
	}
	
	public int getRating(){
		return rating;
	}
	
	public void setDate(Date date){
		this.date = date;
	}
	
	public Date getDate(){
		return date;
	}
}
