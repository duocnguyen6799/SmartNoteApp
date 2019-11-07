package com.npdstudio.smartnote;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvListNotes;
    NoteAdapter noteAdapter;
    ArrayList<Note> arrNotes;
    SearchView searchView;
    TextView txtNoList;
    //private boolean multiSelect = false;
    //private ArrayList<Note> selectedItems = new ArrayList<Note>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,EditorNoteActivity.class);
                intent.putExtra(getResources().getString(R.string.key_note_is_new),true);
                intent.putExtra(getResources().getString(R.string.key_note_send),new Note());
                startActivity(intent);

            }
        });
        addControls();
        addEvents();

    }

    private void addEvents() {
        arrNotes = new ArrayList<>();
        MyDBHelper helper = new MyDBHelper(getApplicationContext());
        //add data from database
        arrNotes = helper.loadAllData();
        //reverse list
        Collections.reverse(arrNotes);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvListNotes.setLayoutManager(layoutManager);

        /*DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,0);
        rvListNotes.addItemDecoration(dividerItemDecoration);*/
        rvListNotes.addItemDecoration(new DividerItemDecoration(this,0));
        noteAdapter = new NoteAdapter(arrNotes,getApplicationContext());

        rvListNotes.setAdapter(noteAdapter);
        if (arrNotes.size()==0){
            txtNoList.setText("Không có ghi chú để hiển thị!");
            txtNoList.setPadding(30,30,30,0);
            txtNoList.setTextSize(24);
        }
        else{
            txtNoList.setText("");
            txtNoList.setPadding(0,0,0,0);
        }

    }

    private void addControls() {
        rvListNotes = findViewById(R.id.rvListNotes);
        txtNoList = findViewById(R.id.txtNoList);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                noteAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        MyDBHelper helper = new MyDBHelper(this);
        arrNotes.clear();
        arrNotes.addAll(helper.loadAllData());
        Collections.reverse(arrNotes);
        noteAdapter.notifyDataSetChanged();
        if (arrNotes.size()==0){
            txtNoList.setText("Không có ghi chú để hiển thị!");
            txtNoList.setPadding(30,30,30,0);
            txtNoList.setTextSize(24);
        }
        else{
            txtNoList.setText("");
            txtNoList.setPadding(0,0,0,0);

        }
        ///invalidateOptionsMenu();
    }
}
