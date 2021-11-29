<?php  
 //filter.php 
 
$servername = "localhost";

// REPLACE with your Database name
$dbname = "";
// REPLACE with Database user
$username = "";
// REPLACE with Database user password
$password = "";

 if(isset($_POST["from_date"], $_POST["to_date"]))  
 {  
      $connect = mysqli_connect($servername, $username, $password, $dbname);  
      $output = '';
      $query = "  
           SELECT * FROM Sensor  
           WHERE reading_time BETWEEN '".$_POST["from_date"]."' AND '".$_POST["to_date"]."' ORDER BY reading_time DESC 
      ";  
      $result = mysqli_query($connect, $query);  
      $output .= '
    <table align="center" class="table table-bordered">
	<thead>
	<tr> 
		<th colspan="6"><h2>Историја на измерени вредности</h2></th> 
		</tr> 
			  <th> ID </th> 
			  <th> Температура </th> 
			  <th> Влажност </th> 
			  <th> CO2 </th>
			  <th> PM2.5 </th>
			  <th> Време на запис </th>
			  
		</tr> 
      '; 
      if(mysqli_num_rows($result) > 0)  
      {  
           while($row = mysqli_fetch_array($result))  
           {  $output .= ' 
                <tr>  
                    <td>'. $row["id"] .'</td>  
                    <td>'. $row["value1"] .'</td>  
                    <td>'. $row["value2"] .'</td>  
                    <td>'. $row["value3"] .'</td>  
                    <td>'. $row["value4"] .'</td>  
                    <td>'. $row["reading_time"] .'</td> 
                </tr>   
            '; 
           }  
      }  
      else  
      {   $output .= '  
                <tr>  
                     <td colspan="5">No Order Found</td>  
                </tr>  
           ';  
      }  
      $output .= '</table>';  
      echo $output;  
 }  
 ?>