package com.revature.ers.controller;


import com.revature.ers.dto.ReimbursementDTO;
import com.revature.ers.service.ReimbursementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/reimbursements")
public class ReimbursementController {

    private final ReimbursementService reimbursementService;

    @Autowired
    public ReimbursementController(ReimbursementService reimbursementService){
        this.reimbursementService = reimbursementService;
    }

    @GetMapping
    public Page<ReimbursementDTO> getAllReimbursements(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                       @RequestParam(value = "offset", required = false, defaultValue = "25") int offset,
                                                       @RequestParam(value = "sort", required = false, defaultValue = "id") String sortBy,
                                                       @RequestParam(value = "order", required = false, defaultValue = "ASC") String order){
        return reimbursementService.getAllReimbursements(page, offset, sortBy, order);
    }

    @GetMapping("/{id}")
    public ReimbursementDTO getByReimbursementId(@PathVariable(name = "id") String id){
        return reimbursementService.getReimbursementById(Integer.parseInt(id));
    }

    @PostMapping
    public ReimbursementDTO createReimbursement(@RequestBody ReimbursementDTO reimbursement){
        return reimbursementService.createReimbursement(reimbursement);
    }

    @PostMapping("/resolve")
    public ReimbursementDTO updateReimbursement(@RequestBody ReimbursementDTO reimbursement){
        return reimbursementService.updateReimbursement(reimbursement);
    }
}
