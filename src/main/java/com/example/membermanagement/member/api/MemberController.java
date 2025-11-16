package com.example.membermanagement.member.api;

import com.example.membermanagement.member.api.request.MemberJoinRequest;
import com.example.membermanagement.member.api.request.MemberLoginRequest;
import com.example.membermanagement.member.api.response.MemberJoinResponse;
import com.example.membermanagement.member.api.response.MemberResponse;
import com.example.membermanagement.member.api.response.Response;
import com.example.membermanagement.member.model.Member;
import com.example.membermanagement.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // 회원가입
    @PostMapping("/join")
    public Response<MemberJoinResponse> join(@RequestBody MemberJoinRequest request) {
        Member member = memberService.join(request.username(), request.password(), request.email());
        return Response.sucess(MemberJoinResponse.fromMember(member));
    }

    // 로그인
    @PostMapping("/login")
    public Response<String> login(@RequestBody MemberLoginRequest request) {
        return Response.sucess(memberService.login(request.username(), request.password()));
    }

    // 단건 조회
    @GetMapping("/{username}")
    public Response<MemberResponse> loadMember(@PathVariable String username) {
        Member member = memberService.loadMemberByUsername(username);
        return Response.sucess(MemberResponse.fromMember(member));
    }

    // 전체 조회
    @GetMapping
    public Response<List<MemberResponse>> loadAllMembers() {
        List<Member> members = memberService.loadAllMembers();
        return Response.sucess(members.stream().map(MemberResponse::fromMember).toList());
    }
}
