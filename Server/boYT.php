<?php
	require "connect.php";

    $query="DELETE FROM `YeuThich` WHERE Username='admin' and IdBaiHat='8' ";


	if(isset($_POST['Username'])){
		$Username = $_POST['Username'];
        $IdBaiHat = $_POST['IdBaiHat'];
        $query="DELETE FROM `YeuThich` WHERE Username='$Username' and IdBaiHat='$IdBaiHat' ";
	}

    if ($con-> query($query) === TRUE) {
        $arrayBaiHat= 'thành công';
	}else{
		$arrayBaiHat= 'thất bại';
	}
	$con->close();
	
	
	echo json_encode($arrayBaiHat);
?>