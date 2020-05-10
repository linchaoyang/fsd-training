package fsd.msservice.auth.api.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    
    // @PostMapping()
    // public Person addHero(@RequestBody Person hero) {
    //     System.out.println(hero.toString());
    //     return service.saveHero(hero);
    // }
}