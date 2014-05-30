//#define SERIAL_BUFFER_SIZE 128
#include <U8glib.h>
#include "image.h"
U8GLIB_MINI12864 u8g(10, 9, 7);
//#define g_width 88
//#define SEGMENT 16
#define WIDTH_B 11
#define HEIGHT 64
#define WIDTH 86
#define SEGMENTED_HEIGHT 4
#define H_OFF 0
unsigned char g_received[WIDTH_B*SEGMENTED_HEIGHT+10];
#define LED_PIN 6
void draw(void)
{
   for(int i=0; i<HEIGHT; i+=SEGMENTED_HEIGHT){
        //u8g.drawXBMP(0,i,WIDTH,SEGMENTED_HEIGHT,x_bits+(WIDTH_B*i));
        digitalWrite(LED_PIN, LOW);
        Serial.readBytes(g_received,WIDTH_B*SEGMENTED_HEIGHT);
        //Serial.write(g_received,WIDTH_B*SEGMENTED_HEIGHT);
        digitalWrite(LED_PIN, HIGH);
        //u8g.drawXBM(0,i,WIDTH,SEGMENTED_HEIGHT,g_received);
        u8g.drawBitmap(H_OFF,i,WIDTH_B,SEGMENTED_HEIGHT,g_received);
        //u8g.drawBitmapP(0,i,13,4,x_bits+(13*i));
    }
    //u8g.drawXBMP(0,0,x_width,x_height,x_bits);
}

void graphic_setup()
{
    pinMode(13, OUTPUT);
    digitalWrite(13, HIGH);
}

void setup()
{
    // put your setup code here, to run once:
    Serial.begin(125000);
    graphic_setup();
    pinMode(LED_PIN,OUTPUT);
    digitalWrite(LED_PIN, HIGH);
    
}

void loop()
{
    // put your main code here, to run repeatedly:
    //unsigned int micro_0=micros();
    //unsigned int count=0;
    u8g.firstPage();
    do
    {
        draw();
        //count++;
    }
    while ( u8g.nextPage() );
    //micro_0=micros()-micro_0;
    //Serial.print(count);
    //Serial.print('-');
    //Serial.println(micro_0);
    //Serial.println( SERIAL_BUFFER_SIZE);

}
/*void loop(){
   u8g.firstPage();
   draw();
   Serial.print("tick\n");
}*/
