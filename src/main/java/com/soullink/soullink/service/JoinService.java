package com.soullink.soullink.service;

import com.soullink.soullink.domain.UserEntity;
import com.soullink.soullink.dto.JoinDto;
import com.soullink.soullink.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    //이미 회원이 존재하는지 검증하기
    public void joinProc(JoinDto joinDto) {
        boolean exist = userRepository.existsByUsername(joinDto.getUsername());
        if(exist) {
            return;
        }

        UserEntity user = new UserEntity();
        user.setUsername(joinDto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(joinDto.getPassword())); //security config에서 등록한 bean으로 암호화
        user.setRole("ROLE_ADMIN");

        userRepository.save(user);
    }
}
