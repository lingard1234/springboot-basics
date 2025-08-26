package com.example.firstproject.controller;

import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Slf4j  // 로깅 기능을 위한 어노테이션 추가
@Controller
public class MemberController {
    @Autowired
    private MemberRepository memberRepository;
    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping("/signup")
    public String newMemberForm() {
        return "Members/new";
    }

    @GetMapping("/members/{id}") // 데이터 조회 요청 접수
    public String show(@PathVariable Long id, Model model) {
        log.info("id: " + id);
        // 1. id를 조회해 데이터 가져오기
        Member memberEntity = memberRepository.findById(id).orElse(null);
        // 2. 모델에 데이터 등록하기
        model.addAttribute("member", memberEntity);
        // 3. 뷰 페이지 반환하기
        return "Members/show";
    }

    @GetMapping("/members")
    public String index(Model model) {
        ArrayList<Member> memberList = memberRepository.findAll();
        model.addAttribute("memberList", memberList);
        return "Members/index"; //
    }

    @GetMapping("members/new")
    public String newMembersForm() {
        return "Members/new";
    }

    @GetMapping("members/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        // 수정 데이터 가져오기
        Member memberEntity = memberRepository.findById(id).orElse(null);
        // 데이터 모델에 등록하기
        model.addAttribute("member", memberEntity);
        return "Members/edit";
    }

    @GetMapping("members/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        Member target = memberRepository.findById(id).orElse(null);
        if (target != null) {
            memberRepository.delete(target);
            rttr.addFlashAttribute("msg","삭제됐습니다!");
        }
        log.info(target.toString());
        return "redirect:/members";
    }

    @PostMapping("members/update")
    public String update(MemberForm form) {
        log.info(form.toString());
        // dto를 엔티티로 변환하기
        Member memberEntity = form.toEntity();
        // 엔티티를 데이터로 저장하기
        Member target = memberRepository.findById(memberEntity.getId()).orElse(null);
        // 데이터를 가져와서 기존 데이터를 저장 후
        if (target != null) {
            memberRepository.save(memberEntity);
        }
        // 수정 결과 페이지로 리다이렉트하기
        return "redirect:/members/" + memberEntity.getId();
    }

    @PostMapping("/join")
    public String createMember(MemberForm form) {
        log.info(form.toString());
        //System.out.println(form.toString());
        // 1. DTO를 엔티티로 변환
        Member member= form.toEntity();
        log.info(member.toString());
        //System.out.println(article.toString());
        // 2. 리파지터리로 엔티티를 DB로 저장
        Member saved = memberRepository.save(member);
        log.info(saved.toString());
        //System.out.println(saved.toString());
        return "redirect:/members/" + saved.getId();
    }
}

