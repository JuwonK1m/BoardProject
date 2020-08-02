package service;

import dto.BoardDTO;
import dto.BoardDecommendationDTO;
import dto.BoardRecommendationDTO;
import dto.FavoriteBoardDTO;
import other.Pagination;

import java.util.List;

public interface BoardService {
    // Create board
    public String insertBoard(BoardDTO boardDTO);

    // Read board
    public BoardDTO getBoard(BoardDTO boardDTO);
    public boolean isFavoriteBoard(FavoriteBoardDTO favoriteBoardDTO);
    public String getCommendation(BoardRecommendationDTO boardRecommendationDTO);

    // Update board
    public void updateBoard(BoardDTO boardDTO);

    // Delete board
    public void deleteBoard(BoardDTO boardDTO);

    // Get board list
    public List<BoardDTO> getBoardList(BoardDTO boardDTO);
    public List<BoardDTO> getPageBoardList(Pagination pagination);

    // Get board list by sort
    public List<BoardDTO> getDateDescBoardList(Pagination pagination);
    public List<BoardDTO> getHitDescBoardList(Pagination pagination);
    public List<BoardDTO> getCommentDescBoardList(Pagination pagination);
    public List<BoardDTO> getRecommendationDescBoardList(Pagination pagination);
    public List<BoardDTO> getDecommendationDescBoardList(Pagination pagination);
    public List<BoardDTO> getUserBoardList(BoardDTO boardDTO, Pagination pagination);
    public List<BoardDTO> getUserFavoriteBoardList(FavoriteBoardDTO favoriteBoardDTO, Pagination pagination);

    // Get board list by search
    public List<BoardDTO> getContainTitleBoardList(BoardDTO boardDTO);
    public List<BoardDTO> getContainContentBoardList(BoardDTO boardDTO);
    public List<BoardDTO> getContainWriterBoardList(BoardDTO boardDTO);
    public List<BoardDTO> getContainIntegrationBoardList(String text);

    // Add user favorite board
    public void insertFavoriteBoard(FavoriteBoardDTO favoriteBoardDTO);
    public void deleteFavoriteBoard(FavoriteBoardDTO favoriteBoardDTO);

    // Add user board commendation
    public void insertBoardRecommendation(BoardRecommendationDTO boardRecommendationDTO);
    public void insertBoardDecommendation(BoardDecommendationDTO boardDecommendationDTO);

    // Delete user board commendation
    public void deleteBoardRecommendation(BoardRecommendationDTO boardRecommendationDTO);
    public void deleteBoardDecommendation(BoardDecommendationDTO boardDecommendationDTO);
}
