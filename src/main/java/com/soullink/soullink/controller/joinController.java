package com.soullink.soullink.controller;

import com.soullink.soullink.dto.JoinDto;
import com.soullink.soullink.service.JoinService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class joinController {
    private JoinService joinService;

    public joinController(JoinService joinService) {
        this.joinService = joinService;
    }

    @GetMapping("/join")
    public String join() {
        return "join";
    }

    @PostMapping("/joinProc")
    public String joinProcess(JoinDto joinDto) {
        joinService.joinProc(joinDto);
        return "redirect:/login";
    }
}

