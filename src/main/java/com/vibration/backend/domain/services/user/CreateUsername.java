package com.vibration.backend.domain.services.user;

import com.vibration.backend.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class CreateUsername {

    private static final Random RAND = new Random();

    private static final List<String> NAME_LIST = List.of(
            "Joan", "Jaume", "Marc", "Pere", "Josep", "Manel", "Marti", "Albert", "Antoni", "Estanislau",
            "Ana", "Mireia", "Marta", "Isabel", "Nuria", "Montserrat", "Lluisa", "Maria", "Aida", "Gisela");

    public User getUsername() {
        var name = NAME_LIST.get(RAND.nextInt(NAME_LIST.size()));
        var suffix = String.valueOf(RAND.nextInt(9999));
        var username = name.concat("-").concat(suffix);
        return new User(username);
    }
}
