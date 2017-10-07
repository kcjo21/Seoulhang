package com.hbag.seoulhang.appbase_package;

import android.support.v7.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cwh62 on 2017-09-10.
 */

public class BaseActivity extends AppCompatActivity {


    public void progressON() {
        RealmInit.getInstance().progressON(this, null);
    }

    public void progressON(String message) {
        RealmInit.getInstance().progressON(this, message);
    }

    public void progressOFF() {
        RealmInit.getInstance().progressOFF();
    }

    public boolean checkEmail(String email){ //이메일 유효성 체크

        String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        return m.matches();

    }
    public boolean textValidate(String password) {  //영어+숫자+특수문자 혼합해서 6~18자 사이 비밀번호를 체크
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%^&+=])(?=\\S+$).{6,18}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public boolean checkId(String id){ //첫글자는 영어, 나머지 아이디는 영어와 숫자로 구성된 5~12자 아이디
        String regex = "^[a-zA-Z]{1}[a-zA-Z0-9_]{4,11}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(id);
        return m.matches();

    }

}
