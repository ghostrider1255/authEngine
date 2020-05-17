package com.javasree.ms.authengine.controller;

import com.javasree.ms.authengine.model.dto.RoleDto;
import com.javasree.ms.authengine.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired private ModelMapper modelMapper;

    @GetMapping(path="/check-api", produces = "application/json")
    public String status(){
        return "API is working fine";
    }

    @PostMapping(consumes =MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto requestRoleDto){

        RoleDto roleDto = roleService.createRole(requestRoleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(roleDto);
    }
}
