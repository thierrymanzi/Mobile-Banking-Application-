<?php
//parameter

$accno=$_GET['accountNumber'];
$pin=$_GET['pin'];


// server credentials
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "bpr";


$con = new mysqli($servername, $username, $password, $dbname);
if($con->connect_error) {
 die("Connection failed: " . $con->connect_error);
} 

//forming a query
$que="select * from customer  where customerId=(select customerId from accountdetails where accno='".$accno."')";
//statement and execution 
//$result= mysqli_query($con, $que);
$result=$con->query($que);
//retrieve data from array
//$row= mysqli_fetch_assoc($result);
//looping
$valid=false;
if($result->num_rows >0){
    
    //loopin
    while($row= $result->fetch_assoc()){
    if($pin==$row["password"]){
            
            //json messages
            $valid=true;
            $logMessage="Login Successfully";

$arr = array('error' => "no error", 'result' => "success", "message"=>$logMessage);

            break;
        }
    }
    if($valid==false){
        
        $logMessage="password is invalid";
  $arr = array('error' => "error", 'result' => "Fail", "message"=>$logMessage);
   
        
    }
    
}else{
    
    //json messages
     $logMessage="the customer is not known";
    $arr = array('error' => "error", 'result' => "Fail", "message"=>$logMessage);
    
}



   echo json_encode($arr);

?>