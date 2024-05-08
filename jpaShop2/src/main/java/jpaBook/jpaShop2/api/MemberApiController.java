package jpaBook.jpaShop2.api;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jpaBook.jpaShop2.domain.Member;
import jpaBook.jpaShop2.domain.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

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

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse editMemberV2(@RequestBody @Valid UpdateMemberRequest request,
                                             @PathVariable Long id) {
        Member member = memberService.findOne(id);
        member.setName(request.name);
        UpdateMemberResponse updateMemberResponse = new UpdateMemberResponse(member.getId(), member.getName());
        return updateMemberResponse;
    }

    @GetMapping("/api/v2/members")
    public Result getMembers() {
        List<Member> members = memberService.findMembers();
        Stream<GetMembers> getMembersStream = members.stream().map(member -> new GetMembers(member.getName()));
        List<GetMembers> list = getMembersStream.toList();
        Result result = new Result(list.size(),list);
        return result;
    }

    @Data
    static class UpdateMemberRequest {
        @NotEmpty
        private String name;
    }


    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class GetMembers {
        @NotEmpty
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {


        private Long id;
        private String name;
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
