#include "Sparki.h"
#include <inttypes.h>
#include <stdio.h>
#include <string.h>
#include <limits.h>
#include <Arduino.h>

#include <avr/pgmspace.h>
#include <util/delay.h>
#include <stdlib.h>
#include "SPI.h"

#include "SparkiWire.h"
#include "SparkiEEPROM.h"
#include "Sparkii2c.h"

static int8_t step_dir[3];                 // -1 = ccw, 1 = cw  

static uint8_t motor_speed[3];              // stores last set motor speed (0-100%)

uint8_t pixel_color = WHITE;

uint8_t ir_active = 1;

static volatile uint8_t move_speed = 100;
static volatile uint8_t speed_index[3];
static volatile uint8_t speed_array[3][SPEED_ARRAY_LENGTH];    
                                        // for each motor, how many 200uS waits between each step. 
                                        // Cycles through an array of 10 of these counts to average 
                                        // for better speed control


static volatile int8_t step_index[3];       // index into _steps array  
static uint8_t _steps_right[9];                   // bytes defining stepper coil activations
static uint8_t _steps_left[9];                   // bytes defining stepper coil activations
static volatile uint32_t remainingSteps[3]; // number of steps before stopping motor
static volatile uint32_t isRunning[3];      // tells if motor is running

static volatile int speedCounter[3];      // variable used in maintaing speed
static volatile int speedCount[3];      // what speedCount is set at when speed cycle resets

static volatile uint8_t shift_outputs[3];      // tells if motor is running

// initialize the RGB timer variables
static volatile uint8_t RGB_vals[3];
static volatile uint16_t RGB_timer;

static volatile uint8_t irSwitch;
static volatile uint8_t irSwitch2;

// variables for communication between the IR read function and its interrupt
#define MAX_IR_PULSE 20000
volatile long timeSinceLastPulse = 0;
volatile long lastPulseTime = 0;
volatile uint16_t pulsesIR[50][2]; // LOW,HIGH
volatile uint8_t currentPulse = 0;
volatile uint8_t haltIRRead = 0;

// shares the values of the accelerometers
volatile float xAxisAccel;
volatile float yAxisAccel;
volatile float zAxisAccel;

// variables for the magnetometer
volatile uint8_t mag_buffer[RawMagDataLength];

// values for the servo
volatile int8_t servo_deg_offset = 0;

SparkiClass sparki;

SparkiClass::SparkiClass()
{
 begin();
}

void SparkiClass::begin( ) {
 
  Serial.begin(9600);
  Serial1.begin(9600);

  // set up the Status LED
  pinMode(STATUS_LED, OUTPUT);
  digitalWrite(STATUS_LED, LOW);

  // Setup Buzzer
  pinMode(BUZZER, OUTPUT);
  digitalWrite(BUZZER, LOW);
  
  // Setup Analog Multiplexer
  pinMode(MUX_ANALOG, INPUT);
  pinMode(MUX_A, OUTPUT);
  pinMode(MUX_B, OUTPUT);
  pinMode(MUX_C, OUTPUT);
  
  // Setup IR Send
  pinMode(IR_SEND, OUTPUT);
  
  // Setup Ultrasonic
  pinMode(ULTRASONIC_TRIG, OUTPUT);
  pinMode(ULTRASONIC_ECHO, INPUT);
  
  // Setup Servo
  pinMode(SERVO, OUTPUT);    
  //startServoTimer();
  if( EEPROM.read(0) > 127) {
    servo_deg_offset = -256+EEPROM.read(0);
  }
  else{
    servo_deg_offset = EEPROM.read(0);
  }
  //servo(SERVO_CENTER);
    
  
  // Setup the SPI bus for the shift register
  // !!! Need to remove the essential functions from the SPI Library, 
  // !!! and include in the main code
  SPI.begin(); 
  SPI.setClockDivider(SPI_CLOCK_DIV2); 

  // Set the shift-register clock select pin to output 
  DDRD |= (1<<5);
  
  // Clear out the shift registers
  PORTD &= 0xDF;    // pull PD5 low
  SPI.transfer(shift_outputs[1]);
  SPI.transfer(shift_outputs[0]);
  PORTD |= 0x20;    // pull PD5 high to latch in spi transfers


  // Setup the IR Switch
  irSwitch = 0;

  // defining steps for the stepper motors
  _steps_left[0] = 0x10;
  _steps_left[1] = 0x30;
  _steps_left[2] = 0x20;
  _steps_left[3] = 0x60;
  _steps_left[4] = 0x40;
  _steps_left[5] = 0xC0;
  _steps_left[6] = 0x80;
  _steps_left[7] = 0x90;
  _steps_left[8]  = 0x00;

  _steps_right[0] = 0x01;
  _steps_right[1] = 0x03;
  _steps_right[2] = 0x02;
  _steps_right[3] = 0x06;
  _steps_right[4] = 0x04;
  _steps_right[5] = 0x0C;
  _steps_right[6] = 0x08;
  _steps_right[7] = 0x09;
  _steps_right[8] = 0x00;


  // Setup initial Stepper settings
  motor_speed[MOTOR_LEFT] = motor_speed[MOTOR_RIGHT] = motor_speed[MOTOR_GRIPPER] = move_speed;
  
  // Set up the scheduler routine to run every 200uS, based off Timer4 interrupt
  cli();          // disable all interrupts
  TCCR4A = 0;
  TCCR4B = 0;
  TCNT4  = 0;

  OCR4A = 48;               // compare match register 64MHz/2048 = 31250hz
  //TCCR4B |= (1 << WGM12);   // CTC mode
  TCCR4B = 0x06;
  //TCCR4B = _BV(CS43) | _BV(CS42);            // CLK/2048 prescaler
  TIMSK4 |= (1 << OCIE4A);  // enable Timer4 compare interrupt A
  sei();             // enable all interrupts
  
  // Setup the IR Remote Control pin and pin interrupt
  noInterrupts();
  pinMode(IR_RECEIVE, INPUT);
  
  // Setup the pin interrupt for INT6 (Pin 7) to trigger the IR function
  EICRB = (EICRB & ~((1 << ISC60) | (1 << ISC61))) | (CHANGE << ISC60);
  EIMSK |= (1 << INT6); 
  
  interrupts();
  
  initAccelerometer();
  
  WireWrite(ConfigurationRegisterB, (0x01 << 5));
  WireWrite(ModeRegister, Measurement_Continuous);  
  readMag(); // warm it up  

}

void SparkiClass::setMux(uint8_t A, uint8_t B, uint8_t C){
	digitalWrite(MUX_A, A);
	digitalWrite(MUX_B, B);
	digitalWrite(MUX_C, C);
	delay(1);
}

/* 
* Light Sensors
*/

int SparkiClass::lightRight(){
	setMux(LIGHT_RIGHT);
	return analogRead(MUX_ANALOG);
}

int SparkiClass::lightCenter(){
	setMux(LIGHT_CENTER);
	return analogRead(MUX_ANALOG);
}

int SparkiClass::lightLeft(){
	setMux(LIGHT_LEFT);
	return analogRead(MUX_ANALOG);
}

/*
* Infrared line sensors
*/


int SparkiClass::edgeRight(){
	setMux(IR_EDGE_RIGHT);
    return readSensorIR(MUX_ANALOG);
}

int SparkiClass::lineRight(){
	setMux(IR_LINE_RIGHT);
    return readSensorIR(MUX_ANALOG);
}

int SparkiClass::lineCenter(){
	setMux(IR_LINE_CENTER);
    return readSensorIR(MUX_ANALOG);
}

int SparkiClass::lineLeft(){
	setMux(IR_LINE_LEFT);
    return readSensorIR(MUX_ANALOG);
}

int SparkiClass::edgeLeft(){
	setMux(IR_EDGE_LEFT);
    return readSensorIR(MUX_ANALOG);
}

int SparkiClass::readSensorIR(int pin){
    int read = 0;
	onIR();
	read = analogRead(pin);
	offIR();
	return read;
}

void SparkiClass::onIR()  // turns off the IR Detection LEDs
{
    irSwitch = 1;
    delay(1); // give time for a scheduler cycle to run
}

void SparkiClass::offIR() // turns off the IR Detection LEDs
{
    irSwitch = 0;
    delay(1); // give time for a scheduler cycle to run
}

int SparkiClass::readBlindSensorIR(int pin0, int pin1, int pin2){
    int read = 0;
    setMux(pin0, pin1, pin2);
    delay(1);
	read = analogRead(MUX_ANALOG);
	delay(1);
	return read;
}

int SparkiClass::diffIR(int pin0, int pin1, int pin2){
    setMux(pin0, pin1, pin2);
    delay(1);
	int readOff = analogRead(MUX_ANALOG);
	delay(10);
	onIR();
	int readOn = analogRead(MUX_ANALOG);
	offIR();
	return readOff-readOn;
}

void SparkiClass::beep(){
    tone(BUZZER, BUZZER_FREQ, 200);
}

void SparkiClass::beep(int freq){
    tone(BUZZER, freq, 200);
}

void SparkiClass::beep(int freq, int time){
    tone(BUZZER, freq, time);
}

void SparkiClass::noBeep(){
    noTone(BUZZER);
}

void SparkiClass::RGB(uint8_t R, uint8_t G, uint8_t B)
{
    if(R > 100){
        R = 100;
    }
    if(G > 100){
        G = 100;
    }
    if(B > 100){
        B = 100;
    }
	RGB_vals[0] = R;
	RGB_vals[1] = G;
	RGB_vals[2] = B;
}

/*
 * motor control (non-blocking, except when moving distances)
 * speed is percent 0-100
*/

void SparkiClass::moveRight(float deg)
{
  unsigned long steps = STEPS_PER_DEGREE*deg;
  if(deg == 0){
      moveRight();
  }
  else{
      if(deg < 0){
        moveLeft(deg);
      }
      else{
          stepRight(steps);
          while( areMotorsRunning() ){
            delay(1);
          }
      }
  }
}

void SparkiClass::stepRight(unsigned long steps)
{
  motorRotate(MOTOR_LEFT, DIR_CCW, move_speed, steps);
  motorRotate(MOTOR_RIGHT, DIR_CCW, move_speed, steps);
}

void SparkiClass::moveRight()
{
  motorRotate(MOTOR_LEFT, DIR_CCW, move_speed, ULONG_MAX);
  motorRotate(MOTOR_RIGHT, DIR_CCW, move_speed, ULONG_MAX);
}

void SparkiClass::moveLeft(float deg)
{
  unsigned long steps = STEPS_PER_DEGREE*deg;
  if(deg == 0){
      moveLeft();
  }
  else{
      if(deg < 0){
        moveRight(deg);
      }
      else{
          stepLeft(steps);
          while( areMotorsRunning() ){
            delay(1);
          }
      }
  }
}

void SparkiClass::stepLeft(unsigned long steps)
{
  motorRotate(MOTOR_LEFT,  DIR_CW, move_speed, steps);
  motorRotate(MOTOR_RIGHT, DIR_CW, move_speed, steps);
}

void SparkiClass::moveLeft()
{
  motorRotate(MOTOR_LEFT,  DIR_CW, move_speed, ULONG_MAX);
  motorRotate(MOTOR_RIGHT, DIR_CW, move_speed, ULONG_MAX);
}

void SparkiClass::moveForward(float cm)
{
  unsigned long steps = STEPS_PER_CM*cm;
  if(cm == 0){
      moveForward();
  }
  else{
      if(cm < 0){
        moveBackward(cm);
      }
      else{
          stepForward(steps);
          while( areMotorsRunning() ){
            delay(1);
          }
      }
  }
}

void SparkiClass::stepForward(unsigned long steps)
{
  motorRotate(MOTOR_LEFT, DIR_CCW, move_speed, steps);
  motorRotate(MOTOR_RIGHT, DIR_CW, move_speed, steps);
}

void SparkiClass::moveForward()
{
  motorRotate(MOTOR_LEFT, DIR_CCW, move_speed, ULONG_MAX);
  motorRotate(MOTOR_RIGHT, DIR_CW, move_speed, ULONG_MAX);
}

void SparkiClass::moveBackward(float cm)
{
  unsigned long steps = STEPS_PER_CM*cm;
  if(cm == 0){
      moveBackward();
  }
  else{
      if(cm < 0){
        moveForward(cm);
      }
      else{
          stepBackward(steps);
          while( areMotorsRunning() ){
            delay(1);
          }
      }
  }
}

void SparkiClass::stepBackward(unsigned long steps)
{
  motorRotate(MOTOR_LEFT,   DIR_CW, move_speed, steps);
  motorRotate(MOTOR_RIGHT, DIR_CCW, move_speed, steps);
}

void SparkiClass::moveBackward()
{
  motorRotate(MOTOR_LEFT,   DIR_CW, move_speed, ULONG_MAX);
  motorRotate(MOTOR_RIGHT, DIR_CCW, move_speed, ULONG_MAX);
}

void SparkiClass::moveStop()
{
  motorStop(MOTOR_LEFT);
  motorStop(MOTOR_RIGHT);
}

void SparkiClass::gripperOpen()
{
  motorRotate(MOTOR_GRIPPER, DIR_CCW, move_speed, ULONG_MAX);
}
void SparkiClass::gripperClose()
{
  motorRotate(MOTOR_GRIPPER, DIR_CW, move_speed, ULONG_MAX);
}
void SparkiClass::gripperStop()
{
  motorStop(MOTOR_GRIPPER);
}

void SparkiClass::speed(uint8_t speed)
{
    move_speed = speed;
}


void SparkiClass::motorRotate(int motor, int direction, int speed, long steps)
{
   Serial.print("Motor ");Serial.print(motor); Serial.print(" rotate, dir= "); 
   Serial.print(direction); Serial.print(", steps= "); Serial.println(steps);
   
   motor_speed[motor] = speed; // speed in 1-100 precent
   
   // populate the speed array with multiples of 200us waits between steps
   // having 10 different waits allows finer grained control
   if(speed == 0){
      uint8_t oldSREG = SREG; cli();
      remainingSteps[motor] = 0; 
      isRunning[motor] = false;
      SREG = oldSREG; sei(); 
   }
   else{
      int base_waits = 500/speed;
      int remainder_waits = int((500.0/float(speed) - float(base_waits))*SPEED_ARRAY_LENGTH); 

      for(uint8_t i=0; i< (SPEED_ARRAY_LENGTH-remainder_waits); i++){
         speed_array[motor][i] = base_waits+1;
       }
      for(uint8_t i=(SPEED_ARRAY_LENGTH-remainder_waits); i<SPEED_ARRAY_LENGTH; i++){
         speed_array[motor][i] = base_waits;
       }
      
      uint8_t oldSREG = SREG; cli();
      speed_index[motor] = 0;
      speedCount[motor] = speed_array[motor][0];
      speedCounter[motor] = speedCount[motor];
      remainingSteps[motor] = steps;
      step_dir[motor] = direction;  
      isRunning[motor] = true;
      SREG = oldSREG; sei(); 

      Serial.print("base: ");
      Serial.print(base_waits);
      Serial.print(", remainder: ");
      Serial.println(remainder_waits);
   }
   delay(1);
}

void SparkiClass::motorStop(int motor)
{
   motorRotate(motor, 1, 0, 0);
}
 
 // returns true if one or both motors a still stepping
 bool SparkiClass::areMotorsRunning()
 {
   bool result;
   uint8_t oldSREG = SREG;
   cli();
   result =  isRunning[MOTOR_LEFT] || isRunning[MOTOR_RIGHT] || isRunning[MOTOR_GRIPPER] ;
   SREG = oldSREG; 
   sei();
   return result;
 }

int SparkiClass::ping_single(){
  long duration; 
  float cm;
  digitalWrite(ULTRASONIC_TRIG, LOW); 
  delayMicroseconds(2); 
  digitalWrite(ULTRASONIC_TRIG, HIGH); 
  delayMicroseconds(10); 
  digitalWrite(ULTRASONIC_TRIG, LOW); 
  

  uint8_t bit = digitalPinToBitMask(ULTRASONIC_ECHO);
  uint8_t port = digitalPinToPort(ULTRASONIC_ECHO);
  uint8_t stateMask = (HIGH ? bit : 0);
  
  unsigned long startCount = 0;
  unsigned long endCount = 0;
  unsigned long width = 0; // keep initialization out of time critical area
  
  // convert the timeout from microseconds to a number of times through
  // the initial loop; it takes 16 clock cycles per iteration.
  unsigned long numloops = 0;
  unsigned long maxloops = 5000;
	
  // wait for any previous pulse to end
  while ((*portInputRegister(port) & bit) == stateMask)
    if (numloops++ == maxloops)
      return -1;
	
  // wait for the pulse to start
  while ((*portInputRegister(port) & bit) != stateMask)
    if (numloops++ == maxloops)
      return -1;
  
  startCount = micros();
  // wait for the pulse to stop
  while ((*portInputRegister(port) & bit) == stateMask) {
    if (numloops++ == maxloops)
      return -1;
    delayMicroseconds(10); //loop 'jams' without this
    if((micros() - startCount) > 58000 ){ // 58000 = 1000CM
      return -1;
      break;
    }
  }
  duration = micros() - startCount;
  //--------- end pulsein
  cm = (float)duration / 29.0 / 2.0; 
  return int(cm);
}

int SparkiClass::ping(){
  int attempts = 5;
  float distances [attempts];
  for(int i=0; i<attempts; i++){
    distances[i] = ping_single();
    delay(20);
  }
  
  // sort them in order
  int i, j;
  float temp;
 
  for (i = (attempts - 1); i > 0; i--)
  {
    for (j = 1; j <= i; j++)
    {
      if (distances[j-1] > distances[j])
      {
        temp = distances[j-1];
        distances[j-1] = distances[j];
        distances[j] = temp;
      }
    }
  }
  
  // return the middle entry
  return int(distances[(int)ceil((float)attempts/2.0)]); 
}

// Uses timer3 to send on/off IR pulses according to the NEC IR transmission standard
// http://wiki.altium.com/display/ADOH/NEC+Infrared+Transmission+Protocol
// protocol. Turns off timer3 functions and timer4 motor/LED interference to avoid conflict
void SparkiClass::sendIR(uint8_t code){
  char oldSREG = SREG;				
  noInterrupts();  // Disable interrupts for 16 bit register access
  
  //***********************************************
  // Set up and tear down Timer3 and Timer4 roles
  //***********************************************
  
  // saves settings for timer3
  uint8_t TIMSK3_store = TIMSK3;
  uint8_t TCCR3A_store = TCCR3A;
  uint8_t TCCR3B_store = TCCR3B;
  uint8_t TCNT3_store = TCNT3;  
  uint8_t EIMSK_store = EIMSK;
  
  uint8_t TIMSK4_store = TIMSK4;
  
  // wipe the timer settings
  TIMSK4 = 0;
  TIMSK3 = 0;
  TCCR3A = 0;
  TCCR3B = 0;
  TCNT3  = 0;
  EIMSK  = 0;

  TCCR3B |= _BV(CS31);      // set timer clock at 1/8th of CLK_i/o (=CLK_sys)
  OCR3B = 22;               // compare match register
  
  TIMSK3 |= (1 << OCIE3B);  // enable Timer3 compare interrupt B

  interrupts();  // re-enable interrupts
  SREG = oldSREG;
  
  
  //*****************************************
  // send the pulses 
  //*****************************************
  
  
  // leadings 9ms pulse, 4.5ms gap
  irPulse(9000,4500);
  
  // 8 bit address
  for(int i=0; i<8; i++){
      irPulse(563,563); // NEC logical 0
  }
 
  // 8 bit address' logical inverse
  for(int i=0; i<8; i++){
      irPulse(563,1687); // NEC logical 1
  }
  
  // 8 bit command
  for(uint8_t i=0; i<8; i++){
    if( (code & (1<<i)) > 0 ){
        irPulse(563,1687); // NEC logical 1
    }
    else{
        irPulse(563,563);  // NEC logical 0  
    }
  }

  // 8 bit command's logical inverse
  for(uint8_t i=0; i<8; i++){
    if( (code & (1<<i)) > 0 ){
        irPulse(563,563);  // NEC logical 0
    }
    else{
        irPulse(563,1687); // NEC logical 1
    }
  }
  
  // 562.5缁炬澘顔�pulse to signal end of transmission
  irPulse(563,10); // NEC logical 1  

  //*****************************************
  // restore Timer3 and Timer4 roles
  //*****************************************
  
  // restore the timer
  TIMSK4 = TIMSK4_store;
  TIMSK3 = TIMSK3_store;
  TCCR3A = TCCR3A_store;
  TCCR3B = TCCR3B_store;
  TCNT3  = TCNT3_store;
  EIMSK  = EIMSK_store;
}

void SparkiClass::irPulse(uint16_t on, uint16_t off){
    TIMSK3 |= (1 << OCIE3B);  // enable  38khz signal
    delayMicroseconds(on);
    TIMSK3 &= ~(1 << OCIE3B);  // disable 38khz signal
    PORTD &= ~(1<<7); // make sure the LED is off
    delayMicroseconds(off);    
}


void SparkiClass::startServoTimer(){
  char oldSREG = SREG;				
  noInterrupts();                                       // Disable interrupts for 16 bit register access
  TCCR1A = 0;                                           // clear control register A 
  TCCR1B = _BV(WGM13);                                  // set mode 8: phase and frequency correct pwm, stop the timer
  ICR1 = 20000;                                         // ICR1 is TOP in p & f correct pwm mode
  TCCR1B &= ~(_BV(CS10) | _BV(CS11) | _BV(CS12));
  TCCR1B |= 0x02;                                       // reset clock select register, and starts the clock
  DDRB |= _BV(PORTB1);                                  // sets data direction register for pwm output pin
  TCCR1A |= _BV(COM1A1);                                // activates the output pin
  interrupts();                                         // re-enable interrupts
  SREG = oldSREG;
}

void SparkiClass::servo(int deg)
{ 
  startServoTimer();
  int duty = int((((float(-deg+servo_deg_offset)*2000/180)+1500)/20000)*1024); // compute the duty cycle for the servo
  //0 = 26
  //180 = 128
  
  unsigned long dutyCycle = 20000;
  dutyCycle *= duty;
  dutyCycle >>= 10;
   
  char oldSREG = SREG;
  noInterrupts();
  OCR1A = dutyCycle;
  
  SREG = oldSREG;
  interrupts();
}

/*
Returns the current IR Code. 
Uses the interrupt on INT6 (PE6, Pin 7) to do the signal reading
If there is no code waiting, pass -1 back immediately.
If there is a code, but its still reading, wait it out then return code
Wipes the current stored code once read.

NEC IR code details here:
http://wiki.altium.com/display/ADOH/NEC+Infrared+Transmission+Protocol
*/

int SparkiClass::readIR(){
    uint8_t code = 0;
    if(currentPulse != 0){ // check there's a code waiting
        while( micros()-lastPulseTime <= MAX_IR_PULSE){
            delayMicroseconds(MAX_IR_PULSE);
        }; // wait for the reading to time out
        // tell the interrupt to not take any more codes
        haltIRRead = 1;
        
        // decode the signal
        for(int i=0; i<8; i++){
            if(pulsesIR[17+i][1] > 1000){
                code |= (1<<i);
            }
        }
        currentPulse = 0; // 'reset' the current IR pulse reading
        haltIRRead = 0;
        return int(code); // return the decoded value
    }
    else{
        return -1; // no signal found, return -1
    }
}

SIGNAL(INT6_vect) {
  if(haltIRRead != 1){
      long currentTime = micros(); // take the current time
      int pinStatus = PINE & (1 << 6); // read the current status of the IR Pin
      timeSinceLastPulse = currentTime-lastPulseTime;
      
      // Tell if its the start of the reading cycle or not (time since last pulse), starts low
      if( (timeSinceLastPulse >= MAX_IR_PULSE) && (pinStatus == LOW)){
        // if reading new pulse, set up. Wipes out the last pulse
        currentPulse = 0;
      }
      else{
          // otherwise, read the current code
          if(pinStatus){ //(PE6 high) pulse has risen from the end of a low pulse
            pulsesIR[currentPulse][0] = timeSinceLastPulse;
          }
          else{ //(PE6 low) pulse has fallen from the end of a high pulse
            pulsesIR[currentPulse][1] = timeSinceLastPulse;
            currentPulse++;
          }  
      }    
      lastPulseTime = currentTime;
  }
}

float SparkiClass::accelX(){
    readAccelData();
    return -xAxisAccel*9.8;
}
float SparkiClass::accelY(){
    readAccelData();
    return -yAxisAccel*9.8;
}
float SparkiClass::accelZ(){
    readAccelData();
    return -zAxisAccel*9.8;
}

float SparkiClass::readMag(){
  WireRead(DataRegisterBegin, RawMagDataLength);
  xAxisMag = ((mag_buffer[0] << 8) | mag_buffer[1]) * M_SCALE;
  zAxisMag = ((mag_buffer[2] << 8) | mag_buffer[3]) * M_SCALE;
  yAxisMag = ((mag_buffer[4] << 8) | mag_buffer[5]) * M_SCALE;    
}

float SparkiClass::compass(){
  readMag();
  
  float heading = atan2(yAxisMag,xAxisMag);
  
  if(heading < 0)
    heading += 2*PI;
  if(heading > 2*PI)
    heading -= 2*PI;
    
  float headingDegrees = heading * 180/M_PI; 
  return headingDegrees;
}

float SparkiClass::magX(){
    readMag();
    return xAxisMag;
}

float SparkiClass::magY(){
    readMag();
    return yAxisMag;
}

float SparkiClass::magZ(){
    readMag();
    return zAxisMag;
}

void SparkiClass::WireWrite(int address, int data){
  Wire.beginTransmission(HMC5883L_Address);
  Wire.write(address);
  Wire.write(data);
  Wire.endTransmission();
}

uint8_t* SparkiClass::WireRead(int address, int length){
  Wire.beginTransmission(HMC5883L_Address);
  Wire.write(DataRegisterBegin);
  Wire.endTransmission();
  
  Wire.beginTransmission(HMC5883L_Address);
  Wire.requestFrom(HMC5883L_Address, RawMagDataLength);

  if(Wire.available() == RawMagDataLength)
  {
	  for(uint8_t i = 0; i < RawMagDataLength; i++)
	  {
		  mag_buffer[i] = Wire.read();
	  }
  }
  Wire.endTransmission();
}

 /*
  * private functions
  */
 
 // set the number if steps for the given motor 

ISR(TIMER3_COMPB_vect) // IR send function, operates at ~38khz when active
{
    PORTD ^= (1<<7); // toggle the IR LED pin
    TCNT3=0;
}

/***********************************************************************************
The Scheduler
Every 200uS (5,000 times a second), we update the 2 shift registers used to increase
the amount of outputs the processor has
***********************************************************************************/
ISR(TIMER4_COMPA_vect)          // interrupt service routine that wraps a user defined function supplied by attachInterrupt
{
//void SparkiClass::scheduler(){ 
    // Clear the timer interrupt counter
    TCNT4=0;

	// clear the shift register values so we can re-write them
    shift_outputs[0] = 0x00;
    shift_outputs[1] = 0x00;
    
    // Update the RGB leds
    if(RGB_timer < RGB_vals[0]){ // update Red led
		shift_outputs[RGB_SHIFT] |= RGB_R;
    }
    if(RGB_timer < RGB_vals[1]){ // update Green led
		shift_outputs[RGB_SHIFT] |= RGB_G;
    }
    if(RGB_timer < RGB_vals[2]){ // update Blue led
		shift_outputs[RGB_SHIFT] |= RGB_B;
    }
    RGB_timer++;
    if(RGB_timer == 100){
    	RGB_timer = 0;
    }

    // IR Detection Switch
    if(irSwitch == 0){
    	shift_outputs[1] &= 0xF7;
    }
    else{
    	shift_outputs[1] |= 0x08;
    }
    
    //// Motor Control ////
    //   Determine what state the stepper coils are in
	for(byte motor=0; motor<3; motor++){
		if( remainingSteps[motor] > 1 ){ // check if finished stepping   
		    // speedCount determines the stepping frequency
		    // interrupt speed (5khz) divided by speedCounter equals stepping freq
		    // 1khz is the maximum reliable frequency at 5v, so we use 5 as the top speed
		    // 5,000hz/5 = 1000hz = micro-stepping frequency
			if(speedCounter[motor] == 0) { 
				step_index[motor] += step_dir[motor];
				remainingSteps[motor]--;
				speedCounter[motor] = speed_array[motor][speed_index[motor]];
				speed_index[motor]++;
				if(speed_index[motor] >= SPEED_ARRAY_LENGTH){
			      speed_index[motor] = 0;
			    }
			}
			else{
			   speedCounter[motor] = speedCounter[motor]-1;
			}
			
		}
		else {  // if this was the last step
			remainingSteps[motor] = 0;  
			isRunning[motor] = false;
			step_index[motor] = 8;
			speedCounter[motor] = -1;
		}     
		
		//   keep indicies from rolling over or under
		if( step_index[motor] >= 8){
			step_index[motor] = 0;
		}
		else if( step_index[motor] < 0){
			step_index[motor] = 7;
		}
		if(isRunning[motor] == false){
			step_index[motor] = 8;
		}
	}

    shift_outputs[0] |= _steps_right[step_index[MOTOR_RIGHT]];
    shift_outputs[0] |= _steps_left[step_index[MOTOR_GRIPPER]];
    shift_outputs[1] |= _steps_left[step_index[MOTOR_LEFT]];
    
	//output values to shift registers
    PORTD &= ~(1<<5);    // pull PD5 (shift-register latch) low
    SPI.transfer(shift_outputs[1]);
    SPI.transfer(shift_outputs[0]);
    PORTD |= (1<<5);    // pull PD5 (shift-register latch) high 
}


void SparkiClass::readAccelData()
{
  int accelCount[3];
  uint8_t rawData[6];  // x/y/z accel register data stored here
  
  
  readi2cRegisters(0x01, 6, &rawData[0], MMA8452_ADDRESS);  // Read the six raw data registers into data array
  

  // Loop to calculate 12-bit ADC and g value for each axis
  for (uint8_t i=0; i<6; i+=2)
  {
    accelCount[i/2] = ((rawData[i] << 8) | rawData[i+1]) >> 4;  // Turn the MSB and LSB into a 12-bit value
    if (rawData[i] > 0x7F)
    {  
      // If the number is negative, we have to make it so manually (no 12-bit data type)
      accelCount[i/2] = ~accelCount[i/2] + 1;
      accelCount[i/2] *= -1;  // Transform into negative 2's complement #
    }
  }
  xAxisAccel = (float) accelCount[0]/((1<<12)/(2*ACCEL_SCALE));
  yAxisAccel = (float) accelCount[1]/((1<<12)/(2*ACCEL_SCALE));
  zAxisAccel = (float) accelCount[2]/((1<<12)/(2*ACCEL_SCALE));
}

int SparkiClass::initAccelerometer()
{
  uint8_t c = readi2cRegister(0x0D, MMA8452_ADDRESS);  // Read WHO_AM_I register
  if (c == 0x2A){ // WHO_AM_I should always be 0x2
    // Must be in standby to change registers, so we do that
    c = readi2cRegister(0x2A, MMA8452_ADDRESS);
    readi2cRegister(0x2A, c & ~(0x01), MMA8452_ADDRESS);
  
    // Set up the full scale range
    readi2cRegister(0x0E, ACCEL_SCALE >> 2, MMA8452_ADDRESS); 
  
    // Setup the 3 data rate bits, from 0 to 7
    readi2cRegister(0x2A, readi2cRegister(0x2A, MMA8452_ADDRESS) & ~(0x38), MMA8452_ADDRESS);
    if (ACCEL_DATARATE <= 7)
      readi2cRegister(0x2A, readi2cRegister(0x2A, MMA8452_ADDRESS) | (ACCEL_DATARATE << 3), MMA8452_ADDRESS);  
  
    // Set back to active mode to start reading
    c = readi2cRegister(0x2A, MMA8452_ADDRESS);
    readi2cRegister(0x2A, c | 0x01, MMA8452_ADDRESS);
    return 1;
  }
  else{
    return -1;
  }
}

// Read i registers sequentially, starting at address into the dest byte array
void SparkiClass::readi2cRegisters(byte address, int i, byte * dest, uint8_t i2cAddress)
{
  i2cSendStart();
  i2cWaitForComplete();

  i2cSendByte((i2cAddress<<1)); // write 0xB4
  i2cWaitForComplete();

  i2cSendByte(address);	// write register address
  i2cWaitForComplete();

  i2cSendStart();
  i2cSendByte((i2cAddress<<1)|0x01); // write 0xB5
  i2cWaitForComplete();
  for (int j=0; j<i; j++)
  {
    i2cReceiveByte(-1); // -1 = True
    i2cWaitForComplete();
    dest[j] = i2cGetReceivedByte(); // Get MSB result
  }
  i2cWaitForComplete();
  i2cSendStop();

  cbi(TWCR, TWEN); // Disable TWI
  sbi(TWCR, TWEN); // Enable TWI
}

// Read a single byte from address and return it as a byte
byte SparkiClass::readi2cRegister(uint8_t address, uint8_t i2cAddress)
{
  byte data;
  
  i2cSendStart();
  i2cWaitForComplete();
  
  i2cSendByte((i2cAddress<<1)); // Write 0xB4
  i2cWaitForComplete();
  
  i2cSendByte(address);	// Write register address
  i2cWaitForComplete();
  
  i2cSendStart();
  
  i2cSendByte((i2cAddress<<1)|0x01); // Write 0xB5
  i2cWaitForComplete();
  i2cReceiveByte(-1); // -1 = True
  i2cWaitForComplete();
  
  data = i2cGetReceivedByte();	// Get MSB result
  i2cWaitForComplete();
  i2cSendStop();
  
  cbi(TWCR, TWEN);	// Disable TWI
  sbi(TWCR, TWEN);	// Enable TWI
  
  return data;
}

// Writes a single byte (data) into address
void SparkiClass::readi2cRegister(unsigned char address, unsigned char data, uint8_t i2cAddress)
{
  i2cSendStart();
  i2cWaitForComplete();

  i2cSendByte((i2cAddress<<1)); // Write 0xB4
  i2cWaitForComplete();

  i2cSendByte(address);	// Write register address
  i2cWaitForComplete();

  i2cSendByte(data);
  i2cWaitForComplete();

  i2cSendStop();
}




