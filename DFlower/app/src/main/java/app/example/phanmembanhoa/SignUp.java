package app.example.phanmembanhoa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import app.example.phanmembanhoa.Model.User;

public class SignUp extends AppCompatActivity {

    MaterialEditText edtUsername,edtName,edtPass,edtPhone,edtMail;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtName = (MaterialEditText)findViewById(R.id.edtName);
        edtUsername = (MaterialEditText)findViewById(R.id.edtTaikhoan);
        edtPass = (MaterialEditText)findViewById(R.id.edtMatkhau);
        edtPhone = (MaterialEditText)findViewById(R.id.edtPhone);
        edtMail = (MaterialEditText)findViewById(R.id.edtMail);

        btnSignUp = (Button)findViewById(R.id.btnSignup);

        final FirebaseDatabase database =  FirebaseDatabase.getInstance();
        final DatabaseReference table_user =    database.getReference("Account");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("Vui lòng chờ một chút...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(edtUsername.getText().toString()).exists()){
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this, "Tài khoản có người sử dụng", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            mDialog.dismiss();
                            User user = new User(edtName.getText().toString(),
                                    edtPass.getText().toString(),edtUsername.getText().toString(),
                                    edtPhone.getText().toString(),edtMail.getText().toString()) ;
                            table_user.child(edtUsername.getText().toString()).setValue(user);
                            Toast.makeText(SignUp.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        });
}
}
