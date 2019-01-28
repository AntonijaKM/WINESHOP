package ba.sum.fpmoz.wineshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import ba.sum.fpmoz.wineshop.Common.Common;
import ba.sum.fpmoz.wineshop.Interface.ItemClickListener;
import ba.sum.fpmoz.wineshop.holders.WineViewHolder;
import ba.sum.fpmoz.wineshop.model.Wine;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase database;
    DatabaseReference vina;
    TextView txtFullName;
    RecyclerView recycler_View;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Wine, WineViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance();
        vina=database .getReference("vina");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView=navigationView.getHeaderView(0);
        txtFullName=(TextView)headerView.findViewById(R.id.txtFullName);
        txtFullName.setText(Common.currentKorisnik.getEmail());

        recycler_View = (RecyclerView)findViewById(R.id.recycler_View);
        recycler_View.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recycler_View.setLayoutManager(layoutManager);
        loadMenu();
    }

    private void loadMenu() {

        FirebaseRecyclerOptions<Wine> options = new FirebaseRecyclerOptions.Builder<Wine>().setQuery(
                vina.child("vina"), new SnapshotParser<Wine>() {
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
                Intent popisVina = new Intent(Home.this, PopisVina.class);
                popisVina.putExtra("VinaId",adapter.getRef(i).getKey());
                startActivity(popisVina);
                return wineViewHolder;
            }


            @Override
            protected void onBindViewHolder(@NonNull WineViewHolder holder, int position, @NonNull Wine model) {
                super.onBindViewHolder(holder, position);
            }
        };
        this.recycler_View.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_menu) {

        } else if (id == R.id.nav_kosarica) {

        } else if (id == R.id.nav_narudzbe) {

        } else if (id == R.id.nav_odjava) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}