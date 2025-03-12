package org.example._10_sroun_davit_spring_homework001.Controller;


import org.apache.tomcat.util.http.parser.HttpParser;
import org.example._10_sroun_davit_spring_homework001.Model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {



    ArrayList<Ticket> tk= new ArrayList<>(Arrays.asList(
            new Ticket("Davit","2024-12-21","Station A","Station B",100,false,Status.BOOKED,"B2"),
            new Ticket("Minh","2024-10-11","Station C","Station D",50,true,Status.CANCELLED,"B4"),
            new Ticket("Sa","2024-02-20","Station E","Station F",70,false,Status.BOOKED,"B1"),
            new Ticket("Nana","2024-03-01","Station G","Station H",80,true,Status.COMPLETED,"B3")
    ));

    String localDateTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    @GetMapping()
    public ResponseEntity<ApiResponse<List<Ticket>>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {


        if (page < 1 || size <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Invalid page or size value", HttpStatus.BAD_REQUEST, null, LocalDateTime.now()));
        }

        int start = (page - 1) * size;
        int end = Math.min(start + size, tk.size());


        if (start >= tk.size()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Page not found", HttpStatus.NOT_FOUND, null, LocalDateTime.now()));
        }

        List<Ticket> paginatedTickets = tk.stream()
                .skip(start)
                .limit(size)
                .collect(Collectors.toList());
        ApiResponse<List<Ticket>> response = new ApiResponse<>(
                true,
                "Tickets retrieved successfully",
                HttpStatus.OK,
                paginatedTickets,
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping  ("/bulk")
    public  ResponseEntity< ApiResponse< ArrayList<Ticket>>> create(@RequestBody ArrayList<TicketNoId> tkN){
        ArrayList<Ticket> tkNew= new ArrayList<>();
        for(TicketNoId tk: tkN){
            tkNew.add(new Ticket(tk.getPassengerName(), tk.getTravelDate(),tk.getSourceStation(), tk.getDestinationStation(), tk.getPrice(), tk.isPaymentStatus(),tk.getTicketStatus(),tk.getSeatNumber()));
        }
        tk.addAll(tkNew);

        if (tkNew.isEmpty()) {
            ApiResponse<ArrayList<Ticket>> response = new ApiResponse<>(
                    false,
                    "Failed to create tickets, invalid data",
                    HttpStatus.NOT_FOUND,
                    LocalDateTime.now()
            );
            return ResponseEntity.status(404).body(response);
        }


        ApiResponse<ArrayList<Ticket>> response = new ApiResponse<>(
                true,
                "Tickets Cannot Created successfully",
                HttpStatus.NOT_FOUND,
                tkNew,
                LocalDateTime.now()
        );

        return ResponseEntity.status(201).body(response);
    }



    @GetMapping("/{ticket-id}")
    public ResponseEntity<ApiResponse< Optional<Ticket>>> GetById(@PathVariable(name = "ticket-id") int ticket_id ){
      Optional<Ticket > b = tk.stream().filter(w->w.getTicketId() == ticket_id).findFirst();
        if (!b.isPresent()) {

            ApiResponse<Optional<Ticket>> response = new ApiResponse<>(
                    false,
                    "No ticket found with ID"+ticket_id,
                    HttpStatus.NOT_FOUND,
                    Optional.empty(),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(404).body(response);
        }
        ApiResponse<Optional<Ticket>> response = new ApiResponse<>(
                true,
                "Tickets retrived successfully",
                HttpStatus.OK,
                b,
                LocalDateTime.now()
        );

        return ResponseEntity.status(201).body(response);
    }





    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Ticket>> serachByName(@RequestParam String name){
        for (Ticket t: tk){
            if (t.getPassengerName().equals(name)){
                ApiResponse<Ticket> response = new ApiResponse<>(
                        true,
                        "Tickets retrived successfully",
                        HttpStatus.OK,
                        t,
                        LocalDateTime.now()
                );

                return ResponseEntity.status(201).body(response);
            }

        }
        ApiResponse response = new ApiResponse<>(
                false,
                "Tickets name "+name+" Not found.",
                HttpStatus.NOT_FOUND,
                LocalDateTime.now()
        );

        return ResponseEntity.status(404).body(response);
    }




    @PutMapping("/{ticket-id}")
    public ResponseEntity<ApiResponse<Ticket>>  update( @RequestParam int id, @RequestBody TicketNoId tkn){

        for (  Ticket t: tk){
            if (t.getTicketId() == id) {
                t.setPrice(tkn.getPrice());
                t.setPassengerName(tkn.getPassengerName());
                t.setSourceStation(tkn.getSourceStation());
                t.setTicketStatus(tkn.getTicketStatus());
                t.setPaymentStatus(tkn.isPaymentStatus());
                t.setTravelDate(tkn.getTravelDate());
                t.setDestinationStation(tkn.getDestinationStation());
                t.setSeatNumber(tkn.getSeatNumber());
                ApiResponse<Ticket> response = new ApiResponse<>(
                        true,
                        "Tickets Updated successfully",
                        HttpStatus.OK,
                        t,
                        LocalDateTime.now()
                );

                return ResponseEntity.status(201).body(response);
            }
        }
        return null;
    }


    @PostMapping("/tickets")
    public ResponseEntity<ApiResponse<ArrayList<Ticket>>> addOne(@RequestBody TicketNoId tkn){
      tk.add(new Ticket(tkn.getPassengerName(), tkn.getTravelDate(),tkn.getSourceStation(), tkn.getDestinationStation(), tkn.getPrice(), tkn.isPaymentStatus(),tkn.getTicketStatus(),tkn.getSeatNumber()));


        ApiResponse<ArrayList<Ticket>> response = new ApiResponse<>(
                true,
                "Tickets  Created successfully",
                HttpStatus.OK,
                tk,
                LocalDateTime.now()
        );

        return ResponseEntity.status(201).body(response);

    }




    @PutMapping("/tickets")
    public ResponseEntity<ApiResponse<ArrayList<Ticket>>> upDatePayment(@RequestBody UpdatePaymentStatus status ){
    ArrayList <Ticket> tickets= new ArrayList<>();
    for (Ticket t: tk){
        for ( int a:  status.getTicketIds()){
           if (t.getTicketId() == a){
               t.setPaymentStatus(status.getPaymentstatus());
               tickets.add(t);
           }
        }
    }
    if (tickets.isEmpty()){
        ApiResponse<ArrayList<Ticket>> response = new ApiResponse<>(
                false,
                "Tickets Cannot Update successfully",
                HttpStatus.NOT_FOUND,
                LocalDateTime.now()
        );

        return ResponseEntity.status(404).body(response);
    }


        ApiResponse<ArrayList<Ticket>> response = new ApiResponse<>(
                true,
                "Tickets Updaate Status successfully",
                HttpStatus.OK,
                tickets,
                LocalDateTime.now()
        );

        return ResponseEntity.status(201).body(response);
    }




    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<ArrayList<Ticket>>> filter(@RequestParam Status status, @RequestParam String travel_Date){
        ArrayList<Ticket> tickets = new ArrayList<>();
        for (Ticket t: tk){
            if (t.getTicketStatus().equals(status) && t.getTravelDate().equals(travel_Date)){
                tickets.add(t);
            }
        }
        if (tickets.isEmpty()){
            ApiResponse<ArrayList<Ticket>> response = new ApiResponse<>(
                    false,
                    "Tickets filter cannot found",
                    HttpStatus.NOT_FOUND,
                    LocalDateTime.now()
            );

            return ResponseEntity.status(404).body(response);
        }

        ApiResponse<ArrayList<Ticket>> response = new ApiResponse<>(
                true,
                "Tickets Filter successfully",
                HttpStatus.OK,
                tickets,
                LocalDateTime.now()
        );

        return ResponseEntity.status(201).body(response);
    }




    @DeleteMapping("/{ticket-id}")
    public ResponseEntity<ApiResponse<Ticket>> delete(@RequestParam int id){
        for (Ticket t: tk) {
            if (t.getTicketId()== id){
                tk.remove(t);
                ApiResponse response = new ApiResponse<>(
                        true,
                        "Tickets id:"+id+" Deleted Successfully",
                        HttpStatus.OK,
                        LocalDateTime.now()
                );

                return ResponseEntity.status(201).body(response);
            }
        }
        ApiResponse response = new ApiResponse<>(
                false,
                "Tickets id "+id+" Not found.",
                HttpStatus.NOT_FOUND,
                LocalDateTime.now()
        );

        return ResponseEntity.status(404).body(response);
    }


}
