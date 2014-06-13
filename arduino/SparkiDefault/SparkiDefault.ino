#include <Sparki.h>  // include the robot library

int program = true;
float x,y,z;
unsigned long time_acc;

void setup() {
  sparki.servo(SERVO_CENTER); // center the servo
  Serial1.begin(9600);
  time_acc=millis();
}


void loop() {

  //Scan for IR Receiver
  int code = sparki.readIR();

  // if there is a valid remote button press
  if(code != -1){
    sparki.moveStop(); // stop the motor
    sparki.RGB(RGB_OFF); // clear the RGB
    program = false; // turn off the starter program
  } 

  switch(code){
  case 70: 
    sparki.moveForward(); 
    break;
  case 21: 
    sparki.moveBackward(); 
    break;
  case 67: 
    sparki.moveRight(); 
    break;
  case 71: 
    sparki.moveRight(); 
    break;
  case 68: 
    sparki.moveLeft(); 
    break;
  case 69: 
    sparki.moveLeft(); 
    break;
  case 64: 
    sparki.moveStop();
    sparki.gripperStop(); 
    break;      

    // Gripper Buttons
  case 9:  
    sparki.gripperOpen(); 
    break;
  case 7:  
    sparki.gripperClose(); 
    break;

    // RGB
  case 25: 
    sparki.RGB(RGB_OFF); 
    break;
  case 12: 
    sparki.RGB(RGB_RED); 
    break;
  case 24: 
    sparki.RGB(RGB_GREEN); 
    break;
  case 94: 
    sparki.RGB(RGB_BLUE); 
    break;

    // Servo
  case 90: 
    sparki.servo(SERVO_LEFT); 
    break;
  case 28: 
    sparki.servo(SERVO_CENTER); 
    break;
  case 8: 
    sparki.servo(SERVO_RIGHT); 
    break;

    // buzzer
  case 74: 
    sparki.beep(); 
    break;

    // Program Control
  case 66:  
    sparki.moveStop();
    sparki.RGB(0,0,0);
    program = false; 
    break;
  case 82:  
    program = true; 
    break;
  }  

  // Run Autonomy Code if
  if(program == true){
    sparki.moveForward();
    sparki.RGB(RGB_GREEN);
    int cm = sparki.ping(); // measures the distance with Sparki's eyes

    if(cm != -1) // make sure its not too close or too far
    { 
      if(cm < 20) // if the distance measured is less than 20 centimeters
      {
        sparki.RGB(RGB_RED); // turn the led red
        sparki.beep(); // beep!
        sparki.moveBackward(10); // move sparki backwards
        sparki.moveRight(30);
      }
    }

    delay(10); // wait 0.1 seconds (100 milliseconds)
  }
  if(millis()-time_acc > 100){
    sparki.clearLCD();
    
    time_acc=millis();
    x  = sparki.accelX();   // measure the accelerometer x-axis
    y  = sparki.accelY();   // measure the accelerometer y-axis
    z  = sparki.accelZ();   // measure the accelerometer z-axis
    Serial1.print("t:");
    Serial1.print(time_acc);
    Serial1.print(" x:");
    Serial1.print(x);
    Serial1.print(" y:");
    Serial1.print(y);
    Serial1.print(" z:");
    Serial1.println(z);

    sparki.print("Accel XX: "); 
    sparki.println(x);

    sparki.print("Accel Y: "); 
    sparki.println(y);

    sparki.print("Accel Z: "); 
    sparki.println(z);
    
    sparki.updateLCD();
  }
}

