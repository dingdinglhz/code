//problem: memory leakage... must take out unnecessary stuffs from the memory out
//also, if something is approaching from the side, it might also be dangerous.
//any value larger than 50cm could be neglected in practice ??
//data error rate is large.
//data are not as correct in every directions.
//data transmission rate has to be faster...
#include "Sparki.h"
// SparkiMapper.ino
#define ANG_L -70//angle-left
#define ANG_C 0  //angle-center
#define ANG_R 70 //angle-right.
#define ANG_S 10 //seperation of sampling angles(deg)
#define ANG_N 15 //number of samples
#define SERVO_T 40 //time needed to wait for the servo to go to the correct position.

#define MIN_DIST 8  //minimum distance(cm) - danger
#define THR_DIST 20 //threshold distance(cm) - make turn 

#define STEP_LEN 2 //length for each step(cm)

int dis[ANG_N];//distance at different angles.
float posX, posY, direct;
//x,y coordinates and direction(in RAD) of the robot
bool clockWiseServo; //whether the servo rotates clockwise or not.
void setup()
{
    posX = 0;
    posY = 0;
    direct = PI / 2.0;
    for (int i = 0; i < ANG_N; i++) {
        dis[i] = 0;
    }

    clockWiseServo = true;
    sparki.servo(ANG_L);
}

void moveRobot(const float &cm)
{
    //move the robot while updating position.
    sparki.moveForward(cm);
    posX += cm * cos(direct);
    posY += cm * sin(direct);
}
void rotateRobot(const float &deg)
{
    //rotate the robot while updating direction.
    sparki.moveRight(deg);
    direct -= deg * PI / float(360);
    if (direct < 0) {
        direct += PI;
    }
    if (direct > PI) {
        direct -= PI;
    }
}
void checkDis(const int &ang, const int &i)
{
    //turn to the desired angle and check the distance.
    sparki.servo(ang);
    delay(SERVO_T);
    dis[i] = sparki.ping();
    Serial1.print(F("A "));
    Serial1.print(ang);
    Serial1.print(F(" - "));
    Serial1.println(dis[i]);
}
void loop()
{
    Serial1.print(F("P "));
    Serial1.print(posX, 8);
    Serial1.print(F(" "));
    Serial1.print(posY, 8);
    Serial1.print(F(" "));
    Serial1.println(direct, 8);
    //send the current position/direction through Serial1(Bluetooth)
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

    if (dis[ANG_N / 2] < MIN_DIST) { //if very close to an obstacle, go back.
        sparki.beep();
        moveRobot(-STEP_LEN);
        sparki.beep();
    } else if (dis[ANG_N / 2] < THR_DIST) {
    	//if close but still a reasonable distance away from an obstacle, try to find a best angle to turn.
        sparki.beep();
        int maxI = 0, maxD = 0;
        for (int i = 0; i < ANG_N; i++) {
            if (dis[i] > maxD) {
                maxI = i;
                maxD = dis[i];
            }
        }
        rotateRobot(ANG_L + maxI * ANG_S);
    } else {
    	//if far from an obstacle, go ahead.
        moveRobot(STEP_LEN);
    }
}

