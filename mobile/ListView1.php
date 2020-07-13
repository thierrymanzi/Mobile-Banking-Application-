 <?php
 
// array for JSON response
$response = array();
 
// include db connect class
require_once 'db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
 
    // get a product from products table
    $result = mysql_query("SELECT * FROM message");
 if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["message"] = array();
 
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $product = array();
            $product["accno"] = $row["accno"];
            $product["Content"] = $row["Content"];
            $product["doneDate"] = $row["doneDate"];
			$product["messageId"] = $row["messageId"];
			
        // push single product into final response array
        array_push($response["message"], $product);
    }
    // success
    $response["success"] = 1;
 
    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No Message found";
 
    // echo no users JSON
    echo json_encode($response);
}
?>