package app.example.phanmembanhoa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import app.example.phanmembanhoa.Common.Common;
import app.example.phanmembanhoa.Model.User;

public class SignActive extends AppCompatActivity {
    EditText edtUsername,edtPass;
    Button btnSignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_active);

        edtPass = (MaterialEditText)findViewById(R.id.edtMatkhau);
        edtUsername = (MaterialEditText)findViewById(R.id.edtTaikhoan);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);


        final FirebaseDatabase database =  FirebaseDatabase.getInstance();
        final DatabaseReference table_user =    database.getReference("Account");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog mDialog = new ProgressDialog(SignActive.this);
                mDialog.setMessage("Vui lòng chờ một chút...");
                mDialog.show();
                table_user.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //kiem tra user co trong database ko
                        if(dataSnapshot.child(edtUsername.getText().toString()).exists()) {
                            //lay thong tin nguoi dung
                            mDialog.dismiss();
                            User user = dataSnapshot.child(edtUsername.getText().toString()).getValue(User.class);
                            user.setName(edtUsername.getText().toString());
                            if(user.getPassword().equals(edtPass.getText().toString())) {
                                {
                                    Intent home = new Intent(SignActive.this,Home.class);
                                    Common.currentUser = user;
                                    startActivity(home);
                                    finish();

                                }
                            } else {
                                Toast.makeText(SignActive.this, "Lỗi tài khoản mật khẩu!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            mDialog.dismiss();
                            Toast.makeText(SignActive.this, "Tài khoản không tồn tại!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}
