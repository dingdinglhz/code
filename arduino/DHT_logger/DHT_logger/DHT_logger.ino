#include <DHT.h>
#include <OneWire.h>
#include <DallasTemperature.h>
#include "U8glib.h"


DHT dht;
OneWire oneWire(2);
DallasTemperature sensors(&oneWire);
U8GLIB_MINI12864 u8g(10,9,7);
float temp;
char tmp[7]="T=__._";


void u8g_prepare(void) {
  u8g.setFont(u8g_font_6x10);
  u8g.setFontRefHeightExtendedText();
  u8g.setDefaultForegroundColor();
  u8g.setFontPosTop();
}

void draw(void) {
  u8g_prepare();
  u8g.drawStr( 0, 22, tmp);
}

void graphic_setup(){
  pinMode(13, OUTPUT);           
  digitalWrite(13, HIGH);  
}

void setup()
{
  Serial.begin(9600);
  Serial.println();
  Serial.println("Status\tHumidity (%)\tTemperature (C)\t DS18B20");

  dht.setup(8); // data pin 8
  sensors.begin();
  graphic_setup();
  
}

void loop()
{
  float humidity = dht.getHumidity();
  float temperature = dht.getTemperature();
  sensors.requestTemperatures();

  Serial.print(dht.getStatusString());
  Serial.print("\t");
  Serial.print(humidity, 2);
  Serial.print("\t\t");
  Serial.print(temperature, 2);
  Serial.print("\t\t");
  temp=sensors.getTempCByIndex(0);
  Serial.println(temp, 1);
  tmp[2]=int(temp/10)%10+'0';
  tmp[3]=int(temp)%10+'0';
  tmp[5]=int(temp*10)%10+'0';
  //sprintf(tmp,"T= %f",temp);
  //Serial.println(tmp);
  u8g.firstPage();  
  do {
    draw();
  } 
  while( u8g.nextPage() );
  delay(250);
}


