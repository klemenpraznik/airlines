package com.example.adrijaerlajns.Reservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.InputFilter;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adrijaerlajns.Adapters.InputFilterMinMax;
import com.example.adrijaerlajns.Flights.ReturnFlightActivity;
import com.example.adrijaerlajns.Models.Client;
import com.example.adrijaerlajns.Models.Flight;
import com.example.adrijaerlajns.Models.ReservationInfo;
import com.example.adrijaerlajns.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;

public class ReservationActivityStep1 extends AppCompatActivity {
    private List<Flight> flightList = new ArrayList<>();
    private int flightId;
    private Flight flight;
    TextView locationFrom, locationTo, timeFrom, timeTo;
    EditText numberOfPeople, customerName, customerSurname, customerMail, customerPhoneNumber;
    Switch aSwitch;
    RadioButton maleButton, femaleButton, ecoRadio, businessRadio, fistRadio;
    RadioGroup flightClass;
    List<Client> clients;


    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_step1);
        Intent intent = getIntent();
        flightId = intent.getIntExtra("iFlightId", 0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        populateFlightList();
        flight = getFlight(flightId);
        setTitle("Rezervacija leta " + flight.getFlightCode());

        aSwitch = findViewById(R.id.povratniLetSwitch);

        numberOfPeople = findViewById(R.id.numberOfClients);
        numberOfPeople.setFilters(new InputFilter[]{new InputFilterMinMax("1", "15")});

        locationFrom = findViewById(R.id.locationFrom);
        locationFrom.setText(flight.getTakeOffLocation());

        locationTo = findViewById(R.id.locationTo);
        locationTo.setText(flight.getLandingLocation());

        timeTo = findViewById(R.id.timeTo);
        timeTo.setText(dateFormat.format(flight.getLandingDateTime()));

        timeFrom = findViewById(R.id.timeFrom);
        timeFrom.setText(dateFormat.format(flight.getTakeOffDateTime()));

        customerName = findViewById(R.id.tName);
        customerSurname = findViewById(R.id.surnameInput);
        customerMail = findViewById(R.id.emailInput);
        customerPhoneNumber = findViewById(R.id.phoneInput);

        maleButton = findViewById(R.id.radioMale);
        femaleButton = findViewById(R.id.radioFemale);
        ecoRadio = findViewById(R.id.radioEco);
        businessRadio = findViewById(R.id.radioBusiness);
        fistRadio = findViewById(R.id.radioFirst);

        flightClass = findViewById(R.id.radioGroupClass);
    }

    public void nextStep(View view) throws ParseException {
        Client client = new Client(
                customerName.getText().toString(),
                customerSurname.getText().toString(),
                maleButton.isChecked() ? "male" : "female",
                customerMail.getText().toString(),
                customerPhoneNumber.getText().toString());

        String flightClass = "";
        if (fistRadio.isChecked()){
            flightClass = "prvi";
        }
        else if (businessRadio.isChecked()){
            flightClass = "poslovni";
        }
        else {
            flightClass = "ekonomski";
        }

        String peopleNumber = numberOfPeople.getText().toString();
        int number = 0;
        try{
            if (peopleNumber == "" || peopleNumber == " "){
                number = 0;
            }
            else{
                number = Integer.parseInt(peopleNumber);
            }
        }
        catch (Exception e){

        }

        ReservationInfo info = new ReservationInfo(
                number,
                aSwitch.isChecked() ? "true" : "false",
                flightClass
        );

        if (aSwitch.isChecked()){
            Intent returnFlight = new Intent(this, ReturnFlightActivity.class);
                returnFlight.putExtra("iFlightId", flightId);
                returnFlight.putExtra("iFlightTakeoffLocation", flight.getTakeOffLocation());
                returnFlight.putExtra("iFlightLandingLocation", flight.getLandingLocation());
                returnFlight.putExtra("iClient", (Parcelable) client);
                returnFlight.putExtra("iFlightInfo", (Parcelable) info);
            this.startActivity(returnFlight);
        }
        else if (peopleNumber.equals("0") || peopleNumber.equals("1") || peopleNumber.equals("") || peopleNumber.equals(" ")){
            Intent payment = new Intent(this, PaymentActivity.class);
                payment.putExtra("iFlightId", flightId);
                payment.putExtra("iClient", (Parcelable) client);
                payment.putExtra("iFlightInfo", (Parcelable) info);
            this.startActivity(payment);
        }
        else {
            clients = new ArrayList<>();
            clients.add(client);
            Intent travellersInfo = new Intent(this, TravellerInfo.class);
                travellersInfo.putExtra("iFlightId", flightId);
                travellersInfo.putExtra("iFlightTakeoffLocation", flight.getTakeOffLocation());
                travellersInfo.putExtra("iFlightLandingLocation", flight.getLandingLocation());
                travellersInfo.putExtra("iClient", (Parcelable) client);
                travellersInfo.putExtra("iFlightInfo", (Parcelable) info);
                travellersInfo.putExtra("iTotalClients", info.getNumberOfTravellers());
                travellersInfo.putExtra("iCount", 1);
                travellersInfo.putParcelableArrayListExtra("iClientsList", (ArrayList<Client>) clients);
            this.startActivity(travellersInfo);
        }
    }

    public void classChange(View view){
        Context context = getApplicationContext();
        CharSequence text = "";
        int duration = Toast.LENGTH_SHORT;
        if (ecoRadio.isChecked()){
            text = "Ekonomski razred (cena: " + flight.getEconomyClassPrice() + " €).";
        }
        else if (businessRadio.isChecked()){
            text = "Poslovni razred (cena: " + flight.getBusinessClassPrice() + " €).";
        }
        else if (fistRadio.isChecked()){
            text = "Prvi razred (cena: " + flight.getFirstClassPrice() + " €).";
        }
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void returnFlightSwitch(View view){
        Context context = getApplicationContext();
        CharSequence text = "";
        int duration = Toast.LENGTH_SHORT;
        if (aSwitch.isChecked()){
            text = "S povratnim letom.";
        }
        else {
            text = "Brez povratnega leta.";
        }
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
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
