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
#define SERVO_T 15 //time needed to wait for the servo to go to the correct position.

#define MIN_DIST 15  //2* minimum distance(cm) - danger
#define THR_DIST 25  //2* threshold distance(cm) - make turn 
#define MAX_DIST 10000

#define STEP_LEN 2 //length for each step(cm)
#define MAX_CONS_TURN 2

#define PING_NUM 16 //total number of trials
#define PING_LB 5 //left bound to calculate error
#define PING_CI 7 //center index
#define PING_RB 10 //right bound to calculate error
#define PING_T 18 //delayed time between readings

#define IGNORE_THLHD 160

float dis[ANG_N];//distance at different angles.
float err[ANG_N];
float disTmp[PING_NUM];
//Notice, to save the calculating power and increase percision, the distance is taken as the sum of 2 measurement.
//float posX, posY, direct;
int minD, maxI, maxD; //The max/min distance and its index.
int consecutiveTurn = 0;
bool clockWiseServo;
bool controlled;
void setup()
{
    for (int i = 0; i < ANG_N; i++) {
        dis[i] = 0;
    }
    sparki.servo(ANG_L);
    clockWiseServo = true;
    controlled = false;
    delay(SERVO_T);
    Serial1.println(F("RESET"));
}

void moveRobot(const int &cm)
{
    //move the robot while updating position.
    Serial1.print(F("M "));
    Serial1.println(cm);
    if (cm != 0) {
        sparki.moveForward(cm);
    }
}
void rotateRobot(const int &deg)
{
    //rotate the robot while updating direction.
    Serial1.print(F("T "));
    Serial1.println(deg);
    if (deg > 0) {
        sparki.moveRight(deg);
    } else if (deg < 0) {
        sparki.moveLeft(-deg);
    }
}
void checkDis(const int &ang, const int &index )
{
    sparki.servo(ang);
    delay(SERVO_T);
    for (int i = 0; i < PING_NUM; i++) {
        delay(PING_T);
        disTmp[i] = sparki.ping_single();
    }
    // sort them in order
    int i, j;
    int temp;
    for (i = (PING_NUM - 1); i > 0; i--) {
        for (j = 1; j <= i; j++) {
            if (disTmp[j - 1] > disTmp[j]) {
                temp = disTmp[j - 1];
                disTmp[j - 1] = disTmp[j];
                disTmp[j] = temp;
            }
        }
    }
    dis[index] = disTmp[PING_CI] + disTmp[PING_CI + 1];
    err[index] = abs(disTmp[PING_RB] - disTmp[PING_LB]) / float(PING_RB - PING_LB);
    Serial1.print(F("A "));
    Serial1.print(ang);
    Serial1.print(F(" "));
    Serial1.print(dis[index]);
    //Notice, to save the calculating power and increase percision, the distance is taken as the sum of 2 measurement.
    Serial1.print(F(" "));
    Serial1.println(err[index]);
}
void checkAllDistance()
{
    if (clockWiseServo) {//Save the rotation time of servo by going back and forth.
        for (int ang = ANG_L, i = 0; i < ANG_N; i++, ang += ANG_S) {
            checkDis(ang, i);
        }
    } else {
        for (int ang = ANG_R, i = ANG_N - 1; i >= 0; i--, ang -= ANG_S) {
            checkDis(ang, i);
        }
    }
    clockWiseServo = !clockWiseServo;
}
bool obstacleInFront()
{
    for (int i = ANG_N / 2 - 4; i <= ANG_N / 2 + 4; i++) {
        if (dis[i] < THR_DIST) {
            return true;
        }
    }
    return false;
}
void turnToMaxDistance()
{
    maxI = 0; maxD = 0; //The max/min distance and its index.
    for (int i = 0; i < ANG_N; i++) {
        if (dis[i] > maxD && dis[i] + 10 * err[i] < IGNORE_THLHD) { //if larger and valid data exists,
            maxI = i;
            maxD = dis[i];
        }
    }
    rotateRobot(ANG_L + maxI * ANG_S);
    ++consecutiveTurn;
}
int findMinDistance()
{
    minD = MAX_DIST; //The max/min distance and its index. used as min here
    for (int i = 0; i < ANG_N; i++) {
        if (dis[i] < minD) {
            minD = dis[i];
        }
    }
    return minD;
}
void moveAhead()
{
    moveRobot(STEP_LEN);
    --consecutiveTurn;
    if (consecutiveTurn < 0) {
        consecutiveTurn = 0;
    }
}
void followSerialControl()
{
    do {
        int tmp;
        while (true) {
            tmp = Serial1.read();
            if(tmp==-1 && !controlled){
                return;
            }
            if(tmp=='C'){
                break;
            }
        }
        while(true){
            tmp = Serial1.read();
            if(tmp!=-1){
                break;
            }
        }
        //Serial1.print("tmp=");
        Serial1.println(char(tmp));
        int tmpI;
        switch (tmp) {
        case 'T':
            tmpI = Serial1.parseInt();
            //Serial1.print("tmpI=");
            //Serial1.println(tmpI);
            rotateRobot(tmpI);
            break;
        case 'M':
            tmpI = Serial1.parseInt();
            //Serial1.print("tmpI=");
            //Serial1.println(tmpI);
            moveRobot(tmpI);
            break;
        case 'A':
            checkAllDistance();
        case 'E':
            controlled = false;
            break;
        case 'S':
            controlled = true;
            break;
        default:
            break;
        }
    } while (controlled);
}
void loop()
{
    if (Serial1.available() > 0) {
        followSerialControl();
    }

    checkAllDistance();

    if (obstacleInFront()) {
        if (consecutiveTurn > MAX_CONS_TURN) {
            do {
                rotateRobot(43); //select a prime number to avoid scanning the same angle;
                checkAllDistance();
            } while (obstacleInFront());
            moveAhead();
        } else {
            turnToMaxDistance();
        }

    } else if (findMinDistance() < THR_DIST) {
        if (consecutiveTurn > MAX_CONS_TURN) {
            moveAhead();
        } else {
            turnToMaxDistance();
        }
    } else {
        //if far from an obstacle, go ahead.
        moveAhead();
    }
    //moveRobot(STEP_LEN);
}












