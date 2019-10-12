package com.example.myapplication;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivityList extends AppCompatActivity implements ContactsAdapter.ContactsAdapterListener {

    private static final String TAG = MainActivityList.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<Upload2> contactList;
    private ContactsAdapter mAdapter;
    private SearchView searchView;

    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabaseRef;
    String uemail="";
    Map<String, String> map = new HashMap<String, String>();
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_main_list);
//        setProgressBarIndeterminateVisibility(true);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        // toolbar fancy stuff
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle(R.string.toolbar_title);
//        getSupportActionBar().setTitle((Html.fromHtml(
//                "<font color=\"#FFFFFF\">"
//                        + getString(R.string.toolbar_title)
//                //+ "</font>"
//        )));

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            uemail=auth.getCurrentUser().getEmail();
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view2);
        contactList = new ArrayList<>();
        mAdapter = new ContactsAdapter(getBaseContext(), contactList, this);

        // white background notification bar
//        whiteNotificationBar(recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 20));
        recyclerView.setAdapter(mAdapter);

        //change stutus ber color..
        Window window=this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
//        uemail = getIntent().getExtras().getString("email");

        initialize();
    }

    private void initialize() {
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contactList.clear();
                Log.d(TAG, "onDataChange0: contactList"+contactList.size());
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload2 upload = postSnapshot.getValue(Upload2.class);
                    contactList.add(upload);
                    mAdapter.notifyDataSetChanged();
                    map.put(upload.getUserEmail(),postSnapshot.getKey());
                    Log.d(TAG, contactList.size()+upload.getUserEmail()+"onDataChange1: "+postSnapshot.getKey()+"---"+map.get(uemail));
                }
                Log.d(TAG, "onDataChange2: contactList"+contactList.size());
//                setProgressBarIndeterminateVisibility(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivityList.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

//        Toast.makeText(MainActivityList.this, uemail+"key="+map.get(uemail), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            return true;
        }
        if (id == R.id.action_about) {
            final Context context = this;
//            for (Upload2 user:contactList){
//                if(user.getUserEmail().equals("asmaull15-10312@diu.edu.bd")){
//                Gson gson = new Gson();
//                String myJson = gson.toJson(user);
                Intent intent = new Intent(context, About.class);
//                intent.putExtra("myjson", myJson);
//                Log.d(TAG, "asmaull15-10312@diu.edu.bd"+"=onDataChange3: "+map.get("asmaull15-10312@diu.edu.bd"));
                startActivity(intent);
//                break;
//            }
//            }
            return true;
        }
        if (id == R.id.action_profile) {
            final Context context = this;
            if(!uemail.equals("")) {
                for (Upload2 user : contactList) {
                    if (user.getUserEmail().equals(uemail)) {
                        Gson gson = new Gson();
                        String myJson = gson.toJson(user);
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra("user", myJson);
                        intent.putExtra("key", map.get(uemail));
                        Log.d(TAG, uemail + "onDataChange4: " + user.getUserEmail() + "---" + map.get(uemail));
                        startActivity(intent);
                        break;
                    }
                }
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    @Override
    public void onContactSelected(Upload2 contact) {
        final Context context = this;
        Gson gson = new Gson();
        String myJson = gson.toJson(contact);

        Intent intent = new Intent(context, DetailActivity.class);
//        intent.putExtra("roll", contact.getPhone());
        intent.putExtra("myjson", myJson);
        startActivity(intent);
        //Toast.makeText(getApplicationContext(), "Selected: " + contact.getName() + ", " + contact.getPhone(), Toast.LENGTH_LONG).show();
    }
}