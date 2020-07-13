 <?php
 
// array for JSON response
$passedAc=$_GET['pass'];

$response = array();
 

// include db connect class

error_reporting(0);
require_once 'db_connect.php';

 
// connecting to db


$db = new DB_CONNECT();
  
  $result = mysql_query("SELECT * FROM tigo_stock");
 //$result = mysql_query("SELECT * FROM message ");
if (mysql_num_rows($result) > 0) {
    
// looping through all results
    
// products node
   
 $response["message"] = array();
 
  
  while ($row = mysql_fetch_array($result)) {
   
     // temp user array
       
 $product = array();
         
   $product["stock_id"] = $row["stock_id"];
     
       $product["prod_id"] = $row["prod_id"];
 
          $product["qty"] = $row["qty"];
	
			      
 // push single product into final response array
      
  array_push($response["message"],$product);
    }
 
   // success
    $response["success"] = 1;
 
   
 // echoing JSON response
    
echo json_encode($response);

} else {
    
// no products found
  
  $response["success"] = 0;
 
   $response["message"] = "No product found";
 
 
   // echo no users JSON
 
   echo json_encode($response);
}


?>