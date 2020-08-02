package service;

import dto.*;
import org.springframework.stereotype.Service;
import other.Pagination;
import repository.BoardMapper;
import repository.CommentMapper;
import repository.FavoriteBoardMapper;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {
    @Resource(name = "boardMapper")
    private BoardMapper boardMapper;
    @Resource(name = "commentMapper")
    private CommentMapper commentMapper;
    @Resource(name = "favoriteBoardMapper")
    private FavoriteBoardMapper favoriteBoardMapper;

    public static String checkDataLength(BoardDTO boardDTO) {
        if ((boardDTO.getTitle().getBytes().length > 100) && (boardDTO.getContent().getBytes().length > 20000))
            return "titleAndContentExcess";
        else if (boardDTO.getTitle().getBytes().length > 100)
            return "titleExcess";
        else if (boardDTO.getContent().getBytes().length > 20000)
            return "contentExcess";
        else if (boardDTO.getTitle().getBytes().length == 0)
            return "titleEmpty";
        else
            return "success";
    }

    public String insertBoard(BoardDTO boardDTO) {
        // 올바른 제목길이, 내용길이인지 확인
        if (!checkDataLength(boardDTO).equals("success"))
            return checkDataLength(boardDTO);

        boardMapper.insertBoard(boardDTO);
        return "insertBoardSuccess";
    }

    public BoardDTO getBoard(BoardDTO boardDTO) {
        BoardDTO returnBoardDTO = boardMapper.getBoard(boardDTO);

        returnBoardDTO.setHit(returnBoardDTO.getHit() + 1);
        boardMapper.updateBoardHit(returnBoardDTO);

        return returnBoardDTO;
    }

    public boolean isFavoriteBoard(FavoriteBoardDTO favoriteBoardDTO) {
        FavoriteBoardDTO favoriteBoard = favoriteBoardMapper.getFavoriteBoard(favoriteBoardDTO);

        if (favoriteBoard == null)
            return false;

        return true;
    }

    public String getCommendation(BoardRecommendationDTO boardRecommendationDTO) {
        BoardRecommendationDTO boardRecommendation = boardMapper.getBoardRecommendation(boardRecommendationDTO);

        BoardDecommendationDTO boardDecommendation = new BoardDecommendationDTO();
        boardDecommendation.setUserId(boardRecommendationDTO.getUserId());
        boardDecommendation.setBoardNumber(boardRecommendationDTO.getBoardNumber());
        boardDecommendation = boardMapper.getBoardDecommendation(boardDecommendation);

        if ((boardRecommendation == null) && (boardDecommendation == null))
            return "nothing";
        else if (boardRecommendation != null)
            return "recommendation";
        else
            return "decommendation";
    }

    public void updateBoard(BoardDTO boardDTO) {
        boardMapper.updateBoard(boardDTO);
    }

    public void deleteBoard(BoardDTO boardDTO) {
        boardMapper.deleteBoard(boardDTO);
    }

    public List<BoardDTO> getBoardList(BoardDTO boardDTO) {
        List<BoardDTO> boardList = boardMapper.getBoardList();

        return boardList;
    }

    public List<BoardDTO> getPageBoardList(Pagination pagination) {
        List<BoardDTO> boardList = boardMapper.getNumberDescBoardList();
        List<BoardDTO> returnBoardList = new ArrayList<BoardDTO>();

        if ((boardList.size() % 18 == 0) && (boardList.size() != 0))
            pagination.setPageCount(boardList.size() / 18);
        else
            pagination.setPageCount(boardList.size() / 18 + 1);

        // 페이지 넘버에 맞는 게시물 리스트 걸러내기
        for (int i = (18 * pagination.getPageNumber() - 18); ((i < boardList.size()) && (i < (18 * pagination.getPageNumber()))); i++)
            returnBoardList.add(boardList.get(i));

        return returnBoardList;
    }

    public List<BoardDTO> getDateDescBoardList(Pagination pagination) {
        List<BoardDTO> boardList = boardMapper.getDateDescBoardList();
        List<BoardDTO> returnBoardList = new ArrayList<BoardDTO>();

        if ((boardList.size() % 18 == 0) && (boardList.size() != 0))
            pagination.setPageCount(boardList.size() / 18);
        else
            pagination.setPageCount(boardList.size() / 18 + 1);

        // 페이지 넘버에 맞는 게시물 리스트 걸러내기
        for (int i = (18 * pagination.getPageNumber() - 18); ((i < boardList.size()) && (i < (18 * pagination.getPageNumber()))); i++)
            returnBoardList.add(boardList.get(i));

        return returnBoardList;
    }

    public List<BoardDTO> getHitDescBoardList(Pagination pagination) {
        List<BoardDTO> boardList = boardMapper.getHitDescBoardList();
        List<BoardDTO> returnBoardList = new ArrayList<BoardDTO>();

        if ((boardList.size() % 18 == 0) && (boardList.size() != 0))
            pagination.setPageCount(boardList.size() / 18);
        else
            pagination.setPageCount(boardList.size() / 18 + 1);

        // 페이지 넘버에 맞는 게시물 리스트 걸러내기
        for (int i = (18 * pagination.getPageNumber() - 18); ((i < boardList.size()) && (i < (18 * pagination.getPageNumber()))); i++)
            returnBoardList.add(boardList.get(i));

        return returnBoardList;
    }

    public List<BoardDTO> getCommentDescBoardList(Pagination pagination) {
        List<BoardDTO> boardList = boardMapper.getCommentDescBoardList();
        List<BoardDTO> returnBoardList = new ArrayList<BoardDTO>();

        if ((boardList.size() % 18 == 0) && (boardList.size() != 0))
            pagination.setPageCount(boardList.size() / 18);
        else
            pagination.setPageCount(boardList.size() / 18 + 1);

        // 페이지 넘버에 맞는 게시물 리스트 걸러내기
        for (int i = (18 * pagination.getPageNumber() - 18); ((i < boardList.size()) && (i < (18 * pagination.getPageNumber()))); i++)
            returnBoardList.add(boardList.get(i));

        return returnBoardList;
    }

    public List<BoardDTO> getRecommendationDescBoardList(Pagination pagination) {
        List<BoardDTO> boardList = boardMapper.getRecommendationDescBoardList();
        List<BoardDTO> returnBoardList = new ArrayList<BoardDTO>();

        if ((boardList.size() % 18 == 0) && (boardList.size() != 0))
            pagination.setPageCount(boardList.size() / 18);
        else
            pagination.setPageCount(boardList.size() / 18 + 1);

        // 페이지 넘버에 맞는 게시물 리스트 걸러내기
        for (int i = (18 * pagination.getPageNumber() - 18); ((i < boardList.size()) && (i < (18 * pagination.getPageNumber()))); i++)
            returnBoardList.add(boardList.get(i));

        return returnBoardList;
    }

    public List<BoardDTO> getDecommendationDescBoardList(Pagination pagination) {
        List<BoardDTO> boardList = boardMapper.getDecommendationDescBoardList();
        List<BoardDTO> returnBoardList = new ArrayList<BoardDTO>();

        if ((boardList.size() % 18 == 0) && (boardList.size() != 0))
            pagination.setPageCount(boardList.size() / 18);
        else
            pagination.setPageCount(boardList.size() / 18 + 1);

        // 페이지 넘버에 맞는 게시물 리스트 걸러내기
        for (int i = (18 * pagination.getPageNumber() - 18); ((i < boardList.size()) && (i < (18 * pagination.getPageNumber()))); i++)
            returnBoardList.add(boardList.get(i));

        return returnBoardList;
    }

    public List<BoardDTO> getUserBoardList(BoardDTO boardDTO, Pagination pagination) {
        List<BoardDTO> userBoardList = boardMapper.getUserBoardList(boardDTO);
        List<BoardDTO> filteredUserBoardList = new ArrayList<BoardDTO>();

        if ((userBoardList.size() % 18 == 0) && (userBoardList.size() != 0))
            pagination.setPageCount(userBoardList.size() / 18);
        else
            pagination.setPageCount(userBoardList.size() / 18 + 1);

        for (int i = (18 * pagination.getPageNumber() - 18); ((i < userBoardList.size()) && (i < (18 * pagination.getPageNumber()))); i++)
            filteredUserBoardList.add(userBoardList.get(i));

        return filteredUserBoardList;
    }

    public List<BoardDTO> getUserFavoriteBoardList(FavoriteBoardDTO favoriteBoardDTO, Pagination pagination) {
        List<FavoriteBoardDTO> userFavoriteList = favoriteBoardMapper.getUserFavoriteBoardList(favoriteBoardDTO);

        List<Integer> boardNumberList = new ArrayList<Integer>();
        for (int i = 0; i < userFavoriteList.size(); i++)
            boardNumberList.add(userFavoriteList.get(i).getBoardNumber());

        List<BoardDTO> returnBoardList = new ArrayList<BoardDTO>();
        for (int i = 0; i < boardNumberList.size(); i++) {
            BoardDTO boardDTO = new BoardDTO();
            boardDTO.setNumber(boardNumberList.get(i));
            boardDTO = boardMapper.getBoard(boardDTO);
            returnBoardList.add(boardDTO);
        }

        List<BoardDTO> filteredUserFavoriteBoardList = new ArrayList<BoardDTO>();

        if ((returnBoardList.size() % 18 == 0) && (returnBoardList.size() != 0))
            pagination.setPageCount(returnBoardList.size() / 18);
        else
            pagination.setPageCount(returnBoardList.size() / 18 + 1);

        for (int i = (18 * pagination.getPageNumber() - 18); ((i < returnBoardList.size()) && (i < (18 * pagination.getPageNumber()))); i++)
            filteredUserFavoriteBoardList.add(returnBoardList.get(i));

        return filteredUserFavoriteBoardList;
    }

    public List<BoardDTO> getContainTitleBoardList(BoardDTO boardDTO) {
        List<BoardDTO> allBoardList = boardMapper.getBoardList();

        String boardTitle = boardDTO.getTitle();

        List<BoardDTO> returnBoardList = new ArrayList<BoardDTO>();
        for (int i = 0; i < allBoardList.size(); i++) {
            if (allBoardList.get(i).getTitle().contains(boardTitle))
                returnBoardList.add(allBoardList.get(i));
        }

        return returnBoardList;
    }

    public List<BoardDTO> getContainContentBoardList(BoardDTO boardDTO) {
        List<BoardDTO> allBoardList = boardMapper.getBoardList();

        String boardContent = boardDTO.getContent();

        List<BoardDTO> returnBoardList = new ArrayList<BoardDTO>();
        for (int i = 0; i < allBoardList.size(); i++) {
            if (allBoardList.get(i).getContent().contains(boardContent))
                returnBoardList.add(allBoardList.get(i));
        }

        return returnBoardList;
    }

    public List<BoardDTO> getContainWriterBoardList(BoardDTO boardDTO) {
        List<BoardDTO> allBoardList = boardMapper.getBoardList();

        String boardWriter = boardDTO.getWriter();

        List<BoardDTO> returnBoardList = new ArrayList<BoardDTO>();
        for (int i = 0; i < allBoardList.size(); i++) {
            if (allBoardList.get(i).getWriter().contains(boardWriter))
                returnBoardList.add(allBoardList.get(i));
        }

        return returnBoardList;
    }

    public static boolean boardNumberAlreadyExist(List<BoardDTO> boardList, CommentDTO commentDTO) {
        for (int i = 0; i < boardList.size(); i++) {
            if (boardList.get(i).getNumber() == commentDTO.getBoardNumber())
                return true;
        }
        return false;
    }

    public List<BoardDTO> getContainIntegrationBoardList(String text) {
        List<BoardDTO> allBoardList = boardMapper.getBoardList();

        // 전체 게시물 중에서 제목 or 내용 or 글쓴이 중 어느하나라도 text 문자열을 포함한다면 returnBoardList에 add
        List<BoardDTO> returnBoardList = new ArrayList<BoardDTO>();
        for (int i = 0; i < allBoardList.size(); i++) {
            if ((allBoardList.get(i).getTitle().contains(text)) || (allBoardList.get(i).getContent().contains(text)) ||
                    (allBoardList.get(i).getWriter().contains(text)))
                returnBoardList.add(allBoardList.get(i));
        }

        List<CommentDTO> commentList = commentMapper.getAllCommentList();

        // 댓글 중에서 댓글 내용 or 댓글 작성자 중 어느하나라도 text 문자열을 포함한다면 commentTextContainList에 add
        List<CommentDTO> containTextCommentList = new ArrayList<CommentDTO>();
        for (int i = 0; i < commentList.size(); i++) {
            if ((commentList.get(i).getContent().contains(text)) || (commentList.get(i).getWriter().contains(text)))
                containTextCommentList.add(commentList.get(i));
        }

        // 검열된 댓글들중에서 boardNumber가, boardList의 객체들중에서의 number와 겹치치 않는다면 returnBoardList에 add
        for (int i = 0; i < containTextCommentList.size(); i++) {
            if (!boardNumberAlreadyExist(returnBoardList, containTextCommentList.get(i))) {
                BoardDTO boardDTO = new BoardDTO();
                boardDTO.setNumber(containTextCommentList.get(i).getBoardNumber());
                boardDTO = boardMapper.getBoard(boardDTO);
                returnBoardList.add(boardDTO);
            }
        }

        return returnBoardList;
    }

    public void insertFavoriteBoard(FavoriteBoardDTO favoriteBoardDTO) {
        favoriteBoardMapper.insertFavoriteBoard(favoriteBoardDTO);

        // 무분별한 조회수 증가 방지
        BoardDTO board = new BoardDTO();
        board.setNumber(favoriteBoardDTO.getBoardNumber());
        board = boardMapper.getBoard(board);
        board.setHit(board.getHit() - 1);
        boardMapper.updateBoardHit(board);
    }

    public void deleteFavoriteBoard(FavoriteBoardDTO favoriteBoardDTO) {
        favoriteBoardMapper.deleteFavoriteBoard(favoriteBoardDTO);

        // 무분별한 조회수 증가 방지
        BoardDTO board = new BoardDTO();
        board.setNumber(favoriteBoardDTO.getBoardNumber());
        board = boardMapper.getBoard(board);
        board.setHit(board.getHit() - 1);
        boardMapper.updateBoardHit(board);
    }

    public void insertBoardRecommendation(BoardRecommendationDTO boardRecommendationDTO) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setNumber(boardRecommendationDTO.getBoardNumber());
        boardDTO = boardMapper.getBoard(boardDTO);

        // 무분별한 조회수 증가 방지
        boardDTO.setHit(boardDTO.getHit() - 1);
        boardMapper.updateBoardHit(boardDTO);

        BoardDecommendationDTO boardDecommendation = new BoardDecommendationDTO();
        boardDecommendation.setUserId(boardRecommendationDTO.getUserId());
        boardDecommendation.setBoardNumber(boardRecommendationDTO.getBoardNumber());
        BoardDecommendationDTO decommendationExist = boardMapper.getBoardDecommendation(boardDecommendation);

        // 비추천이 있다면 비추천 데이터를 지운다
        if (decommendationExist != null) {
            boardMapper.deleteBoardDecommendation(boardDecommendation);

            boardDTO.setDecommendationCount(boardDTO.getDecommendationCount() - 1);
            boardMapper.updateBoardDecommendationCount(boardDTO);
        }

        boardMapper.insertBoardRecommendation(boardRecommendationDTO);
        boardDTO.setRecommendationCount(boardDTO.getRecommendationCount() + 1);
        boardMapper.updateBoardRecommendationCount(boardDTO);
    }

    public void insertBoardDecommendation(BoardDecommendationDTO boardDecommendationDTO) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setNumber(boardDecommendationDTO.getBoardNumber());
        boardDTO = boardMapper.getBoard(boardDTO);

        // 무분별한 조회수 증가 방지
        boardDTO.setHit(boardDTO.getHit() - 1);
        boardMapper.updateBoardHit(boardDTO);

        BoardRecommendationDTO boardRecommendation = new BoardRecommendationDTO();
        boardRecommendation.setUserId(boardDecommendationDTO.getUserId());
        boardRecommendation.setBoardNumber(boardDecommendationDTO.getBoardNumber());
        BoardRecommendationDTO recommendationExist = boardMapper.getBoardRecommendation(boardRecommendation);

        // 추천이 있다면 추천 데이터를 지운다
        if (recommendationExist != null) {
            boardMapper.deleteBoardRecommendation(boardRecommendation);

            boardDTO.setRecommendationCount(boardDTO.getRecommendationCount() - 1);
            boardMapper.updateBoardRecommendationCount(boardDTO);
        }

        boardMapper.insertBoardDecommendation(boardDecommendationDTO);
        boardDTO.setDecommendationCount(boardDTO.getDecommendationCount() + 1);
        boardMapper.updateBoardDecommendationCount(boardDTO);
    }

    public void deleteBoardRecommendation(BoardRecommendationDTO boardRecommendationDTO) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setNumber(boardRecommendationDTO.getBoardNumber());
        boardDTO = boardMapper.getBoard(boardDTO);

        // 무분별한 조회수 증가 방지
        boardDTO.setHit(boardDTO.getHit() - 1);
        boardMapper.updateBoardHit(boardDTO);

        boardMapper.deleteBoardRecommendation(boardRecommendationDTO);
        boardDTO.setRecommendationCount(boardDTO.getRecommendationCount() - 1);
        boardMapper.updateBoardRecommendationCount(boardDTO);
    }

    public void deleteBoardDecommendation(BoardDecommendationDTO boardDecommendationDTO) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setNumber(boardDecommendationDTO.getBoardNumber());
        boardDTO = boardMapper.getBoard(boardDTO);

        // 무분별한 조회수 증가 방지
        boardDTO.setHit(boardDTO.getHit() - 1);
        boardMapper.updateBoardHit(boardDTO);

        boardMapper.deleteBoardDecommendation(boardDecommendationDTO);
        boardDTO.setDecommendationCount(boardDTO.getDecommendationCount() - 1);
        boardMapper.updateBoardDecommendationCount(boardDTO);
    }
}
