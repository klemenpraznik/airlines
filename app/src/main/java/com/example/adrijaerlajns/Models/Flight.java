package com.example.adrijaerlajns.Models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
    private Integer flightId;
    private String flightCode;

    private String takeOffLocation;
    private Date takeOffDateTime;

    private String landingLocation;
    private Date landingDateTime;

    private double economyClassPrice;
    private double businessClassPrice;
    private double firstClassPrice;
}
