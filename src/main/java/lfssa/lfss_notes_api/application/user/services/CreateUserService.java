package lfssa.lfss_notes_api.application.user.services;

import lfssa.lfss_notes_api.application.AppMapper;
import lfssa.lfss_notes_api.application.user.dto.CreateUserCommand;
import lfssa.lfss_notes_api.application.user.dto.UserOutput;
import lfssa.lfss_notes_api.application.user.port.in.CreateUserUseCase;
import lfssa.lfss_notes_api.application.user.port.out.SaveUser;
import lfssa.lfss_notes_api.domain.entity.User;
import org.springframework.stereotype.Service;

@Service
public class CreateUserService implements CreateUserUseCase {
    public final SaveUser repo;

    public CreateUserService(SaveUser repo) {
        this.repo = repo;
    }

    @Override
    public UserOutput createUser(CreateUserCommand user) {
        String passwordHash = user.password();
        User newUser = new User(null, user.name(), user.email(), passwordHash, null);
        return AppMapper.userToOutput(repo.save(newUser));
    }

}
