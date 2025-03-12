package org.example._10_sroun_davit_spring_homework001.Controller;


import org.example._10_sroun_davit_spring_homework001.Model.Status;
import org.example._10_sroun_davit_spring_homework001.Model.Ticket;
import org.example._10_sroun_davit_spring_homework001.Model.TicketNoId;
import org.example._10_sroun_davit_spring_homework001.Model.UpdatePaymentStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    ArrayList<Ticket> tk= new ArrayList<>(Arrays.asList(
            new Ticket("Davit","2024-12-21","Station A","Station B",100,false,Status.BOOKED,"B2"),
            new Ticket("Minh","2024-10-11","Station C","Station D",50,true,Status.CANCELLED,"B4"),
            new Ticket("Sa","2024-02-20","Station E","Station F",70,false,Status.BOOKED,"B1"),
            new Ticket("Nana","2024-03-01","Station G","Station H",80,true,Status.COMPLETED,"B3")
    ));


    @GetMapping()
    public ArrayList<Ticket> getAll(){
        return tk;
    }

    @PostMapping  ("/bulk")
    public ArrayList<Ticket> create(@RequestBody ArrayList<TicketNoId> tkN){
        ArrayList<Ticket> tkNew= new ArrayList<>();
        for(TicketNoId tk: tkN){
            tkNew.add(new Ticket(tk.getPassengerName(), tk.getTravelDate(),tk.getSourceStation(), tk.getDestinationStation(), tk.getPrice(), tk.isPaymentStatus(),tk.getTicketStatus(),tk.getSeatNumber()));
        }
        tk.addAll(tkNew);
        return tk;
    }
    @GetMapping("/{ticket-id}")
    public Optional<Ticket> GetById(@RequestParam int ticket_id ){
      Optional<Ticket > b = tk.stream().filter(w->w.getTicketId() == ticket_id).findFirst();
        return b;
    }


    @PutMapping("/{ticket-id}")
    public ArrayList<Ticket>  Update( @RequestParam int id, @RequestBody TicketNoId tkn){
        for (  Ticket t: tk){
            if (t.getTicketId() == id){
                t.setPrice(tkn.getPrice());
                t.setPassengerName(tkn.getPassengerName());
                t.setTicketStatus(tkn.getTicketStatus());
                t.setPaymentStatus(tkn.isPaymentStatus());
                t.setTravelDate(tkn.getTravelDate());
                t.setDestinationStation(tkn.getDestinationStation());
                t.setSeatNumber(tkn.getSeatNumber());

            }
        }
        return tk;
    }

    @PostMapping("/tickets")
    public ResponseEntity<ArrayList<Ticket>> addOne(@RequestBody TicketNoId tkn){
        System.out.println("crate"+ tkn);
        tk.add(new Ticket(tkn.getPassengerName(), tkn.getTravelDate(),tkn.getSourceStation(), tkn.getDestinationStation(), tkn.getPrice(), tkn.isPaymentStatus(),tkn.getTicketStatus(),tkn.getSeatNumber()));
            return ResponseEntity.status(201).body(tk);
    }

    @PutMapping("/tickets")
    public ArrayList<Ticket> upDatePayment(@RequestBody UpdatePaymentStatus status ){
    for (Ticket t: tk){
        for ( int a:  status.getTicketIds()){
           if (t.getTicketId() == a){
               t.setPaymentStatus(status.getPaymentstatus());
           }
        }
    }
        return tk ;
    }


    @GetMapping("/search")
    public ResponseEntity<Ticket> serachByName(@RequestParam String name){
        for (Ticket t: tk){
            if (t.getPassengerName().equals(name)){
                return  ResponseEntity.status(201).body(t);
            }
        }
        return  null;
    }

    @GetMapping("/filter")
    public ResponseEntity<ArrayList<Ticket>> filter(@RequestParam Status status, @RequestParam String travel_Date){
        ArrayList<Ticket> tickets = new ArrayList<>();
        for (Ticket t: tk){
            if (t.getTicketStatus().equals(status) && t.getTravelDate().equals(travel_Date)){
                tickets.add(t);
                System.out.println("success");

            }
        }
        return ResponseEntity.status(201).body(tickets);
    }

    @DeleteMapping("/{ticket-id}")
    public ArrayList<Ticket> delete(@RequestParam int id){
        for (Ticket t: tk) {
            if (t.getTicketId()== id){
                tk.remove(t);
            }
        }
        return tk;
    }


}
