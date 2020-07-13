<?php

//$transId=$_GET['transId'];
$senderAccount=$_GET['senderAccount'];
$doneDate=$_GET['doneDate'];
$accoudestination=$_GET['accoudestination'];
$type="transfer";
$agentId=$_GET['agentId'];
$amount=$_GET['amount'];
$comments="good";

//senderAccount=44-11&transId=null&doneDate=2015-04-19&agentId=1&accoudestination=44-22&type=Transfer&amount=1000&comments=good

//try to insert in table message

$messagecontent="Wohereje amafaranga:".$amount."Avuye Kuri Konti:".$senderAccount."Ajya kuri Konti yawe".$accoudestination."Murakoze!!!";


$messagreceiver="wakiriye amafaranga:".$amount."Avuye kuri Konti:".$senderAccount."Ajya
kuri Konti yawe".$accoudestination."Murakoze!!!";

for($transId=1;$transId<=100;$transId++){

    $transId.=mt_rand(0,9);;

}


for($i=1;$i<=100;$i++){

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
$conn = new mysqli($servername, $username, $password1, $dbname);
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
$sql2 = "update account set amount  ='".$newSenderBalance."' where accno='".$senderAccount."'  ";
$sql3 = "update account set amount  ='".$newReceiverBalance."' where accno='".$accoudestination."' ";

  
if ($conn->query($sql2) === TRUE && $conn->query($sql3)===TRUE ) {


  //THESE CODE HELP TO INSERT INT TABLE MESSAGES

$sql5="insert into message(accno,Content,doneDate,messageId)values('$senderAccount','$messagecontent','$doneDate','$j')";
$sql6="insert into message(accno,Content,doneDate,messageId)values('$accoudestination','$messagreceiver','$doneDate','$i')";
$sql4="insert into transaction(transId,Main_account,doneDate,agentId,amount,type,accountDest,comments)values
      ('$transId','$senderAccount','$doneDate','$agentId','$amount','$type','$accoudestination','$comments')";

if($conn->query($sql5)===TRUE && $conn->query($sql6)===TRUE && $conn->query($sql4)===TRUE){

$arr = array('error' => "no error", 'result' => "success", "message"=>"Wohereje Neza!!Urakoze");

 echo json_encode($arr);

 }else{

$arr = array('error'=> "error",'result' =>"failed","message"=>"Kubikuza ntibikunze!!!!");
   echo json_encode($arr);

}

   }else{

    $arr = array('error'=> "error",'result' =>"failed","message"=>"Konti washyizemo ntizibaho");
   echo json_encode($arr);
    }


    }else{
          $arr = array('error'=> "error",'result' =>"failed","message"=>"Konti y'uwohererezwa ntibaho!!!!");

          echo json_encode($arr);}
     
   }else{

    $arr = array('error'=> "error",'result' =>"failed","message"=>"Amafaranga yawe ntahagije!!");

   echo json_encode($arr);
 }

  }else{

    $arr = array('error'=> "error",'result' =>"failed","message"=>"konte yawe ntizwi!!!!");
   echo json_encode($arr);

    }  




//HOW TO UNLOCK ACCOUNT

}else{

   $arr = array('error'=> "error",'result' =>"failed","message"=>"Please your account is Locked,Please contact admin");

   echo json_encode($arr);

}

}else{

 $arr = array('error'=> "error",'result' =>"failed","message"=>"Error,Please try Again");

   echo json_encode($arr);

}

   
$conn->close();

?>

