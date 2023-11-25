# ezetap_sdk

[![pub package](https://img.shields.io/pub/v/ezetap_sdk.svg)](https://pub.dev/packages/ezetap_sdk)
[![pub points](https://img.shields.io/pub/points/ezetap_sdk?color=2E8B57&label=pub%20points)](https://pub.dev/packages/ezetap_sdk/score)

Welcome to Ezetap Payments Flutter SDK integration! You can easily collect payments from your
existing android applications by integrating the SDK.

## How integration works

1. Include the SDK in your mobile application to collect payments.
2. SDK interfaces with a Service App, this App will be installed during run-time.
3. Service App interfaces with the Card Device and Ezetap Servers to finish payment processing and
   notifies the status to SDK.

## Prerequisites

Supported on Android API version 16(JellyBean) or above.

## Use this package as a library

This will add a line like this to your package's pubspec.yaml (and run an
implicit ```flutter pub get```)

```
dependencies:
  ezetap_sdk: ^0.1.2
```

## Import it

Now in your Dart code, you can use:

```import 'package:ezetap_sdk/ezetap_sdk.dart';```

## Methods Available

```javascript
 var json = {
      "prodAppKey": "Your prod app key",
      "demoAppKey": "Your demo app key",
      "merchantName": "merchantName",
      "userName": "userName",
      "currencyCode": 'INR',
      "appMode": "Your environment",
      "captureSignature": 'true',
      "prepareDevice": 'false',
      "captureReceipt": 'false'
    };
  
  EzetapSdk.initialize(json);
```
```javascript
  EzetapSdk.prepareDevice();
```
```javascript
  var sendReceiptJson = {
      "customerName": "customerName",
      "mobileNo": "mobileNo",
      "email": "emailId",
      "txnId": "transactionID"
    };
    
  EzetapSdk.sendReceipt(sendReceiptJson);
```
```javascript
    var json = {
      "issueType": "issueType",
      "issueInfo": "issueInfo",
      "tags": [
        "tag1","tag2"
      ]
    };

  EzetapSdk.serviceRequest(json);
```
```javascript
    var json = {
      "agentName": "username",
      "saveLocally": false
    };

  EzetapSdk.searchTransaction(json);
```
```javascript
   var json = {
      "amount": "100",
      "options": {
        "amountTip": 0.0,
        "references": {
          "reference1": "sffr",
          "additionalReferences": [
    
          ]
        },
        "customer": {
    
        },
        "serviceFee": -1.0,
        "addlData": {
          "addl1": "addl1",
          "addl2": "addl2",
          "addl3": "addl3"
        },
        "appData": {
          "app1": "app1",
          "app2": "app2",
          "app3": "app3"
        }
      }
    };

  EzetapSdk.getTransaction(json);
```
```javascript
   var json = {};
  EzetapSdk.checkForIncompleteTransaction(json);
  ```
  ```javascript
  //Pass the transactionId to this function
  EzetapSdk.voidTransaction(String transactionID);
```
```javascript
   var json = {"txnId": "transactionID"};
  EzetapSdk.attachSignature(json);
```
```javascript
var json = {
  "amount": "123",
  "options": {
    "serviceFee": 100,
    "paymentBy": "CREDIT OR DEBIT OR ANY (**To be used only if the service fee is being added.)",
    "paymentMode": "Card/Cash/Cheque/UPI/UPI_VOUCHER/RemotePay/BHARATQR/Brand_Offers/Brand_EMI/Normal_EMI/Wallet ",
    "providerName": "<NBFC> eg. ZestMoney. (**providerName and emiType are to be passed only if user wants to use ZestMoney. In this scenario, set \"paymentMode”:”EMI”)",
    "emiType": "NORMAL, BRAND, EMI",
    "references": {
      "reference1": "1234",
      "additionalReferences": [
        "addRef_xx1",
        "addRef_xx2"
      ]
    },
    "appData": {
      "walletAcquirer": "freecharge/ola_money/ etc.(**walletAcquirer are to be passed only if user sets \"paymentMode”:”Wallet”)"
    },
    "customer": {
      "name": "xyz",
      "mobileNo": "1234567890",
      "email": "abc@xyz.com"
    }
  }
};
  EzetapSdk.pay(json);
```
```javascript
  //Pass the transactionId to this function
  EzetapSdk.printReceipt(String transactionID);
```
```javascript
  //Pass the image as base64 string to this function
  EzetapSdk.printBitmap(String? base64Image);
```
```javascript
  EzetapSdk.logout();
```
```javascript
  var json = {"txnIds":[""]};
  EzetapSdk.stopPayment(json);
```
```javascript
  EzetapSdk.scanBarcode();
```
```javascript
   var json = {
     "amount": "100",
     "txnId": "transactionID"
   };
  EzetapSdk.refundTransaction(json);
```
```javascript
  EzetapSdk.checkForUpdates();
```

```dart
Future<void> onPaymentClicked(json) async {
  String? result = await EzetapSdk.pay(json);
  if (!mounted) return;
  setState(() {
    _result = result;
  });
}
```
```dart
Future<void> onBarcodePressed() async {
  String? result = await EzetapSdk.scanBarcode();
  if (!mounted) return;
  setState(() {
    _result = result;
  });
}
```

## What you need

1. Flutter development environment
2. Android phone that can connect to internet
3. This documentation
4. Ezetap app key or login credentials to Ezetap service
5. Ezetap device to test card payments

