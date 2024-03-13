package com.example.immediatemeetupbe.domain.member.repository;

import com.example.immediatemeetupbe.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    List<Member> findByEmailContainingOrNameContaining(String emailKeyword, String nameKeyword);

}
