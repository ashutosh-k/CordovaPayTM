package com.paytm.cordova;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.paytm.pgsdk.*;

public class PayTM extends CordovaPlugin {

    private PaytmPGService paytm_service;

    protected void pluginInitialize() {
    }

    public boolean execute(String action, JSONArray args, CallbackContext callbackContext)
            throws JSONException {
        if (action.equals("startPayment")) {
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("REQUEST_TYPE", args.getString(1));
            paramMap.put("ORDER_ID", args.getString(2));
            paramMap.put("MID", args.getString(3));
            paramMap.put("CUST_ID", args.getString(4));
            paramMap.put("CHANNEL_ID", args.getString(5));
            paramMap.put("INDUSTRY_TYPE_ID", args.getString(6));
            paramMap.put("WEBSITE", args.getString(7));
            paramMap.put("EMAIL", args.getString(8));
            paramMap.put("MOBILE_NO", args.getString(9));
            paramMap.put("TXN_AMOUNT", args.getString(10));
            paramMap.put("CALLBACK_URL", args.getString(11));
            paramMap.put("CHECKSUMHASH", args.getString(12));
            startPayment(paramMap, args.getString(0), callbackContext);
            return true;
        }
        return false;
    }

    private void startPayment(final Map paramMap,
                              final String method,
                              final CallbackContext callbackContext){

        if(method.equals("staging")){
            paytm_service = PaytmPGService.getStagingService();
        }else{
            paytm_service = PaytmPGService.getProductionService();
        }



        PaytmOrder order = new PaytmOrder(paramMap);

        this.paytm_service.initialize(order, null);
        this.paytm_service.startPaymentTransaction(cordova.getActivity(), false, false, new PaytmPaymentTransactionCallback()
        {

            @Override
            public void onTransactionResponse(Bundle inResponse) {
                Log.i("Error", "onTransactionSuccess :" + inResponse);

                JSONObject paymentResponse = new JSONObject();
                try {

                    paymentResponse.put("STATUS", inResponse.getString("STATUS"));
                    paymentResponse.put("ORDERID", inResponse.getString("ORDERID"));
                    paymentResponse.put("TXNID", inResponse.getString("TXNID"));
                    paymentResponse.put("TXNDATE", inResponse.getString("TXNDATE"));
                    paymentResponse.put("BANKTXNID", inResponse.getString("BANKTXNID"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
		        Log.v("onTransactionSuccess",  paymentResponse.toString());
                callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, paymentResponse.toString()));
            }

            @Override
            public void onBackPressedCancelTransaction()
            {
                Log.i("Error","onBackPressedCancelTransaction :");
                callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, "onBackPressedCancelTransaction : Back button pressed"));
            }

            @Override
            public void onTransactionCancel(String inErrorMessage,Bundle inResponse)
            {
                Log.i("Error","onTransactionCancel :"+inErrorMessage);
                callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, "onTransactionCancel :"+inErrorMessage));
            }


            @Override
            public void clientAuthenticationFailed(String inErrorMessage)
            {
                Log.i("Error","clientAuthenticationFailed :"+inErrorMessage);
                callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, "clientAuthenticationFailed :"+inErrorMessage));
            }


            @Override
            public void networkNotAvailable() {
                // TODO Auto-generated method stub
                Log.i("Error","networkNotAvailable");
                callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, "NetworkNotAvailable : Check your network connection !"));
            }

            @Override
            public void onErrorLoadingWebPage(int arg0, String arg1, String arg2) {
                // TODO Auto-generated method stub
                Log.i("Error","onErrorLoadingWebPage arg0  :"+arg0);
                Log.i("Error","onErrorLoadingWebPage arg1  :"+arg1);
                Log.i("Error","onErrorLoadingWebPage arg2  :"+arg2);
                callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, "ErrorLoadingWebPage : Some error occurred loading view !"));
            }

            @Override
            public void someUIErrorOccurred(String arg0) {
                // TODO Auto-generated method stub
                Log.i("Error","someUIErrorOccurred :"+arg0);
                callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, "UIErrorOccurred : "+arg0));
            }
        });
    }
}
