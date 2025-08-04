package gr.android.softposecr.transactions;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class TransactionsActivity extends AppCompatActivity {
    public static final String APPLICATION_ID = "com.mellon.ecr";
    public static final String uriPrefix = "nbgpaytxn://"; // or "cosmotepaytxn://"
    public static final String intentMethod = "DEEPLINK"; // or "INTENT"
    public static final String CALLBACK_NAME = "showcasecallback";

//    public static void createTestProviderDataV2() {
//        //This is just a showcase
//        //Replace with you own data
//        //Replacing with your own data implies that you should first inject the private and public key (look at the token crypto proposal document)
//        //So that you can produce a valid signature that will be verified from the other side (meaning the softPos app)
//        //String signature, String providerDataUid, String mark, String signatureTimestamp, String netAmount, String vatAmount, String totalAmount, String paidAmount
//        String providerId = "000";
//        String epwnymia_paroxoy = "Mellon Technologies";
//        String signature = "3046022100DC4350AD0ABB451701C9592D07A06EA7FB3DB021786BA72755E41D9452562833022100CE112AF4252C606862F2CB9FC1AC86FD47D2CC94DFFFFAF6CCD2FD699705E323";
//        String providerDataUid = "D4F6A5F5C6123658F78369E5191ED5C9D73CB7AC";
//        String mark = "400013293980417";
//        String signatureTimestamp = "20231114100000";
//        String netAmount = "100";
//        String vatAmount = "24";
//        String totalAmount = "124";
//        String paidAmount = "124";
//        String providerTid = "01234567";
//        //providerTid = edtTid.getText().toString();
//        gr.android.softposecr.ProviderData providerData = new com.mellon.ecr.providerdataversion2.ProviderData(providerId, epwnymia_paroxoy,
//                signature,
//                providerDataUid, mark,
//                signatureTimestamp, netAmount, vatAmount, totalAmount, paidAmount, providerTid);
//
//        Gson gson = new Gson();
//        json = gson.toJson(providerData);
//        Log.d(TAG, "Json " + json);
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String transactionType = extras.getString("TRANSACTION_TYPE", "SALE");
            switch (transactionType) {
                case "SALE":
                    transactionSale(extras);
                    break;
                case "REFUND":
                    transactionRefund(extras);
                    break;
                case "BILL_PAYMENT":
                    transactionSale(extras);
//                    transactionBillPayment(extras);
                    break;
                case "CLOSE_BATCH":
                    transactionCloseBatch(extras);
                    break;
                case "VOID":
//                    transactionVoid(extras);
                    transactionRefund(extras);
                    break;
            }
        }
    }

    private void transactionSale(Bundle extras) {
        String amount = extras.getString("AMOUNT", "1");
        String tip = extras.getString("TIP", "0");
        String installments = extras.getString("INSTALLMENTS", "0");
        String currency = extras.getString("CURRENCY", "EUR");
        String email = extras.getString("EMAIL");
        String phoneNumber = extras.getString("PHONE_NUMBER");
        String uid = extras.getString("UID", "0");
        performSaleAadeV1(amount, tip, installments, currency, email, phoneNumber, uid);
    }

    private void transactionRefund(Bundle extras) {
        String amount = extras.getString("AMOUNT", "1");
        String rnn = extras.getString("RRN", "10");
        String batchNumber = extras.getString("BATCH_NUMBER", "1");
        String originalAuthCode = extras.getString("ORIGINAL_AUTH_CODE", "1");
        String currency = extras.getString("CURRENCY", "EUR");
        String email = extras.getString("EMAIL");
        String phoneNumber = extras.getString("PHONE_NUMBER");
        String uid = extras.getString("UID", "0");
        performRefund(amount, currency, rnn, batchNumber, originalAuthCode, email, phoneNumber, uid);
    }

    private void transactionVoid(Bundle extras) {
        String amount = extras.getString("AMOUNT", "1");
        String email = extras.getString("EMAIL");
        String phoneNumber = extras.getString("PHONE_NUMBER");
        String uid = extras.getString("UID", "0");
        String originalUid = extras.getString("ORIGINAL_UID", "0");
        performVoid(amount, originalUid, email, phoneNumber, uid);
    }

    private void performVoid(String amount, String originalUid, String email, String phoneNumber, String uid) {
        String uri = String.format("request/v1?amount=%s&originalUid=%s&transactionName=void&email=%s&phoneNumber=%s&uid=%s&appId=%s",
                amount, originalUid, email, phoneNumber, uid, APPLICATION_ID);

        SendRequest(uri);
    }

    private void transactionBillPayment(Bundle extras) {
        String amount = extras.getString("AMOUNT", "0");
        String tip = extras.getString("TIP", "0");
        String installments = extras.getString("INSTALLMENTS", "0");
        String currency = extras.getString("CURRENCY", "EUR");
        String email = extras.getString("EMAIL");
        String phoneNumber = extras.getString("PHONE_NUMBER");
        String uid = extras.getString("UID", "0");
        performBillPayment(amount, tip, installments, currency, email, phoneNumber, uid);
    }

    private void transactionCloseBatch(Bundle extras) {
        String uid = extras.getString("UID", "0");
        performCloseBatch(uid);
    }

    private void performSaleAadeV1(String amount, String tip, String installments, String currency, String email, String phoneNumber, String uid) {
        String json = ""; //empty
        String uri = String.format("request/v1?amount=%s&currency=%s&tip=%s&installments=%s&email=%s&phoneNumber=%s&uid=%s&transactionName=sale&providerData=%s&appId=%s",
                amount, currency, tip, installments, email, phoneNumber, uid,
                "", APPLICATION_ID);
        uri = String.format("request/v1?amount=%s&currency=%s&tip=%s&installments=%s&email=%s&phoneNumber=%s&uid=%s&transactionName=sale&providerData=%s&appId=%s",
                amount,currency, tip, installments, email, phoneNumber, uid,
                json, APPLICATION_ID);

        try {
//            Log.d(TAG, "Before encode: " + uriPrefix + uri);
            uri = URLEncoder.encode(uri, "UTF-8");
            uri = uriPrefix + uri;
        } catch (UnsupportedEncodingException e) {
//            Log.d(TAG, e.getMessage());
        }
//        Log.d(TAG, "uri " + uri);

        SendRequest(uri);
    }


    private void performRefund(String amount, String currency, String rrn, String batchNumber,
                               String originalAuthCode, String email, String phoneNumber, String uid) {
        String uri = String.format(uriPrefix + "request/v1?amount=%s&transactionName=refund&currency=%s&rrn=%s&batchNumber=%s&authCode=%s&email=%s&phoneNumber=%s&uid=%s&appId=%s", amount, currency, rrn, batchNumber, originalAuthCode, email, phoneNumber, uid, APPLICATION_ID);
        SendRequest(uri);
    }

    private void performBillPayment(String amount, String tip, String installments, String currency, String email, String phoneNumber, String uid) {
        String uri = String.format(uriPrefix + "request/v1?amount=%s&currency=%s&tip=%s&installments=%s&email=%s&phoneNumber=%s&uid=%s&appId=%s&transactionName=BILL_PAYMENT&specialPaymentCode=123456123456", amount, currency, tip, installments, email, phoneNumber, uid, APPLICATION_ID);
        SendRequest(uri);
    }

    private void performCloseBatch(String uid) {
        String uri = String.format("request/v1?transactionName=batchClose&uid=%s&appId=%s", uid, APPLICATION_ID);
        try {
            uri = URLEncoder.encode(uri, "UTF-8");
            uri = uriPrefix + uri;
        } catch (UnsupportedEncodingException e) {
//            Log.d(TAG, e.getMessage());
        }
        SendRequest(uri);
    }


    private void SendRequest(String uri) {
        switch (intentMethod) {
            case "DEEPLINK":
//                Log.d(TAG, "Sending txn request through deeplink");
                uri += String.format("&callback=%s://result", CALLBACK_NAME);
                Intent deepLinkIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                deepLinkIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(deepLinkIntent);
                break;
//            case "INTENT":
////                Log.d(TAG, "Sending txn request through intent");
//                Intent intent = new Intent();
//                if (uriPrefix.equals("nbgpaytxn://"))
//                    intent.setAction("com.arkeNBG.PERFORM_TRANSACTION");
//                else if (uriPrefix.equals("cosmotepaytxn://"))
//                    intent.setAction("com.mellongroup.payzy.PERFORM_TRANSACTION");
//                intent.putExtra("Uri", uri);
//                mStartForResult.launch(intent);
//                break;
//            default:
//                Log.d(TAG, "What method are you using? " + intentMethod);
        }
    }


}
