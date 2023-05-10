package com.example.demo.controller;

import com.example.demo.model.Drama;
import com.example.demo.dto.DramaRequestBody;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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

    @PostMapping(value = "/save")
    @ResponseBody
    public void saveDrama(@RequestBody DramaRequestBody body, HttpServletResponse response) throws IOException {
        String res = userService.saveDrama(body.getProvider(), body.getName());
        response.setContentType("text/plain;charset=UTF-8");
        response.setStatus(HttpStatus.CREATED.value());
        response.getWriter().write(String.format("{ provider: %s, name: %s } %s", body.getProvider(), body.getName(), res));
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
