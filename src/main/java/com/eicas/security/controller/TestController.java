package com.eicas.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author osnudt
 * @since 2022/4/26
 */

@RestController
public class TestController {
    @GetMapping("/test/e01")
    @PreAuthorize("hasAuthority('test:create')")
    public String test01() {
        return "test01";
    }
}
