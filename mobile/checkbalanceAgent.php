<?php

$senderAccount=$_GET['senderAccount'];
$pin=$_GET['pin'];


//senderAccount=44-11&transId=1&doneDate=2015-04-19&agentId=1&accoudestination=44-22&type=withdraw&amount=2000&comments=good;

 $servername = "localhost";
$username = "root";
$password = "";
$dbname = "bpr";


// Create connection
$conn = new mysqli($servername,$username,$password,$dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: ".$conn->connect_error);
} 



//forming a query
$que="select * from account where accno=(select accno from agent where password='".$pin."')";
//statement and execution 
//$result= mysqli_query($con, $que);
$result=$conn->query($que);
//retrieve data from array
//$row= mysqli_fetch_assoc($result);
//looping
$valid=false;

if($result->num_rows >0){
    
    //loopin
    while($row= $result->fetch_assoc()){

       $accountno= $row["accno"];

    if($senderAccount==$accountno){
          

$sql = "SELECT amount   FROM account where  accno='".$senderAccount."'";
$result = $conn->query($sql);
$balSender=0;

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
       $balSender = $row["amount"] ;
    }
$arr = array('error'=>"no error",'result'=>"success",'message'=>"Ufite Amafaranga:".$balSender);

 echo json_encode($arr);

} else {
   $arr = array('error' => "error", 'result' => "failed",'message'=>"Account Does not Exit!!!");
   echo json_encode($arr);
}

}else{

            $arr = array('error' => "error", 'result' => "failed", "message"=>"account doenst match!!");
   echo json_encode($arr);
        }
}
    }else{

            $arr = array('error' => "error", 'result' => "failed", "message"=>"AccountNo or Password Doesn't Exist");
   echo json_encode($arr);
        }
  

$conn->close();


?>