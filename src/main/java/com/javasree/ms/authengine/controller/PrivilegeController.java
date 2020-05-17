package com.javasree.ms.authengine.controller;

import com.javasree.ms.authengine.model.dto.PrivilegeDto;
import com.javasree.ms.authengine.model.dto.UserDto;
import com.javasree.ms.authengine.model.vo.UserResponseVO;
import com.javasree.ms.authengine.model.vo.UserVO;
import com.javasree.ms.authengine.service.PrivilegeService;
import com.javasree.ms.authengine.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/privilege")
public class PrivilegeController {

    @Autowired
    private PrivilegeService privilageService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(path="/check-api", produces = "application/json")
    public String status(){
        return "API is working fine";
    }

    @PostMapping(consumes =MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PrivilegeDto> createPrivilege(@RequestBody PrivilegeDto requestPrivilegeDto){
        PrivilegeDto privilegeDto = privilageService.createPrivilege(requestPrivilegeDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(privilegeDto);
    }
}
