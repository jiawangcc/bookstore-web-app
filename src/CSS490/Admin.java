package CSS490;

import java.io.Serializable;

public class Admin implements Serializable{
 
	private String email;
	private String passwd; 
	
	public Admin(){		
	}
	

	
	public void setPasswd(String passwd){
		this.passwd = passwd;
	}
	
	public String getPasswd(){
		return passwd;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getEmail(){
		return email;
	}

}