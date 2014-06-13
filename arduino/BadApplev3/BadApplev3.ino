//#define SERIAL_BUFFER_SIZE 128
#include <U8glib.h>
#include "image.h"
U8GLIB_MINI12864_2X u8g(10, 9, 7);
//#define g_width 88
//#define SEGMENT 16
#define WIDTH_B 11
#define HEIGHT 64
#define WIDTH 86
#define SEGMENTED_HEIGHT 16
#define H_OFF 0
unsigned char g_received[WIDTH_B * HEIGHT + 1];
unsigned int page_count = 0;
#define LED_PIN 6
void draw(void)
{
        
        int i=page_count*SEGMENTED_HEIGHT;
            //u8g.drawXBMP(0,i,WIDTH,SEGMENTED_HEIGHT,x_bits+(WIDTH_B*i));
            digitalWrite(LED_PIN, LOW);
            Serial.readBytes(g_received+(WIDTH_B*i), WIDTH_B * SEGMENTED_HEIGHT);
            Serial.write('N'); //Tell PC to start sending next round of data.
            //Serial.write(g_received,WIDTH_B*SEGMENTED_HEIGHT);
            digitalWrite(LED_PIN, HIGH);
            //u8g.drawXBM(0,i,WIDTH,SEGMENTED_HEIGHT,g_received);
            u8g.drawBitmap(H_OFF,0,WIDTH_B,HEIGHT,g_received);
            //u8g.drawBitmapP(0,i,13,4,x_bits+(13*i));
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
    pinMode(LED_PIN, OUTPUT);
    digitalWrite(LED_PIN, HIGH);

}


void loop()
{
    //unsigned int micro_0 = micros();
    u8g.firstPage();
    page_count = 0;
    do
    {
        draw();
        page_count++;
    }
    while ( u8g.nextPage() );
    //micro_0 = micros() - micro_0;
    //Serial.print(page_count);
    Serial.println('-');
    //Serial.println(micro_0);
    //Serial.println( SERIAL_BUFFER_SIZE);

}
/*void loop(){
   u8g.firstPage();
   draw();
   Serial.print("tick\n");
}*/

