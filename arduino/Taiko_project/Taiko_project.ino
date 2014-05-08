//format: 00+KEYC+UD
//KEYC: key code
//left side:    1000
//left center:  0100
//right center: 0010
//right side:   0001
//UD: up/down
//up: 01 down: 10
#define KEY_LS_U B00100001
#define KEY_LS_D B00100010
#define KEY_LC_U B00010000
#define KEY_LC_D B00010010
#define KEY_RC_U B00001001
#define KEY_RC_D B00001010
#define KEY_RS_U B00000101
#define KEY_RS_D B00000110
volatile bool flagC,flagS;
void setup()
{
  Serial.begin(38400);
  flagS=false;
  flagC=false;
  //attachInterrupt(0, signalC, LOW);
  //attachInterrupt(1, signalS, LOW);
}
void signalC(){
  flagC=true;
}
void signalS(){
  flagS=true;
}
void loop()
{
  if(flagC){
    Serial.write('C');
    flagC=false;
  }
  if(flagS){
    Serial.write('S');
    flagS=false;
  }

}

