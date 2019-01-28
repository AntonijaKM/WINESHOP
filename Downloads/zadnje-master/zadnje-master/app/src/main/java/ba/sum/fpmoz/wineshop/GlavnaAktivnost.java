package ba.sum.fpmoz.wineshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class GlavnaAktivnost extends AppCompatActivity {

    Button login_btn, registracija_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glavna_aktivnost);

        login_btn = (Button) findViewById(R.id.login_btn);
        registracija_btn = (Button) findViewById(R.id.registracija_btn);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        registracija_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registracija = new Intent(GlavnaAktivnost.this,RegistracijaAktivnost.class);
                startActivity(registracija);

            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent prijava = new Intent(GlavnaAktivnost.this,PrijavaAktivnost.class);
                startActivity(prijava);
            }
        });
    }
}


