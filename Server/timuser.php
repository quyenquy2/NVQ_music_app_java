<?php
	require "connect.php";

	class TaiKhoan{
		function TaiKhoan($Id, $Username, $Password, $Email){
			$this->Id = $Id;
			$this->Username = $Username;
			$this->Password = $Password;
			$this->Email = $Email;
		}
	}

	$Username = 'admin';
	$query = "SELECT * FROM TaiKhoan WHERE Username = '$Username'";

	if(isset($_POST['Username'])){
		$Username = $_POST['Username'];
		$query = "SELECT * FROM TaiKhoan WHERE Username = '$Username'";
	}
	
	$arrayTaiKhoan= array();
	$data = mysqli_query($con, $query);
	while($row = mysqli_fetch_assoc($data)){
		array_push($arrayTaiKhoan, new TaiKhoan($row['Id']
												,$row['Username']
												,$row['Password']
												,$row['Email']
				));
												 
	}
	
	echo json_encode($arrayTaiKhoan);
?>