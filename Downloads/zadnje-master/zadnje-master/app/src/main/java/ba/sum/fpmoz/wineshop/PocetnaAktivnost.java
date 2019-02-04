package ba.sum.fpmoz.wineshop;

import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ba.sum.fpmoz.wineshop.R;
import ba.sum.fpmoz.wineshop.holders.WineViewHolder;
import ba.sum.fpmoz.wineshop.model.Wine;

public class PocetnaAktivnost extends AppCompatActivity {


    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference mDatabase;
    RecyclerView wineRecyclerView;
    FirebaseRecyclerAdapter<Wine, WineViewHolder>adapter;
    Button odjava_btn;
    Button narudzba_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pocetna_aktivnost);

        auth = FirebaseAuth.getInstance();
        this.user=auth.getCurrentUser();
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
        this.wineRecyclerView=findViewById(R.id.recycleview_wine);
        this.odjava_btn=findViewById(R.id.odjava_btn);
        this.narudzba_btn=findViewById(R.id.narudzba_btn);


        odjava_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                finish();
                startActivity(new Intent(PocetnaAktivnost.this, RegistracijaAktivnost.class));
            }
        });
        narudzba_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PocetnaAktivnost.this, NarudzbaAktivnost.class));
            }
        });


        LinearLayoutManager manager=new LinearLayoutManager(this);
        this.wineRecyclerView.setLayoutManager(manager);
        this.wineRecyclerView.setHasFixedSize(true);

        FirebaseRecyclerOptions<Wine> options = new FirebaseRecyclerOptions.Builder<Wine>().setQuery(
                mDatabase.child("vina"), new SnapshotParser<Wine>() {
                    @NonNull
                    @Override
                    public Wine parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return snapshot.getValue(Wine.class);
                    }
                }
        ).build();
        adapter = new FirebaseRecyclerAdapter<Wine, WineViewHolder>(options) {
            @NonNull
            @Override
            public WineViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wine_list, viewGroup, false);
                WineViewHolder wineViewHolder = new WineViewHolder(view);
                wineViewHolder.setOnItemClickListener(new WineViewHolder.ItemClickListener() {
                    @Override
                    public void onItemClickListener(View v, int position) {
                        DatabaseReference wineReference = getRef(position);
                        Toast.makeText(getApplicationContext(), wineReference.getKey(), Toast.LENGTH_LONG).show();
                    }
                });
                return wineViewHolder;
            }
            @Override
            protected void onBindViewHolder(@NonNull WineViewHolder holder, int position, @NonNull Wine model) {
                holder.setWine(model);
            }
        };
        this.wineRecyclerView.setAdapter(adapter);

    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
