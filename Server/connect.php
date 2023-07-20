<?php
	$hostname = "localhost";
	$username = "id17383392_thanhliem";
	$password = "abc-123-Abc-123";
	$databasename = "id17383392_musicdatabase";
	
	$con = mysqli_connect($hostname, $username, $password, $databasename);
	if($con)
		echo "";
	else
		echo "That bai";
	
	mysqli_query($con, "SET NAMES 'utf8'");

	
?>