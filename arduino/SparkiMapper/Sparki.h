#ifndef Sparki_h
#define Sparki_h

#include "Arduino.h"

#define SHIFTREG_LATCH      TXLED0   // PD5
#define STATUS_LED          13        
#define BUZZER              11       // PB7
#define BUZZER_FREQ         2800
#define ULTRASONIC_ECHO     5        // PC6
#define ULTRASONIC_TRIG     10       // PB6
#define IR_RECEIVE          7        // PE6
#define IR_SEND             6        // PD7

#define SERVO               9        // 
#define SERVO_LEFT          -75
#define SERVO_CENTER        0
#define SERVO_RIGHT         75

// defining the MUX pins
#define MUX_ANALOG		A2 // PF5
#define MUX_A  	        A3 // PF4
#define MUX_B      		A4 // PF1
#define MUX_C        	A5 // PF0

// defining the IR line sensor pins (on the multiplexer)
#define IR_EDGE_RIGHT      LOW, HIGH, LOW	// Mux A2     
#define IR_LINE_RIGHT      HIGH, LOW, LOW	// Mux A1     
#define IR_LINE_CENTER     LOW, LOW, LOW	// Mux A0      
#define IR_LINE_LEFT       HIGH, HIGH, LOW	// Mux A3 
#define IR_EDGE_LEFT       HIGH, LOW, HIGH // Mux A5       

// defining the light sensors
#define LIGHT_RIGHT         HIGH, HIGH, HIGH // Mux A7
#define LIGHT_CENTER        LOW, HIGH, HIGH	 // Mux A6
#define LIGHT_LEFT          LOW, LOW, HIGH	 // Mux A4

// defining battery monitor constants
#define BATTERY_MONITOR     A10       // PB6
#define ADC_REFERENCE       2.56
#define VOLTAGE_DIVIDER     4.7  	 // using 47k and 10k resistors
#define MAX_BAT_V          (VOLTAGE_DIVIDER * ADC_REFERENCE) 

// defining the buttons on the shipped-with remote control
#define REMOTE_ERROR    -2
#define REMOTE_REPEAT   -3
#define REMOTE_POWER    69
#define REMOTE_MENU     71
#define REMOTE_TEST     68
#define REMOTE_PLUS     64
#define REMOTE_BACK     67
#define REMOTE_REWIND   7
#define REMOTE_PLAY     21
#define REMOTE_FF       9
#define REMOTE_0        22
#define REMOTE_MINUS    25
#define REMOTE_C        13
#define REMOTE_1        12
#define REMOTE_2        24
#define REMOTE_3        94
#define REMOTE_4        8
#define REMOTE_5        28
#define REMOTE_6        90
#define REMOTE_7        66
#define REMOTE_8        82
#define REMOTE_9        74

// define the shift registers pin output values for the RGB arrays
#define RGB_R 0x01 // pin value of the Red LED on the RGB on the shift register
#define RGB_G 0x02 // pin value of the Green LED on the RGB on the shift register
#define RGB_B 0x04 // pin value of the Blue LED on the RGB on the shift register
#define RGB_SHIFT 1 // which shift register the RGB is on (starts at 0)

#define RGB_RED     100, 0,   0
#define RGB_ORANGE  90,  100, 0
#define RGB_YELLOW  60,  100, 0
#define RGB_GREEN   0,   100, 0
#define RGB_BLUE    0,   0,   100
#define RGB_PINK    90,  0,   100
#define RGB_INDIGO  20,  0,   100
#define RGB_VIOLET  60,  0,   100
#define RGB_WHITE   60,  100, 90
#define RGB_OFF     0,   0,   0

// properties about the robot in cm
#define PI 3.1415926536
const int   STEPS_PER_REV          = 4096; // steps for wheels to revolve 360 degrees
const float WHEEL_DIAMETER_CM      = 5.00;
const float WHEEL_CIRCUMFERENCE_CM = WHEEL_DIAMETER_CM * PI;
const float CM_PER_STEP            = WHEEL_CIRCUMFERENCE_CM / STEPS_PER_REV;
const float STEPS_PER_CM            = STEPS_PER_REV / WHEEL_CIRCUMFERENCE_CM;

const float TRACK_WIDTH_CM         = 8.51;              //tire seperation in cm  
const float STEPS_PER_ROTATION     = (TRACK_WIDTH_CM / WHEEL_DIAMETER_CM) * STEPS_PER_REV ;  // robot rotation
const float STEPS_PER_DEGREE       = STEPS_PER_ROTATION / 360.0;         // robot rotation
const float CM_PER_DEGREE          = WHEEL_CIRCUMFERENCE_CM / 360.0;     // wheel movement per degree rotation of robot 

//#define SPARKI_CORRECTION_VALUE 1.0319444
#define SPARKI_CORRECTION_VALUE 1.0

#define DISTANCE_TIME_COSNTANT 222.222222
#define DEGREES_TIME_COSNTANT  21.388888

// defines for left and right motors
#define MOTOR_LEFT    0
#define MOTOR_RIGHT   1
#define MOTOR_GRIPPER 2

// defines for direction
#define DIR_CCW -1
#define DIR_CW   1

//includes for the LCD 

#define swap(a, b) { uint8_t t = a; a = b; b = t; }

#define BLACK 0
#define WHITE 1

// SPI definitions
#define SPI_MODE_MASK 0x0C  // CPOL = bit 3, CPHA = bit 2 on SPCR
#define SPI_CLOCK_MASK 0x03  // SPR1 = bit 1, SPR0 = bit 0 on SPCR
#define SPI_2XCLOCK_MASK 0x01  // SPI2X = bit 0 on SPSR
#define SPI_CLOCK_DIV2 0x04

// Magnetometer definitions
#define Measurement_Continuous 0x00  
#define DataRegisterBegin 0x03
#define HMC5883L_Address 0x1E
#define DataRegisterBegin 0x03
#define RawMagDataLength 6
#define ConfigurationRegisterB 0x01
#define ModeRegister 0x02
#define M_SCALE 0.92

// Accelerometer definitions
#define MMA8452_ADDRESS 0x1D  // SA0 is high, 0x1C if low
#define ACCEL_SCALE 2  // Sets full-scale range to +/-2, 4, or 8g. Used to calc real g values.
#define ACCEL_DATARATE 0 //Set the output data rate: 0=800Hz, 1=400, 2=200, 3=100, 4=50, 5=12.5, 6=6.25, 7=1.56

#define SPEED_ARRAY_LENGTH 10  // uses an array to determine speed. 
                               // increase this number (<255) to increase precision of speed control
#define PING_ATTEMPTS 7

class SparkiClass{

public:
  SparkiClass();
  float ping_single();
  float ping();
  void begin();
  void beep();
  void beep(int);
  void beep(int, int);
  void noBeep();
  void RGB(uint8_t,uint8_t,uint8_t);

  void setMux(uint8_t, uint8_t, uint8_t);
  
// Light level sensors
  int lightRight();
  int lightCenter();
  int lightLeft();

// Infrared reflectance sensors
  int edgeRight();
  int lineRight();
  int lineCenter();
  int lineLeft();  
  int edgeLeft();
  
  int readSensorIR(int);
  int readBlindSensorIR(int,int,int);
  int diffIR(int,int,int);
  

// Infrared Remote sensor
  int readIR();

// Infrared Remote sensor
  void sendIR(uint8_t);
  void irPulse(uint16_t, uint16_t);

// Wire Functions
  void WireWrite(int, int);
  uint8_t* WireRead(int, int);

// Compass
  float compass();

// Magnetometer
  float readMag();
  float xAxisMag;
  float zAxisMag;
  float yAxisMag;  
  float magX();
  float magY();
  float magZ();

// Accelerometer
  int accelGood;
  float xAxisAccel;
  float zAxisAccel;
  float yAxisAccel;  
  float accelX();
  float accelY();
  float accelZ();
  
  void readAccelData();
  int initAccelerometer();
 
// I2C functions 
  void readi2cRegisters(uint8_t, int, uint8_t *, uint8_t);
  uint8_t readi2cRegister(uint8_t, uint8_t);
  void readi2cRegister(unsigned char, unsigned char, uint8_t);

// Servo Functions
  void startServoTimer();
  void servo(int);
  int8_t servo_deg_offset;

// high-level move functions
  void moveForward(float);
  void stepForward(unsigned long);
  void moveForward();
  
  void moveBackward(float);
  void stepBackward(unsigned long);
  void moveBackward();
  
  void moveLeft(float);
  void stepLeft(unsigned long);
  void moveLeft();
  
  void moveRight(float);
  void stepRight(unsigned long);
  void moveRight();

  void gripperOpen();
  void gripperClose();
  void gripperStop();
  
  void moveStop();
  void speed(uint8_t);

// individual motor control (non-blocking)
// speed range is percent 0-100
  void motorRotate( int motor, int direction,  int speed, long steps);
  void motorStop(int motor);

// returns true if one or both motors a still stepping
  bool areMotorsRunning();
   
  void onIR();
  void offIR();


private:    
  static void scheduler();

};

extern SparkiClass sparki;

#endif















