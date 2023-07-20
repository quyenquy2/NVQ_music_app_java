<?php
	require "connect.php";
	$query = "SELECT * FROM TheLoai";
	$data = mysqli_query($con, $query);

	class TheLoai{
		function TheLoai($IdTheLoai,$TenTheLoai, $HinhTheLoai){
			$this->IdTheLoai = $IdTheLoai;
			$this->TenTheLoai = $TenTheLoai;
			$this->HinhTheLoai = $HinhTheLoai;
		}
	}
	
	$arrayTheLoai = array();
	while($row = mysqli_fetch_assoc($data)){
		array_push($arrayTheLoai, new TheLoai($row['IdTheLoai']
												,$row['TenTheLoai']
												,$row['HinhTheLoai']));
												 
	}
	
	echo json_encode($arrayTheLoai);
?>