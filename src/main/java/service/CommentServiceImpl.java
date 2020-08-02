package service;

import dto.BoardDTO;
import dto.CommentDTO;
import org.springframework.stereotype.Service;
import repository.BoardMapper;
import repository.CommentMapper;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Resource(name = "commentMapper")
    private CommentMapper commentMapper;
    @Resource(name = "boardMapper")
    private BoardMapper boardMapper;

    public void insertComment(CommentDTO commentDTO) {
        commentMapper.insertComment(commentDTO);

        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setNumber(commentDTO.getBoardNumber());
        boardDTO = boardMapper.getBoard(boardDTO);

        boardDTO.setCommentCount(boardDTO.getCommentCount() + 1);
        boardMapper.updateBoardOnlyCommentCount(boardDTO);
    }

    public void updateComment(CommentDTO commentDTO) {
        commentMapper.updateComment(commentDTO);
    }

    public void deleteComment(CommentDTO commentDTO) {
        commentMapper.deleteComment(commentDTO);

        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setNumber(commentDTO.getBoardNumber());
        boardDTO = boardMapper.getBoard(boardDTO);

        boardDTO.setCommentCount(boardDTO.getCommentCount() - 1);
        boardMapper.updateBoardOnlyCommentCount(boardDTO);
    }

    public List<CommentDTO> getCommentList(CommentDTO commentDTO) {
        List<CommentDTO> commentList = commentMapper.getCommentList(commentDTO);

        return commentList;
    }

    public static boolean boardNumberAlreadyExist(List<Integer> boardNumberList, int boardNumber) {
        for (int i = 0; i < boardNumberList.size(); i++) {
            if (boardNumber == boardNumberList.get(i))
                return true;
        }
        return false;
    }

    public List<BoardDTO> getContainCommentBoardList(CommentDTO commentDTO) {
        List<CommentDTO> allCommentList = commentMapper.getAllCommentList();

        // 요청된 내용을 포함하는 댓글을 commentContentList에 추가
        String commentContent = commentDTO.getContent();
        List<CommentDTO> containCommentList = new ArrayList<CommentDTO>();
        for (int i = 0; i < allCommentList.size(); i++) {
            if (allCommentList.get(i).getContent().contains(commentContent))
                containCommentList.add(allCommentList.get(i));
        }

        // 검열된 댓글들의 boardNumber를 중복없이 저장한다
        List<Integer> boardNumberList = new ArrayList<Integer>();
        for (int i = 0; i < containCommentList.size(); i++) {
            int boardNumber = containCommentList.get(i).getBoardNumber();

            if (!boardNumberAlreadyExist(boardNumberList, boardNumber))
                boardNumberList.add(boardNumber);
        }

        // boardNumberList의 게시물 번호에 해당하는 게시물을 최종 리스트에 추가
        List<BoardDTO> returnBoardList = new ArrayList<BoardDTO>();
        for (int i = 0; i < boardNumberList.size(); i++) {
            BoardDTO boardDTO = new BoardDTO();
            boardDTO.setNumber(boardNumberList.get(i));

            boardDTO = boardMapper.getBoard(boardDTO);
            returnBoardList.add(boardDTO);
        }

        return returnBoardList;
    }

    public List<BoardDTO> getContainCommentWriterBoardList(CommentDTO commentDTO) {
        List<CommentDTO> allCommentList = commentMapper.getAllCommentList();

        // 요청된 작성자를 포함하는 댓글을 commentWriterList에 추가
        String commentWriter = commentDTO.getWriter();
        List<CommentDTO> containCommentWriterList = new ArrayList<CommentDTO>();
        for (int i = 0; i < allCommentList.size(); i++) {
            if (allCommentList.get(i).getWriter().contains(commentWriter))
                containCommentWriterList.add(allCommentList.get(i));
        }

        // 검열된 댓글들의 boardNumber를 중복없이 저장한다
        List<Integer> boardNumberList = new ArrayList<Integer>();
        for (int i = 0; i < containCommentWriterList.size(); i++) {
            int boardNumber = containCommentWriterList.get(i).getBoardNumber();

            if (!boardNumberAlreadyExist(boardNumberList, boardNumber))
                boardNumberList.add(boardNumber);
        }

        // boardNumberList의 게시물 번호에 해당하는 게시물을 최종 리스트에 추가
        List<BoardDTO> returnBoardList = new ArrayList<BoardDTO>();
        for (int i = 0; i < boardNumberList.size(); i++) {
            BoardDTO boardDTO = new BoardDTO();
            boardDTO.setNumber(boardNumberList.get(i));

            boardDTO = boardMapper.getBoard(boardDTO);
            returnBoardList.add(boardDTO);
        }

        return returnBoardList;
    }
}
