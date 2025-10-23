package com.example.notebook.service;

import com.example.notebook.domain.Note;
import com.example.notebook.repository.NoteRepository;
import com.example.notebook.web.model.NoteForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Transactional(readOnly = true)
    public List<Note> fetchAllOrdered() {
        return noteRepository.findAllOrderByUpdatedAtDesc();
    }

    @Transactional(readOnly = true)
    public Optional<Note> findById(Long id) {
        return noteRepository.findById(id);
    }

    public Note create(NoteForm form) {
        Note note = new Note();
        note.setTitle(form.getTitle());
        note.setContent(form.getContent());
        return noteRepository.save(note);
    }

    public Optional<Note> update(Long id, NoteForm form) {
        return noteRepository.findById(id).map(existing -> {
            existing.setTitle(form.getTitle());
            existing.setContent(form.getContent());
            return noteRepository.save(existing);
        });
    }

    public boolean delete(Long id) {
        return noteRepository.findById(id).map(note -> {
            noteRepository.delete(note);
            return true;
        }).orElse(false);
    }
}
