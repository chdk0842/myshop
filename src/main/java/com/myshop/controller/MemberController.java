package com.myshop.controller;

import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myshop.dto.MemberFormDto;
import com.myshop.entity.Member;
import com.myshop.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;

	//회원가입 화면
	@GetMapping(value = "/new")
	public String memberForm(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "member/memberForm";
	}

	//회원가입 버튼을 눌렀을 때 실행되는 메소드
	@PostMapping(value = "/new")
	public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
         //@Valid : 유효성을 검증하려는 객체 앞에 붙인다.
		 //BindingResult : 유효성 검증 후에 결과를 넣어준다.
		
		 //에러가 있다면 회원가입 페이지로 이동
		 if(bindingResult.hasErrors()) {
			 return "member/memberForm";
		 }
			 
		 try {
			 Member member = Member.createMember(memberFormDto, passwordEncoder);
		     memberService.saveMember(member);
		} catch (IllegalStateException e) {
		     model.addAttribute("errorMessage", e.getMessage());
		     return "member/memberForm";
		}
	     
	     return "redirect:/";
	}

}
