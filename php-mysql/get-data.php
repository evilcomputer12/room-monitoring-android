<?php


$servername = "localhost";

// REPLACE with your Database name
$dbname = "";
// REPLACE with Database user
$username = "";
// REPLACE with Database user password
$password = "";

// Keep this API Key value to be compatible with the ESP32 code provided in the project page. 
// If you change this value, the ESP32 sketch needs to match
$api_key_value = "";

$api_key= $value1 = $value2 = $value3 = "";

if ($_SERVER["REQUEST_METHOD"] == "GET") {
  
        // Create connection
        $conn = new mysqli($servername, $username, $password, $dbname);
        // Check connection
        if ($conn->connect_error) {
            die("Connection failed: " . $conn->connect_error);
        } 

        $sql1 = "SELECT * FROM SensorMomentary";
        $result=mysqli_query($conn,$sql1);

        $_ResultSet = array();
        while ($row = mysqli_fetch_assoc($result)) {
           $_ResultSet[] = $row;
        }
           header("Content-type: application/json; charset=utf-8");
           echo json_encode($_ResultSet); 
    
        $conn->close();
}
else {
    echo "";
}

function test_input($data) {
    $data = trim($data);
    $data = stripslashes($data);
    $data = htmlspecialchars($data);
    return $data;
}
