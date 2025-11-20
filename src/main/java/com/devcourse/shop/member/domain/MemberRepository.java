package com.devcourse.shop.member.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepository {
    Page<Member> findAll(Pageable pageable);

    Optional<Member> findById(UUID id);

    Member save(Member member);

    void deleteById(UUID id);
}
