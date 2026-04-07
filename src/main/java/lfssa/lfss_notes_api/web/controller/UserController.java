package lfssa.lfss_notes_api.web.controller;

import lfssa.lfss_notes_api.application.user.dto.CreateUserCommand;
import lfssa.lfss_notes_api.application.user.dto.UserOutput;
import lfssa.lfss_notes_api.application.user.port.in.CreateUserUseCase;
import lfssa.lfss_notes_api.web.WebMapper;
import lfssa.lfss_notes_api.web.dto.CreateUserRequest;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/users")
@RestController
public class UserController {
    private final CreateUserUseCase createUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    @PostMapping
    public UserOutput createUser(@RequestBody CreateUserRequest request) {
        CreateUserCommand command = WebMapper.createRequestToCommand(request);
        return createUserUseCase.createUser(command);
    }

}
