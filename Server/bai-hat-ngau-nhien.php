<?php
	require "connect.php";
	$query = "SELECT * FROM BaiHat LIMIT 3";
	$data = mysqli_query($con, $query);

	class BaiHat{
		function BaiHat($IdBaiHat, $IdAlbum, $IdPlaylist, $IdTheLoai ,$TenBaiHat, $TenCaSi, $HinhAnhBaiHat, $LinkBaiHat){
			$this->IdBaiHat = $IdBaiHat;
			$this->IdAlbum = $IdAlbum;
			$this->IdPlaylist = $IdPlaylist;
			$this->IdTheLoai = $IdTheLoai;
			$this->TenBaiHat = $TenBaiHat;
			$this->TenCaSi = $TenCaSi;
			$this->HinhAnhBaiHat = $HinhAnhBaiHat;
			$this->LinkBaiHat = $LinkBaiHat;
		}
	}
	
	$arrayBaiHat= array();
	while($row = mysqli_fetch_assoc($data)){
		array_push($arrayBaiHat, new BaiHat($row['IdBaiHat']
												,$row['IdAlbum']
												,$row['IdPlaylist']
												,$row['IdTheLoai']
												,$row['TenBaiHat']
												,$row['TenCaSi']
												,$row['HinhAnhBaiHat']
												,$row['LinkBaiHat']));
												 
	}
	
	echo json_encode($arrayBaiHat);
?>