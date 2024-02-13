package com.project.shop.common.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.shop.member.domain.Member;
import com.project.shop.member.repository.AuthorityRepository;
import com.project.shop.member.repository.MemberRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class ControllerCommon {

    @Autowired
    public MockMvc mockMvc;
    @Autowired
    public PasswordEncoder passwordEncoder;
    public ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    public MemberRepository memberRepository;
    @Autowired
    public AuthorityRepository authorityRepository;
    public Member member1;
    public Member member2;



}
