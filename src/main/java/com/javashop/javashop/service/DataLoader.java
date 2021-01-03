package com.javashop.javashop.service;


import com.javashop.javashop.model.Complaint;
import com.javashop.javashop.model.ComplaintType;
import com.javashop.javashop.model.TaxCategory;
import com.javashop.javashop.repository.TaxCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.javashop.javashop.repository.ComplaintRepository;
import com.javashop.javashop.repository.ComplaintTypeRepository;

import javax.annotation.PostConstruct;
import java.util.Date;

@Service
public class DataLoader {

    @Autowired
    private ComplaintTypeRepository complaintTypeRepository;

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private TaxCategoryRepository taxCategoryRepository;

    @PostConstruct
    public void loadData(){
        ComplaintType complaintType1 = new ComplaintType("Reklamacja");
        ComplaintType complaintType2 = new ComplaintType("Gwarancja");
        ComplaintType complaintType3 = new ComplaintType("Zwrot 14-dniowy");


        TaxCategory taxCategory1 = new TaxCategory("podatek VAT", 23);

//        taxCategoryRepository.save(taxCategory1);
//        Complaint complaint1 = new Complaint(new Date(2020,12,10));
//        Complaint complaint2 = new Complaint(new Date(2020,12,11));
//        Complaint complaint3 = new Complaint(new Date(2020,12,12));
//        complaint1.setComplaintType(complaintType1);
//        complaint2.setComplaintType(complaintType2);
//        complaint3.setComplaintType(complaintType3);
//
//
////        Uncomment when seeds needed
//        complaintTypeRepository.save(complaintType1);
//        complaintTypeRepository.save(complaintType2);
//        complaintTypeRepository.save(complaintType3);
//
//        complaintRepository.save(complaint1);
//        complaintRepository.save(complaint2);
//        complaintRepository.save(complaint3);

    }

}