//problem: memory leakage... must take out unnecessary stuffs from the memory out--> solved
//also, if something is approaching from the side, it might also be dangerous--> solved
//any value larger than 50cm could be neglected in practice ??
//data have some error, but it seems that it is always false negative, not false positive. Maybe the max value is always correct??
//data are not as correct in every directions. Seems quite off at +60 ~ +70 deg
//data transmission rate has to be faster...(not really?)
//Crash a lot, even the display part of the library is taken out. Should be interupt related problem.
//If it goes straight and map, it seems fine. However, whenver it see a wall and needs to turn, it is likely to crash (not always)
//Algorithm seems ok, it is just these non-ideal defects that needs to be dealt.
//
//Also, the library writer is so sketchy, f******************k!

#include "Sparki.h"
// SparkiMapper.ino
#define ANG_L -70//angle-left
#define ANG_C 0  //angle-center
#define ANG_R 70 //angle-right.
#define ANG_S 10 //seperation of sampling angles(deg)
#define ANG_N 15 //number of samples
#define SERVO_T 30 //time needed to wait for the servo to go to the correct position.

#define MIN_DIST 16  //2* minimum distance(cm) - danger
#define THR_DIST 20  //2* threshold distance(cm) - make turn 

#define STEP_LEN 2 //length for each step(cm)
#define MAX_CONS_TURN 6
float dis[ANG_N];//distance at different angles.
//Notice, to save the calculating power and increase percision, the distance is taken as the sum of 2 measurement.
//float posX, posY, direct;
int angTmp, disTmp, i;
int minD, maxI,maxD; //The max/min distance and its index.
int consecutiveTurn=0;
//x,y coordinates and direction(in RAD) of the robot
void setup()
{
    //posX = 0;
    //posY = 0;
    //direct = PI / 2.0;
    for (i = 0; i < ANG_N; i++) {
        dis[i] = 0;
    }
    sparki.servo(ANG_L);
    delay(SERVO_T);
    Serial1.println(F("RESET"));
}

void moveRobot(int cm)
{
    //move the robot while updating position.
    
    Serial1.print(F("M "));
    Serial1.println(cm);
    if(cm!=0){sparki.moveForward(cm);}
}
void rotateRobot(int deg)
{
    //rotate the robot while updating direction.
    deg=-abs(deg);
    Serial1.print(F("T "));
    Serial1.println(deg);
    delay(SERVO_T);
    if(deg>0){
      sparki.moveRight(deg);
    }else if(deg<0){
      sparki.moveLeft(-deg);
    }
}
void loop()
{

    /*
    Serial1.print(posX, 8);
    Serial1.print(F(" "));
    Serial1.print(posY, 8);
    Serial1.print(F(" "));
    Serial1.println(direct, 8);
    //send the current position/direction through Serial1(Bluetooth)
    */
    for (angTmp = ANG_R, i = ANG_N - 1; i >= 0; i--, angTmp -= ANG_S) {
        sparki.servo(angTmp);
        delay(SERVO_T);
        //delay(140);
        dis[i] = sparki.ping();
    }
    delay(SERVO_T);
    for (angTmp = ANG_L, i = 0; i < ANG_N; i++, angTmp += ANG_S) {
        sparki.servo(angTmp);
        delay(SERVO_T);
        //delay(140);
        disTmp = sparki.ping();
        Serial1.print(F("A "));
        Serial1.print(angTmp);
        Serial1.print(F(" "));
        Serial1.print(dis[i] + disTmp);
        //Notice, to save the calculating power and increase percision, the distance is taken as the sum of 2 measurement.
        Serial1.print(F(" "));
        Serial1.println(abs(dis[i] - disTmp));
        dis[i] += disTmp;
    }
        minD = THR_DIST;maxI = 0; maxD = 0; //The max/min distance and its index. used as min here
        for (i = 0; i < ANG_N; i++) {
            if (dis[i] < minD) {
                minD = dis[i];
            }
            if (dis[i] > maxD) {
                maxI = i;
                maxD = dis[i];
            }
        }
        Serial1.println(F("C"));
        if (minD < THR_DIST && consecutiveTurn<MAX_CONS_TURN) {
            rotateRobot(ANG_L + maxI * ANG_S);
            ++consecutiveTurn;
        } else {
            //if far from an obstacle, go ahead.
            moveRobot(STEP_LEN);
            consecutiveTurn=0;
        }
    //moveRobot(STEP_LEN);
}






