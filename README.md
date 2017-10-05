# CordovaPayTM
Tested with PayTM 2.1 android SDK
Supporting platform

|    Platform     |    OS level    |
|:---------------:|:--------------:|
|     Android     |  Android 4.0+  | 

This plugin was based on this Apache project and has a compatible API.

## Installation

    // you may also install directly from this repo
    
    cordova plugin add https://github.com/ashutosh-k/CordovaPayTM.git

	OR
    git clone https://github.com/ashutosh-k/CordovaPayTM.git
    cordova plugin add <local folder CordovaPayTM path> --nofetch
    

## Usage
         //METHOD is either "staging" OR "production"
	 window.plugins.paytm.startPayment(
            METHOD,
            REQUEST_TYPE,
            ORDER_ID,
            MID,
            CUST_ID,
            CHANNEL_ID,
            INDUSTRY_TYPE_ID,
            WEBSITE,
            EMAIL,
            MOBILE_NO,
            TXN_AMOUNT,
            CALLBACK_URL,
            CHECKSUMHASH,
            successCallback,
            failureCallback
        );
        function successCallback(response) {
            alert(JSON.stringify(response));
            console.log("Payed Successfully");
        }

        function failureCallback(message) {
            alert('Failed because: ' + message);
            console.log('Failed because: ' + message);
        } 
## Methods

- staging support
    Pass "staging" as METHOD for Staging(Testing support)
    
- live support
    Pass "production" as METHOD for Live Product(Live support or before releasing app) 

Feel free ask ask questions n even i love new request..

