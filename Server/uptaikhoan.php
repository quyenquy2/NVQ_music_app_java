<?php
	require "connect.php";

    $Username='admin';
    $Password='quyen123';
    $query = "UPDATE `TaiKhoan` SET Password = '$Password' WHERE Username = '$Username'";

	if(isset($_POST['Username'])){
		$Username = $_POST['Username'];
        $Password = $_POST['Password'];
		$query = "UPDATE `TaiKhoan` SET Password = '$Password' WHERE Username = '$Username'";
	}

    if ($con-> query($query) === TRUE) {
        $arrayBaiHat= 'thành công';
	}else{
		$arrayBaiHat= 'thất bại';
	}
	$con->close();
	
	
	echo json_encode($arrayBaiHat);
?>