package com.example.adrijaerlajns.Reservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.adrijaerlajns.Models.Client;
import com.example.adrijaerlajns.Models.ReservationInfo;
import com.example.adrijaerlajns.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TravellerInfo extends AppCompatActivity {
    EditText tName, tSurname;
    RadioButton maleButton, femaleButton;
    RadioGroup flightClass;
    int currentIndex, lastIndex, flightId, returnFlightId, count;
    String takeoffLocation, landingLocation;
    Client client;
    TextView personCount;
    ReservationInfo info;
    List<Client> clients;
    TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traveller_info);
        Intent intent = getIntent();
        currentIndex = intent.getIntExtra("iCount", 1);
        lastIndex = intent.getIntExtra("iTotalClients", 1);
        flightId = intent.getIntExtra("iFlightId", 0);
        takeoffLocation = intent.getStringExtra("iFlightTakeoffLocation");
        landingLocation = intent.getStringExtra("iFlightLandingLocation");
        returnFlightId = intent.getIntExtra("iReturnFlightId", 0);
        client = (Client) intent.getParcelableExtra("iClient");
        info = (ReservationInfo) intent.getParcelableExtra("iFlightInfo");
        clients = intent.getParcelableArrayListExtra("iClientsList");

        setTitle("Potnik št.: " + currentIndex);

        personCount = findViewById(R.id.personCountLabel);
        personCount.setText(personCount.getText() + " " + currentIndex + ":");

        tName = findViewById(R.id.tName);
        tSurname = findViewById(R.id.tSurname);
        maleButton = findViewById(R.id.radioMale);
        femaleButton = findViewById(R.id.radioFemale);
        mDisplayDate = findViewById(R.id.birthDate);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        TravellerInfo.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;

                String date = dayOfMonth + "." + month + "." + year;
                mDisplayDate.setText(date);
            }
        };

        if (currentIndex == 1){
            tName.setText(client.getName());
            tSurname.setText(client.getSurname());
            if (client.getGender().equals("female")){
                femaleButton.setChecked(true);
                maleButton.setChecked(false);
            }
            else if (client.getGender().equals("male")){
                femaleButton.setChecked(false);
                maleButton.setChecked(true);
            }
        }
    }

    public void nextStep(View view){
        if (currentIndex == lastIndex){ //goes to payment activity
            Intent payment = new Intent(this, PaymentActivity.class);
                payment.putExtra("iFlightId", flightId);
                payment.putExtra("iReturnFlightId", returnFlightId);
                payment.putExtra("iFlightTakeoffLocation", takeoffLocation);
                payment.putExtra("iFlightLandingLocation", landingLocation);
                payment.putExtra("iClient", (Parcelable) client);
                payment.putExtra("iFlightInfo", (Parcelable) info);
                payment.putExtra("iTotalClients", info.getNumberOfTravellers());
                payment.putExtra("iCount", 1);
                payment.putParcelableArrayListExtra("iClientsList", (ArrayList<Client>) clients);
            this.startActivity(payment);
        }
        else { //goes to travellers activity
            if (currentIndex != 1) {
                Client newClient = new Client(
                    tName.getText().toString(),
                    tSurname.getText().toString(),
                    maleButton.isChecked() ? "male" : "female",
                    "",
                    ""
                );
                clients.add(newClient);
            }
            Intent travellersInfo = new Intent(this, TravellerInfo.class);
                travellersInfo.putExtra("iFlightId", flightId);
                travellersInfo.putExtra("iReturnFlightId", returnFlightId);
                travellersInfo.putExtra("iFlightTakeoffLocation", takeoffLocation);
                travellersInfo.putExtra("iFlightLandingLocation", landingLocation);
                travellersInfo.putExtra("iClient", (Parcelable) client);
                travellersInfo.putExtra("iFlightInfo", (Parcelable) info);
                travellersInfo.putExtra("iTotalClients", info.getNumberOfTravellers());
                travellersInfo.putExtra("iCount", currentIndex + 1);
            travellersInfo.putParcelableArrayListExtra("iClientsList", (ArrayList<Client>) clients);
            this.startActivity(travellersInfo);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
