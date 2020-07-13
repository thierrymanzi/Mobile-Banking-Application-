<?php
//parameter
$var1 = $_GET['var1'];
$var2 = $_GET['var2'];


//'".$startDate."'
//accountNumber,pin

 //server credentials
 $servername = "localhost";
$username = "root";
$password = "";
$dbname = "bpr";

$con = new mysqli($servername, $username, $password, $dbname);
if($con->connect_error) {
 die("Connection failed: ".$con->connect_error);
} 
//forming a query

$que="select * from agent where accno='".$var1."' and password='".$var2."'";
$result=$con->query($que);
$agid;
if($result->num_rows>0){
	
  
    while($row = $result->fetch_assoc()) {
       $agid=$row['agentId'];
    }
      $logMessage="Login Successfully";
 $arr = array('error'=> "no error", 'result'=>"success",'agentId'=>$agid,'message'=>$logMessage);
   
   }else{

$logMessage="Invalid accno or password";
  
  $arr = array('error' => "error",'result'=>"Fail",'message'=>$logMessage);
   
    }
   echo json_encode($arr);


?>