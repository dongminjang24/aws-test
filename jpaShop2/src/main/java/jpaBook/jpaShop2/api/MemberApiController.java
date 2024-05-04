package jpaBook.jpaShop2.api;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jpaBook.jpaShop2.domain.Member;
import jpaBook.jpaShop2.domain.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    // 엔티티를 직접 넣는 것은 안된다!!!
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @Data
    static class CreateMemberRequest {
        @NotEmpty
        private String name;
    }

        @Data
        static class CreateMemberResponse {
            private Long id;

            public CreateMemberResponse(Long id) {
                this.id = id;
            }
        }
    }
