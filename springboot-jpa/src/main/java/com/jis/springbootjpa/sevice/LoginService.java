package com.jis.springbootjpa.sevice;


import com.jis.springbootjpa.domain.Member;
import com.jis.springbootjpa.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {
    
    private final MemberRepository memberRepository;
    
    public Member login(String loginId, String password){
        Optional<Member> memberOptional = memberRepository.findByLoginId(loginId);
        return memberOptional.filter(s->s.getPassword().equals(password))
                .orElse(null);

    }
}
