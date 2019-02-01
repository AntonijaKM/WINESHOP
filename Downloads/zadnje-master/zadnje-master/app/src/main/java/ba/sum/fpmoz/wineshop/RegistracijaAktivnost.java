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

import com.google.android.gms.common.internal.service.Common;
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

    EditText email_txt;
    EditText lozinka_txt;
    Button registracija_btn;
    Button login_btn;
    TextView uvod_txt;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registracija_aktivnost);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            Intent i = new Intent(RegistracijaAktivnost.this, PocetnaAktivnost.class);
            startActivity(i);
            finish();
        }

        this.email_txt = findViewById(R.id.email_txt);
        this.lozinka_txt = findViewById(R.id.lozinka_txt);
        this.registracija_btn = findViewById(R.id.registracija_btn);
        this.login_btn = findViewById(R.id.login_btn);
        this.uvod_txt=findViewById(R.id.uvod_txt);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_korisnik = database.getReference("korisnik");

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog mDialog = new ProgressDialog(RegistracijaAktivnost.this);
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
                                    Intent homeIntent = new Intent(RegistracijaAktivnost.this, PocetnaAktivnost.class);
                                    startActivity(homeIntent);
                                    finish();

                                }
                            } else {
                                Toast.makeText(RegistracijaAktivnost.this, "Neuspješna prijava", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(RegistracijaAktivnost.this, "Korisnik ne postoji", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        registracija_btn.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    String email = email_txt.getText().toString();
                                                    String lozinka = lozinka_txt.getText().toString();

                                                    auth.createUserWithEmailAndPassword(email, lozinka)
                                                            .addOnCompleteListener(RegistracijaAktivnost.this, new OnCompleteListener<AuthResult>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                                    Toast.makeText(getApplicationContext(), "Uspješno ste registrirani.", Toast.LENGTH_LONG).show();

                                                                }
                                                            });
                                                }
                                            }
        );
    }}