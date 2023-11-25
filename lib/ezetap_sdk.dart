import 'dart:async';

import 'package:flutter/services.dart';

class EzetapSdk {
  static const MethodChannel _channel = MethodChannel('eze_sdk_flutter');

  static Future<String> initialize(json) async {
    final String result = await _channel.invokeMethod('initialize', json);
    return result;
  }

  static Future<String> prepareDevice() async {
    final String result = await _channel.invokeMethod('prepareDevice');
    return result;
  }

  static Future<String> sendReceipt(json) async {
    final String result = await _channel.invokeMethod('sendReceipt', json);
    return result;
  }

  static Future<String> serviceRequest(json) async {
    final String result = await _channel.invokeMethod('serviceRequest', json);
    return result;
  }

  static Future<String> searchTransaction(json) async {
    final String result =
        await _channel.invokeMethod('searchTransaction', json);
    return result;
  }

  static Future<String> getTransaction(json) async {
    final String result = await _channel.invokeMethod('getTransaction', json);
    return result;
  }

  static Future<String> checkForIncompleteTransaction(json) async {
    final String result =
        await _channel.invokeMethod('checkForIncompleteTransaction', json);
    return result;
  }

  static Future<String> voidTransaction(String transactionID) async {
    final String result =
        await _channel.invokeMethod('voidTransaction', transactionID);
    return result;
  }

  static Future<String> attachSignature(json) async {
    final String result = await _channel.invokeMethod('attachSignature', json);
    return result;
  }

  static Future<String> pay(json) async {
    final String result = await _channel.invokeMethod('pay', json);
    return result;
  }
  @Deprecated("can be invoked with pay method")
  static Future<String> brandEMITransaction(json) async {
    final String result =
        await _channel.invokeMethod('brandEMITransaction', json);
    return result;
  }

  @Deprecated("can be invoked with pay method")
  static Future<String> normalEMITransaction(json) async {
    final String result =
        await _channel.invokeMethod('normalEMITransaction', json);
    return result;
  }

  static Future<String> printReceipt(String transactionID) async {
    final String result =
        await _channel.invokeMethod('printReceipt', transactionID);
    return result;
  }

  static Future<String> printBitmap(String? base64Image) async {
    final String result =
        await _channel.invokeMethod('printBitmap', base64Image);
    return result;
  }

  static Future<String> logout() async {
    final String result = await _channel.invokeMethod('logout');
    return result;
  }

  @Deprecated("not in use")
  static Future<String> preAuth(json) async {
    final String result = await _channel.invokeMethod('preAuth', json);
    return result;
  }

  @Deprecated("not in use")
  static Future<String> confirmPreAuth(json) async {
    final String result = await _channel.invokeMethod('confirmPreAuth', json);
    return result;
  }

  @Deprecated("not in use")
  static Future<String> releasePreAuth(json) async {
    final String result = await _channel.invokeMethod('releasePreAuth', json);
    return result;
  }

  static Future<String> stopPayment(json) async {
    final String result = await _channel.invokeMethod('stopPayment', json);
    return result;
  }

  static Future<String> scanBarcode() async {
    final String result = await _channel.invokeMethod('scanBarcode');
    return result;
  }

  static Future<String> getCardInfo() async {
    final String result = await _channel.invokeMethod('getCardInfo');
    return result;
  }

  static Future<String> getLoyaltyCardInfo(json) async {
    final String result =
        await _channel.invokeMethod('getLoyaltyCardInfo', json);
    return result;
  }

  @Deprecated("can be invoked with pay method")
  static Future<String> walletTransaction(json) async {
    final String result =
        await _channel.invokeMethod('walletTransaction', json);
    return result;
  }

  @Deprecated("can be invoked with pay method")
  static Future<String> chequeTransaction(json) async {
    final String result =
        await _channel.invokeMethod('chequeTransaction', json);
    return result;
  }

  @Deprecated("can be invoked with pay method")
  static Future<String> cardTransaction(json) async {
    final String result = await _channel.invokeMethod('cardTransaction', json);
    return result;
  }

  @Deprecated("can be invoked with pay method")
  static Future<String> cashTransaction(json) async {
    final String result = await _channel.invokeMethod('cashTransaction', json);
    return result;
  }

  @Deprecated("can be invoked with pay method")
  static Future<String> remotePayment(json) async {
    final String result = await _channel.invokeMethod('remotePayment', json);
    return result;
  }

  static Future<String> displayTransactionHistory(json) async {
    final String result =
        await _channel.invokeMethod('displayTransactionHistory', json);
    return result;
  }

  @Deprecated("can be invoked with pay method")
  static Future<String> upiTransaction(json) async {
    final String result = await _channel.invokeMethod('upiTransaction', json);
    return result;
  }

  @Deprecated("can be invoked with pay method")
  static Future<String> qrCodeTransaction(json) async {
    final String result =
        await _channel.invokeMethod('qrCodeTransaction', json);
    return result;
  }

  static Future<String> refundTransaction(json) async {
    final String result =
        await _channel.invokeMethod('refundTransaction', json);
    return result;
  }

  static Future<String> checkForUpdates() async {
    final String result = await _channel.invokeMethod('checkUpdates');
    return result;
  }
}
