<?php
	session_start();
	if(session_destroy())
	{
		$response["success"] = 1;
		$response["message"] = "Logout successful!";
		die(json_encode($response));
	}
?>