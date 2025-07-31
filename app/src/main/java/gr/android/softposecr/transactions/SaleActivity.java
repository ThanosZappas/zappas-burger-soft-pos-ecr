package gr.android.softposecr.transactions;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;


public class SaleActivity extends AppCompatActivity {
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

        String amount = String.format(Locale.US, "%.2f", 100.00);
        String tip = "0.00";
        String installments = "1";
        String currency = "EUR";
        String email = "customer@example.com";
        String phoneNumber = "306900000000";
        String uid = "POS001";
        performSaleAadeV1(amount, tip, installments, currency, email, phoneNumber, uid);
    }

    public void performSaleAadeV1(String amount, String tip, String installments, String currency, String email, String phoneNumber, String uid) {
//        updateUID();
//        String msg = String.format("Performing sale: amount=%s tip=%s installments=%s currency=%s uid=%s", amount, tip, installments, currency, uid);
//        appendText(msg);
//        Log.d(TAG, msg);
//        createTestProviderDataV1();

        String uri = String.format("request/v1?amount=%s&currency=EUR&tip=%s&installments=%s&email=%s&phoneNumber=%s&uid=%s&transactionName=sale&providerData=%s&appId=%s",
                amount, tip, installments, email, phoneNumber, uid,
                "", APPLICATION_ID );
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
