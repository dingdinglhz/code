#include <DHT.h>
#include <OneWire.h>
#include <DallasTemperature.h>
#include <U8glib.h>
#include <DS1302.h>

#define ONE_WIRE_BUS 2

DHT dht;
OneWire oneWire(ONE_WIRE_BUS);
DallasTemperature sensors(&oneWire);
U8GLIB_MINI12864 u8g(10,9,7);
DS1302 rtc(3, 4, 5);

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
  char* dateStr;
  char* timeStr;
  do{
     timeStr=rtc.getTimeStr();
     dateStr=rtc.getDateStr();
  }while(timeStr[6]=='8');
  u8g.drawStr( 0, 0,dateStr);
  u8g.drawStr( 0, 16,timeStr);
  u8g.drawStr( 0,32, tmp);
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
  
   rtc.halt(false);
  //rtc.writeProtect(false);
  rtc.setDOW(SATURDAY);        // Set Day-of-Week to FRIDAY
  //rtc.setTime(9, 40, 0);     // Set the time to 12:00:00 (24hr format)
  //rtc.setDate(3, 5, 2014);   // Set the date to August 6th, 2010
  rtc.writeProtect(true);
  
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


