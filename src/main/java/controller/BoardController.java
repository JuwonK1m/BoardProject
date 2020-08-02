package controller;

import dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import other.Pagination;
import service.BoardService;
import service.CommentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class BoardController {
    private BoardService boardService;
    private CommentService commentService;

    @Autowired
    public BoardController(BoardService boardService, CommentService commentService) {
        this.boardService = boardService;
        this.commentService = commentService;
    }

    @RequestMapping(value = "board-list/view-insert", method = RequestMethod.GET)
    public String getInsertBoardView(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();

        model.addAttribute("pageNumber", request.getParameter("pageNumber"));

        if (session.getAttribute("information") != null) {
            model.addAttribute("information", session.getAttribute("information"));
            session.removeAttribute("information");
        }

        return "insertBoard";
    }

    @RequestMapping(value = "board-list/board", method = RequestMethod.POST)
    public String insertBoard(HttpServletRequest request) {
        HttpSession session = request.getSession();

        UserDTO user = (UserDTO)session.getAttribute("user");
        String title = request.getParameter("title");
        String writer = user.getId();
        String content = request.getParameter("content");

        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setTitle(title);
        boardDTO.setWriter(writer);
        boardDTO.setContent(content);

        String result = boardService.insertBoard(boardDTO);

        if (result.equals("insertBoardSuccess"))
            return "redirect:main?pageNumber=1";
        else {
            session.setAttribute("information", result);
            return "redirect:view-insert?pageNumber=1";
        }
    }

    @RequestMapping(value = "board-list/board/{boardNumber}", method = RequestMethod.GET)
    public String getBoard(@PathVariable int boardNumber, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();

        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setNumber(boardNumber);
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setBoardNumber(boardNumber);

        // 게시물, 댓글 리스트 얻어오기
        boardDTO = boardService.getBoard(boardDTO);
        List<CommentDTO> commentList = commentService.getCommentList(commentDTO);

        // 즐겨찾기 여부 확인
        UserDTO user = (UserDTO)session.getAttribute("user");
        FavoriteBoardDTO favoriteBoardDTO = new FavoriteBoardDTO();
        favoriteBoardDTO.setId(user.getId());
        favoriteBoardDTO.setBoardNumber(boardNumber);
        boolean isFavoriteBoard = boardService.isFavoriteBoard(favoriteBoardDTO);

        // 추천, 비추천 여부 확인
        BoardRecommendationDTO boardRecommendationDTO = new BoardRecommendationDTO();
        boardRecommendationDTO.setUserId(user.getId());
        boardRecommendationDTO.setBoardNumber(boardNumber);
        String commendation = boardService.getCommendation(boardRecommendationDTO);

        model.addAttribute("board", boardDTO);
        model.addAttribute("commentList", commentList);
        model.addAttribute("isFavoriteBoard", isFavoriteBoard);
        model.addAttribute("commendation", commendation);
        model.addAttribute("pageNumber", request.getParameter("pageNumber"));

        return "getBoard";
    }

    @RequestMapping(value = "board-list/board/view-update", method = RequestMethod.GET)
    public String getUpdateBoardView(HttpServletRequest request, Model model) {
        model.addAttribute("pageNumber", request.getParameter("pageNumber"));

        return "updateBoard";
    }

    @RequestMapping(value = "board-list/board/{boardNumber}", method = RequestMethod.PUT)
    public String updateBoard(@PathVariable int boardNumber, HttpServletRequest request) {
        HttpSession session = request.getSession();

        String title = request.getParameter("title");
        String content = request.getParameter("content");

        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setNumber(boardNumber);
        boardDTO.setTitle(title);
        boardDTO.setContent(content);

        boardService.updateBoard(boardDTO);

        return "redirect:../board/" + session.getAttribute("currentBoardNumber") + "/?pageNumber=" + request.getParameter("pageNumber");
    }

    @RequestMapping(value = "board-list/board/view-delete", method = RequestMethod.GET)
    public String getDeleteBoardView(HttpServletRequest request, Model model) {
        model.addAttribute("pageNumber", request.getParameter("pageNumber"));

        return "checkDeleteBoard";
    }

    @RequestMapping(value = "board-list/board/{number}", method = RequestMethod.DELETE)
    public String deleteBoard(@PathVariable int number, HttpServletRequest request) {
        HttpSession session = request.getSession();

        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setNumber(number);

        boardService.deleteBoard(boardDTO);

        String pageCondition = (String)session.getAttribute("pageCondition");
        if ((pageCondition.equals("main")) || (pageCondition.equals("sort-recent")) || (pageCondition.equals("sort-hits")) ||
                (pageCondition.equals("sort-comments")) || (pageCondition.equals("sort-recommendations")) ||
                (pageCondition.equals("sort-decommendations")) || (pageCondition.equals("ownership")) ||
                (pageCondition.equals("board-favorite")))
            return "redirect:../" + session.getAttribute("pageCondition") + "?pageNumber=1";
        else
            return "redirect:../main?pageNumber=1";
    }

    @RequestMapping(value = "/board-list/{state}", method = RequestMethod.GET)
    public String getBoardList(@PathVariable String state, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();

        String[] searchCondition = request.getParameterValues("searchCondition");

        BoardDTO boardDTO = new BoardDTO();
        CommentDTO commentDTO = new CommentDTO();
        List<BoardDTO> boardList = null;
        Pagination pagination = new Pagination();

        String strPageNumber = request.getParameter("pageNumber");
        int pageNumber = Integer.parseInt(strPageNumber);
        pagination.setPageNumber(pageNumber);

        if (state.equals("main")) {
            session.setAttribute("pageCondition", state);

            boardList = boardService.getPageBoardList(pagination);
        } else if (state.equals("sort-recent")) {
            session.setAttribute("pageCondition", state);

            boardList = boardService.getDateDescBoardList(pagination);
        } else if (state.equals("sort-hits")) {
            session.setAttribute("pageCondition", state);

            boardList = boardService.getHitDescBoardList(pagination);
        } else if (state.equals("sort-comments")) {
            session.setAttribute("pageCondition", state);

            boardList = boardService.getCommentDescBoardList(pagination);
        } else if (state.equals("sort-recommendations")) {
            session.setAttribute("pageCondition", state);

            boardList = boardService.getRecommendationDescBoardList(pagination);
        } else if (state.equals("sort-decommendations")) {
            session.setAttribute("pageCondition", state);

            boardList = boardService.getDecommendationDescBoardList(pagination);
        } else if (state.equals("ownership")) {
            session.setAttribute("pageCondition", state);

            UserDTO userDTO = (UserDTO)session.getAttribute("user");
            boardDTO.setWriter(userDTO.getId());

            boardList = boardService.getUserBoardList(boardDTO, pagination);
        } else if (state.equals("board-favorite")) {
            session.setAttribute("pageCondition", state);

            FavoriteBoardDTO favoriteBoardDTO = new FavoriteBoardDTO();
            UserDTO userDTO = (UserDTO)session.getAttribute("user");
            favoriteBoardDTO.setId(userDTO.getId());

            boardList = boardService.getUserFavoriteBoardList(favoriteBoardDTO, pagination);
        } else if (searchCondition[0].equals("title")) {
            session.setAttribute("pageCondition", searchCondition[0]);

            pagination.setPageCount(1);

            String title = request.getParameter("text");
            boardDTO.setTitle(title);

            boardList = boardService.getContainTitleBoardList(boardDTO);
        } else if (searchCondition[0].equals("content")) {
            session.setAttribute("pageCondition", searchCondition[0]);

            pagination.setPageCount(1);

            String content = request.getParameter("text");
            boardDTO.setContent(content);

            boardList = boardService.getContainContentBoardList(boardDTO);
        } else if (searchCondition[0].equals("writer")) {
            session.setAttribute("pageCondition", searchCondition[0]);

            pagination.setPageCount(1);

            String writer = request.getParameter("text");
            boardDTO.setWriter(writer);

            boardList = boardService.getContainWriterBoardList(boardDTO);
        } else if (searchCondition[0].equals("comment")) {
            session.setAttribute("pageCondition", searchCondition[0]);

            pagination.setPageCount(1);

            String commentContent = request.getParameter("text");
            commentDTO.setContent(commentContent);

            boardList = commentService.getContainCommentBoardList(commentDTO);
        } else if (searchCondition[0].equals("commentWriter")) {
            session.setAttribute("pageCondition", searchCondition[0]);

            pagination.setPageCount(1);

            String commentWriter = request.getParameter("text");
            commentDTO.setWriter(commentWriter);

            boardList = commentService.getContainCommentWriterBoardList(commentDTO);
        } else if (searchCondition[0].equals("integration")) {
            session.setAttribute("pageCondition", searchCondition[0]);

            pagination.setPageCount(1);

            String text = request.getParameter("text");

            boardList = boardService.getContainIntegrationBoardList(text);
        }

        model.addAttribute("pagination", pagination);
        model.addAttribute("boardList", boardList);

        return "getBoardList";
    }

    @RequestMapping(value = "board-list/board/{boardNumber}/favorite", method = RequestMethod.POST)
    public String insertFavoriteBoard(@PathVariable int boardNumber, HttpServletRequest request) {
        HttpSession session = request.getSession();

        UserDTO user = (UserDTO)session.getAttribute("user");
        FavoriteBoardDTO favoriteBoardDTO = new FavoriteBoardDTO();
        favoriteBoardDTO.setId(user.getId());
        favoriteBoardDTO.setBoardNumber(boardNumber);

        boardService.insertFavoriteBoard(favoriteBoardDTO);

        return "redirect:../" + session.getAttribute("currentBoardNumber") + "/?pageNumber=" + request.getParameter("pageNumber");
    }

    @RequestMapping(value = "board-list/board/{boardNumber}/favorite", method = RequestMethod.DELETE)
    public String deleteFavoriteBoard(@PathVariable int boardNumber, HttpServletRequest request) {
        HttpSession session = request.getSession();

        UserDTO user = (UserDTO)session.getAttribute("user");
        FavoriteBoardDTO favoriteBoardDTO = new FavoriteBoardDTO();
        favoriteBoardDTO.setId(user.getId());
        favoriteBoardDTO.setBoardNumber(boardNumber);

        boardService.deleteFavoriteBoard(favoriteBoardDTO);

        return "redirect:../" + session.getAttribute("currentBoardNumber") + "/?pageNumber=" + request.getParameter("pageNumber");
    }

    @RequestMapping(value = "board-list/board/{boardNumber}/recommendation", method = RequestMethod.PUT)
    public String updateBoardRecommendation(@PathVariable int boardNumber, HttpServletRequest request) {
        HttpSession session = request.getSession();

        BoardRecommendationDTO boardRecommendationDTO = new BoardRecommendationDTO();
        UserDTO user = (UserDTO)session.getAttribute("user");
        boardRecommendationDTO.setUserId(user.getId());
        boardRecommendationDTO.setBoardNumber(boardNumber);

        boardService.insertBoardRecommendation(boardRecommendationDTO);

        return "redirect:../" + session.getAttribute("currentBoardNumber") + "/?pageNumber=" + request.getParameter("pageNumber");
    }

    @RequestMapping(value = "board-list/board/{boardNumber}/decommendation", method = RequestMethod.PUT)
    public String updateBoardDecommendation(@PathVariable int boardNumber, HttpServletRequest request) {
        HttpSession session = request.getSession();

        BoardDecommendationDTO boardDecommendationDTO = new BoardDecommendationDTO();
        UserDTO user = (UserDTO)session.getAttribute("user");
        boardDecommendationDTO.setUserId(user.getId());
        boardDecommendationDTO.setBoardNumber(boardNumber);

        boardService.insertBoardDecommendation(boardDecommendationDTO);

        return "redirect:../" + session.getAttribute("currentBoardNumber") + "/?pageNumber=" + request.getParameter("pageNumber");
    }

    @RequestMapping(value = "board-list/board/{boardNumber}/recommendation", method = RequestMethod.DELETE)
    public String deleteBoardRecommendation(@PathVariable int boardNumber, HttpServletRequest request) {
        HttpSession session = request.getSession();

        BoardRecommendationDTO boardRecommendationDTO = new BoardRecommendationDTO();
        UserDTO user = (UserDTO)session.getAttribute("user");
        boardRecommendationDTO.setUserId(user.getId());
        boardRecommendationDTO.setBoardNumber(boardNumber);

        boardService.deleteBoardRecommendation(boardRecommendationDTO);

        return "redirect:../" + session.getAttribute("currentBoardNumber") + "/?pageNumber=" + request.getParameter("pageNumber");
    }

    @RequestMapping(value = "board-list/board/{boardNumber}/decommendation", method = RequestMethod.DELETE)
    public String deleteBoardDecommendation(@PathVariable int boardNumber, HttpServletRequest request) {
        HttpSession session = request.getSession();

        BoardDecommendationDTO boardDecommendationDTO = new BoardDecommendationDTO();
        UserDTO user = (UserDTO)session.getAttribute("user");
        boardDecommendationDTO.setUserId(user.getId());
        boardDecommendationDTO.setBoardNumber(boardNumber);

        boardService.deleteBoardDecommendation(boardDecommendationDTO);

        return "redirect:../" + session.getAttribute("currentBoardNumber") + "/?pageNumber=" + request.getParameter("pageNumber");
    }
}
