package com.example.adrijaerlajns.Flights;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adrijaerlajns.MainActivity;
import com.example.adrijaerlajns.Models.Flight;
import com.example.adrijaerlajns.R;
import com.example.adrijaerlajns.Reservation.ReservationActivityStep1;

import java.io.Console;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.SneakyThrows;

public class DisplayFlight extends AppCompatActivity {
    private int flightId;
    private List<Flight> flightList = new ArrayList<>();
    private Flight flight;
    TextView flightCode, takeoffLocation, takeoffDate, takeoffTime, landingLocation, landingDate, landingTime, economyClass, businessClass, firstClass, flightCodeField;

    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_flight);
        populateFlightList();

        Intent intent = getIntent();
        flightId = intent.getIntExtra("flightId", 0);

        flight = getFlight(flightId);
        if (flight != null){
            try{
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                String takeOffDateString = dateFormat.format(flight.getTakeOffDateTime());

                setTitle(flight.getTakeOffLocation() + " -> " + flight.getLandingLocation() + " (" + takeOffDateString + ")");

                //take off
                takeoffLocation = findViewById(R.id.displayTakeOffLocation);
                takeoffLocation.setText(takeoffLocation.getText() + flight.getTakeOffLocation());

                takeoffDate = findViewById(R.id.displayTakeOffDate);
                takeoffDate.setText(dateFormat.format(flight.getTakeOffDateTime()));

                takeoffTime = findViewById(R.id.displayTakeOffTime);
                takeoffTime.setText(timeFormat.format(flight.getTakeOffDateTime()));

                //landing
                landingLocation = findViewById(R.id.displayLandingLocation);
                landingLocation.setText(landingLocation.getText() + flight.getLandingLocation());

                landingDate = findViewById(R.id.displayLandingDate);
                landingDate.setText(dateFormat.format(flight.getLandingDateTime()));

                landingTime = findViewById(R.id.displayLandingTime);
                landingTime.setText(timeFormat.format(flight.getLandingDateTime()));

                economyClass = findViewById(R.id.displayEconomyClassPrice);
                economyClass.setText(flight.getEconomyClassPrice() + " €");

                businessClass = findViewById(R.id.displayBusinessClassPrice);
                businessClass.setText(flight.getBusinessClassPrice() + " €");

                firstClass = findViewById(R.id.displayFirstClassPrice);
                firstClass.setText(flight.getFirstClassPrice() + " €");

                flightCodeField = findViewById(R.id.displayFlightCode);
                flightCodeField.setText(flight.getFlightCode());
            }
            catch (Exception e){

            }
        }
        else {
            Intent allFlightsIntent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
        }
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

    public void createReservation(View view){
        Intent newReservation = new Intent(this, ReservationActivityStep1.class);
        newReservation.putExtra("iFlightId", flightId);
        newReservation.putExtra("iFlightCode", flight.getFlightCode());
        newReservation.putExtra("iFlightTakeoffLocation", flight.getTakeOffLocation());
        newReservation.putExtra("iFlightTakeoffDate", flight.getTakeOffDateTime());
        newReservation.putExtra("iFlightLandingLocation", flight.getLandingLocation());
        newReservation.putExtra("iFlightLandingDate", flight.getLandingDateTime());
        newReservation.putExtra("iFlightEconomyClass", flight.getEconomyClassPrice());
        newReservation.putExtra("iFlightFirstClass", flight.getFirstClassPrice());
        newReservation.putExtra("iFlightBusinessClass", flight.getBusinessClassPrice());
        this.startActivity(newReservation);
    }
}
