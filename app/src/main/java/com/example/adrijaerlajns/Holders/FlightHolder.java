package com.example.adrijaerlajns.Holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adrijaerlajns.ItemClickListener;
import com.example.adrijaerlajns.R;

public class FlightHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView dTakeOffLocation, dTakeOffTime, dLandingLocation, dLandingTime;
    public ItemClickListener itemClickListener;


    public FlightHolder(@NonNull View view) {
        super(view);
        this.dTakeOffLocation = view.findViewById(R.id.takeOffLocation);
        this.dTakeOffTime = view.findViewById(R.id.takeOffDate);
        this.dLandingLocation = view.findViewById(R.id.landingLocation);
        this.dLandingTime = view.findViewById(R.id.landingTime);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClickListener(v, getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener ic){
        this.itemClickListener = ic;
    }
}
