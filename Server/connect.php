<?php
// 	$hostname = "localhost";
// 	$username = "id17383392_thanhliem";
// 	$password = "abc-123-Abc-123";
// 	$databasename = "id17383392_musicdatabase";

	$servername = "localhost";
	$username = "id20599219_quyenquy2";
	$password = "5C[5_DMKEUhvqj-O";
	$databasename = "id20599219_mobileapp";
	
//	$con = mysqli_connect($hostname, $username, $password, $databasename);
    	$con = new mysqli($servername, $username, $password, $databasename);
// 	if($con)
// 		echo "";
// 	else
// 		echo "That bai";

    	if ($con->connect_error) {
		die("Connect fail: ".$con->connect_error);
	} else {
		//echo 'Connect successfull!';
	    //	echo "kết nối thành công";
	}
	
	mysqli_query($con, "SET NAMES 'utf8'");
	
?>

