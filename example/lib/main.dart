import 'dart:async';
import 'dart:convert';

import 'package:eze_sdk_flutter_example/bill_widget.dart';
import 'package:ezetap_sdk/ezetap_sdk.dart';
import 'package:flutter/material.dart';
import 'package:screenshot/screenshot.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _result = 'Unknown';

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initSdk() async {
    var json = {};
    String? result = await EzetapSdk.initialize(jsonEncode(json));
    if (!mounted) return;
    setState(() {
      _result = result;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Ezetap Sample'),
        ),
        body: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            ElevatedButton(onPressed: initSdk, child: const Text("SDK Init")),
            ElevatedButton(
                onPressed: onPreparePressed,
                child: const Text("Prepare Device")),
            ElevatedButton(
                onPressed: onPressed, child: const Text("Print Bitmap")),
            ElevatedButton(
                onPressed: onBarcodePressed, child: const Text("Scan Barcode")),
            Expanded(
              flex: 1,
              child: SingleChildScrollView(
                  scrollDirection: Axis.vertical, child: Text(_result)),
            ),
          ],
        ),
      ),
    );
  }

  Future<void> onBarcodePressed() async {
    String? result = await EzetapSdk.scanBarcode();
    if (!mounted) return;
    setState(() {
      _result = result;
    });
  }

  Future<void> onPressed() async {
    final controller = ScreenshotController();

    ///@author TIJO THOMAS
    ///BillWidget is a dummy receipt widget to create base64
    final bytes =
        await controller.captureFromWidget(const BillWidget(), pixelRatio: 2.0);
    var base64Image = base64Encode(bytes);
    await EzetapSdk.printBitmap(base64Image);
  }

  Future<void> onPreparePressed() async {
    String? result = await EzetapSdk.prepareDevice();
    if (!mounted) return;
    setState(() {
      _result = result;
    });
  }
}
