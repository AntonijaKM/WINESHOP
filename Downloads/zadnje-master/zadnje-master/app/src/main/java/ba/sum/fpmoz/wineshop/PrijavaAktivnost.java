package ba.sum.fpmoz.wineshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ba.sum.fpmoz.wineshop.Common.Common;
import ba.sum.fpmoz.wineshop.model.Korisnik;

public class PrijavaAktivnost extends AppCompatActivity {
    TextView email_txt, lozinka_txt;
    TextView login_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prijava_aktivnost);

    email_txt=(TextView) findViewById(R.id.email_txt);
    lozinka_txt=(TextView) findViewById(R.id.lozinka_txt);
    login_btn=(Button)findViewById(R.id.login_btn);

        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference table_korisnik = database.getReference("korisnik");


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog mDialog = new ProgressDialog(PrijavaAktivnost.this);
                mDialog.setMessage("Pričekajte molim");
                mDialog.show();

                table_korisnik.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(email_txt.getText().toString()).exists()) {
                            mDialog.dismiss();
                            Korisnik korisnik = dataSnapshot.child(email_txt.getText().toString()).getValue(Korisnik.class);
                            if (korisnik.getLozinka().equals(lozinka_txt.getText().toString())) {
                                {
                                    Intent homeIntent = new Intent(PrijavaAktivnost.this, Home.class);
                                    Common.currentKorisnik=korisnik;
                                    startActivity(homeIntent);
                                    finish();

                                }
                            } else {
                                Toast.makeText(PrijavaAktivnost.this, "Neuspješna prijava", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(PrijavaAktivnost.this, "Korisnik ne postoji", Toast.LENGTH_SHORT).show();
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
