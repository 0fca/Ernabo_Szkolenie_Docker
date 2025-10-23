package com.example.notebook.web.model;

import com.example.notebook.domain.Note;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NoteForm {

    @NotBlank(message = "Title is required")
    @Size(max = 120, message = "Title must be 120 characters or less")
    private String title;

    @NotBlank(message = "Content is required")
    @Size(max = 4000, message = "Content must be 4000 characters or less")
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static NoteForm from(Note note) {
        NoteForm form = new NoteForm();
        form.setTitle(note.getTitle());
        form.setContent(note.getContent());
        return form;
    }
}
