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
	$query = "SELECT * FROM TaiKhoan WHERE Username = '$Username'";

    if (isset($_POST['Username'])) {
        $Username = $_POST['Username'];
        $query = "SELECT * FROM TaiKhoan WHERE Username = '$Username'";
    }
            $data = mysqli_query($con, $query);
        if ($row = mysqli_fetch_assoc($data)) {
            $taiKhoan = new TaiKhoan($row['Id'], $row['Username'], $row['Password'], $row['Email']);
            echo json_encode($taiKhoan);
        } else {
            echo "Tài khoản không tồn tại";
        }
?>
