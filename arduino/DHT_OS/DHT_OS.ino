#include <DHT.h>
#include <OneWire.h>
#include <DallasTemperature.h>
#include <U8glib.h>
#include <DS1302.h>
#include <NilRTOS.h>
#include <NilSerial.h>

// Macro to redefine Serial as NilSerial.
#define Serial NilSerial
#define ONE_WIRE_BUS 2

DHT dht;
OneWire oneWire(ONE_WIRE_BUS);
DallasTemperature tempSensors(&oneWire);
U8GLIB_MINI12864 u8g(10,9,7);
DS1302 rtc(3, 4, 5);


volatile float temp;
char tmp[7]="T=__._";

volatile unsigned int count1=0;
volatile unsigned int count2=0;

void u8g_prepare(void) {
  u8g.setFont(u8g_font_6x10);
  u8g.setFontRefHeightExtendedText();
  u8g.setDefaultForegroundColor();
  u8g.setFontPosTop();
}

void draw(void) {
  u8g_prepare();
  u8g.drawStr( 0,32, tmp);
}

void graphic_setup(){
  pinMode(13, OUTPUT);           
  digitalWrite(13, HIGH);  
}

//------------------------------------------------------------------------------
/*
 * Thread 1, Get Data and print them
 */
// Declare a stack with 16 bytes beyond context switch and interrupt needs. 
NIL_WORKING_AREA(waThread1, 96);

// Declare thread function for thread 1.
NIL_THREAD(Thread1, arg) {
   
  while (TRUE) {
    nilThdSleepMilliseconds(950);
    delay(50);// have certain delay. Don't know why but this increases the success rate. Maybe there's disturbance after recovering???
    float humidity = dht.getHumidity();
    float temperature = dht.getTemperature();
    tempSensors.requestTemperatures();
    
    Serial.print(dht.getStatusString());
    Serial.print("\t");
    Serial.print(humidity, 2);
    Serial.print("\t\t");
    Serial.print(temperature, 2);
    Serial.print("\t\t");
    temp=tempSensors.getTempCByIndex(0);
    Serial.println(temp, 1);
    tmp[2]=int(temp/10)%10+'0';
    tmp[3]=int(temp)%10+'0';
    tmp[5]=int(temp*10)%10+'0';
    
    Serial.print("Count1:");
    Serial.print(count1);
    count1=0;
    nilPrintUnusedStack(&Serial);

  }
}
//------------------------------------------------------------------------------
/*
 * Thread 2, Graphics display.
 */
// Declare a stack with 100 bytes beyond context switch and interrupt needs.
NIL_WORKING_AREA(waThread2, 48);

// Declare thread function for thread 2.
NIL_THREAD(Thread2, arg) { 
  
  while (TRUE) {
    u8g.firstPage();  
    do {
      draw();
    } 
    while( u8g.nextPage() );
    
    Serial.print("Count2:");
    Serial.println(count2);
    count2=0;
    
    nilThdSleepMilliseconds(200);    
  }
}
//------------------------------------------------------------------------------
/*
 * Threads static table, one entry per thread.  A thread's priority is
 * determined by its position in the table with highest priority first.
 * 
 * These threads start with a null argument.  A thread's name may also
 * be null to save RAM since the name is currently not used.
 */
NIL_THREADS_TABLE_BEGIN()
NIL_THREADS_TABLE_ENTRY(NULL, Thread1, NULL, waThread1, sizeof(waThread1))
NIL_THREADS_TABLE_ENTRY(NULL, Thread2, NULL, waThread2, sizeof(waThread2))
NIL_THREADS_TABLE_END()
//------------------------------------------------------------------------------
void setup()
{
  
  
  Serial.begin(125000);
  Serial.println();
  Serial.println("Status\tHumidity (%%)\tTemperature (C)\t DS18B20");
  
  dht.setup(8); // data pin 8
  tempSensors.begin();
  graphic_setup();
  
  nilSysBegin();
}
//------------------------------------------------------------------------------
// Loop is the idle thread.  The idle thread must not invoke any 
// kernel primitive able to change its state to not runnable.
void loop() {
  count1++;
  count2++;
}
