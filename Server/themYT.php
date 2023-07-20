<?php
	require "connect.php";

    $query="INSERT INTO `YeuThich`(`Username`, `IdBaiHat`) VALUES ('admin','8')";

	if(isset($_POST['Username'])){
		$Username = $_POST['Username'];
        $IdBaiHat = $_POST['IdBaiHat'];
        $query="INSERT INTO `YeuThich`(`Username`, `IdBaiHat`) VALUES ('$Username','$IdBaiHat')";
	}

    if ($con-> query($query) === TRUE) {
        $arrayBaiHat= 'thành công';
	}else{
		$arrayBaiHat= 'thất bại';
	}
	$con->close();
	
	
	echo json_encode($arrayBaiHat);
?>