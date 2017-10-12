String inputString = "";
boolean stringComplete = false;

void setup() {
  Serial.begin(9600);
  inputString.reserve(200);
  pinMode(2, OUTPUT);
  pinMode(3, OUTPUT);
  pinMode(4, OUTPUT);
  pinMode(5, OUTPUT);
  pinMode(6, OUTPUT);
  pinMode(8, OUTPUT);
  pinMode(9, OUTPUT);
  pinMode(10, OUTPUT);
  pinMode(11, OUTPUT);
  pinMode(12, OUTPUT);
  pinMode(13, OUTPUT);

  // DIGITAL SENSOR
  pinMode(7, INPUT);
}

void loop() {
  if (stringComplete) {
    delay(200);
    if(inputString.substring(0,1) == "r"){
      int pin = inputString.substring(1,3).toInt();
      if (inputString.substring(3,4) == "a"){
          Serial.println(analogRead(pin));
      }else{
    	  Serial.println(digitalRead(pin));
      }
    }else{
      int pin = inputString.substring(1,3).toInt();
      if (inputString.substring(3,4) == "a"){
          // TODO
      }else{
          //Digital
          int option = inputString.substring(4,5).toInt();
          if(option == 0){
            digitalWrite(pin,LOW);
          }else{
            digitalWrite(pin,HIGH);
          }
      }
    }

    inputString = "";
    stringComplete = false;
  }
}

void serialEvent() {
  while (Serial.available()) {
    char inChar = (char)Serial.read();
    inputString += inChar;
    if (inChar == '*') {
      stringComplete = true;
    }
  }
}
