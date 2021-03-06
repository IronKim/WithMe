package com.mju.hps.withme.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.mju.hps.withme.LoginActivity;
import com.mju.hps.withme.MainActivity;
import com.mju.hps.withme.R;

/**
 * Created by KMC on 2016. 11. 17..
 */

public class WithMeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.e("action", action);
        if(action.equals("com.mju.hps.withme.reciver.createUserFail")){
            Toast.makeText(context, "회원 가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
        }
        else if(action.equals("com.mju.hps.withme.reciver.createUserSuccess")){

            Intent resultIntent =new Intent(context, LoginActivity.class);
//            intent2.putExtra("text","data");
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(resultIntent);
            Toast.makeText(context, "회원 가입에 성공하였습니다.", Toast.LENGTH_SHORT).show();
        }
        else if(action.equals("com.mju.hps.withme.reciver.createUserError")){
            Toast.makeText(context, "서버에러 입니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
        }
        else if(action.equals(NfcAdapter.ACTION_NDEF_DISCOVERED)){
            Parcelable[] rawMsgs = intent
                    .getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            // Android Beam에서는 한 번에 한 개의 메시지만 송수신 가능
            NdefMessage msg = (NdefMessage) rawMsgs[0];

            // 첫 번째 레코드에 MIME데이터가 포함된다
            String text = new String(msg.getRecords()[0].getPayload());

            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        }
//        else if(action.equals("com.mju.hps.withme.reciver.loginSuccess")){
//            Log.i("receiver", "loginSuccess");
//            Intent resultIntent=new Intent(context, MainActivity.class);
//            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            context.startActivity(resultIntent);
//            Toast.makeText(context, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
//        }
//        else if(action.equals("com.mju.hps.withme.reciver.loginFail")){
//            Log.i("receiver", "loginFail");
//            Toast.makeText(context, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
//        }
//        else if(action.equals("com.mju.hps.withme.reciver.loginError")){
//            Log.i("receiver", "loginError");
//            Toast.makeText(context, "서버에러 입니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
//        }
    }
}
