<!DOCTYPE html>
<html lang="en">
<!-- Designed of this is based on 'main.html' file -->

<head>
    
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" integrity="sha256-eZrrJcwDc/3uDhsdt61sL2oOBY362qM3lon1gyExkL0=" crossorigin="anonymous" />
    <title>Web Air Quality Monitor</title>
    
    <style>
    
    
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        h3 {
            position:absolute;
            top: 150px;
        }

        body {
            height: 100vh;
            /* background-color: #ffd900; */
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .skill {
            width: 160px;
            height: 160px;
            position: relative;
        }

        .outer {
            height: 160px;
            width: 160px;
            border-radius: 50%;
            padding: 20px;
            box-shadow: 6px 6px 10px 1px #18181826, -6px -6px 10px -1px #ffffffb3;
        }

        .inner {
            height: 120px;
            width: 120px;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 50%;
            background-color: #fff;
            box-shadow: inset 4px 4px 6px -1px rgba(0, 0, 0, 0.2), inset -4px -4px 6px -1px rgba(255, 255, 255, 0.7), -0.5px -0.5px 0px rgba(255, 255, 255, 1), -0.5px -0.5px 0px rgba(0, 0, 0, 0.15), 0px 12px 10px -10px rgba(0, 0, 0, 0.05);
        }

        #number {
            font-size: 20px;
            font-weight: 600;
            font-family: 'Courier New', Courier, monospace;
        }

        #k1 {
            fill: none;
            stroke: url(#GradientColor);
            stroke-width: 20px;
            stroke-dasharray: 439;
            stroke-dashoffset: 439;
        }

        #s1 {
            position: absolute;
            top: 0;
            left: 0;
        }
        
        .skill2 {
            width: 160px;
            height: 160px;
            position: relative;
        }

        .outer2 {
            height: 160px;
            width: 160px;
            border-radius: 50%;
            padding: 20px;
            box-shadow: 6px 6px 10px 1px #18181826, -6px -6px 10px -1px #ffffffb3;
        }

        .inner2 {
            height: 120px;
            width: 120px;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 50%;
            background-color: #fff;
            box-shadow: inset 4px 4px 6px -1px rgba(0, 0, 0, 0.2), inset -4px -4px 6px -1px rgba(255, 255, 255, 0.7), -0.5px -0.5px 0px rgba(255, 255, 255, 1), -0.5px -0.5px 0px rgba(0, 0, 0, 0.15), 0px 12px 10px -10px rgba(0, 0, 0, 0.05);
        }

        #number2 {
            font-size: 20px;
            font-weight: 600;
            font-family: 'Courier New', Courier, monospace;
        }

        #k2 {
            fill: none;
            stroke: url(#GradientColor);
            stroke-width: 20px;
            stroke-dasharray: 439;
            stroke-dashoffset: 439;
        }

        #s2 {
            position: absolute;
            top: 0;
            left: 0;
        }
    </style>
</head>

<body>
    
    <audio id="alarm" src="https://github.com/evilcomputer12/room-monitoring-android/blob/main/android-5+/airq/app/src/main/res/raw/alarm.mp3 preload="auto"></audio>
    
    <h3 style="font-weight: 600;font-family: 'Courier New', Courier, bold;">Мониторинг на квалитет на воздух</h3>
    <div class="skill">
        <div class="outer">
            <div class="inner">
                <div id="number">

                </div>
            </div>
        </div>


        <svg id="s1" xmlns="http://www.w3.org/2000/svg" version="1.1" width="160px" height="160px" class="progress">
            <defs>
                <linearGradient id="GradientColor">
                     <stop offset="0%" stop-color="red" />
                     <stop offset="50%" stop-color="yellow" />
                          <stop offset="100%" stop-color="green" />

                <!--
                    <stop offset="0%" stop-color="#000000d7 " />
                    <stop offset="200%" stop-color="#000000d7 " /> -->
                </linearGradient>
            </defs>

            <circle id = "k1" class="progressCircle" cx="80" cy="80" r="70" stroke-linecap="round" fill='transparent'
                stroke='black' stroke-width='5' />
            <!-- text Count -->
            <text class="loading" fill='black' x='80' y='80' text-anchor='middle' alignment-baseline='middle'></text>
        </svg>

        
    </div>
    
    <br>
    <br>
    <br>
    
    <div class="skill2">
        <div class="outer2">
            <div class="inner2">
                <div id="number2">

                </div>
            </div>
        </div>


        <svg id="s2" xmlns="http://www.w3.org/2000/svg" version="1.1" width="160px" height="160px" class="progress2">
            <defs>
                <linearGradient id="GradientColor2">
                     <stop offset="0%" stop-color="red" />
                     <stop offset="50%" stop-color="yellow" />
                          <stop offset="100%" stop-color="green" />

                <!--
                    <stop offset="0%" stop-color="#000000d7 " />
                    <stop offset="200%" stop-color="#000000d7 " /> -->
                </linearGradient>
            </defs>

            <circle id = "k2" class="progressCircle2" cx="80" cy="80" r="70" stroke-linecap="round" fill='transparent'
                stroke='black' stroke-width='5' />
            <!-- text Count -->
            <text class="loading" fill='black' x='80' y='80' text-anchor='middle' alignment-baseline='middle'></text>
        </svg>
        <i class="fa fa-thermometer-half" style="font-size:60px;color:red;margin-left: -20px;"></i>
        <h5 id="temp" style="font-size: 20px;font-weight: 600;font-family: 'Courier New', Courier, monospace;min-width: 214px;margin-left: -20px;"></h5>
        <i class="fa fa-tint" style="font-size:60px;color:blue;margin-left: -20px;"></i>
        <h5 id="hum" style="font-size: 20px;font-weight: 600;font-family: 'Courier New', Courier, monospace;min-width: 214px;margin-left: -20px;"></h5>
        <h5 style="font-size: 15px;font-weight: 600;font-family: 'Courier New', Courier, monospace;margin-left: -60px;">Последно мерење: </h5>
        <h5 id="lu" style="font-size: 15px;font-weight: 600;font-family: 'Courier New', Courier, monospace;margin-left: -60px;"></h5>
    </div>

    <!-- Jquery CDN -->
    <script src="https://code.jquery.com/jquery-3.6.0.js"
        integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
    </script>
    
    <script>
        $(document).ready(function(){
            $('body').find('img[src$="https://cdn.000webhost.com/000webhost/logo/footer-powered-by-000webhost-white2.png"]').parent().closest('a').closest('div').remove();
        });
</script>

<script>
$(document).ready(function() {
    startLoop();
});

var frequency = 60000; // 5 seconds in miliseconds
var interval = 0;
// STARTS and Resets the loop
function startLoop() {
    loadData();
    if (interval > 0) clearInterval(interval); // stop
    interval = setInterval("loadData()", frequency); // run
}

function loadData() {
    $.ajax({
        type: "GET",
        url: "get-data.php"
    }).done(function(data) {
        console.log(data);
        var values = JSON.parse(JSON.stringify(data));
        //console.log(values);
        doStuf(values[0]["value1"], values[0]["value2"], values[0]["value3"], values[0]["value4"], values[0]["reading_time"]);
    });
    
}
        
function notifyMe() {
  // Let's check if the browser supports notifications
  if (!("Notification" in window)) {
    alert("Веб пребарувачот не подржува нотификации");
  }

  // Let's check whether notification permissions have already been granted
  else if (Notification.permission === "granted") {
    // If it's okay let's create a notification
    var notification = new Notification("Има големо загадување во просторијата !");
  }

  // Otherwise, we need to ask the user for permission
  else if (Notification.permission !== "denied") {
    Notification.requestPermission().then(function (permission) {
      // If the user accepts, let's create a notification
      if (permission === "granted") {
        var notification = new Notification("Има големо загадување во просторијата !");
      }
    });
  }
}
      
        
function doStuf(value1, value2, value3, value4, reading_time) {
        if(value4 > 100 || value3 > 1000) {
            //document.getElementById('alarm').play();
            //alert("Има големо загадување во просторијата !");
            notifyMe();
        }else {alert(reading_time);}
    const circle = document.querySelector('.progressCircle');
        var number = document.getElementById('number');
        var counter = 0;
        var fcount = value4;
        var duration = 0;
        const circumfarance = circle.getTotalLength();
        console.log(circumfarance);


        // JQuery for animation of stroke
        $(document).ready(function () {
            $('#k1').animate({
                'stroke-dashoffset': circumfarance - (fcount / 500) * circumfarance
            }, {
                duration: duration,
                easing: "linear",
            })
        })
        
    number.innerHTML = fcount + "ug/m3";
    
    const circle2 = document.querySelector('.progressCircle2')
        var number2 = document.getElementById('number2')
        var counter2 = 0;
        var fcount2 = value3;
        var duration2 = 0;
        const circumfarance2 = circle2.getTotalLength();
        console.log(circumfarance2);


        // JQuery for animation of stroke
        $(document).ready(function () {
            $('#k2').animate({
                'stroke-dashoffset': circumfarance2 - (fcount2 / 2000) * circumfarance2
            }, {
                duration: duration2,
                easing: "linear",
            })
        })
        number2.innerHTML = fcount2 + "ppm";
        
    
        
        let val1 = value1;
        let val2 = value2;
        let datetime = reading_time;
        let temp = document.getElementById('temp')
        let hum = document.getElementById('hum')
        let lu = document.getElementById("lu")
        temp.innerHTML = val1+"°C";
        hum.innerHTML = val2+"%";
        lu.innerHTML = datetime;
        
        //emp.replaceWith(temp.innerHTML + val1+"°C");
        //hum.replaceWith(hum.innerHTML + val2+"%");
        //lu.replaceWith(lu.innerHTML+datetime);
        
        
}
</script>


</body>

</html>
