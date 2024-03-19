package org.zerock.mallapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.mallapi.domain.Member;

public interface MemberRepository extends JpaRepository<Member, String> {


}
