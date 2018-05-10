#include <Keypad.h>
#include <EEPROM.h>

#define SIZE_BUFFER_DATA       50

//Buffer
char        bufferData [SIZE_BUFFER_DATA];

//Arreglo con comandos
//String resultado[3];
String *resultado;

String param0;
String param1;
String param2;

//String recibido por serial
String inputString;

//Flag para saber si ya se completo el String
boolean stringComplete = false;

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
int redLedPin = A0;
int CONTACT_PIN = 11;
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
  pinMode(CONTACT_PIN, INPUT);
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

    // Iniciamos el monitor serie
  Serial.begin(9600);

  // Ouput pin definition for BATTERY_LED
  pinMode(BATTERY_LED,OUTPUT);

  //Input pin definition for battery measure
  pinMode(BATTERY_PIN,INPUT);


  //Prueba EEPROM añadir contraseña
//  addPassword(1111, 1);
//  addPassword(2222, 2);
//  addPassword(3333, 3);
//  addPassword(4444, 4);
//  addPassword(5555, 5);
//  addPassword(6666, 6);
//  addPassword(7777, 7);
//  addPassword(8888, 8);
//  addPassword(9999, 9);
//  addPassword(1010, 10);
//
//  addPassword(1001, 11);
//  addPassword(1212, 12);
//  addPassword(1313, 13);
//  addPassword(1414, 14);
//  addPassword(1515, 15);
//  addPassword(1616, 16);
//  addPassword(1717, 17);
//  addPassword(1818, 18);
//  addPassword(1919, 19);
//  addPassword(2020, 20);
//
//  updatePassword(1234,7);
//  deletePassword(9);
//  deleteAllPasswords();
//  
  
}

void setColor(int redValue, int greenValue, int blueValue) {
  analogWrite(redPin, redValue);
  analogWrite(greenPin, greenValue);
  analogWrite(bluePin, blueValue);
}

void verificarEEPROM(){
    receiveData();

    if(inputString != "")
    {
      Serial.println(inputString);
      stringComplete = true;
    }
    
    

    if(stringComplete){
    //processCommand(resultado,inputString);
    procesarComandoPropio(inputString);

    Serial.println(param0);
    Serial.println(param1);
    Serial.println(param2);
    if(param0=="CHANGE_PASSWORD"){
        updatePassword(param2.toInt(),param1.toInt());
      }
    else if(param0=="ADD_PASSWORD"){
      addPassword(param2.toInt(),param1.toInt());
    }
    else if(param0=="DELETE_PASSWORD"){
      deletePassword(param1.toInt());
    }
    else if(param0=="DELETE_ALL"){
      deleteAllPasswords();
    }

    }

    param0 = "";
    param1 = "";
    param2 = "";
    inputString = "";
    stringComplete = false;
    
}

void receiveData() {
  while (Serial.available()) {
    // get the new byte:
    char inChar = (char)Serial.read();
    // add it to the inputString:
    inputString += inChar;
    // if the incoming character is a newline, set a flag
    // so the main loop can do something about it:
    if (inChar == '\n') {
      inputString.toCharArray(bufferData, SIZE_BUFFER_DATA);
      stringComplete = true;
    }
  }
}

void loop() {
  //Button input read and processing 
  if(!buttonState) {
    if(digitalRead(CONTACT_PIN)) {
      currTime = millis();
      buttonState = true;
      setColor(0, 255, 0);
      open = true;
      attempts = 0;
      //Serial.println("Door Opened");
    }
  }
  else {
    if(digitalRead(CONTACT_PIN)) {
      if((millis()-currTime)>=30000) {
        setColor(255, 0, 0);
        //Alerta #11111 Por puerta abierta más de 30 seg
        Serial.println(11111);
      }
    }else{
      setColor(0, 0, 255);
      open = false;
      buttonState = false;
      //Serial.println("Door closed!!");
    }
  }

  char customKey;

  if(!block) {
    //Selected key parsed;
    customKey = customKeypad.getKey();
  }
  else {
    //Serial.println("Number of attempts exceeded");
    while(true);
  }

  //Verification of input and appended value
  if (customKey) {  
    currentKey+=String(customKey);
    //Serial.println(currentKey);
  }

  //If the current key contains '*' and door is open
  if(open && currentKey.endsWith("*")) {
    open = false;
    //Serial.println("Door closed");
    digitalWrite(10,LOW);
    currentKey = "";
    //BLUE when the door is closed (standby)
    setColor(0, 0, 255); 
    currTime = 0;
  }
  //If the current key contains '#' reset attempt
  if(currentKey.endsWith("#")&&currentKey.length()<=KEY.length()) {
    currentKey = "";
    //Serial.println("Attempt deleted");
  }

  //If current key matches the key length
  if (currentKey.length()== KEY.length()) {

    //Abertura desde la EEPROM
    if(compareKey(currentKey)) {
      digitalWrite(10,HIGH);
      open = true;
      //Serial.println("Door opened!!");
      attempts = 0;
      //GREEN when doors is open
      setColor(0, 255, 0);
      currTime = millis();
      currentKey += "entro";
    } else {
      attempts++;
      currentKey = "";
      //Serial.println("Number of attempts: "+String(attempts));
      //RED for 1 second when the pass is wrong
      setColor(255, 0, 0);
      delay(1000);
      setColor(0, 0, 255); 
    }
  } else if(currentKey.length()> KEY.length()){
    //If times >= 30000 led go RED
    if ((millis() - currTime) >= 30000){
      //Alerta #11111 Por puerta abierta más de 30 seg
      Serial.println(11111);
      setColor(255, 0, 0);
    } else {
      //Serial.println("Door opened!!");
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
      //Alerta #22222 Por detección de movimiento en el sensor
      Serial.println(22222);
      // We only want to print on the output change, not state
      pirState = HIGH;
    }
  } else {
    digitalWrite(redLedPin, LOW); // turn LED OFF
    if (pirState == HIGH){
      // we have just turned of
      //Serial.println("Motion ended!");
      // We only want to print on the output change, not state
      pirState = LOW;
    }
  }

  //Value conversion from digital to voltage
  batteryCharge = (analogRead(BATTERY_PIN)*5.4)/1024;
  
  //Measured value comparison with min voltage required
  if(batteryCharge<=MIN_VOLTAGE) {
    digitalWrite(BATTERY_LED,HIGH);
    Serial.println(44444);
  }
  else {
    digitalWrite(BATTERY_LED,LOW);
  }

  verificarEEPROM();

  delay(100);
}

// Method that compares a key with stored keys
boolean compareKey(String key) {
  int acc = 3;
  int codif, arg0, arg1; 
  for(int i=0; i<3; i++) {
    codif = EEPROM.read(i);
    while(codif!=0) {
      if(codif%2==1) {
        arg0 = EEPROM.read(acc);
        arg1 = EEPROM.read(acc+1)*256;
        arg1+= arg0;
        if(String(arg1)==key) {
          return true;
        }
      }
      acc+=2;
      codif>>=1;
    }
    acc=(i+1)*16+3;
  }
  return false;
}

// Methods that divides the command by parameters
void processCommand(String* result, String command) {
  char buf[sizeof(command)];
  String vars = "";
  vars.toCharArray(buf, sizeof(buf));
  char *p = buf;
  char *str;
  int i = 0;
  while ((str = strtok_r(p, ";", &p)) != NULL) {
    // delimiter is the semicolon
    result[i++] = str;
  }
}

//Method that adds a password in the specified index
void addPassword(int val, int index) {
  byte arg0 = val%256;
  byte arg1 = val/256;
  EEPROM.write((index*2)+3,arg0);
  EEPROM.write((index*2)+4,arg1);
  byte i = 1;
  byte location = index/8;
  byte position = index%8;
  i<<=position;
  byte j = EEPROM.read(location);
  j |= i;
  EEPROM.write(location,j);
}

//Method that updates a password in the specified index
void updatePassword(int val, int index) {
  byte arg0 = val%256;
  byte arg1 = val/256;
  EEPROM.write((index*2)+3,arg0);
  EEPROM.write((index*2)+4,arg1);
}

//Method that deletes a password in the specified index
void deletePassword(int index) {
  byte i = 1;
  byte location = index/8;
  byte position = index%8;
  i<<=position;
  byte j = EEPROM.read(location);
  j ^= i;
  EEPROM.write(location,j);
}

//Method that deletes all passwords
void deleteAllPasswords() {
  //Password reference to inactive
  EEPROM.write(0,0);
  EEPROM.write(1,0);
  EEPROM.write(2,0);
}

void procesarComandoPropio(String recibido){
   param0 = getValue(recibido,';',0);
   param1 = getValue(recibido,';',1);
   param2 = getValue(recibido,';',2);
}

String getValue(String data, char separator, int index)
{
    int found = 0;
    int strIndex[] = { 0, -1 };
    int maxIndex = data.length() - 1;

    for (int i = 0; i <= maxIndex && found <= index; i++) {
        if (data.charAt(i) == separator || i == maxIndex) {
            found++;
            strIndex[0] = strIndex[1] + 1;
            strIndex[1] = (i == maxIndex) ? i+1 : i;
        }
    }
    return found > index ? data.substring(strIndex[0], strIndex[1]) : "";
}

