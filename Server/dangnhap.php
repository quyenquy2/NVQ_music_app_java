<?php
    require "connect.php";

    class TaiKhoan {
        function TaiKhoan($Id, $Username, $Password, $Email) {
            $this->Id = $Id;
            $this->Username = $Username;
            $this->Password = $Password;
            $this->Email = $Email;
        }
    }
    $Username = 'admin';
    $Password = 'quyen123';
	$query = "SELECT * FROM TaiKhoan WHERE Username = '$Username' and Password ='$Password'";

    if (isset($_POST['Username'])) {
        $Username = $_POST['Username'];
        $Password = $_POST['Password'];
        $query = "SELECT * FROM TaiKhoan WHERE Username = '$Username' and Password ='$Password'";
    }
            $data = mysqli_query($con, $query);
        if ($row = mysqli_fetch_assoc($data)) {
            $taiKhoan = new TaiKhoan($row['Id'], $row['Username'], $row['Password'], $row['Email']);
            echo json_encode($taiKhoan);
        } else {
            echo "Tài khoản không tồn tại";
        }
?>
