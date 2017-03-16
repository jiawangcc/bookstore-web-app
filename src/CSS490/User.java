package CSS490;

import java.io.Serializable;

public class User implements Serializable{
	

	private String passwd; 
	private String name;
	private String email;
	
	public User(){		
	}
		
	public void setPasswd(String passwd){
		this.passwd = passwd;
	}
	
	public String getPasswd(){
		return passwd;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getEmail(){
		return email;
	}
	
}