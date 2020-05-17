package com.javasree.ms.authengine.controller;

import com.javasree.ms.authengine.model.dto.UserDto;
import com.javasree.ms.authengine.model.vo.UserResponseVO;
import com.javasree.ms.authengine.model.vo.UserVO;
import com.javasree.ms.authengine.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService usersService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(path="/check-api", produces = "application/json")
    public String status(){
        return "API is working fine";
    }

    @PostMapping(consumes =MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseVO> createUser(@RequestBody UserVO userVo){

        UserDto user = modelMapper.map(userVo, UserDto.class);
        UserDto userDto = usersService.createUser(user);
        UserResponseVO userResponseVO = modelMapper.map(userDto,UserResponseVO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseVO);
    }

    @PutMapping(value="{userId}", consumes =MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseVO> updateUser(@PathVariable String userId, @RequestBody UserVO userVo ){

        UserDto user = modelMapper.map(userVo, UserDto.class);
        user.setUserId(userId);
        if(userVo.getRoles()!=null && user.getRoles()==null){
            user.setRoles(userVo.getRoles());
        }
        UserDto userDto = usersService.updateUser(user);
        UserResponseVO userResponseVO = modelMapper.map(userDto,UserResponseVO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseVO);
    }
}
