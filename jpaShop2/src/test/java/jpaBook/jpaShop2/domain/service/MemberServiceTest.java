package jpaBook.jpaShop2.domain.service;

import jakarta.persistence.EntityManager;
import jpaBook.jpaShop2.domain.Member;
import jpaBook.jpaShop2.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @Rollback(false)
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");
        //when
        Long savedId = memberService.join(member);
        entityManager.flush(); //롤백은 되지만,인서트문 보게 하려면 flush를 강제로 해줘야함.

        //then
        Assertions.assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim1");

        Member member2 = new Member();
        member2.setName("kim1");

        //when
        memberService.join(member);
        Assertions.assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        //then
        // 코드가 오다가 실패할때는 오면 안됨.
//        fail("예외가 발생해야 한다.");

    }

}