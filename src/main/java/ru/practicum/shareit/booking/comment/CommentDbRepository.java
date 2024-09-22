package ru.practicum.shareit.booking.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentDbRepository extends JpaRepository<Comment, Long> {
    Comment save(Comment comment);

    List<Comment> findAllCommentByItemId(Long itemId);
}
