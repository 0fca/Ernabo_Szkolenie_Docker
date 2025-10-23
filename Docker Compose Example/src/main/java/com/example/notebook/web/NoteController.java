package com.example.notebook.web;

import com.example.notebook.domain.Note;
import com.example.notebook.service.NoteService;
import com.example.notebook.web.model.NoteForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping({"", "/"})
    public String list(Model model) {
        model.addAttribute("notes", noteService.fetchAllOrdered());
        return "list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("noteForm", new NoteForm());
        model.addAttribute("formTitle", "Create Note");
        model.addAttribute("errors", Map.of());
        return "form";
    }

    @PostMapping({"", "/"})
    public String create(@Valid @ModelAttribute("noteForm") NoteForm form,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("formTitle", "Create Note");
            model.addAttribute("errors", extractErrors(bindingResult));
            return "form";
        }
        Note saved = noteService.create(form);
        redirectAttributes.addFlashAttribute("message", "Note '%s' created.".formatted(saved.getTitle()));
        return "redirect:/notes";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Note> noteOpt = noteService.findById(id);
        if (noteOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Note with id %d not found.".formatted(id));
            return "redirect:/notes";
        }
        Note note = noteOpt.get();
        model.addAttribute("noteForm", NoteForm.from(note));
        model.addAttribute("formTitle", "Edit Note");
        model.addAttribute("noteId", note.getId());
        model.addAttribute("errors", Map.of());
        return "form";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("noteForm") NoteForm form,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("formTitle", "Edit Note");
            model.addAttribute("noteId", id);
            model.addAttribute("errors", extractErrors(bindingResult));
            return "form";
        }
        Optional<Note> updated = noteService.update(id, form);
        if (updated.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Note with id %d not found.".formatted(id));
        } else {
            redirectAttributes.addFlashAttribute("message", "Note '%s' updated.".formatted(updated.get().getTitle()));
        }
        return "redirect:/notes";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        boolean deleted = noteService.delete(id);
        if (deleted) {
            redirectAttributes.addFlashAttribute("message", "Note deleted.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Note with id %d not found.".formatted(id));
        }
        return "redirect:/notes";
    }

    private Map<String, String> extractErrors(BindingResult bindingResult) {
        Map<String, String> errors = new LinkedHashMap<>();
        bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }
}
