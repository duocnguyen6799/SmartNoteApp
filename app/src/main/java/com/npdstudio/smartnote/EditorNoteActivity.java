package com.npdstudio.smartnote;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.irshulx.Editor;
import com.github.irshulx.EditorListener;
import com.github.irshulx.models.EditorTextStyle;

import java.io.IOException;
import java.util.Calendar;
import java.util.Map;

import jp.wasabeef.richeditor.RichEditor;

import static java.util.Calendar.DAY_OF_MONTH;

public class EditorNoteActivity extends AppCompatActivity {
    EditText edtTagNote;
    TextView txtTime;
    RichEditor mEditor;
    Note note;
    int ID_NOTE_UPDATE;
    boolean isNewNote;
    MyDBHelper helper;
    boolean isSavedNote = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_note);
        helper = new MyDBHelper(this);
        receiveData();
        addControls();
        addEvents();

    }

    private void addEvents() {
        addEventsEditor();
        //add Data for edit notes
        if(!isNewNote){
            //set data to edt
            //edtTitleNote.setText(note.getmTitle());
            edtTagNote.setText(note.getmTag()); // add "" if not.getmTag() = null
            mEditor.setHtml(note.getmContent());
            txtTime.setText("Thời gian cập nhật gần nhất: "+note.getmTime());
            txtTime.setTextSize(20);

        }
        else{
            Calendar calendar = Calendar.getInstance();
            String currentTime = calendar.get(DAY_OF_MONTH)+"/"
                    +calendar.get(Calendar.MONTH)+"/"
                    +calendar.get(Calendar.YEAR)+", "
                    +calendar.get(Calendar.HOUR_OF_DAY)+"H"
                    +calendar.get(Calendar.MINUTE)+"'"
                    +calendar.get(Calendar.SECOND)+"\"";
            txtTime.setText("Thời gian truy cập: "+currentTime);
            txtTime.setTextSize(20);
        }
    }

    private void addEventsEditor() {
        mEditor.setEditorHeight(200);
        mEditor.setEditorFontSize(22);
        mEditor.setEditorFontColor(Color.BLACK);
        //mEditor.setEditorBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundResource(R.drawable.bg);
        mEditor.setPadding(10, 10, 10, 10);
        //mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        mEditor.setPlaceholder("Nhập ghi chú...");
        //mEditor.setInputEnabled(false);

        mEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override public void onTextChange(String text) {
            }
        });
        //String t = mEditor.getHtml();


        findViewById(R.id.action_undo).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.undo();
            }
        });

        findViewById(R.id.action_redo).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.redo();
            }
        });

        findViewById(R.id.action_bold).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setBold();
            }
        });

        findViewById(R.id.action_italic).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setItalic();
            }
        });

        findViewById(R.id.action_subscript).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setSubscript();
            }
        });

        findViewById(R.id.action_superscript).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setSuperscript();
            }
        });

        findViewById(R.id.action_strikethrough).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setStrikeThrough();
            }
        });

        findViewById(R.id.action_underline).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setUnderline();
            }
        });

        findViewById(R.id.action_heading1).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(1);
            }
        });

        findViewById(R.id.action_heading2).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(2);
            }
        });

        findViewById(R.id.action_heading3).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(3);
            }
        });

        findViewById(R.id.action_heading4).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(4);
            }
        });

        findViewById(R.id.action_heading5).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(5);
            }
        });

        findViewById(R.id.action_heading6).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(6);
            }
        });

        findViewById(R.id.action_txt_color).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override public void onClick(View v) {
                mEditor.setTextColor(isChanged ? Color.BLACK : Color.RED);
                isChanged = !isChanged;
            }
        });

        findViewById(R.id.action_bg_color).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override public void onClick(View v) {
                mEditor.setTextBackgroundColor(isChanged ? Color.TRANSPARENT : Color.YELLOW);
                isChanged = !isChanged;
            }
        });

        findViewById(R.id.action_indent).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setIndent();
            }
        });

        findViewById(R.id.action_outdent).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setOutdent();
            }
        });

        findViewById(R.id.action_align_left).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setAlignLeft();
            }
        });

        findViewById(R.id.action_align_center).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setAlignCenter();
            }
        });

        findViewById(R.id.action_align_right).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setAlignRight();
            }
        });

        findViewById(R.id.action_blockquote).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setBlockquote();
            }
        });

        findViewById(R.id.action_insert_bullets).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setBullets();
            }
        });

        findViewById(R.id.action_insert_numbers).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setNumbers();
            }
        });

        findViewById(R.id.action_insert_image).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.insertImage("http://www.1honeywan.com/dachshund/image/7.21/7.21_3_thumb.JPG",
                        "dachshund");
            }
        });

        findViewById(R.id.action_insert_link).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.insertLink("https://github.com/wasabeef", "wasabeef");
            }
        });
        findViewById(R.id.action_insert_checkbox).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.insertTodo();
            }
        });
    }

    private void addControls() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        edtTagNote = findViewById(R.id.edtTagNote);
        mEditor = (RichEditor) findViewById(R.id.editor);
        txtTime = findViewById(R.id.txtTime);

    }
    //Saved: true
    private boolean saveNote(){
        note = new Note();
        if(mEditor.getHtml().length()>0){
            String mContent = mEditor.getHtml();
            String mTitle = mContent.split("<br>")[0]; //split by first line-down
            String strippedText = mTitle.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", "");
            note.setmTitle(strippedText);
            note.setmTag(edtTagNote.getText().toString());
            note.setmContent(mContent);

            Calendar calendar = Calendar.getInstance();
            String date = calendar.get(DAY_OF_MONTH)+"/"
                    + (calendar.get(Calendar.MONTH)+1)+ "/"
                    + calendar.get(Calendar.YEAR);
            String time = calendar.get(Calendar.HOUR_OF_DAY)+"H";
            if(calendar.get(Calendar.MINUTE)<10)
                time += "0";
            time += calendar.get(Calendar.MINUTE)+"'";
            if(calendar.get(Calendar.SECOND)<10)
                time +="0";
            time+= calendar.get(Calendar.SECOND)+"\"";
            note.setmTime(date+", "+time);
            //if a new note, insert into db, otherwise, update db
            if(isNewNote && !isSavedNote)
                helper.insertNote(note);
            else
                helper.updateNote(ID_NOTE_UPDATE,note);
            return  true;
        }
        else{
            Toast.makeText(getApplicationContext(),"Tiêu đề rỗng!",Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void receiveData(){
        Intent intent = getIntent();
        isNewNote = intent.getBooleanExtra(getResources().getString(R.string.key_note_is_new),false);
        note = (Note) intent.getSerializableExtra(getResources().getString(R.string.key_note_send));
        ID_NOTE_UPDATE = note.getmID();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
                if(!isSavedNote){
                    addDialogExit();
                }
                else{
                    finish();
                }
                return true;
            case R.id.action_save:
                if(saveNote()){
                    isSavedNote = true;
                    Toast.makeText(getApplicationContext(),"Đã lưu",Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }
    private void addDialogExit(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn lưu lại ghi chú không?");
        builder.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //save data
                if(saveNote()){
                    Toast.makeText(getApplicationContext(),"Đã lưu",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if(!isSavedNote){
            addDialogExit();
        }
        else{
            finish();
        }
    }
}
