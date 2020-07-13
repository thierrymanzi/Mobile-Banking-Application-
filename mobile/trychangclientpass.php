<?php
//parameter

$accno=$_GET['accountNumber'];
$oldpassword=$_GET['oldpassword'];
$newpassword= $_GET['newpassword'];
$repeat_newpassword=$_GET['repeat_newpassword'];

   //accountNumber,oldpassword,newpassword,repeat_newpassword


// server credentials
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "bpr";


$con = new mysqli($servername,$username,$password,$dbname);
if($con->connect_error) {
 die("Connection failed: " . $con->connect_error);
} 

//forming a query
$que="select * from customer  where customerId=(select customerId from accountdetails where accno='".$accno."')";
$result=$con->query($que);

$oldpasswordi;
if($result->num_rows >0){
    
     while($row= $result->fetch_assoc()){
    
    $oldpasswordi=$row["password"];

  }
            
      if ($oldpasswordi==$oldpassword){

             if ($newpassword==$repeat_newpassword){


                   $querychange ="UPDATE customer SET password = '$newpassword' WHERE customerId=(select customerId from accountdetails where accno='".$accno."')";
                    $con->query($querychange);
                     
                    $logMessage="Pin yawe yahindutse Urakoze!!!!!!";

                  $arr = array('error' => "no error", 'result' => "success", "message"=>$logMessage);

            
        }else{

            $arr = array('error' => "error", 'result' => "success", "message"=>"Imibare y'ibanga mishya ntago isa!!!");

        }

}else{

       $arr = array('error' => "error", 'result' => "Fail", "message"=>"umubare wibanga wakoreshaga mbere siwo!!!");

}
 
}
  else{

       $arr = array('error' => " error", 'result' => "Fail", "message"=>"Konti Washyizemo ntibaho Wongere Mukanya!!!");

}


   echo json_encode($arr);

?>