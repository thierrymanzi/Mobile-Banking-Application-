<?php
//parameter

$uname=$_GET['username'];
$pwd=$_GET['password'];


// server credentials
$servername = "sql312.thefreecpanel.com";
$username = "freecp_24181690";
$password = "maurice!";
$dbname = "freecp_24181690_ebroker";


$con = new mysqli($servername, $username, $password, $dbname);
if($con->connect_error) {
 die("Connection failed: " . $con->connect_error);
} 

//forming a query
$que="select * from rider  where username='".$uname."'";
$result=$con->query($que);
$valid=false;
if($result->num_rows >0){
    
    while($row=$result->fetch_assoc()){
    if($row==$row["password"]){
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
     $logMessage="the user is not known";
    $arr = array('error' => "error", 'result' => "Fail", "message"=>$logMessage);
    
}

   echo json_encode($arr);

?>