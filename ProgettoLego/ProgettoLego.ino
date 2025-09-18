/*
 * Codice Arduino per far funzionare una macchinina radiotelecomandata e automaticamente  
 * @author Ripamonti Angelo, Avveduto Luca, Kotis Alexandros
 * @version 1.0
 */

#include <Wire.h>
#include <EVShield.h>
#include <EVs_EV3Touch.h>
#include <EVShieldAGS.h>
#include <EVs_EV3Infrared.h>

#define NOTE_B3 247
#define distancePin A0
#define ULTRASONIC_TRIG_PIN 11
#define ULTRASONIC_ECHO_PIN 12

EVShield evshield(0x34, 0x36);

char input;
String inputString = ""; 
bool stringComplete = false;
int velocity = 0;
int rotation = 0;
uint16_t value;
uint16_t range;
float distance;

//float batteryVoltage = 0;


/*
 * Funzione setup per l'inizializzazione delle variabili e il settaggio dei pin
 */
void setup() {
  Serial.begin(9600);
  evshield.init(SH_HardwareI2C);
  evshield.bank_a.motorReset();
  evshield.bank_b.motorReset();
  velocity = SH_Speed_Slow;
  pinMode (distancePin, INPUT);
  pinMode(ULTRASONIC_TRIG_PIN, OUTPUT);
  pinMode(ULTRASONIC_ECHO_PIN, INPUT);
}

/*
 * Funzione che fa funzionare l'intero programma tramite un ciclo perpetuo
 */
void loop() {
  //Sensore di Distanza
  value = analogRead (distancePin);
  range = get_gp2d12 (value);
  //Sensore Ultrasuoni
  digitalWrite(ULTRASONIC_TRIG_PIN, LOW);
  delayMicroseconds(2);
  digitalWrite(ULTRASONIC_TRIG_PIN, HIGH);
  delayMicroseconds(10);
  digitalWrite(ULTRASONIC_TRIG_PIN, LOW);
  long duration = pulseIn(ULTRASONIC_ECHO_PIN, HIGH);
  distance=(duration/2.0)*0.0343;

  if (stringComplete) {
    Serial.println(inputString);
    inputString = "";
    stringComplete = false;
  }

  if (range < 100 || distance < 15) { // range Sensore di Distanza | distance Sensore Infrarossi
    Serial.print(distance);
    Serial.println("cm");
    Serial.print(range);
    Serial.println(" mm");
    evshield.bank_b.motorStop(SH_Motor_1, SH_Next_Action_BrakeHold);
    evshield.bank_b.motorStop(SH_Motor_2, SH_Next_Action_BrakeHold);
  } 
  else {
    if (Serial.available() > 0) {
      input = Serial.read();
      if (input == 'w' || input == 'W') {
        if (rotation == 1) {
          evshield.bank_a.motorRunDegrees(SH_Motor_Both, SH_Direction_Reverse, velocity, 90, SH_Completion_Dont_Wait, SH_Next_Action_BrakeHold);  
        } else if (rotation == -1) {
          evshield.bank_a.motorRunDegrees(SH_Motor_Both, SH_Direction_Forward, velocity, 90, SH_Completion_Dont_Wait, SH_Next_Action_BrakeHold);
        }
        rotation = 0;
        Serial.println(input);
        evshield.bank_b.motorRunUnlimited(SH_Motor_Both, SH_Direction_Forward, velocity);
      } 
      else if (input == 'a' || input == 'A') {
        if (rotation == 1) {
          evshield.bank_a.motorRunDegrees(SH_Motor_Both, SH_Direction_Reverse, velocity, 180, SH_Completion_Dont_Wait, SH_Next_Action_BrakeHold);
        } else if (rotation == 0) {
          evshield.bank_a.motorRunDegrees(SH_Motor_Both, SH_Direction_Reverse, velocity, 90, SH_Completion_Dont_Wait, SH_Next_Action_BrakeHold);
        }
        rotation = -1;
        Serial.println(input);
        evshield.bank_b.motorRunUnlimited(SH_Motor_Both, SH_Direction_Forward, velocity);
      } 
      else if (input == 's' || input == 'S') {
        Serial.println(input);
        evshield.bank_b.motorRunUnlimited(SH_Motor_Both, SH_Direction_Reverse, velocity);
      } 
      else if (input == 'd' || input == 'D') {
        if(rotation==-1){
          evshield.bank_a.motorRunDegrees(SH_Motor_Both, SH_Direction_Forward, velocity, 180, SH_Completion_Dont_Wait, SH_Next_Action_BrakeHold);
        }
        else if(rotation==0){
          evshield.bank_a.motorRunDegrees(SH_Motor_Both, SH_Direction_Forward, velocity, 90, SH_Completion_Dont_Wait, SH_Next_Action_BrakeHold);
        }
        rotation=1;
        Serial.println(input);
        evshield.bank_b.motorRunUnlimited(SH_Motor_Both, SH_Direction_Forward, velocity);
      } 
      else if (input == 'c' || input == 'C') {
        Serial.println(input);
        tone(8, NOTE_B3, 1000 / 4);
        int pauseBetweenNotes = (1000 / 4) * 1.30;
        delay(pauseBetweenNotes);
        noTone(8);
      } 
      else if (input == 17) {  //CTRL
        Serial.println(input);
        if (velocity == SH_Speed_Slow) {
          velocity = SH_Speed_Medium;
        } 
        else if (velocity == SH_Speed_Medium) {
          velocity = SH_Speed_Full;
        }
      } 
      else if (input == 16) {  //SHIFT
        Serial.println(input);
        if (velocity == SH_Speed_Full) {
          velocity = SH_Speed_Medium;
        } 
        else if (velocity == SH_Speed_Medium) {
          velocity = SH_Speed_Slow;
        }
      } 
      else if (input == ' ') {
        Serial.println(input);
        evshield.bank_b.motorStop(SH_Motor_Both, SH_Next_Action_Brake);
      }
    }
  }
}

/*
 * Funzione per la gestione degl'eventi provenienti dalla seriale
 */
void serialEvent() {
  while (Serial.available()) {
    char inChar = (char)Serial.read();
    inputString += inChar;
    if (inChar == '\n') {
      stringComplete = true;
    }
  }
}

/*
 * Fuozione per il calcolo della distanza tra un oggetto e la macchina
 * @param value Il valore ricevuto dal Sensore di Distanza
 * @return La distanza in mm
 */
uint16_t get_gp2d12 (uint16_t value) {
    if (value < 10) value = 10;
    return ((67870.0 / (value - 3.0)) - 40.0);
}
