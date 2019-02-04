package ba.sum.fpmoz.wineshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NarudzbaAktivnost extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference mDatabase;
    RecyclerView orderRecyclerView;
    Button naruci_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_narudzba_aktivnost);

        auth = FirebaseAuth.getInstance();
        this.user=auth.getCurrentUser();
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
        this.orderRecyclerView=findViewById(R.id.recycleview_narudzba);
        this.naruci_btn=findViewById(R.id.naruci_btn);
        this.database=FirebaseDatabase.getInstance();
        naruci_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //stavi string itd

            }
        });

    }
}
