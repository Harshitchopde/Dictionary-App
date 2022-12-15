package com.example.dictonary;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public
class MainActivity extends AppCompatActivity {
    TextView word, meaning;
    FloatingActionButton floatingActionButton;
    LinearLayout llinout;
    SqliteDatabase sqliteDatabase;
    RecycleViewAdapter recycleViewAdapter;
    DatabaseHelper db;
    RecyclerView recyclerView;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    ArrayList<Expenses> arrayExpenses = new ArrayList<>();


    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVariable();
        whenFloationButtonClick();
        onNavigationOptionClick();


    }

    private
    void onNavigationOptionClick() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public
            boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id==R.id.homeBtn){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else {
                    AlertDialog.Builder alertDialg = new AlertDialog.Builder(MainActivity.this);
                    alertDialg.setTitle("Log Out")
                            .setMessage("Are you sure want to log out your data will be deleted")
                            .setNegativeButton("No",null)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public
                                void onClick(DialogInterface dialog, int which) {
                                    for (int i=0;i<arrayExpenses.size();i++){
                                        db.expenseDAO().deleteTx(new Expenses(arrayExpenses.get(i).getId(),arrayExpenses.get(i).getTitle(),arrayExpenses.get(i).getAmount()));
                                    }

                                   MainActivity.super.onBackPressed();
                                }
                            });
                    alertDialg.show();

                }
                return true;
            }
        });
    }

    private
    void whenFloationButtonClick() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.adding_update);
                Log.d(TAG, "onClick: after dialog create");
                EditText wordIN = dialog.findViewById(R.id.wordIP);
                EditText meaningIN = dialog.findViewById(R.id.meaningIP);
                Button btn = dialog.findViewById(R.id.addingBtn);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public
                    void onClick(View v) {
                        String word = wordIN.getText().toString();
                        String meaning = meaningIN.getText().toString();
                        db.expenseDAO().addTx(new Expenses(word, meaning));
                        arrayExpenses.add(new Expenses(word, meaning));
//                        Toast.makeText(MainActivity.this, "sucessfull", Toast.LENGTH_SHORT).show();
                        recycleViewAdapter.notifyItemInserted(arrayExpenses.size()-1);
                        dialog.dismiss();
                        recyclerView.scrollToPosition(arrayExpenses.size()-1);
                    }
                });
                dialog.show();


            }
        });

    }

   public void initVariable(){
       word = findViewById(R.id.word);
       meaning = findViewById(R.id.meaning);
       llinout = findViewById(R.id.basicLayout);
       floatingActionButton = findViewById(R.id.floatingBtn);
       initiliseTheNavigtionBar();


       sqliteDatabase = new SqliteDatabase(this);
       recyclerView = findViewById(R.id.recycleView);
       db = DatabaseHelper.getDB(this);

       //step 1
       recyclerView.setLayoutManager(new LinearLayoutManager(this));
     showDicNary();

   }


    private
    void initiliseTheNavigtionBar() {
        // for drawer layout
        drawerLayout = findViewById(R.id.drawerNav);
        navigationView = findViewById(R.id.NavigationDrawer);
        toolbar = findViewById(R.id.toobar);
        // set the support
        setSupportActionBar(toolbar);

        // toggle action bar drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.opendrw,R.string.closedrw);
        // click view swip kar ka toggle
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    public void
   showDicNary(){
       arrayExpenses = (ArrayList<Expenses>) db.expenseDAO().getAllExpense();
       recycleViewAdapter = new RecycleViewAdapter(this,arrayExpenses,db);
       recyclerView.setAdapter(recycleViewAdapter);



   }

    @Override
    public
    void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
        drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();

        }
    }
}
