package CSS490;

import java.io.Serializable;
import java.util.Date;

public class Trans implements Serializable{
	
	private int transactionId; 
	private int customerId;
	private int productId;
	private int quantity;
	private Date date;
	
	public Trans(){
		
	}
	
	public void setTransactionId(int transactionId){
		this.transactionId = transactionId;
	}
	
	public int getTransactionId(){
		return transactionId;
	}

	public void setCustomerId(int customerId){
		this.customerId = customerId;
	}
	
	public int getCustomerId(){
		return customerId;
	}
	
	public void setProductId(int productId){
		this.productId = productId;
	}
	
	public int getProductId(){
		return productId;
	}
	
	public void setQuantity(int quantity){
		this.quantity = quantity;
	}
	
	public int getQuantity(){
		return quantity;
	}
	
	public void setDate(Date date){
		this.date = date;
	}
	
	public Date getDate(){
		return date;
	}
}
