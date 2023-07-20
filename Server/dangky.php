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

	$query = "INSERT INTO `TaiKhoan`(`Username`, `Password`, `Email`) VALUES ('quyen','quyen','quyen')";

	if(isset($_POST['Username'])){
		$Username = $_POST['Username'];
        $password = $_POST['Password'];
        $Email = $_POST['Email'];
		$query = "INSERT INTO `TaiKhoan`(`Username`, `Password`, `Email`) VALUES ('$Username','$password','$Email')";
	}
	
    if ($con-> query($query) === TRUE) {
        $arrayTaikhoan= 'thành công';
	}else{
		$arrayTaikhoan= 'thất bại';
	}
	$con->close();
	
	
	echo json_encode($arrayTaikhoan);
?>