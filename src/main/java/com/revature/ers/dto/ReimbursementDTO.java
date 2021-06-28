package com.revature.ers.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.revature.ers.model.Reimbursement;
import com.revature.ers.model.ReimbursementStatus;
import com.revature.ers.model.ReimbursementType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Timestamp;
import java.util.function.Function;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReimbursementDTO {

    private int id;
    private double amount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.sss")
    private Timestamp submitted;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.sss")
    private Timestamp resolved;
    private String description;
    private String receiptURI;
    private String authorUsername;
    private String resolverUsername;
    private String reimbursementStatus;
    private String reimbursementType;

    public ReimbursementDTO(int id, double amount, Timestamp submitted, String description,
                            String author, String reimbursementStatus, String reimbursementType) {
        this.id = id;
        this.amount = amount;
        this.submitted = submitted;
        this.description = description;
        this.authorUsername = author;
        this.reimbursementStatus = reimbursementStatus;
        this.reimbursementType = reimbursementType;
    }

    public static Function<Reimbursement, ReimbursementDTO> reimbursementToDTO(){
        return reimbursement -> {
            if(reimbursement == null){
                throw new IllegalArgumentException("Parameter [reimbursement] cannot be null.");
            }
            return new ReimbursementDTO(
                    reimbursement.getId(),
                    reimbursement.getAmount(),
                    reimbursement.getSubmitted(),
                    reimbursement.getResolved(),
                    reimbursement.getDescription(),
                    reimbursement.getReceiptURI(),
                    reimbursement.getAuthor().getUsername(),
                    reimbursement.getResolver().getUsername(),
                    reimbursement.getReimbursementStatus().toString(),
                    reimbursement.getReimbursementType().toString()
            );
        };
    }

    public static Function<Reimbursement, ReimbursementDTO> newReimbursementToDTO(){
        return reimbursement -> {
            if(reimbursement == null){
                throw new IllegalArgumentException("Parameter [reimbursement] cannot be null.");
            }
            return new ReimbursementDTO(
                    reimbursement.getId(),
                    reimbursement.getAmount(),
                    reimbursement.getSubmitted(),
                    reimbursement.getDescription(),
                    reimbursement.getAuthor().getUsername(),
                    reimbursement.getReimbursementStatus().toString(),
                    reimbursement.getReimbursementType().toString()
            );
        };
    }

    public static Function<ReimbursementDTO, Reimbursement> dtoToReimbursement(){
        return reimbursementDTO -> {
            if(reimbursementDTO == null){
                throw new IllegalArgumentException("Parameter [reimbursementDTO} cannot be null");
            }
            return new Reimbursement(
                    reimbursementDTO.getId(),
                    reimbursementDTO.getAmount(),
                    reimbursementDTO.getDescription(),
                    ReimbursementStatus.getByName(reimbursementDTO.getReimbursementStatus()),
                    ReimbursementType.getByName(reimbursementDTO.getReimbursementType())
            );
        };
    }
}
