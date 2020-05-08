package com.example.adrijaerlajns.Flights;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.adrijaerlajns.Adapters.ReturnFlightAdapter;
import com.example.adrijaerlajns.Models.Client;
import com.example.adrijaerlajns.Models.Flight;
import com.example.adrijaerlajns.Models.ReservationInfo;
import com.example.adrijaerlajns.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;

public class ReturnFlightActivity extends AppCompatActivity {
    List<Flight> flightList = new ArrayList<>();
    List<Flight> returnFlights = new ArrayList<>();
    int flightId;
    RecyclerView recView;
    ReturnFlightAdapter flightAdapter;
    Client client;
    ReservationInfo info;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_flight);
        Intent intent = getIntent();
        String takeoffLocation = intent.getStringExtra("iFlightTakeoffLocation");
        String landingLocation = intent.getStringExtra("iFlightLandingLocation");
        flightId = intent.getIntExtra("iFlightId", 0);
        client = (Client) intent.getParcelableExtra("iClient");
        info = (ReservationInfo) intent.getParcelableExtra("iFlightInfo");


        populateFlightList();
        for (Flight flight : flightList){
            if (flight.getTakeOffLocation().equals(landingLocation) && (flight.getLandingLocation().equals(takeoffLocation))){
                returnFlights.add(flight);
            }
        }

        setTitle("Izbira povratnega leta");

        recView = findViewById(R.id.returnFlightsRecyclerView);
        recView.setLayoutManager(new LinearLayoutManager(this));

        flightAdapter = new ReturnFlightAdapter(this, getArrayList(returnFlights), flightId, client, info);
        recView.setAdapter(flightAdapter);
    }

    public ArrayList<Flight> getArrayList (List<Flight> documentsList){
        ArrayList flightArrayList = new ArrayList<Flight>();
        for (Flight document : documentsList){
            flightArrayList.add(document);
        }
        return flightArrayList;
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

    private Flight getFlight(int flightId) {
        for (Flight flight : flightList){
            if (flight.getFlightId() == flightId){
                return flight;
            }
        }
        return null;
    }
}
