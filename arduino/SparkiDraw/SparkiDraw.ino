/*******************************************
Basic function test to path following code

********************************************/
#include <Sparki.h> // include the sparki 
#include <math.h> // include for atan2 function
#define PI 3.14159265
int program = false;

float moveToPoint(float currentDirection, float oldX, float oldY, float newX, float newY)
{
  // calculate the direction the new point is relative to the old point
  // bit of trig used here
  float newDirection;
  newDirection = atan2(newY-oldY,newX-oldX) * 180 / PI;
  
  // calculate the turn angle needed
  float turnAngle;
  turnAngle = newDirection - currentDirection;
  
  // turnAngle might be outside of +/-180degrees, so do some logic on it
  if (turnAngle < -180)
  {
    turnAngle += 360; // add 360 to turnAngle if less than -180
  } 
  if (turnAngle > 180)
  {
    turnAngle -= 360; // subtract 360 from turnAngle if more than 180
  }
  
  // calculate distance to travel between old and new point
  float distanceToTravel;
  distanceToTravel = sqrt((newX-oldX)*(newX-oldX)+(newY-oldY)*(newY-oldY)); // use pythagoras to get distance
  
  // debug information to LCD
  sparki.clearLCD();
  
  sparki.print("Old: ");
  sparki.print(oldX);
  sparki.print(",");
  sparki.println(oldY);
  
  sparki.print("New: ");
  sparki.print(newX);
  sparki.print(",");
  sparki.println(newY);
  
  sparki.print("Ang: ");
  sparki.println(turnAngle);
  
  sparki.print("Dist: ");
  sparki.println(distanceToTravel);
  
  sparki.updateLCD();

  // turn Sparki in correct direction
  if (turnAngle > 0)
  {
    sparki.moveLeft(turnAngle); // positive angles are to the left
  } else {
    sparki.moveRight(-turnAngle); // negative angles are to the right
  }
  
  // move Sparki to the new point
  sparki.moveForward(distanceToTravel); 
  
  // return new direction that Sparki's pointing in
  return newDirection;
}
#define LEN 32
float xValues[LEN] = {  0,  3,  5,  7, 10, 10, 14, 14, 10,17.5,17.5,17.5, 21, 21, 21,26.5, 28, 28,24.5,24.5, 26, 35,31.5,31.5,35, 39,40.5,40.5, 37, 37,38.5,40.5};
float yValues[LEN] = {  0,  6,  1,  6,  0,  6,4.5,1.5,  0,   0,   6,   3,  3,  6,  0,   0,  1,  2,   4,   5,  6,  6, 4.3, 1.7, 0,  0,   1,   2,  4,  5,   6,   6};
void setup()
{
  // list of points to move through (grid is in cm)
  
//  float xValues[5] = {0,-5,-5,0,0};
//  float yValues[5] = {0,0,-5,-5,0};
  
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
    float currentDirection = 90; // default start orientation (+ve y-axis)
  
  // loop through co-ordinate array
  for (int i=1; i<LEN; i++)
  {
    // currentDirection is returned from moveToPoint function
    currentDirection = 
      moveToPoint(currentDirection,
                  xValues[i-1],yValues[i-1],
                  xValues[i],yValues[i]);
  }
  }
}
