package com.revature.ers.service;

import com.revature.ers.dto.ReimbursementDTO;
import com.revature.ers.dto.UserDTO;
import com.revature.ers.model.Reimbursement;
import com.revature.ers.model.ReimbursementStatus;
import com.revature.ers.model.User;
import com.revature.ers.repository.ReimbursementRepository;

import com.revature.ers.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Locale;

@Service
public class ReimbursementService {

    private final ReimbursementRepository reimbursementRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public ReimbursementService(ReimbursementRepository reimbursementRepository, UserService userService,
                                ModelMapper modelMapper, UserRepository userRepository){
        this.reimbursementRepository = reimbursementRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public Page<ReimbursementDTO> getAllReimbursements(int page, int offset, String sortBy, String order){
        page = pageValidation(page);
        sortBy = sortByValidation(sortBy);
        offset = offsetValidation(offset);

        Page<Reimbursement> reimbursements;

        if(order.equalsIgnoreCase("DESC"))
            reimbursements = reimbursementRepository.findAll(PageRequest.of(page, offset, Sort.by(sortBy).descending()));
        else
            reimbursements = reimbursementRepository.findAll(PageRequest.of(page, offset, Sort.by(sortBy).ascending()));
        return reimbursements.map((reimbursement -> modelMapper.map(reimbursement, ReimbursementDTO.class)));
    }

    public ReimbursementDTO getReimbursementById(int id){
        return modelMapper.map(reimbursementRepository.findById(id).orElse(null),ReimbursementDTO.class);
    }

    public ReimbursementDTO createReimbursement(ReimbursementDTO reimbursementDTO){
        Reimbursement reimbursement = ReimbursementDTO.dtoToReimbursement().apply(reimbursementDTO);

        reimbursement.setAuthor(userRepository.findByUsername(reimbursementDTO.getAuthorUsername()));

        reimbursement.setSubmitted(new Timestamp(new Date().getTime()));

        return modelMapper.map(reimbursementRepository.save(reimbursement), ReimbursementDTO.class);
    }

    public ReimbursementDTO updateReimbursement(ReimbursementDTO reimbursementDTO){
        Reimbursement reimbursement = reimbursementRepository.getById(reimbursementDTO.getId());

        reimbursement.setResolved(new Timestamp(new Date().getTime()));
        reimbursement.setReimbursementStatus(
                ReimbursementStatus.getByName(reimbursementDTO.getReimbursementStatus()));
        reimbursement.setResolver(userRepository.findByUsername(reimbursementDTO.getResolverUsername()));
        reimbursement.setReceiptURI("/reimbursements/" + reimbursement.getId());

        return modelMapper.map(reimbursementRepository.save(reimbursement), ReimbursementDTO.class);
    }

    private String sortByValidation(String sort){
        switch(sort.toLowerCase(Locale.ROOT)){
            case "amount":
                return "amount";
            case "submitted":
                return "submitted";
            case "resolved":
                return "resolved";
            case "author":
                return "author";
            case "resolver":
                return "resolver";
            case "reimbursementStatus":
                return "reimbursementStatus";
            case "reimbursementType":
                return "reimbursementType";
            default:
                return "id";
        }
    }

    private int pageValidation(int page){
        if(page < 0)
            page = 0;
        return page;
    }

    private int offsetValidation(int offset){
        if(offset != 5 && offset != 10  && offset != 25 && offset != 50){
            offset = 25;
        }
        return offset;
    }
}
