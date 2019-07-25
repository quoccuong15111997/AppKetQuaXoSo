package com.example.appketquaxoso;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.callback.TaiKhoanOnClickListener;

public class DialogTaiKhoan extends Dialog {
    public DialogTaiKhoan(Context context, TaiKhoanOnClickListener taiKhoanOnClickListener) {
        super(context);
       this.taiKhoanOnClickListener=taiKhoanOnClickListener;
    }


   TaiKhoanOnClickListener taiKhoanOnClickListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_tai_khoan);

        final EditText edtUsername=findViewById(R.id.edtUsername);
        Button btnLuu = (Button) findViewById(R.id.btnThamGia);
        btnLuu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                taiKhoanOnClickListener.onButtonClick(edtUsername.getText().toString());
                edtUsername.setText("");
                dismiss();
            }
        });
    }
}
