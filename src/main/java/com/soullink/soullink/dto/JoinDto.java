package com.soullink.soullink.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class JoinDto {
    private String username;
    private String password;
}
