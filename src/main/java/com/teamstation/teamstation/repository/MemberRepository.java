package com.teamstation.teamstation.repository;

import com.teamstation.teamstation.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
