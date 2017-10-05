module.exports = {
    startPayment: function(method, request_type, orderId, mid, customerId, channel, ind_type, website, email, phone, amount, callback_url, checksum, successCallback, failureCallback) {
        console.log('cordova.exec: startPayment');
        cordova.exec(successCallback,
            failureCallback,
            "PayTM",
            "startPayment",
            [method, request_type, orderId, mid, customerId, channel, ind_type, website, email, phone, amount, callback_url, checksum]);
    }
};
