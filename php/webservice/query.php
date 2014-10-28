<?php
	include("config.php");
	session_start();
	if($_SERVER["REQUEST_METHOD"] == "POST")
	{
		// username and password sent from Form
		$sql="SELECT * FROM users";
		$result=mysqli_query($db,$sql);
		$count = 0;
		while($row=mysqli_fetch_array($result,MYSQLI_ASSOC))
		{
			$response[$count]["count"] = $count;
			$response[$count]["username"] = $row['username'];
			$response[$count]["password"] = $row['password'];
			$count++;
		}
		
		die(json_encode(array('user' => $response)));
	}
?>
<form action="query.php" method="post">
<input type="submit" value=" Submit "/><br />
</form>