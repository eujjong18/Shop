package com.devcourse.shop.service;

import com.devcourse.shop.common.ResponseEntity;
import com.devcourse.shop.member.Member;
import com.devcourse.shop.member.MemberRepository;
import com.devcourse.shop.member.MemberRequest;
import com.devcourse.shop.member.MemberResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public ResponseEntity<List<MemberResponse>> findAll(){
        List<Member> all = memberRepository.findAll();
        List<MemberResponse> responseList = all.stream()
                .map(MemberResponse::from)
                .collect(Collectors.toList());

        return new ResponseEntity<>(HttpStatus.OK.value(), responseList, responseList.size()); //memberRepository.count()
    }

    public ResponseEntity<MemberResponse> create(MemberRequest request){
        Member member = new Member(
                UUID.randomUUID(),
                request.email(),
                request.name(),
                request.password(),
                request.phone(),
                request.saltKey(),
                request.flag()
        );
        Member saved = memberRepository.save(member);
        return new ResponseEntity<>(HttpStatus.CREATED.value(), MemberResponse.from(saved), 1);
    }

    public ResponseEntity<MemberResponse> update(MemberRequest request, String id){
        UUID uuid = UUID.fromString(id);
        Member member = memberRepository.findById(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + id));

        member.setEmail(request.email());
        member.setName(request.name());
        member.setPassword(request.password());
        member.setPhone(request.phone());
        member.setSaltKey(request.saltKey());
        member.setFlag(request.flag());

        Member updated = memberRepository.save(member);
        return new ResponseEntity<>(HttpStatus.OK.value(), MemberResponse.from(updated), 1);
    }

    public ResponseEntity<Void> delete(String id){
        memberRepository.deleteById(UUID.fromString(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT.value(), null, 0);
    }

}
