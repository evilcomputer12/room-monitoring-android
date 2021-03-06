$(document).ready(function() {
    startLoop();
});

if (cordova.platformId == 'android') {
    $("#menuToggle").css('top', '50px');
    $("#menuToggle").css('left', '15px');
}

$('html, body').css({
    overflow: 'hidden',
    height: '100%',
    weight: '100%'
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
        url: "http://marvelroommonitor.000webhostapp.com/get-data.php"
    }).done(function(data) {
        console.log(data);
        var values = JSON.parse(JSON.stringify(data));
        //console.log(values);
        doStuf(values[0]["value1"], values[0]["value2"], values[0]["value3"], values[0]["value4"], values[0]["reading_time"]);
    });
    
}

    function notifyMe(){
        
        cordova.plugins.notification.local.schedule({
            title: 'Аларм!',
            text: 'Има големо загадување во просторијата ! ',
            foreground: true,
            vibrate: true,
        });

        if (cordova.platformId == 'android') {
        var audio = new Audio('raw/alarm.mp3');
        audio.play();
        navigator.vibrate(1000);
        }
        

    }  
    
function doStuf(value1, value2, value3, value4, reading_time) {
        if(value4 > 100 || value3 > 1000) {
            notifyMe();
        }
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
        

        $( "#graph" ).click(function() {
            cordova.plugins.browser.open("https://marvelroommonitor.000webhostapp.com/chart-data.php", {readerMode: true});
          });
          $( "#tbl" ).click(function() {
            cordova.plugins.browser.open("https://marvelroommonitor.000webhostapp.com/history-table.php", {readerMode: true});
          });
          
}