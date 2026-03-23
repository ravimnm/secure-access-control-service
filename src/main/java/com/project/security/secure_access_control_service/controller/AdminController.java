package com.project.security.secure_access_control_service.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/data")
    @PreAuthorize("hasAuthority('READ_USER')")
    public String data() {
        return "Permission secured data";
    }
}