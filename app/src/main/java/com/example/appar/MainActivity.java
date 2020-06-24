package com.example.appar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Database myDb;
    EditText title_zone, date_zone, scenario_zone, realisation_zone, musique_zone, description_zone, mail_zone;
    Button btn_valider;
    Button btn_afficher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new Database(this);

        title_zone = (EditText)findViewById(R.id.title_zone);
        date_zone = (EditText) findViewById(R.id.date_zone);
        scenario_zone = (EditText) findViewById(R.id.scenario_zone);
        realisation_zone = (EditText) findViewById(R.id.realisation_zone);
        musique_zone = (EditText) findViewById(R.id.musique_zone);
        description_zone = (EditText) findViewById(R.id.description_zone);

        mail_zone = (EditText)findViewById(R.id.mail_zone);

        btn_valider = (Button)findViewById(R.id.btn_valider);

        btn_afficher = (Button)findViewById(R.id.btn_afficher);

        AddData();
        displayAll();
    }

    public void AddData(){
        btn_valider.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(title_zone.getText().toString(),
                                date_zone.getText().toString(),
                                scenario_zone.getText().toString(),
                                realisation_zone.getText().toString(),
                                musique_zone.getText().toString(),
                                description_zone.getText().toString()
                                );

                        String objMail = "testmail";

                        if(isInserted == true) {
                            Toast.makeText(MainActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
                            if(mail_zone.getText().toString() != null || mail_zone.getText().toString() != "Email") {
                                sendEmail();
                            }

                        }
                        else
                            Toast.makeText(MainActivity.this,"Data not inserted", Toast.LENGTH_LONG).show();

                    }
                    }

                    );
    }


    public void displayAll() {
        btn_afficher.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if(res.getCount() == 0){
                            showMessage("Error", "No data found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("ID : "+ res.getString(0) + "\n");
                            buffer.append("Title : "+ res.getString(1)+ "\n");
                            buffer.append("Date : "+ res.getString(2)+ "\n");
                            buffer.append("Note scénario : "+ res.getString(3)+ "\n");
                            buffer.append("Note réalisation : "+ res.getString(4)+ "\n");
                            buffer.append("Note musique : "+ res.getString(5)+ "\n");
                            buffer.append("Description : "+ res.getString(6)+ "\n\n");

                        }

                        showMessage("Critiques",buffer.toString());
                    }
                }
        );
    }


    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    protected void sendEmail() {
        Log.i("Send email", "");

        String[] TO = {"" + mail_zone.getText().toString()};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Critique du film : " + title_zone.getText().toString());
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Titre : " + title_zone.getText().toString() + "\n" + "Date : "  + date_zone.getText().toString() + "\n" + "Note scénario : " + scenario_zone.getText().toString() + "\n" + "Note réalisation : " + realisation_zone.getText().toString() + "\n" + "Note musique : " + musique_zone.getText().toString() + "\n" + "Critique : " + description_zone.getText().toString());

        try {
            startActivity(Intent.createChooser(emailIntent, "Sending email"));
            finish();
            Log.i("Email sent", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this,
                    "Erreur", Toast.LENGTH_SHORT).show();
        }
    }


}
