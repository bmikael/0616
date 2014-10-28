<?php
	include("config.php");
	if($_SERVER["REQUEST_METHOD"] == "POST")
	{
		if(!empty($_POST['username']) && !empty($_POST['password']) && !empty($_POST['repassword']))
		{
			$myusername=mysqli_real_escape_string($db,$_POST['username']); 
			$mypassword=mysqli_real_escape_string($db,$_POST['password']); 
			$myrepassword=mysqli_real_escape_string($db,$_POST['repassword']); 
			if($mypassword != $myrepassword)
			{
				$response["success"] = 0;
				$response["message"] = "Password not consistent!!";
				die(json_encode($response));
			}

			$sql="SELECT username FROM users WHERE username='$myusername'";
			$result=mysqli_query($db,$sql);
			$row=mysqli_fetch_array($result,MYSQLI_ASSOC);
			$count=mysqli_num_rows($result);
			if($count>0)
			{
				$response["success"] = 0;
				$response["message"] = "Username has been registered!!";
				die(json_encode($response));
			}
			else
			{
				$sql="INSERT INTO users (username, password) VALUES('$myusername', '$mypassword')";
				$result=mysqli_query($db,$sql);
				if($result)
				{
			        $response["success"] = 1;
			        $response["message"] = "Your account was successfully created!!";
			        die(json_encode($response));
		        }
		        else
		        {
			        $response["success"] = 0;
			        $response["message"] = "Your account was registration failed!!";
			        die(json_encode($response));
		        } 
			}
		}
		else
		{
	        $response["success"] = 0;
	        $response["message"] = "Please Enter Both a Username and Password.";
	        die(json_encode($response));
		}
	}
?>
<form action="register.php" method="post">
<label>UserName :</label>
<input type="text" name="username"/><br />
<label>Password :</label>
<input type="password" name="password"/><br/>
<label>Re-Password :</label>
<input type="password" name="repassword"/><br/>
<input type="submit" value=" Register "/><br />
</form>
