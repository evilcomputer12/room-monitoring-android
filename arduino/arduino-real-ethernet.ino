/*
  Web client
 This sketch connects to a website (http://www.google.com)
 using an Arduino Wiznet Ethernet shield.
 Circuit:
 * Ethernet shield attached to pins 10, 11, 12, 13
 created 18 Dec 2009
 by David A. Mellis
 modified 9 Apr 2012
 by Tom Igoe, based on work by Adrian McEwen
 */

#include <SPI.h>
#include <Ethernet.h>

// Enter a MAC address for your controller below.
// Newer Ethernet shields have a MAC address printed on a sticker on the shield
byte mac[] = { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };

// if you don't want to use DNS (and reduce your sketch size)
// use the numeric IP instead of the name for the server:
//IPAddress server(74,125,232,128);  // numeric IP for Google (no DNS)
//char server[] = "www.google.com";    // name address for Google (using DNS)

// Set the static IP address to use if the DHCP fails to assign
IPAddress ip(192, 168, 0, 177);
IPAddress myDns(192, 168, 0, 1);

// Initialize the Ethernet client library
// with the IP address and port of the server
// that you want to connect to (port 80 is default for HTTP):
EthernetClient client;

#include <DHT.h>

#define DHTPIN 6

#define DHTTYPE DHT11

DHT dht(DHTPIN, DHTTYPE);

#define anInput     A0                        //analog feed from MQ135
#define digTrigger   2                        //digital feed from MQ135
#define co2Zero     55                        //calibrated CO2 0 level
#define led          13                        //led on pin 9

#define pm25Pin 3 
#define pm10Pin 5 

uint32_t timer;


// Variables to measure the speed
unsigned long beginMicros, endMicros;
unsigned long byteCount = 0;
bool printWebData = true;  // set to false for better speed measurement

void setup() {
  // You can use Ethernet.init(pin) to configure the CS pin
  //Ethernet.init(10);  // Most Arduino shields
  //Ethernet.init(5);   // MKR ETH shield
  //Ethernet.init(0);   // Teensy 2.0
  //Ethernet.init(20);  // Teensy++ 2.0
  //Ethernet.init(15);  // ESP8266 with Adafruit Featherwing Ethernet
  //Ethernet.init(33);  // ESP32 with Adafruit Featherwing Ethernet
  dht.begin();
  pinMode(anInput,INPUT);                     //MQ135 analog feed set for input
  pinMode(digTrigger,INPUT);                  //MQ135 digital feed set for input
  pinMode(led,OUTPUT);                        //led set for output
  Serial.begin(9600);

  pinMode(pm25Pin, INPUT); 
  pinMode(pm10Pin, INPUT); 
  // Open serial communications and wait for port to open:
  Serial.begin(9600);
//  while (!Serial) {
//    ; // wait for serial port to connect. Needed for native USB port only
//  }

  // start the Ethernet connection:
//  Serial.println("Initialize Ethernet with DHCP:");
//  if (Ethernet.begin(mac) == 0) {
//    Serial.println("Failed to configure Ethernet using DHCP");
//    // Check for Ethernet hardware present
//    if (Ethernet.hardwareStatus() == EthernetNoHardware) {
//      Serial.println("Ethernet shield was not found.  Sorry, can't run without hardware. :(");
//      while (true) {
//        delay(1); // do nothing, no point running without Ethernet hardware
//      }
//    }
//    if (Ethernet.linkStatus() == LinkOFF) {
//      Serial.println("Ethernet cable is not connected.");
//    }
//    // try to congifure using IP address instead of DHCP:
//    Ethernet.begin(mac, ip, myDns);
//  } else {
//    Serial.print("  DHCP assigned IP ");
//    Serial.println(Ethernet.localIP());
//  }
//  // give the Ethernet shield a second to initialize:
//  delay(1000);
//  Serial.print("connecting to ");
//  Serial.print(server);
//  Serial.println("...");
//
//  // if you get a connection, report back via serial:
//  if (client.connect(server, 80)) {
//    Serial.print("connected to ");
//  } else {
//    // if you didn't get a connection to the server:
//    Serial.println("connection failed");
//  }
//  beginMicros = micros();
}

void postData() {
  int co2now[10];                               //int array for co2 readings
  int co2raw = 0;                               //int for raw value of co2
  int co2comp = 0;                              //int for compensated co2 
  int co2ppm = 0;                               //int for calculated ppm
  int zzz = 0;                                  //int for averaging
  int grafX = 0;                                //int for x value of graph

  float t = dht.readTemperature();
  int h = dht.readHumidity();

   for (int x = 0;x<10;x++){                   //samplpe co2 10x over 2 seconds
    co2now[x]=analogRead(A0);
    delay(200);
  }

for (int x = 0;x<10;x++){                     //add samples together
    zzz=zzz + co2now[x];
    
  }
  co2raw = zzz/10;                            //divide samples by 10
  co2comp = co2raw - co2Zero;                 //get compensated value
  co2ppm = map(co2comp,0,1023,400,5000);      //map value for atmospheric levels


  String Sco2PPM = String(co2ppm);
  String temp = String(t);
  String hum = String(h);
  String pm2_5 = String(pulseIn(pm25Pin, HIGH, 1500000) / 1000 - 2);
  String pm10 = String(pulseIn(pm10Pin, HIGH, 1500000) / 1000 - 2);
  Serial.println(temp+" "+hum);
  Serial.println(Sco2PPM);
  Serial.println(pm2_5);
  Serial.println(pm10);
  String apiKeyValue = "tPmAT5Ab3j7F9";

  char server[] = "http://marvelroommonitor.000webhostapp.com";

  String data = "value1=" + temp + "&value2=" + hum + "&value3=" + Sco2PPM + "&value4=" + pm2_5 + "";
  if(Ethernet.begin(mac) == 0){
    Serial.println("Failed to configure Ethernet using DHCP");
    while(1);
  }

  Serial.println("Initialize Ethernet with DHCP:");
  if (Ethernet.begin(mac) == 0) {
    Serial.println("Failed to configure Ethernet using DHCP");
    // Check for Ethernet hardware present
    if (Ethernet.hardwareStatus() == EthernetNoHardware) {
      Serial.println("Ethernet shield was not found.  Sorry, can't run without hardware. :(");
      while (true) {
        delay(1); // do nothing, no point running without Ethernet hardware
      }
    }
    if (Ethernet.linkStatus() == LinkOFF) {
      Serial.println("Ethernet cable is not connected.");
    }
    // try to congifure using IP address instead of DHCP:
    Ethernet.begin(mac, ip, myDns);
  } else {
    Serial.print("  DHCP assigned IP ");
    Serial.println(Ethernet.localIP());
  }
  // give the Ethernet shield a second to initialize:
  delay(1000);
  Serial.print("connecting to ");
  Serial.print(server);
  Serial.println("...");

  // if you get a connection, report back via serial:
  if (client.connect(server, 80)) {
    Serial.print("connected to ");
  } else {
    // if you didn't get a connection to the server:
    Serial.println("connection failed");
  }
  beginMicros = micros();

  if (client.connect(server,80)){
      Serial.println("Connected to server");
      client.println("POST /post-data.php HTTP/1.1");
      client.println("Host: marvelroommonitor.000webhostapp.com");
      client.println("Content-Type: application/x-www-form-urlencoded");
      client.print("Content-Length: ");
      client.println(data.length());
      client.println();
      client.println(data);
      client.println();
      Serial.println(data);
      Serial.println(data.length());
  }else{
      Serial.println("Connection to server failed");
  }  
}
void loop() {
 while(client.connected()){
    if(client.available()){
      char c = client.read();
      Serial.print(c);  
    }
  }

  if (millis() > timer) {
  Serial.println("Sent Data");
  timer = millis() + 60000;
  postData();
  } 
}
