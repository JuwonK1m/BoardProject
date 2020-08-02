package controller;

import dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.BoardService;
import service.CommentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CommentController {
    private CommentService commentService;
    private BoardService boardService;

    @Autowired
    public CommentController(CommentService commentService, BoardService boardService) {
        this.commentService = commentService;
        this.boardService = boardService;
    }

    @RequestMapping(value = "board-list/board/{boardNumber}/comment", method = RequestMethod.POST)
    public String insertComment(@PathVariable int boardNumber, HttpServletRequest request) {
        HttpSession session = request.getSession();

        UserDTO user = (UserDTO)session.getAttribute("user");
        String writer = user.getId();
        String content = request.getParameter("content");

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setWriter(writer);
        commentDTO.setContent(content);
        commentDTO.setBoardNumber(boardNumber);

        commentService.insertComment(commentDTO);

        return "redirect:../" + session.getAttribute("currentBoardNumber") + "/?pageNumber=" + request.getParameter("pageNumber");
    }

    @RequestMapping(value = "board-list/board/{boardNumber}/comment/view-update/{commentNumber}", method = RequestMethod.GET)
    public String getUpdateCommentView(@PathVariable int boardNumber, @PathVariable int commentNumber, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();

        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setNumber(boardNumber);
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setBoardNumber(boardNumber);

        // 게시물, 댓글 리스트 얻어오기
        boardDTO = boardService.getBoard(boardDTO);
        List<CommentDTO> commentList = commentService.getCommentList(commentDTO);

        // 즐겨찾기 여부 확인
        UserDTO userDTO = (UserDTO)session.getAttribute("user");
        FavoriteBoardDTO favoriteBoardDTO = new FavoriteBoardDTO();
        favoriteBoardDTO.setId(userDTO.getId());
        favoriteBoardDTO.setBoardNumber(boardNumber);
        boolean isFavoriteBoard = boardService.isFavoriteBoard(favoriteBoardDTO);

        // 추천, 비추천 여부 확인
        BoardRecommendationDTO boardRecommendationDTO = new BoardRecommendationDTO();
        boardRecommendationDTO.setUserId(userDTO.getId());
        boardRecommendationDTO.setBoardNumber(boardNumber);
        String commendation = boardService.getCommendation(boardRecommendationDTO);

        model.addAttribute("board", boardDTO);
        model.addAttribute("commentList", commentList);
        model.addAttribute("isFavoriteBoard", isFavoriteBoard);
        model.addAttribute("commendation", commendation);
        model.addAttribute("updateCommentNumber", commentNumber);
        model.addAttribute("pageNumber", request.getParameter("pageNumber"));

        return "updateComment";
    }

    @RequestMapping(value = "board-list/board/{boardNumber}/comment/view-update/{commentNumber}", method = RequestMethod.PUT)
    public String updateComment(@PathVariable int commentNumber, HttpServletRequest request) {
        HttpSession session = request.getSession();

        String revisedContent = request.getParameter("revisedContent");

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setNumber(commentNumber);
        commentDTO.setContent(revisedContent);

        commentService.updateComment(commentDTO);

        return "redirect:../../../" + session.getAttribute("currentBoardNumber") + "/?pageNumber=" + request.getParameter("pageNumber");
    }

    @RequestMapping(value = "board-list/board/{boardNumber}/comment/{commentNumber}", method = RequestMethod.DELETE)
    public String deleteComment(@PathVariable int boardNumber, @PathVariable int commentNumber, HttpServletRequest request) {
        HttpSession session = request.getSession();

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setNumber(commentNumber);
        commentDTO.setBoardNumber(boardNumber);

        commentService.deleteComment(commentDTO);

        return "redirect:/board-list/board/" + session.getAttribute("currentBoardNumber") + "/?pageNumber=" + request.getParameter("pageNumber");
    }
}
