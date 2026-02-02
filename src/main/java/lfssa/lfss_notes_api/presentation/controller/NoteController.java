package lfssa.lfss_notes_api.presentation.controller;

import jakarta.validation.Valid;
import lfssa.lfss_notes_api.application.dto.CreateNoteCommand;
import lfssa.lfss_notes_api.application.dto.NoteOutput;
import lfssa.lfss_notes_api.application.exception.UserNotFoundException;
import lfssa.lfss_notes_api.application.port.in.CreateNoteUseCase;
import lfssa.lfss_notes_api.application.port.in.ListNotesByUserUseCase;
import lfssa.lfss_notes_api.presentation.dto.CreateNoteRequest;
import lfssa.lfss_notes_api.presentation.dto.NoteResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for notes (1:N with users). Depends only on application DTOs.
 */
@RestController
@RequestMapping("/api/users/{userId}/notes")
public class NoteController {

    private final CreateNoteUseCase createNoteUseCase;
    private final ListNotesByUserUseCase listNotesByUserUseCase;

    public NoteController(CreateNoteUseCase createNoteUseCase, ListNotesByUserUseCase listNotesByUserUseCase) {
        this.createNoteUseCase = createNoteUseCase;
        this.listNotesByUserUseCase = listNotesByUserUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NoteResponse createNote(
            @PathVariable UUID userId,
            @Valid @RequestBody CreateNoteRequest request
    ) {
        CreateNoteCommand command = new CreateNoteCommand(userId, request.title(), request.content());
        NoteOutput output = createNoteUseCase.create(command);
        return toResponse(output);
    }

    @GetMapping
    public List<NoteResponse> listNotes(@PathVariable UUID userId) {
        List<NoteOutput> outputs = listNotesByUserUseCase.listByUserId(userId);
        return outputs.stream().map(NoteController::toResponse).toList();
    }

    private static NoteResponse toResponse(NoteOutput output) {
        return new NoteResponse(
                output.id(),
                output.userId(),
                output.title(),
                output.content(),
                output.createdAt()
        );
    }
}
