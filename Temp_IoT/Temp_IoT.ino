#include <Keypad.h>

const int buzzer = A5;

//Minimum voltage required for an alert
const double MIN_VOLTAGE = 1.2;

//Battery indicator
const int BATTERY_LED = A1;

//Battery measure pin
const int BATTERY_PIN = A2;

//Current battery charge
double batteryCharge;


int inputPin = 2;               // choose the input pin (for PIR sensor)
int pirState = LOW;             // we start, assuming no motion detected
int val = 0;                    // variable for reading the pin status

//Current time when the door is opened 
long currTime;
  
//LED
int redPin= 13;
int greenPin = 10;
int bluePin = 12;
int redLedPin = 11;
int CONTACT_PIN = A0;
boolean buttonState;

//Specified password
const String KEY = "1234";

//Keypad rows
const byte ROWS = 4; 

//Keypad columns
const byte COLS = 3;

//Maximum number of attempts allowed
const byte maxAttempts = 3;

//Keypad mapping matrix
char hexaKeys[ROWS][COLS] = {
  {
    '1', '2', '3'
  }
  ,
  {
    '4', '5', '6'
  }
  ,
  {
    '7', '8', '9'
  }
  ,
  {
    '*', '0', '#'
  }
};

//Keypad row pins definition
byte rowPins[ROWS] = {
  9, 8, 7, 6
}; 

//Keypad column pins definition
byte colPins[COLS] = {
  5, 4, 3
};

//Keypad library initialization
Keypad customKeypad = Keypad(makeKeymap(hexaKeys), rowPins, colPins, ROWS, COLS); 

//Current key variable
String currentKey;
//Door state
boolean open;
//Number of current attempts
byte attempts;
//If the number of current attempts exceeds the maximum allowed
boolean block;

void setup() {

  pinMode(inputPin, INPUT);     // declare sensor as input
  
  //LED 
  pinMode(redPin, OUTPUT);
  pinMode(greenPin, OUTPUT);
  pinMode(bluePin, OUTPUT);
  pinMode(redLedPin, OUTPUT);

  Serial.begin(9600);
  currentKey = "";
  open = false;
  attempts = 0;
  block = false;
  buttonState = false;

  //Blue as standby
  setColor(0, 0, 255);
  digitalWrite(redLedPin, LOW);

  // Ouput pin definition for BATTERY_LED
  pinMode(BATTERY_LED,OUTPUT);

  //Input pin definition for battery measure
  pinMode(BATTERY_PIN,INPUT);

  pinMode(buzzer, OUTPUT);

}

void setColor(int redValue, int greenValue, int blueValue) {
  analogWrite(redPin, redValue);
  analogWrite(greenPin, greenValue);
  analogWrite(bluePin, blueValue);
}

void loop() {
  //Button input read and processing 
  if(!buttonState) {
    if(analogRead(CONTACT_PIN)) {
      currTime = millis();
      buttonState = true;
      setColor(0, 255, 0);
      open = true;
      attempts = 0;
      Serial.println("Door opened!!");
    }
  }
  else {
    if(analogRead(CONTACT_PIN)) {
      if((millis()-currTime)>=30000) {
        setColor(255, 0, 0);
        //Alerta #11111 Por puerta abierta más de 30 seg
        Serial.println(11111);
      }
    }else{
      setColor(0, 0, 255);
      open = false;
      buttonState = false;
      Serial.println("Door closed!!");
    }
  }

  char customKey;

  if(!block) {
    //Selected key parsed;
    customKey = customKeypad.getKey();
  }
  else {
    Serial.println("Number of attempts exceeded");
    while(true);
  }

  //Verification of input and appended value
  if (customKey) {  
    currentKey+=String(customKey);
    Serial.println(currentKey);
  }

  //If the current key contains '*' and door is open
  if(open && currentKey.endsWith("*")) {
    open = false;
    Serial.println("Door closed");
    digitalWrite(10,LOW);
    currentKey = "";
    //BLUE when the door is closed (standby)
    setColor(0, 0, 255); 
    currTime = 0;
  }
  //If the current key contains '#' reset attempt
  if(currentKey.endsWith("#")&&currentKey.length()<=KEY.length()) {
    currentKey = "";
    Serial.println("Attempt deleted");
  }

  //If current key matches the key length
  if (currentKey.length()== KEY.length()) {
    if(currentKey == KEY) {
      digitalWrite(10,HIGH);
      open = true;
      Serial.println("Door opened!!");
      attempts = 0;
      //GREEN when doors is open
      setColor(0, 255, 0);
      currTime = millis();
      currentKey += "entro";
    } else {
      attempts++;
      currentKey = "";
      Serial.println("Number of attempts: "+String(attempts));
      //RED for 1 second when the pass is wrong
      setColor(255, 0, 0);
      delay(1000);
      setColor(0, 0, 255); 
    }
  } else if(currentKey.length()> KEY.length()){
    //If times >= 30000 led go RED
    if ((millis() - currTime) >= 30000){
      Serial.println("Door opened way too long!!");
      setColor(255, 0, 0);
    } else {
      Serial.println("Door opened!!");
    }
  }

  if(attempts>=maxAttempts) {
    block = true;
    //RED for 30 seconds when its block
    setColor(255, 0, 0);
    //Alerta #33333 Numero de intentos excedidos
    Serial.println(33333);
    delay(30000);
    setColor(0, 0, 255);
    block = false;
    attempts = 0;
  } 

 val = digitalRead(inputPin);  // read input value
  if (val == HIGH) {            // check if the input is HIGH
    digitalWrite(redLedPin, HIGH);  // turn LED ON
    if (pirState == LOW) {
      // we have just turned on
      //Alerta #22222 Por detección de movimiento en el sensor
      Serial.println(22222);
      // We only want to print on the output change, not state
      pirState = HIGH;
    }
  } else {
    digitalWrite(redLedPin, LOW); // turn LED OFF
    if (pirState == HIGH){
      // we have just turned of
      Serial.println("Motion ended!");
      // We only want to print on the output change, not state
      pirState = LOW;
    }
  }

  //Value conversion from digital to voltage
  batteryCharge = (analogRead(BATTERY_PIN)*5.4)/1024;
  //batteryCharge = 1;
  //0Serial.println(batteryCharge);
  
  //Measured value comparison with min voltage required
  if(batteryCharge<=MIN_VOLTAGE) {
    analogWrite(BATTERY_LED, 255);
    //Alerta #44444 Por batería crítica
    Serial.println(44444);
            digitalWrite(buzzer, 20);
            delay(2000);                 // Espera
            digitalWrite(buzzer, 0);            // Apaga
            delay(30000);                 // Espera
  }
  else {
  analogWrite(BATTERY_LED, 0);
  }


  delay(100);
}

