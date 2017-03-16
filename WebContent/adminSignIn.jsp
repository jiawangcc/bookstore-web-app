<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/signin.css" rel="stylesheet">
<title>Administrator Sign In</title>
</head>
<body>

<h1>This is admin sign in page</h1>

      <form action="admin/login" method="post">

        <label for="email">Email: </label>
        <input type="text" id="email" name="email"/>
        <div id="feedback1" class="feedback"></div><br>

        <label for="password">Password: </label>
        <input type="password" id="password" name="password"/>
        <div id="feedback2" class="feedback"></div><br>

        <input type="submit" id="submit" value="Sign in"/>

      </form>


 <script>
   		var elMsg = document.getElementById('feedback1'); 
   		var elMsg1 = document.getElementById('feedback2');
		
		 
		function loginCheck(){
	        if(document.getElementById('email').value==""){
	                elMsg.textContent= "Pleaes enter your email";
	                document.getElementById('email').focus();
	                return false;
	        }
	        if(document.getElementById('password').value==""){
	                elMsg1.textContent = "Please enter your password";
	                document.getElementById('password').focus();
	                return false;
	        }
		}
		
		var elSubmit = document.getElementById('submit');
		elSubmit.onclick =loginCheck;
	</script>
</body>
</html>