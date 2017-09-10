package com.szb.szb.start_pack.registerpack;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.szb.szb.BaseActivity;
import com.szb.szb.R;

public class EditprofileActivity extends BaseActivity {
    TextView Withdrawal;
    InputMethodManager keyboard;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        Withdrawal = (TextView)findViewById(R.id.Withdrawal);
        keyboard =(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);


        Withdrawal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(EditprofileActivity.this);
                alert.setCancelable(false);
                alert.setMessage(R.string.회원탈퇴비밀번호);

                final EditText password = new EditText(EditprofileActivity.this);
                password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                password.setMaxLines(1);
                alert.setView(password);

                alert.setPositiveButton(R.string.취소, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });
                alert.setNegativeButton(R.string.확인,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                final AlertDialog dialog = alert.create();
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (password.getText().length()>0) {
                            keyboard.hideSoftInputFromWindow(password.getWindowToken(),0);

                            AlertDialog.Builder alert = new AlertDialog.Builder(EditprofileActivity.this);
                            alert.setCancelable(false);
                            alert.setTitle(R.string.탈퇴재확인);

                            alert.setPositiveButton(R.string.취소, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.dismiss();
                                }
                            });
                            alert.setNegativeButton(R.string.회원탈퇴, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                }
                            });
                            alert.show();
                            dialog.dismiss();
                        }
                        else {
                            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.비밀번호입력), Toast.LENGTH_LONG);
                            toast.show();
                        }

                    }
                });
            }
        });
    }
}
