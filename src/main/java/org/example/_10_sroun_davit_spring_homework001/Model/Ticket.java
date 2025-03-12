package org.example._10_sroun_davit_spring_homework001.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
public class Ticket {
    private static int counter = 1;
    private int ticketId;
    private String passengerName;
    private String travelDate;
    private String sourceStation;
    private String destinationStation;
    private double price;
    private boolean paymentStatus;
    private Status ticketStatus;
    private String seatNumber;

    public Ticket(String passengerName, String travelDate, String sourceStation,
                  String destinationStation, double price, boolean paymentStatus,
                  Status ticketStatus, String seatNumber) {
        this.ticketId = counter++;
        this.passengerName = passengerName;
        this.travelDate = travelDate;
        this.sourceStation = sourceStation;
        this.destinationStation = destinationStation;
        this.price = price;
        this.paymentStatus = paymentStatus;
        this.ticketStatus = ticketStatus;
        this.seatNumber = seatNumber;
    }



}
