<?php
	include("config.php");
	session_start();
	if($_SERVER["REQUEST_METHOD"] == "POST")
	{
		// username and password sent from Form
		$myusername=mysqli_real_escape_string($db,$_POST['username']); 
		$mypassword=mysqli_real_escape_string($db,$_POST['password']); 

		$sql="SELECT id FROM users WHERE username='$myusername' and password='$mypassword'";
		$result=mysqli_query($db,$sql);
		$row=mysqli_fetch_array($result,MYSQLI_ASSOC);
		$count=mysqli_num_rows($result);


		// If result matched $myusername and $mypassword, table row must be 1 row
		if($count==1)
		{
		        $response["success"] = 1;
		        $response["message"] = "Login successful!";
		        die(json_encode($response));
		}
		else 
		{
		        $response["success"] = 0;
		        $response["message"] = "Invalid Credentials!";
		        die(json_encode($response));
		}
	}
?>
<form action="login.php" method="post">
<label>UserName :</label>
<input type="text" name="username"/><br />
<label>Password :</label>
<input type="password" name="password"/><br/>
<input type="submit" value=" Submit "/><br />
</form>