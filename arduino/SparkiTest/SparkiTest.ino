#include <Sparki.h> // include the sparki library

void setup() {
  sparki.servo(SERVO_CENTER); // Center the Servo
}

void loop()
{
  sparki.RGB(RGB_GREEN); // turn the light green
  sparki.moveForward(); // move Sparki forward
  int cm = sparki.ping(); // measures the distance with Sparki's eyes

  if(cm != -1) // make sure its not too close or too far
  { 
    if(cm < 20) // if the distance measured is less than 10 centimeters
    {
      sparki.RGB(RGB_RED); // turn the light red
      sparki.beep(); // beep!
      sparki.moveBackward(10); // back up 10 centimeters
      sparki.moveRight(30); // rotate right 30 degrees
      delay(100);
    }
  }
  sparki.clearLCD(); // wipe the screen

  float x  = sparki.accelX();   // measure the accelerometer x-axis
  float y  = sparki.accelY();   // measure the accelerometer y-axis
  float z  = sparki.accelZ();   // measure the accelerometer z-axis

  // write the measurements to the screen
  sparki.print("Accel X: "); 
  sparki.println(x);

  sparki.print("Accel Y: "); 
  sparki.println(y);

  sparki.print("Accel Z: "); 
  sparki.println(z);

  sparki.updateLCD(); // display all of the information written to the screen

  delay(10); // wait 0.1 seconds (100 milliseconds)
}

