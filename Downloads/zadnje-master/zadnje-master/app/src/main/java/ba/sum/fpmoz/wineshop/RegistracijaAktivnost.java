package ba.sum.fpmoz.wineshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ba.sum.fpmoz.wineshop.model.Korisnik;

public class RegistracijaAktivnost extends AppCompatActivity {

    TextView email_txt, lozinka_txt;
    TextView registracija_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registracija_aktivnost);

        email_txt = (TextView) findViewById(R.id.email_txt);
        lozinka_txt = (TextView) findViewById(R.id.lozinka_txt);
        registracija_btn = (Button)findViewById(R.id.registracija_btn);

        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference table_korisnik = database.getReference("korisnik");

        registracija_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(RegistracijaAktivnost.this);
                mDialog.setMessage("Pričekajte molim");
                mDialog.show();

                table_korisnik.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(email_txt.getText().toString()).exists())
                        {
                            mDialog.dismiss();
                            Toast.makeText(RegistracijaAktivnost.this, "Email već postoji!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            mDialog.dismiss();
                            Korisnik korisnik = new Korisnik(email_txt.getText().toString(),lozinka_txt.getText().toString());
                            table_korisnik.child(email_txt.getText().toString()).setValue(korisnik);
                            Toast.makeText(RegistracijaAktivnost.this, "Registracija uspješna", Toast.LENGTH_SHORT).show();
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

