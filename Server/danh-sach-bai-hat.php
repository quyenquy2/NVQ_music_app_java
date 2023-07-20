<?php
	require "connect.php";

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

	if(isset($_POST['idBaiHat'])){
		$idBaiHat = $_POST['idBaiHat'];
		$query = "SELECT * FROM BaiHat WHERE IdBaiHat = '$idBaiHat'";
	}
	
    if(isset($_POST['idAlbum'])){
		$idAlbum = $_POST['idAlbum'];
		$query = "SELECT * FROM BaiHat WHERE IdAlbum LIKE '%,$idAlbum,%'";
	}
	
	if(isset($_POST['idPlaylist'])){
		$idPlaylist = $_POST['idPlaylist'];
		$query = "SELECT * FROM BaiHat WHERE IdPlaylist LIKE '%,$idPlaylist,%'";
	}
	
	if(isset($_POST['idTheLoai'])){
		$idTheLoai = $_POST['idTheLoai'];
		$query = "SELECT * FROM BaiHat WHERE IdTheLoai LIKE '%,$idTheLoai,%'";
	}
	
	$arrayBaiHat= array();
	$data = mysqli_query($con, $query);
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