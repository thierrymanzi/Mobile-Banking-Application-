<?php

//$transId=$_GET['transId'];
$senderAccount=$_GET['senderAccount'];
$doneDate=$_GET['doneDate'];
$accoudestination=$_GET['accoudestination'];
$pin=$_GET['pin'];
$agentId=$_GET['agentId'];
$type="Kubikuza";
$amount=$_GET['amount'];




$messagecontent="Uhaye umukiriya amafaranga:".$amount."Avuye Kuri Konti Yawe:".$senderAccount."Ajya kuri Konti yawe";


$messagreceiver="wakiriye amafaranga:".$amount."Avuye kuri Konti:".$accoudestination."Murakoaze";



for($transId=1;$transId<=1000;$transId++){

    $transId.=mt_rand(0,9);;

}


for($i=1;$i<=1000;$i++){

$i.= mt_rand(0,9);
//$messgid=5;
}


for($j=1;$j<=100;$j++){

$j.= mt_rand(0,9);
//$messgid=5;
}




// server credentials
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "bpr";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$status="Unlocked";
$sql = "SELECT *  FROM account where  accno='".$accoudestination."'  ";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
       $dbstatus = $row["status"] ;
    }
          if($dbstatus==$status){



$sql ="select password from customer  where customerId=(select customerId from accountdetails where accno='".$accoudestination."')";
$result = $conn->query($sql);
$balSender=0.0;

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
       $pinnum = $row["password"] ;
    }

      if($pin==$pinnum){

$sql = "SELECT *  FROM account where  accno='".$senderAccount."'  ";
$result = $conn->query($sql);
$balSender=0.0;

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
       $balSender = $row["amount"] ;
    }

    //check if sender has enought balance
if ($balSender>$amount){

  // check if receiver has account

$sql1 = "SELECT * FROM account where accno ='".$accoudestination."'  ";
$result1 = $conn->query($sql1);

$balReceiver=0.0;

if ($result1->num_rows > 0) {
    // output data of each row
    while($row1 = $result1->fetch_assoc()) {
       $balReceiver = $row1["amount"] ;
    }

    //perfom transaction

    $newSenderBalance=$balSender-$amount;
    $newReceiverBalance=$balReceiver-$amount;


$sql2 = "update account set amount  ='".$newSenderBalance."' where accno='".$senderAccount."' ";
$sql3 = "update account set amount  ='".$newReceiverBalance."' where accno='".$accoudestination."' ";

$sql5="insert into message(accno,Content,doneDate,messageId)values('$senderAccount','$messagecontent','$doneDate','$i')";
$sql6="insert into message(accno,Content,doneDate,messageId)values('$accoudestination','$messagreceiver','$doneDate','$j')";
$sql4="insert into transaction(transId,Main_account,doneDate,agentId,amount,type,accountDest)values
      ('$transId','$senderAccount','$doneDate','$agentId','$amount','$type','$accoudestination')";


   if($balSender-$amount>=5000 && $balReceiver-$amount>=5000){
   
if($conn->query($sql5)===TRUE && $conn->query($sql6)===TRUE && $conn->query($sql4)===TRUE) {

   if($conn->query($sql2) === TRUE && $conn->query($sql3)===TRUE){

      $arr = array('error'=>" no error",'result'=>"success","message"=>"Kubikuza Byagenze Neza ");
       echo json_encode($arr);


   }else{

       $arr = array('error'=>" no error",'result'=>"success","message"=>"update Fail");
       echo json_encode($arr);
   }

}else{

   $arr = array('error'=>"error",'result'=>"failed","message"=>"Kubikuza Ntibikunze,Mwongere mugerageze");

   echo json_encode($arr);

}

}else{
  $arr = array('error'=>"error",'result'=>"failed","message"=>"Sorry,Konti y'umwagenti cg umukiriya igomba gusigaraho 5000");

   echo json_encode($arr);
}

}else{

  $arr = array('error'=>"error",'result'=>"failed","message"=>"Reba neza niba konti washyizemo ariyo");

   echo json_encode($arr);
}


}else{

 $arr = array('error'=>"error",'result'=>"failed","message"=>"Mukiriya mwiza,Ufitemo amafaranga make");

   echo json_encode($arr);
}

}else{

 $arr = array('error'=>"error",'result' =>"failed","message"=>"konti washyizemo ntibaho");

   echo json_encode($arr);

}

}else{

  $arr = array('error'=> "error",'result'=>"failed","message"=>"Pin yawe ntibaho");

   echo json_encode($arr);
}

}else{

$arr = array('error'=> "error",'result' =>"failed","message"=>"Ibyo mwasabye ntibibashije kuboneka");

   echo json_encode($arr);

}





}else{

   $arr = array('error'=> "error",'result' =>"failed","message"=>"Please your account is Locked,contact admin,Tel:0787925387");

   echo json_encode($arr);

}

}else{

 $arr = array('error'=> "error",'result' =>"failed","message"=>"Error,Please try Again");

   echo json_encode($arr);

}


$conn->close();

?>