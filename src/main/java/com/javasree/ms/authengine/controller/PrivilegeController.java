package com.javasree.ms.authengine.controller;

import com.javasree.ms.authengine.model.dto.PrivilegeDto;
import com.javasree.ms.authengine.service.PrivilegeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/privilege")
public class PrivilegeController {

    @Autowired
    private PrivilegeService privilageService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private Environment environment;

    @GetMapping(path="/check-api", produces = "application/json")
    public String status(){
        return "API is working fine on Port:"+environment.getProperty("local.server.port");
    }

    @PostMapping(consumes =MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PrivilegeDto> createPrivilege(@Valid @RequestBody PrivilegeDto requestPrivilegeDto){
        PrivilegeDto privilegeDto = privilageService.createPrivilege(requestPrivilegeDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(privilegeDto);
    }
}
