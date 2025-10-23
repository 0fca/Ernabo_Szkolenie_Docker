package com.example.notebook.repository;

import com.example.notebook.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    @Query("select n from Note n order by n.updatedAt desc")
    List<Note> findAllOrderByUpdatedAtDesc();
}
