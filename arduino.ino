#include <UIPEthernet.h>

EthernetClient client;
uint8_t mac[6] = {0x00,0x01,0x02,0x03,0x04,0x05};

#include <DHT.h>

#define DHTPIN 8

#define DHTTYPE DHT11

DHT dht(DHTPIN, DHTTYPE);

#define anInput     A0                        //analog feed from MQ135
#define digTrigger   2                        //digital feed from MQ135
#define co2Zero     55                        //calibrated CO2 0 level
#define led          13                        //led on pin 9

uint32_t timer;

void setup() {
  dht.begin();
  pinMode(anInput,INPUT);                     //MQ135 analog feed set for input
  pinMode(digTrigger,INPUT);                  //MQ135 digital feed set for input
  pinMode(led,OUTPUT);                        //led set for output
  Serial.begin(9600);

  
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
  Serial.println(temp+" "+hum);
  Serial.println(Sco2PPM);

  String apiKeyValue = "tPmAT5Ab3j7F9";

  char server[] = "http://marvelroommonitor.000webhostapp.com";

  String data = "api_key=" + apiKeyValue + "&value1=" + temp + "&value2=" + hum + "&value3=" + Sco2PPM + "";

  if(Ethernet.begin(mac) == 0){
    Serial.println("Failed to configure Ethernet using DHCP");
    while(1);
  }

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
  timer = millis() + 60000;
  postData();
  }
}