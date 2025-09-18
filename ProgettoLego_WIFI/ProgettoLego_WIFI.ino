/*
 * Codice Arduino Uno WiFi Rev2 per far funzionare le comunicazioni WiFi tra il programma in Java e il programma in Arduino  
 * @author Ripamonti Angelo, Avveduto Luca, Kotis Alexandros
 * @version 1.0
 */

#include <ArduinoHttpClient.h>
#include <WiFiNINA.h>
/////// WiFi Settings ///////
char ssid[] = "iPhone di Example";
char pass[] = "password";

char serverAddress[] = "127.0.0.1";  // server address
int port = 8025;

WiFiClient wifi;
WebSocketClient client = WebSocketClient(wifi, serverAddress, port);
int status = WL_IDLE_STATUS;
int count = 0;

/*
 * Funzione setup per l'inizializzazione delle variabili, il settaggio dei pin e il settaggio del WiFi
 */
void setup() {
  Serial.begin(9600);
  Serial1.begin(9600);
  //pinMode(TX,OUTPUT);
  //pinMode(RX,INPUT);
  while ( status != WL_CONNECTED) {
    Serial.print("Attempting to connect to Network named: ");
    Serial.println(ssid);                   // print the network name (SSID);

    // Connect to WPA/WPA2 network:
    status = WiFi.begin(ssid, pass);
  }

  // print the SSID of the network you're attached to:
  Serial.print("SSID: ");
  Serial.println(WiFi.SSID());

  // print your WiFi shield's IP address:
  IPAddress ip = WiFi.localIP();
  Serial.print("IP Address: ");
  Serial.println(ip);
}


/*
 * Funzione che fa funzionare l'intero programma fintantochè il dispositivo è connesso al WiFi
 */
void loop() {
  Serial.println("starting WebSocket client");
  client.begin();

  while (client.connected()) {
    Serial.print("Sending hello ");
    Serial.println(count);

    // send a hello #
    client.beginMessage(TYPE_TEXT);
    client.print("hello ");
    client.print(count);
    client.endMessage();

    // increment count for next message
    count++;

    // check if a message is available to be received
    int messageSize = client.parseMessage();

    if (messageSize > 0) {
      Serial.println("Received a message:");
      String a = client.readString();
      Serial1.write(a.c_str());
    }
    // wait 5 seconds
    delay(5000);
  }
  Serial.println("disconnected");
}
