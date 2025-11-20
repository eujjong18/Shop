package com.devcourse.shop.seller.application;

import com.devcourse.shop.common.ResponseEntity;
import com.devcourse.shop.seller.application.dto.SellerCommand;
import com.devcourse.shop.seller.application.dto.SellerInfo;
import com.devcourse.shop.seller.domain.Seller;
import com.devcourse.shop.seller.domain.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    public ResponseEntity<List<SellerInfo>> findAll(Pageable pageable){
        Page<Seller> page = sellerRepository.findAll(pageable);
        List<SellerInfo> sellers = page.stream()
                .map(SellerInfo::from)
                .toList();
        return new ResponseEntity<>(HttpStatus.OK.value(), sellers, page.getTotalElements());
    }
    public ResponseEntity<SellerInfo> create(SellerCommand command){
        Seller seller = Seller.register(
                command.companyName(),
                command.representativeName(),
                command.email(),
                command.phone(),
                command.businessNumber(),
                command.address(),
                command.status()
        );
        Seller saved = sellerRepository.save(seller);
        return new ResponseEntity<>(HttpStatus.CREATED.value(), SellerInfo.from(saved), 1);
    }

    public ResponseEntity<SellerInfo> update(UUID id, SellerCommand command){
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Seller not found: " + id));

        seller.update(
                command.companyName(),
                command.representativeName(),
                command.email(),
                command.phone(),
                command.businessNumber(),
                command.address(),
                command.status()
        );

        Seller updated = sellerRepository.save(seller);
        return new ResponseEntity<>(HttpStatus.OK.value(), SellerInfo.from(updated), 1);
    }

    public ResponseEntity<Void> delete(UUID id){
        sellerRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT.value(), null, 0);
    }
}
