#include "FastLED.h"

// How many leds in your strip?
#define NUM_LEDS 144

// For led chips like Neopixels, which have a data line, ground, and power, you just
// need to define DATA_PIN.  For led chipsets that are SPI based (four wires - data, clock,
// ground, and power), like the LPD8806 define both DATA_PIN and CLOCK_PIN
#define DATA_PIN 3
#define CLOCK_PIN 13

// Define the array of leds
CRGBArray<NUM_LEDS> leds;

void setup() {
  Serial.begin(115200);

  FastLED.addLeds<NEOPIXEL, DATA_PIN>(leds, NUM_LEDS);

}

void serialEvent() {
  int channel = Serial.read();
  int numLedsToLight = Serial.read();
  //int numLedsToLight = map(val, 0, 255, 0, NUM_LEDS);


  // First, clear the existing led values
  FastLED.clear();
  for (int led = 0; led < numLedsToLight; led++) {
    leds[led] = CRGB::Blue;
  }
  FastLED.show();
  //Serial.println(numLedsToLight);
}

int prevVal = 0;
int thres = 20;
void loop() {

  int val = analogRead(2);
  if (val - prevVal > thres || prevVal - val > thres) {
    int numLedsToLight = map(val, 0, 1023, 0, NUM_LEDS);

    // First, clear the existing led values
    FastLED.clear();
    for (int led = 0; led < numLedsToLight; led++) {
      leds[led] = CRGB::Blue;
    }
    FastLED.show();
    prevVal = val;
  }
}
