<?php

   $userna = $_GET['username'];
   $passwo = $_GET['password'];
  // $passwo =md5($passwo);

   $newpassword = $_GET['newpassword'];
   $confirmnewpassword = $_GET['confirmnewpassword'];
  // $confirmnewpassword=md5($confirmnewpassword);
   //$newpassword=md5($newpassword);

  // server credentials
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "bpr";

// Create connection
$conn = new mysqli($servername,$username,$password,$dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$sql="SELECT * FROM agent where accno='".$userna."' ";
$result1 = $conn->query($sql);

$pass;

if ($result1->num_rows > 0) {
    // output data of each row
    while($row1 = $result1->fetch_assoc()) {
       $pass = $row1["password"] ;
       //$pass =md5($pass);

    }

       if($pass==$passwo){

           if($newpassword==$confirmnewpassword){


     $sql="update agent set password=".$newpassword." where accno='".$userna."'";
     $conn->query($sql);
    $arr = array('error'=>"error",'result'=>"success","message"=>"Hello Agent, password yawe yahindutse");

   echo json_encode($arr);



       }else{

        $arr = array('error'=>"error",'result'=>"failed","message"=>"New password and confirm password does not much");

   echo json_encode($arr);
       }


   }else{

      $arr = array('error'=>"error",'result'=>"failed","message"=>"Old password is wrong");

   echo json_encode($arr);

   }

}else{

    $arr = array('error'=>"error",'result'=>"failed","message"=>"Invalid Username");

   echo json_encode($arr);
}

$conn->close();


?>