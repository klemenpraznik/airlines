package com.example.adrijaerlajns.Reservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adrijaerlajns.MainActivity;
import com.example.adrijaerlajns.Models.Client;
import com.example.adrijaerlajns.Models.Flight;
import com.example.adrijaerlajns.Models.ReservationInfo;
import com.example.adrijaerlajns.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;

public class PaymentActivity extends AppCompatActivity {
    int flightId, returnFlightId;
    List<Flight> flightList = new ArrayList<>();
    ReservationInfo info;
    Client client;
    TextView numberOfPeople, flightClass, returnFlightClass, totalPriceText;
    Flight flight, returnFlight;

    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        populateFlightList();

        Intent intent = getIntent();
        flightId = intent.getIntExtra("iFlightId", 0);
        returnFlightId = intent.getIntExtra("iReturnFlightId", 0);

        flight = getFlight(flightId);
        returnFlight = getFlight(returnFlightId);

        client = (Client) intent.getParcelableExtra("iClient");
        info = (ReservationInfo) intent.getParcelableExtra("iFlightInfo");

        setTitle("Plačilo");

        int number = 1;
        double totalPrice = 0;
        if (info.getNumberOfTravellers() > 1){
            number = info.getNumberOfTravellers();
        }

        numberOfPeople = findViewById(R.id.pNumberOfPeople);
        numberOfPeople.setText(numberOfPeople.getText() + " " + number);

        flightClass = findViewById(R.id.pFlightClass);
        if (info.getFlightClass().equals("prvi")){
            flightClass.setText(flightClass.getText() + " " + flight.getFirstClassPrice() + "€ (prvi razred)");
            totalPrice += number * flight.getFirstClassPrice();
        }
        else if (info.getFlightClass().equals("poslovni")){
            flightClass.setText(flightClass.getText() + " " + flight.getBusinessClassPrice() + "€ (poslovni razred)");
            totalPrice += number * flight.getBusinessClassPrice();
        }
        else {
            flightClass.setText(flightClass.getText() + " " + flight.getEconomyClassPrice() + "€ (ekonomski razred)");
            totalPrice += number * flight.getEconomyClassPrice();
        }

        returnFlightClass = findViewById(R.id.pReturnFlightClass);
        if (returnFlight != null){
            if (info.getFlightClass().equals("prvi")){
                returnFlightClass.setText(returnFlightClass.getText() + " " + returnFlight.getFirstClassPrice() + "€ (prvi razred)");
                totalPrice += number * returnFlight.getFirstClassPrice();
            }
            else if (info.getFlightClass().equals("poslovni")){
                returnFlightClass.setText(returnFlightClass.getText() + " " + returnFlight.getBusinessClassPrice() + "€ (poslovni razred)");
                totalPrice += number * returnFlight.getBusinessClassPrice();
            }
            else if (info.getFlightClass().equals("ekonomski")){
                returnFlightClass.setText(returnFlightClass.getText() + " " + returnFlight.getEconomyClassPrice() + "€ (ekonomski razred)");
                totalPrice += number * returnFlight.getEconomyClassPrice();
            }
            else {
                returnFlightClass.setText(returnFlightClass.getText() + " (brez povratnega leta)");
            }
        }
        else {
            returnFlightClass.setText(returnFlightClass.getText() + " (brez povratnega leta)");
        }

        totalPriceText = findViewById(R.id.pTotalPrice);
        totalPriceText.setText(totalPriceText.getText() + " " + totalPrice + "€");

    }

    public void pay(View view){
        PaymentActivity main = this;

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Plačilo");
        alertDialog.setMessage("Plačilo je bilo uspešno izvedeno.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent newIntent = new Intent(main, MainActivity.class);
                        main.startActivity(newIntent);
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void cancelReservation(View view){
        PaymentActivity main = this;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Izhod");
        builder.setMessage("Ali ste prepričani, da preklicati registracijo?");

        builder.setPositiveButton("DA", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                Intent newIntent = new Intent(main, MainActivity.class);
                main.startActivity(newIntent);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NE", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private Flight getFlight(int flightId) {
        for (Flight flight : flightList){
            if (flight.getFlightId() == flightId){
                return flight;
            }
        }
        return null;
    }

    public void populateFlightList() throws ParseException {
        //Ljubljana -> destinacija
        flightList.add(new Flight(1, "F8JPJ","Ljubljana", new SimpleDateFormat("dd.MM.yyyy HH:mm").parse("20.05.2020 10:00"),
                "Belgrade", new SimpleDateFormat("dd.MM.yyyy HH:mm").parse("20.05.2020 12:00"),
                100, 150, 250));

        flightList.add(new Flight(2,"RHVAO", "Ljubljana", new SimpleDateFormat("dd.MM.yyyy HH:mm").parse("22.05.2020 10:00"),
                "Belgrade", new SimpleDateFormat("dd.MM.yyyy HH:mm").parse("22.05.2020 12:00"),
                110, 150, 300));

        flightList.add(new Flight(3, "BIG4W", "Ljubljana", new SimpleDateFormat("dd.MM.yyyy HH:mm").parse("21.05.2020 08:00"),
                "Zagreb", new SimpleDateFormat("dd.MM.yyyy HH:mm").parse("21.05.2020 08:45"),
                50, 90, 150));

        flightList.add(new Flight(4, "G2BFY","Ljubljana", new SimpleDateFormat("dd.MM.yyyy HH:mm").parse("21.05.2020 16:00"),
                "Zagreb", new SimpleDateFormat("dd.MM.yyyy HH:mm").parse("21.05.2020 16:45"),
                60, 90, 150));

        flightList.add(new Flight(5,"6HIU7", "Ljubljana", new SimpleDateFormat("dd.MM.yyyy HH:mm").parse("20.05.2020 11:25"),
                "Berlin", new SimpleDateFormat("dd.MM.yyyy HH:mm").parse("21.05.2020 13:00"),
                50, 90, 150));

        flightList.add(new Flight(6, "AXS7X","Ljubljana", new SimpleDateFormat("dd.MM.yyyy HH:mm").parse("21.05.2020 11:25"),
                "Berlin", new SimpleDateFormat("dd.MM.yyyy HH:mm").parse("21.05.2020 13:00"),
                60, 90, 150));

        //destinacija -> Ljubljana
        flightList.add(new Flight(7, "ITX1K", "Belgrade", new SimpleDateFormat("dd.MM.yyyy HH:mm").parse("20.05.2020 10:00"),
                "Ljubljana", new SimpleDateFormat("dd.MM.yyyy HH:mm").parse("20.05.2020 12:00"),
                100, 150, 250));

        flightList.add(new Flight(8, "PQ4ER", "Belgrade", new SimpleDateFormat("dd.MM.yyyy HH:mm").parse("22.05.2020 10:00"),
                "Ljubljana", new SimpleDateFormat("dd.MM.yyyy HH:mm").parse("22.05.2020 12:00"),
                110, 150, 300));

        flightList.add(new Flight(9, "YQBBU", "Zagreb", new SimpleDateFormat("dd.MM.yyyy HH:mm").parse("21.05.2020 08:00"),
                "Ljubljana", new SimpleDateFormat("dd.MM.yyyy HH:mm").parse("21.05.2020 08:45"),
                50, 90, 150));

        flightList.add(new Flight(10, "RW1C4", "Zagreb", new SimpleDateFormat("dd.MM.yyyy HH:mm").parse("21.05.2020 16:00"),
                "Ljubljana", new SimpleDateFormat("dd.MM.yyyy HH:mm").parse("21.05.2020 16:45"),
                60, 90, 150));

        flightList.add(new Flight(11, "PWJTJ", "Berlin", new SimpleDateFormat("dd.MM.yyyy HH:mm").parse("20.05.2020 11:25"),
                "Ljubljana", new SimpleDateFormat("dd.MM.yyyy HH:mm").parse("21.05.2020 13:00"),
                50, 90, 150));

        flightList.add(new Flight(12, "3I302", "Berlin", new SimpleDateFormat("dd.MM.yyyy HH:mm").parse("21.05.2020 11:25"),
                "Ljubljana", new SimpleDateFormat("dd.MM.yyyy HH:mm").parse("21.05.2020 13:00"),
                60, 90, 150));
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
