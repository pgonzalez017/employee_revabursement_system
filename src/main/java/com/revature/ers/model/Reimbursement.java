package com.revature.ers.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reimbursements")
public class Reimbursement {
    @Id
    @GeneratedValue
    private int id;
    private double amount;
    private Timestamp submitted;
    private Timestamp resolved;
    private String description;
    private String receiptURI;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resolver_id")
    private User resolver;

    @JoinColumn(name = "reimbursement_status_id")
    @Enumerated(EnumType.STRING)
    private ReimbursementStatus reimbursementStatus;

    @JoinColumn(name = "reimbursement_type_id")
    @Enumerated(EnumType.STRING)
    private ReimbursementType reimbursementType;

    public Reimbursement(int id, double amount, String description,
                         ReimbursementStatus reimbursementStatus, ReimbursementType reimbursementType) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.reimbursementStatus = reimbursementStatus;
        this.reimbursementType = reimbursementType;
    }
}
