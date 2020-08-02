package service;

import dto.BoardDTO;
import dto.CommentDTO;

import java.util.List;

public interface CommentService {
    // Create comment
    public void insertComment(CommentDTO commentDTO);

    // Update comment
    public void updateComment(CommentDTO commentDTO);

    // Delete Comment
    public void deleteComment(CommentDTO commentDTO);

    // Get comment list
    public List<CommentDTO> getCommentList(CommentDTO commentDTO);

    // Get board list by search relating to comment
    public List<BoardDTO> getContainCommentBoardList(CommentDTO commentDTO);
    public List<BoardDTO> getContainCommentWriterBoardList(CommentDTO commentDTO);
}
