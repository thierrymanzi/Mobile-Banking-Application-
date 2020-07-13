<?php


//$transId=$_GET['transId'];
$senderAccount=$_GET['senderAccount'];
$doneDate=$_GET['doneDate'];
$agentId=$_GET['agentId'];
$accoudestination  =$_GET['accoudestination'];
$type=$_GET['type'];
$amount=$_GET['amount'];
$comments=$_GET['comments'];
$pin=$_GET['pin'];


//senderAccount=44-11&transId=5&doneDate=2015-04-19&agentId=1&accoudestination=44-22&type=Transfer&amount=1000&comments=good&//pin=123

//try to insert in table message

$messagecontent="Ubikije amafaranga:".$amount."Avuye Kuri Konti Ya Agent:".$senderAccount."Ajya kuri Konti yawe".$accoudestination."Murakoze!!!";

for($transId=1;$transId<=100;$transId++){

    $transId.=mt_rand(0,9);;

}


for($i=1;$i<=100;$i++){

$i.= mt_rand(0,9);
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



//forming a query
$que="select * from customer  where customerId=(select customerId from accountdetails where accno='".$senderAccount."')";
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

       $pinnumber= $row["password"];

    if($pin==$pinnumber){
          

$sql = "SELECT amount   FROM account where  accno='".$senderAccount."'  ";
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
    $newReceiverBalance=$balReceiver+$amount;



     $sql2 = "update account set amount  ='".$newSenderBalance."' where accno='".$senderAccount."'  ";
     $sql3 = "update account set amount  ='".$newReceiverBalance."' where accno='".$accoudestination."' ";

  
if ($conn->query($sql2) === TRUE && $conn->query($sql3)==TRUE ) {


  //THESE CODE HELP TO INSERT INT TABLE MESSAGES


$sql5=("insert into message(accno,Content,doneDate,messageId)values('$senderAccount','$messagecontent','$doneDate','$i')");

if($conn->query($sql5)){

$arr = array('error' => "no error", 'result' => "success", "message"=>"Ubikije Neza Kuri yawe!!Urakoze");
   echo json_encode($arr);

}else{

$arr = array('error' => "error", 'result' => "failed", "message"=>"Please Try Again!!!!");
   echo json_encode($arr);

}



  
       $sql4=("insert into transaction(accountDest,agentId,amount,comments,doneDate,Main_account,transId,type)values
      ('$accoudestination','$agentId','$amount','$comments','$doneDate','$senderAccount','$transId','$type')");

     //$sql4=("insert into transaction(transId,Main_account,doneDate,agentId,amount,type,accountDest,comments)

                     // values('$transId','$senderAccount','$doneDate','$agentId','$amount','$type','$accoudestination','$comments')");

           if($conn->query($sql4)){

$arr = array('error' => "no error", 'result' => "success", "message"=>"Transfer done successfully");
   echo json_encode($arr);


     }else{


    $arr = array('error' => "no error", 'result' => "success", "message"=>"transaction done successfully");
   echo json_encode($arr);

     }

} else {
     $arr = array('error' => "error", 'result' => "failed", "message"=>"Please Try Again!!!!");
   echo json_encode($arr);
}




  }else{
$arr = array('error' => "error", 'result' => "failed", "message"=>"the receiver account not exist ");
   echo json_encode($arr);

  }


//


}else{

  $arr = array('error' => "error", 'result' => "failed", "message"=>"not enought balance for this transaction !!");
   echo json_encode($arr);
}


} else {
    $arr = array('error' => "error", 'result' => "failed", "message"=>"sender account or pin invalid !!");
   echo json_encode($arr);
}




            //json messages
            $valid=true;
            
            break;
        }else{

            $arr = array('error' => "error", 'result' => "failed", "message"=>"Your pin invalid !!");
   echo json_encode($arr);
        }
    }
    
    
}else{

   $arr = array('error' => "error", 'result' => "failed", "message"=>"You Entered Invalid Account!!");
   echo json_encode($arr);

}







$conn->close();


?>