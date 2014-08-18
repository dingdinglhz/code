//#define SERIAL_BUFFER_SIZE 128
#include <U8glib.h>
U8GLIB_MINI12864_2X u8g(10, 9, 7);
//U8GLIB_DOGM128_2X u8g(17,4,12);
//#define g_width 88
//#define SEGMENT 16
#define WIDTH_B 11
#define HEIGHT 64
#define WIDTH 86
#define SEGMENTED_HEIGHT 16
#define H_OFF 16
unsigned char g_received[WIDTH_B * HEIGHT + 1];
unsigned int page_count;
#define LED_PIN 6
void draw(void)
{
    
    int i = page_count * SEGMENTED_HEIGHT;
    digitalWrite(LED_PIN, LOW);
    Serial.readBytes(g_received + (WIDTH_B * i), WIDTH_B * SEGMENTED_HEIGHT);
    if(page_count>=3){Serial.write('-');}
    Serial.write('N'); //Tell PC to start sending next round of data.
    digitalWrite(LED_PIN, HIGH);
    u8g.drawBitmap(H_OFF, 0, WIDTH_B, HEIGHT, g_received);
    
    /*if (page_count)
    {
        u8g.drawBitmap(H_OFF, 0, WIDTH_B, HEIGHT, g_received);
    }
    else
    {
        for (int i = 0; i < HEIGHT; i += SEGMENTED_HEIGHT)
        {
            digitalWrite(LED_PIN, LOW);
            Serial.readBytes(g_received + (WIDTH_B * i), WIDTH_B * SEGMENTED_HEIGHT);
            if(i+SEGMENTED_HEIGHT>=HEIGHT){Serial.write('-');}
            Serial.write('N');
            digitalWrite(LED_PIN, HIGH);
            u8g.drawBitmap(H_OFF, i, WIDTH_B, SEGMENTED_HEIGHT, g_received + (WIDTH_B * i));
        }
    }*/
}

void graphic_setup()
{
    pinMode(13, OUTPUT);
    digitalWrite(13, HIGH);
}

void setup()
{
    Serial.begin(125000);
    //Serial.begin(115200);
    graphic_setup();
    pinMode(LED_PIN, OUTPUT);
    digitalWrite(LED_PIN, HIGH);
    //device start up

    Serial.write('-');
    Serial.write('N');
    //start PC to send images.
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
    //
    //Serial.println(micro_0);
    //Serial.println( SERIAL_BUFFER_SIZE);

}
