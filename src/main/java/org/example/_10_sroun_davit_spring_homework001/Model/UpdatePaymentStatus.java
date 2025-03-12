package org.example._10_sroun_davit_spring_homework001.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePaymentStatus {
    private ArrayList<Integer> ticketIds;
    private  Boolean Paymentstatus;
}
