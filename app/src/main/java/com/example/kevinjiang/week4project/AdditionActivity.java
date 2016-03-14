package com.example.kevinjiang.week4project;

import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AdditionActivity extends AppCompatActivity {
    Button pButton;
    Button submit;
    ImageView image;
    EditText editName;
    EditText description;

    SharedPreferences preferences;

    public static final int SELECT_IMAGE = 1;
    public static final String SAVE_NAME = "savename";
    public static final String SAVE_DESCRIPTION = "savedescription";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        pButton = (Button) findViewById(R.id.buttonPicture);
        submit = (Button) findViewById(R.id.submit);
        image = (ImageView) findViewById(R.id.image1);
        editName = (EditText) findViewById(R.id.editName);
        description = (EditText) findViewById(R.id.editDescription);
        if(editName.getText().toString().equals(""))
            editName.setError("Input fields cannot be left blank!");
        else if (description.getText().toString().equals("")){
            description.setError("Input fields cannot be left blank!");
        }

        String savedName = editName.getText().toString();
        String savedDescription = description.getText().toString();

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(SAVE_NAME, savedName);
        editor.putString(SAVE_DESCRIPTION, savedDescription);
        editor.commit();

        editName.setText(preferences.getString(SAVE_NAME, "error"));
        editName.setText(preferences.getString(SAVE_DESCRIPTION, "error"));

        pButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,SELECT_IMAGE);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.update(editName.getText().toString(), description.getText().toString());
                Notification notif = new Notification.Builder(AdditionActivity.this)
                        .setContentTitle("New photo added!")
                        .setContentText("New Photo Entry")
                        .build();
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_IMAGE) {
                Uri selectedImageUri = data.getData();
                image.setImageURI(selectedImageUri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
