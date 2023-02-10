# Token Payment API

This project is aimed to develop an application for receiving payments using the OSY-QR service mentioned under the Token Payment API title, and to set up a payment database structure.

## Files

### activity_main.xml
This file contains the user interface for the MainActivity. It includes the layout and design of the main screen.

### MainActivity.kt
This file is the main class of the application. It implements the functionality of the main screen, including the QR code generation with Get QR for sale API by Volley, and launching the PaymentActivity.

### PaymentActivity.kt
This file implements the payment processing functionality of the application. It uses the Volley library to make a REST API call to the OSY-QR service, and processes the response to get the payment details.

## Getting Started

1. Clone the repository to your local machine
2. Open the project in Android Studio
3. Run the project on an emulator or an Android device
4. Scan the generated QR code with a compatible payment device to initiate a payment
5. Check the list of payments in the PaymentActivity screen

## Prerequisites

- Android Studio
- Android SDK
- Volley library

## Built With

- Android Studio
- Kotlin programming language

## Contributing

Feel free to contribute to this project by submitting pull requests.
