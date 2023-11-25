package com.plugin.ezetap.sdk.eze_sdk_flutter

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Base64
import com.eze.api.EzeAPI
import com.eze.api.EzeAPIConstants.EzetapErrors
import com.eze.api.EzetapUserConfig
import com.ezetap.printer.EPrintStatus
import com.ezetap.printer.EPrinterImplementation
import com.plugin.ezetap.sdk.eze_sdk_flutter.EzeConstants.ATTACH_SIGNATURE_REQUEST_CODE
import com.plugin.ezetap.sdk.eze_sdk_flutter.EzeConstants.BRAND_EMI_TRANSACTION_REQUEST_CODE
import com.plugin.ezetap.sdk.eze_sdk_flutter.EzeConstants.CARD_INFO_REQUEST_CODE
import com.plugin.ezetap.sdk.eze_sdk_flutter.EzeConstants.CARD_TRANSACTION
import com.plugin.ezetap.sdk.eze_sdk_flutter.EzeConstants.CASH_TRANSACTION
import com.plugin.ezetap.sdk.eze_sdk_flutter.EzeConstants.CHECK_FOR_UPDATES
import com.plugin.ezetap.sdk.eze_sdk_flutter.EzeConstants.CHECK_INCOMPLETE_TRANSACTION_REQUEST_CODE
import com.plugin.ezetap.sdk.eze_sdk_flutter.EzeConstants.CHEQUE_TRANSACTION
import com.plugin.ezetap.sdk.eze_sdk_flutter.EzeConstants.CLOSE_SESSION_REQUEST_CODE
import com.plugin.ezetap.sdk.eze_sdk_flutter.EzeConstants.CONFIRM_PRE_AUTH_REQUEST_CODE
import com.plugin.ezetap.sdk.eze_sdk_flutter.EzeConstants.DISPLAY_TRANSACTION_HISTORY
import com.plugin.ezetap.sdk.eze_sdk_flutter.EzeConstants.GENERIC_TRANSACTION_REQUEST_CODE
import com.plugin.ezetap.sdk.eze_sdk_flutter.EzeConstants.GET_CARD_INFO
import com.plugin.ezetap.sdk.eze_sdk_flutter.EzeConstants.GET_LOYALTY_CARD_INFO
import com.plugin.ezetap.sdk.eze_sdk_flutter.EzeConstants.GET_TRANSACTION_REQUEST_CODE
import com.plugin.ezetap.sdk.eze_sdk_flutter.EzeConstants.INITIALIZE_REQUEST_CODE
import com.plugin.ezetap.sdk.eze_sdk_flutter.EzeConstants.NORMAL_EMI_TRANSACTION_REQUEST_CODE
import com.plugin.ezetap.sdk.eze_sdk_flutter.EzeConstants.PREPARE_DEVICE_REQUEST_CODE
import com.plugin.ezetap.sdk.eze_sdk_flutter.EzeConstants.PRE_AUTH_REQUEST_CODE
import com.plugin.ezetap.sdk.eze_sdk_flutter.EzeConstants.PRINT_BITMAP_REQUEST_CODE
import com.plugin.ezetap.sdk.eze_sdk_flutter.EzeConstants.PRINT_RECEIPT_REQUEST_CODE
import com.plugin.ezetap.sdk.eze_sdk_flutter.EzeConstants.QR_CODE_TRANSACTION
import com.plugin.ezetap.sdk.eze_sdk_flutter.EzeConstants.REFUND_TRANSACTION
import com.plugin.ezetap.sdk.eze_sdk_flutter.EzeConstants.RELEASE_PRE_AUTH_REQUEST_CODE
import com.plugin.ezetap.sdk.eze_sdk_flutter.EzeConstants.REMOTE_TRANSACTION
import com.plugin.ezetap.sdk.eze_sdk_flutter.EzeConstants.SCAN_BARCODE
import com.plugin.ezetap.sdk.eze_sdk_flutter.EzeConstants.SEARCH_TRANSACTION_REQUEST_CODE
import com.plugin.ezetap.sdk.eze_sdk_flutter.EzeConstants.SEND_RECEIPT_REQUEST_CODE
import com.plugin.ezetap.sdk.eze_sdk_flutter.EzeConstants.SERVICE_REQUEST_CODE
import com.plugin.ezetap.sdk.eze_sdk_flutter.EzeConstants.STOP_PAYMENT_REQUEST_CODE
import com.plugin.ezetap.sdk.eze_sdk_flutter.EzeConstants.UPI_TRANSACTION
import com.plugin.ezetap.sdk.eze_sdk_flutter.EzeConstants.VOID_TRANSACTION_REQUEST_CODE
import com.plugin.ezetap.sdk.eze_sdk_flutter.EzeConstants.WALLET_TRANSACTION
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry
import org.json.JSONException
import org.json.JSONObject

/**
@author TIJO THOMAS
@since 10/11/22
 */

class EzeSdkFlutterPlugin : FlutterPlugin, MethodCallHandler, ActivityAware,
    PluginRegistry.ActivityResultListener {
    private lateinit var channel: MethodChannel
    private lateinit var activity: Activity
    private lateinit var ezeResult: Result

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "eze_sdk_flutter")
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        this.ezeResult = result
        when (call.method) {
            "initialize" -> initialize(call.arguments.toString())
            "prepareDevice" -> prepareDevice()
            "sendReceipt" -> sendReceipt(call.arguments.toString())
            "serviceRequest" -> serviceRequest(call.arguments.toString())
            "searchTransaction" -> searchTransaction(call.arguments.toString())
            "getTransaction" -> getTransaction(call.arguments.toString())
            "checkForIncompleteTransaction" -> checkForIncompleteTransaction(call.arguments.toString())
            "voidTransaction" -> voidTransaction(call.arguments.toString())
            "attachSignature" -> attachSignature(call.arguments.toString())
            "pay" -> pay(call.arguments.toString())
            "brandEMITransaction" -> brandEMITransaction(call.arguments.toString())
            "normalEMITransaction" -> normalEMITransaction(call.arguments.toString())
            "printReceipt" -> printReceipt(call.arguments.toString())
            "printBitmap" -> printBitmap(call.arguments)
            "logout" -> close()
            "preAuth" -> preAuth(call.arguments.toString())
            "confirmPreAuth" -> confirmPreAuth(call.arguments.toString())
            "releasePreAuth" -> releasePreAuth(call.arguments.toString())
            "stopPayment" -> stopPayment(call.arguments.toString())
            "scanBarcode" -> scanBarcode()
            "getCardInfo" -> getCardInfo()
            "getLoyaltyCardInfo" -> getLoyaltyCardInfo(call.arguments.toString())
            "walletTransaction" -> walletTransaction(call.arguments.toString())
            "chequeTransaction" -> chequeTransaction(call.arguments.toString())
            "cardTransaction" -> cardTransaction(call.arguments.toString())
            "cashTransaction" -> cashTransaction(call.arguments.toString())
            "remotePayment" -> remotePayment(call.arguments.toString())
            "displayTransactionHistory" -> displayTransactionHistory(call.arguments.toString())
            "upiTransaction" -> upiTransaction(call.arguments.toString())
            "qrCodeTransaction" -> qrCodeTransaction(call.arguments.toString())
            "refundTransaction" -> refundTransaction(call.arguments.toString())
            "checkUpdates" -> checkUpdates()
        }
    }

    /**
     * Check For Updates method is only relevant in the Android platform. It is invoked to check for
     * any updates to the Android Service app.
     */
    private fun checkUpdates() {
        try {
            EzeAPI.checkForUpdates(
                activity, CHECK_FOR_UPDATES
            )
        } catch (e: JSONException) {
            e.printStackTrace()
            ezeResult.error("EZE_ERROR", e.message, "")
        }
    }

    /**
     * Method to initialize the Ezetap transaction
     *
     * @param requestObject requestObject - The request object as defined in the API
     * documentation
     */

    private fun initialize(requestObject: String?) {
        try {
            EzeAPI.initialize(activity,
                INITIALIZE_REQUEST_CODE,
                requestObject?.let { JSONObject(it) })
        } catch (e: JSONException) {
            e.printStackTrace()
            ezeResult.error("EZE_ERROR", e.message, "")
        }
    }

    /**
     * Method to close prepare device before any transaction
     */

    private fun prepareDevice() {
        EzeAPI.prepareDevice(activity, PREPARE_DEVICE_REQUEST_CODE)
    }

    /**
     * Method to scan barcode and retrieve the result
     */
    private fun scanBarcode() {
        EzeAPI.scanBarcode(activity, SCAN_BARCODE, null)
    }

    private fun getCardInfo() {
        EzeAPI.getCardInfo(activity, GET_CARD_INFO)
    }

    private fun getLoyaltyCardInfo(requestObject: String?) {
        try {
            EzeAPI.getLoyaltyCardInfo(activity,
                GET_LOYALTY_CARD_INFO,
                requestObject?.let { JSONObject(it) })
        } catch (e: JSONException) {
            e.printStackTrace()
            ezeResult.error("EZE_ERROR", e.message, "")
        }
    }

    /**
     * Method to send transaction receipt to given mobile no/email id
     *
     * @param requestObject requestObject - The request object as defined in the API
     * documentation
     */

    private fun sendReceipt(requestObject: String?) {
        try {
            EzeAPI.sendReceipt(
                activity,
                SEND_RECEIPT_REQUEST_CODE,
                requestObject?.let { JSONObject(it) })
        } catch (e: JSONException) {
            e.printStackTrace()
            ezeResult.error("EZE_ERROR", e.message, "")
        }
    }


    private fun serviceRequest(requestObject: String?) {
        try {
            EzeAPI.serviceRequest(activity,
                SERVICE_REQUEST_CODE,
                requestObject?.let { JSONObject(it) })
        } catch (e: JSONException) {
            e.printStackTrace()
            ezeResult.error("EZE_ERROR", e.message, "")
        }
    }


    /**
     * Method to search transaction(s)
     *
     * @param requestObject requestObject - The request object as defined in the API
     * documentation
     */

    private fun searchTransaction(requestObject: String?) {
        try {
            EzeAPI.searchTransaction(
                activity,
                SEARCH_TRANSACTION_REQUEST_CODE,
                requestObject?.let { JSONObject(it) })
        } catch (e: JSONException) {
            e.printStackTrace()
            ezeResult.error("EZE_ERROR", e.message, "")
        }
    }

    /**
     * Method to get transaction detail for a given External Reference
     *
     * @param externalReference externalReference - External reference number to look up
     */


    private fun getTransaction(externalReference: String?) {
        try {
            EzeAPI.getTransaction(
                activity,
                GET_TRANSACTION_REQUEST_CODE,
                externalReference?.let { JSONObject(it) })
        } catch (e: JSONException) {
            e.printStackTrace()
            ezeResult.error("EZE_ERROR", e.message, "")
        }
    }

    /**
     * Method to if a previous card transaction is pending. A card transaction
     * could be pending if no response was received after the transaction was
     * initiated or if there was an app crash after the transaction was
     * initiated. The Ezetap SDK checks for pending transactions at the very
     * next Ezetap API call, it is recommended that calling applications call
     * this API at the appropriate time for eg. - upon login or upon calling pay
     * cash where the handling is not done by the Ezetap system.
     */

    private fun checkForIncompleteTransaction(json: String?) {
        try {
            EzeAPI.checkForIncompleteTransaction(activity,
                CHECK_INCOMPLETE_TRANSACTION_REQUEST_CODE,
                json?.let { JSONObject(it) } ?: null as JSONObject?)
        } catch (e: JSONException) {
            e.printStackTrace()
            ezeResult.error("EZE_ERROR", e.message, "")
        }
    }

    /**
     * Method to void transaction
     *
     * @param transactionID transactionID - The ID of the transaction which is to be
     * voided
     */

    private fun voidTransaction(transactionID: String?) {
        try {
            EzeAPI.voidTransaction(activity, VOID_TRANSACTION_REQUEST_CODE, transactionID)
        } catch (e: JSONException) {
            e.printStackTrace()
            ezeResult.error("EZE_ERROR", e.message, "")
        }
    }

    /**
     * Method to attach Signature for a transaction
     *
     * @param requestObject requestObject - The request object as defined in the API
     * documentation
     */

    private fun attachSignature(requestObject: String?) {
        try {
            EzeAPI.attachSignature(
                activity,
                ATTACH_SIGNATURE_REQUEST_CODE,
                requestObject?.let { JSONObject(it) })
        } catch (e: JSONException) {
            e.printStackTrace()
            ezeResult.error("EZE_ERROR", e.message, "")
        }
    }


    /**
     * Method to start a generic pay
     *
     * @param requestObject requestObject - The request object as defined in the API
     * documentation
     */

    private fun pay(requestObject: String?) {
        try {
            EzeAPI.pay(
                activity,
                GENERIC_TRANSACTION_REQUEST_CODE,
                requestObject?.let { JSONObject(it) })
        } catch (e: JSONException) {
            e.printStackTrace()
            ezeResult.error("EZE_ERROR", e.message, "")
        }
    }


    /**
     * Method to start a Brand EMI transaction
     *
     * @param requestObject requestObject - The request object as defined in the API
     * documentation
     */

    private fun brandEMITransaction(requestObject: String?) {
        try {
            EzeAPI.brandEMITransaction(
                activity,
                BRAND_EMI_TRANSACTION_REQUEST_CODE,
                requestObject?.let { JSONObject(it) })
        } catch (e: JSONException) {
            e.printStackTrace()
            ezeResult.error("EZE_ERROR", e.message, "")
        }
    }

    /**
     * Method to start a Brand EMI transaction
     *
     * @param requestObject requestObject - The request object as defined in the API
     * documentation
     */

    private fun normalEMITransaction(requestObject: String?) {
        try {
            EzeAPI.normalEMITransaction(
                activity,
                NORMAL_EMI_TRANSACTION_REQUEST_CODE,
                requestObject?.let { JSONObject(it) })
        } catch (e: JSONException) {
            e.printStackTrace()
            ezeResult.error("EZE_ERROR", e.message, "")
        }
    }

    /**
     * Method to print a receipt for a particular transaction
     *
     * @param transactionID transactionID - The ID of the Ezetap txn
     */

    private fun printReceipt(transactionID: String?) {
        try {
            EzeAPI.printReceipt(activity, PRINT_RECEIPT_REQUEST_CODE, transactionID)
        } catch (e: JSONException) {
            e.printStackTrace()
            ezeResult.error("EZE_ERROR", e.message, "")
        }
    }

    /**
     * Method to print a bitmap image of the current view
     */

    private fun printBitmap(arguments: Any?) {
        activity.runOnUiThread {
            val encodedImageData: String? =
                arguments?.toString() ?: activity.loadBitmapBase64EncodedFromView()
            if (!isDeviceX990()) {
                try {
                    if (EzetapUserConfig.getEzeUserConfig() != null) {
                        EPrinterImplementation.getInstance().apply {
                            val imageBytes = Base64.decode(encodedImageData, Base64.DEFAULT)
                            val decodedImage =
                                BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                            val ePrinter = this
                            when (val status = ePrinter.init(activity)) {
                                EPrintStatus.SUCCESS -> {
                                    Thread {
                                        ePrinter.printBitmap(decodedImage) { _, data ->
                                            activity.runOnUiThread {
                                                ezeResult.success(data)
                                            }
                                        }
                                    }.start()
                                }

                                else -> ezeResult.error("EZE_ERROR", status.message, null)
                            }
                        }
                    } else {
                        ezeResult.error(
                            EzetapErrors.ERROR_INIT_REQUIRED.errorCode,
                            EzetapErrors.ERROR_INIT_REQUIRED.errorMessage,
                            null
                        )
                    }
                } catch (ex: Exception) {
                    ezeResult.error("EZE_ERROR", ex.message, null)
                    ex.printStackTrace()
                }
            } else {
                val jsonRequest = JSONObject()
                val jsonImageObj = JSONObject()
                try {
                    jsonImageObj.put("imageData", encodedImageData)
                    jsonImageObj.put("imageType", "JPEG")
                    jsonImageObj.put("height", "") // optional
                    jsonImageObj.put("weight", "") // optional
                    jsonRequest.put(
                        "image", jsonImageObj
                    )
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                EzeAPI.printBitmap(activity, PRINT_BITMAP_REQUEST_CODE, jsonRequest)
            }
        }
    }

    /**
     * Method to close Ezetap sessions
     */

    private fun close() {
        EzeAPI.close(activity, CLOSE_SESSION_REQUEST_CODE)
    }

    /**
     * Method to Initiate Pre Auth
     *
     * @param jsonObject jsonObject - All txnIds to be passed to stop the payment.
     */
    private fun preAuth(jsonObject: String?) {
        try {
            EzeAPI.preAuth(activity, PRE_AUTH_REQUEST_CODE, jsonObject?.let { JSONObject(it) })
        } catch (e: JSONException) {
            e.printStackTrace()
            ezeResult.error("EZE_ERROR", e.message, "")
        }
    }

    /**
     * Method to Confirm Pre Auth
     *
     * @param jsonObject jsonObject - All txnIds to be passed to stop the payment.
     */
    private fun confirmPreAuth(jsonObject: String?) {
        try {
            EzeAPI.confirmPreAuth(
                activity,
                CONFIRM_PRE_AUTH_REQUEST_CODE,
                jsonObject?.let { JSONObject(it) })
        } catch (e: JSONException) {
            e.printStackTrace()
            ezeResult.error("EZE_ERROR", e.message, "")
        }
    }

    /**
     * Method to Release Pre Auth
     *
     * @param jsonObject jsonObject - All txnIds to be passed to stop the payment.
     */
    private fun releasePreAuth(jsonObject: String?) {
        try {
            EzeAPI.releasePreAuth(
                activity,
                RELEASE_PRE_AUTH_REQUEST_CODE,
                jsonObject?.let { JSONObject(it) })
        } catch (e: JSONException) {
            e.printStackTrace()
            ezeResult.error("EZE_ERROR", e.message, "")
        }
    }

    /**
     * Method to void transaction
     *
     * @param jsonObject jsonObject - All txnIds to be passed to stop the payment.
     */

    private fun stopPayment(jsonObject: String?) {
        try {
            EzeAPI.stopPayment(activity,
                STOP_PAYMENT_REQUEST_CODE,
                jsonObject?.let { JSONObject(it) })
        } catch (e: JSONException) {
            e.printStackTrace()
            ezeResult.error("EZE_ERROR", e.message, "")
        }
    }

    private fun walletTransaction(requestObject: String?) {
        try {
            EzeAPI.walletTransaction(activity,
                WALLET_TRANSACTION,
                requestObject?.let { JSONObject(it) })
        } catch (e: JSONException) {
            e.printStackTrace()
            ezeResult.error("EZE_ERROR", e.message, "")
        }
    }

    private fun upiTransaction(requestObject: String?) {
        try {
            EzeAPI.upiTransaction(activity, UPI_TRANSACTION, requestObject?.let { JSONObject(it) })
        } catch (e: JSONException) {
            e.printStackTrace()
            ezeResult.error("EZE_ERROR", e.message, "")
        }
    }

    private fun qrCodeTransaction(requestObject: String?) {
        try {
            EzeAPI.qrCodeTransaction(activity,
                QR_CODE_TRANSACTION,
                requestObject?.let { JSONObject(it) })
        } catch (e: JSONException) {
            e.printStackTrace()
            ezeResult.error("EZE_ERROR", e.message, "")
        }
    }

    private fun chequeTransaction(requestObject: String?) {
        try {
            EzeAPI.walletTransaction(activity,
                CHEQUE_TRANSACTION,
                requestObject?.let { JSONObject(it) })
        } catch (e: JSONException) {
            e.printStackTrace()
            ezeResult.error("EZE_ERROR", e.message, "")
        }
    }

    private fun refundTransaction(requestObject: String?) {
        try {
            EzeAPI.refundTransaction(activity,
                REFUND_TRANSACTION,
                requestObject?.let { JSONObject(it) })
        } catch (e: JSONException) {
            e.printStackTrace()
            ezeResult.error("EZE_ERROR", e.message, "")
        }
    }

    private fun cardTransaction(requestObject: String?) {
        try {
            EzeAPI.cardTransaction(activity,
                CARD_TRANSACTION,
                requestObject?.let { JSONObject(it) })
        } catch (e: JSONException) {
            e.printStackTrace()
            ezeResult.error("EZE_ERROR", e.message, "")
        }
    }

    private fun cashTransaction(requestObject: String?) {
        try {
            EzeAPI.cashTransaction(activity,
                CASH_TRANSACTION,
                requestObject?.let { JSONObject(it) })
        } catch (e: JSONException) {
            e.printStackTrace()
            ezeResult.error("EZE_ERROR", e.message, "")
        }
    }

    private fun remotePayment(requestObject: String?) {
        try {
            EzeAPI.remotePayment(activity,
                REMOTE_TRANSACTION,
                requestObject?.let { JSONObject(it) })
        } catch (e: JSONException) {
            e.printStackTrace()
            ezeResult.error("EZE_ERROR", e.message, "")
        }
    }

    private fun displayTransactionHistory(requestObject: String?) {
        try {
            EzeAPI.displayTransactionHistory(activity,
                DISPLAY_TRANSACTION_HISTORY,
                requestObject?.let { JSONObject(it) })
        } catch (e: JSONException) {
            e.printStackTrace()
            ezeResult.error("EZE_ERROR", e.message, "")
        }
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        this.activity = binding.activity
        binding.addActivityResultListener(this)
    }

    override fun onDetachedFromActivityForConfigChanges() {

    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        onAttachedToActivity(binding)
    }

    override fun onDetachedFromActivity() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        return when (requestCode) {
            INITIALIZE_REQUEST_CODE, PREPARE_DEVICE_REQUEST_CODE, CARD_INFO_REQUEST_CODE, SEND_RECEIPT_REQUEST_CODE, SERVICE_REQUEST_CODE, SEARCH_TRANSACTION_REQUEST_CODE, GET_TRANSACTION_REQUEST_CODE, CHECK_INCOMPLETE_TRANSACTION_REQUEST_CODE, VOID_TRANSACTION_REQUEST_CODE, ATTACH_SIGNATURE_REQUEST_CODE, GENERIC_TRANSACTION_REQUEST_CODE, BRAND_EMI_TRANSACTION_REQUEST_CODE, NORMAL_EMI_TRANSACTION_REQUEST_CODE, PRINT_RECEIPT_REQUEST_CODE, PRINT_BITMAP_REQUEST_CODE, CLOSE_SESSION_REQUEST_CODE, PRE_AUTH_REQUEST_CODE, CONFIRM_PRE_AUTH_REQUEST_CODE, RELEASE_PRE_AUTH_REQUEST_CODE, STOP_PAYMENT_REQUEST_CODE, SCAN_BARCODE, GET_CARD_INFO, GET_LOYALTY_CARD_INFO, WALLET_TRANSACTION, CHEQUE_TRANSACTION, CARD_TRANSACTION, CASH_TRANSACTION, REMOTE_TRANSACTION, DISPLAY_TRANSACTION_HISTORY, UPI_TRANSACTION, QR_CODE_TRANSACTION, REFUND_TRANSACTION, CHECK_FOR_UPDATES -> {
                ezeResult.success(data?.createJsonObject())
                true
            }

            else -> false
        }
    }
}
