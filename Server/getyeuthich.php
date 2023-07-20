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
    
    $query="SELECT BaiHat.* FROM BaiHat INNER JOIN YeuThich ON BaiHat.IdBaiHat = YeuThich.IdBaiHat WHERE YeuThich.Username = 'quyen2'";

	if(isset($_POST['Username'])){
		$Username = $_POST['Username'];
		$query="SELECT BaiHat.* FROM BaiHat INNER JOIN YeuThich ON BaiHat.IdBaiHat = YeuThich.IdBaiHat WHERE YeuThich.Username = '$Username'";
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