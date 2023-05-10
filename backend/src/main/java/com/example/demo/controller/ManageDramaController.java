package com.example.demo.controller;

import com.example.demo.model.Drama;
import com.example.demo.dto.DramaRequestBody;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(("/user"))
public class ManageDramaController {

    final private UserService userService;

    public ManageDramaController(
            @Autowired UserService userService
    ){
        this.userService = userService;
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> saveDrama(@RequestBody DramaRequestBody body){
        DramaRequestBody res = userService.saveDrama(body.getProvider(), body.getName());
        if (Objects.nonNull(res))
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    String.format("{ provider: %s, name: %s } not found", body.getProvider(), body.getName()));
    }

    @DeleteMapping("/remove")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeDrama(@RequestBody DramaRequestBody body){
        userService.removeDrama(body.getProvider(), body.getName());
    }

    @GetMapping("/record")
    public List<Drama> getAllRecord(){
        return userService.getRecord();
    }
}
