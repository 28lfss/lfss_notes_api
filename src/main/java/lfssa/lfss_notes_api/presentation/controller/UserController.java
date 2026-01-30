package lfssa.lfss_notes_api.presentation.controller;

import jakarta.validation.Valid;
import lfssa.lfss_notes_api.application.dto.CreateUserCommand;
import lfssa.lfss_notes_api.application.port.in.CreateUserUseCase;
import lfssa.lfss_notes_api.domain.entity.User;
import lfssa.lfss_notes_api.presentation.dto.CreateUserRequest;
import lfssa.lfss_notes_api.presentation.dto.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller: maps HTTP to use case (presentation / interface adapter).
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@Valid @RequestBody CreateUserRequest request) {
        CreateUserCommand command = new CreateUserCommand(request.name(), request.password());
        User user = createUserUseCase.create(command);
        return toResponse(user);
    }

    private static UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getName());
    }
}
