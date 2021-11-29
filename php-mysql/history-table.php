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
$api_key_value = "tPmAT5Ab3j7F9";

$api_key= $value1 = $value2 = $value3 = "";

  
        // Create connection
        $conn = new mysqli($servername, $username, $password, $dbname);
        // Check connection
        if ($conn->connect_error) {
            die("Connection failed: " . $conn->connect_error);
        } 

        $sql1 = "SELECT * FROM Sensor ORDER BY reading_time DESC";
        $result=mysqli_query($conn,$sql1);
        ?>
        
        <!DOCTYPE html> 
<html> 
	<head> 
		<title> Историја на измерени вредности </title>
		<meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <script src="https://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>  
        <link rel="stylesheet" href="https://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
        <link rel="stylesheet" href="styles.css">
	</head> 
	<body> 
	<!--<table align="center" border="1px" style="width:600px; line-height:40px;"> -->
	<br /><br />  
	<div class="container">
	<div class="col-md-3">  
    <input type="text" name="from_date" id="from_date" class="form-control" placeholder="From Date" />  
    </div>  
    <div class="col-md-3">  
    <input type="text" name="to_date" id="to_date" class="form-control" placeholder="To Date" />  
    </div>  
    <div class="col-md-5">  
    <input type="button" name="Филтрирај" id="filter" value="Филтрирај" class="btn btn-info" />  
    </div>  
    <div style="clear:both"></div>                 
    <br />
    <div class="table-responsive">
    <div id="order_table">
	<table align="center" class="table table-bordered table-condensed table-sm w-auto text-xsmall">
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
		
		<?php while($row = mysqli_fetch_assoc($result)) { ?> 
		<tr> <td><?php echo $row['id']; ?></td> 
		<td><?php echo $row['value1']; ?></td> 
		<td><?php echo $row['value2']; ?></td> 
		<td><?php echo $row['value3']; ?></td>
		<td><?php echo $row['value4']; ?></td>
		<td><?php echo $row['reading_time']; ?></td>
		</tr> 
        <?php  
            }  
            ?>  
        </table>  
            </div>  
        </div>
        </div>
      </body>  
 </html>
<script>
        $(document).ready(function(){
            $('body').find('img[src$="https://cdn.000webhost.com/000webhost/logo/footer-powered-by-000webhost-white2.png"]').parent().closest('a').closest('div').remove();
        });
</script>
</script>
<script>  
      $(document).ready(function(){  
           $.datepicker.setDefaults({  
                dateFormat: 'yy-mm-dd'   
           });
           $(function(){  
                $("#from_date").datepicker();  
                $("#to_date").datepicker();  
           });  
           $('#filter').click(function(){  
                var from_date = $('#from_date').val();  
                var to_date = $('#to_date').val();  
                if(from_date != '' && to_date != '')  
                {  
                     $.ajax({  
                          url:"filter.php",  
                          method:"POST",  
                          data:{from_date:from_date, to_date:to_date},  
                          success:function(data)  
                          {  
                               $('#order_table').html(data);  
                          }  
                     });  
                }  
                else  
                {  
                     alert("Please Select Date");  
                }  
           });  
      });  
 </script>
