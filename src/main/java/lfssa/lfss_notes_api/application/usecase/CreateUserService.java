package lfssa.lfss_notes_api.application.usecase;

import lfssa.lfss_notes_api.application.dto.CreateUserCommand;
import lfssa.lfss_notes_api.application.dto.UserOutput;
import lfssa.lfss_notes_api.application.port.in.CreateUserUseCase;
import lfssa.lfss_notes_api.application.port.out.SaveUserPort;
import lfssa.lfss_notes_api.domain.entity.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Use case implementation: creates a user and persists via outbound port.
 * Maps domain User to application UserOutput so presentation stays decoupled from domain.
 */
@Service
public class CreateUserService implements CreateUserUseCase {

    private final SaveUserPort saveUserPort;

    public CreateUserService(SaveUserPort saveUserPort) {
        this.saveUserPort = saveUserPort;
    }

    @Override
    public UserOutput create(CreateUserCommand command) {
        User user = new User(UUID.randomUUID(), command.name(), command.password());
        User saved = saveUserPort.save(user);
        return new UserOutput(saved.getId(), saved.getName());
    }
}
