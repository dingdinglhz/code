#include <SparkiEEPROM.h>
#include <Sparki.h>
void setup() {
  // put your setup code here, to run once:

}
int irCode;
int8_t servoOS = 0;
bool update;
void rememberOffSet(){
  EEPROM.write(0,servoOS);
  Serial.println("EEPROM updated");
}
void loop() {
  irCode = sparki.readIR();
  update=false;
  switch (irCode) {
    case   67:
      --servoOS;
      update=true;
      break;
    case 68:
      ++servoOS;
      update=true;
      break;
    case 25:
      servoOS=0;
      update=true;
      break;
    case 21:
      rememberOffSet();
      break;
    default:
      break;
  }
  if(update){
    sparki.servo_deg_offset=servoOS;
    sparki.servo(SERVO_CENTER);
    Serial.print("OffSet:");
    Serial.println(servoOS);
    delay(10);
  }
  
}
