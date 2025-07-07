package br.com.bariane.todolist.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/user")

public class UserController {

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity<Object> createUser(@RequestBody UserModel userModel) {

        //Bloco de verificacao de usuario
        var user = this.userRepository.findByUsername(userModel.getUsername());
        if (user != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario ja existe!");
        }

        //Criptografando a senha
        var passHash = BCrypt.withDefaults().hashToString(12, userModel.getPass().toCharArray());
        userModel.setPass(passHash);

        //Metodo JPA para criar usuario
        var userCreated = this.userRepository.save(userModel);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userCreated);

    }
}
