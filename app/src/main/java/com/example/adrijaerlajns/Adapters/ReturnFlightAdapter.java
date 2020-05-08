package com.example.adrijaerlajns.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adrijaerlajns.Holders.FlightHolder;
import com.example.adrijaerlajns.ItemClickListener;
import com.example.adrijaerlajns.Models.Client;
import com.example.adrijaerlajns.Models.Flight;
import com.example.adrijaerlajns.Models.ReservationInfo;
import com.example.adrijaerlajns.R;
import com.example.adrijaerlajns.Reservation.ReservationActivityStep2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ReturnFlightAdapter extends RecyclerView.Adapter<FlightHolder> {
    Context c;
    ArrayList<Flight> flights;
    int flightId;
    Client client;
    ReservationInfo info;

    public ReturnFlightAdapter(Context c, ArrayList<Flight> models, int flightId, Client client, ReservationInfo info){
        this.c = c;
        this.flights = models;
        this.flightId = flightId;
        this.client = client;
        this.info = info;
    }

    @NonNull
    @Override
    public FlightHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.flight_row, null);

        return new FlightHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightHolder holder, int position) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        holder.dTakeOffLocation.setText(flights.get(position).getTakeOffLocation());
        holder.dTakeOffTime.setText(dateFormat.format(flights.get(position).getTakeOffDateTime()));

        holder.dLandingLocation.setText(flights.get(position).getLandingLocation());
        holder.dLandingTime.setText(dateFormat.format(flights.get(position).getLandingDateTime()));

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Intent intent = new Intent(c, ReservationActivityStep2.class);
                intent.putExtra("iReturnFlightId", flights.get(position).getFlightId());
                intent.putExtra("iFlightId", flightId);
                intent.putExtra("iClient", client);
                intent.putExtra("iFlightInfo", info);
                c.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return flights.size();
    }
}
