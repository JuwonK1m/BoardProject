package repository;

import dto.BoardDTO;
import dto.BoardDecommendationDTO;
import dto.BoardRecommendationDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("boardMapper")
public interface BoardMapper {
    @Insert(value = "INSERT INTO board (title, writer, content) VALUES(#{title}, #{writer}, #{content})")
    public void insertBoard(BoardDTO boardDTO);

    @Update(value = "UPDATE board SET title=#{title}, content=#{content}, date=CURRENT_TIMESTAMP WHERE number=#{number}")
    public void updateBoard(BoardDTO boardDTO);

    @Update(value = "UPDATE board SET hit=#{hit} WHERE number=#{number}")
    public void updateBoardHit(BoardDTO boardDTO);

    @Update(value = "UPDATE board SET recommendationCount=#{recommendationCount} WHERE number=#{number}")
    public void updateBoardRecommendationCount(BoardDTO boardDTO);

    @Update(value = "UPDATE board SET decommendationCount=#{decommendationCount} WHERE number=#{number}")
    public void updateBoardDecommendationCount(BoardDTO boardDTO);

    @Delete(value = "DELETE FROM board WHERE number=#{number}")
    public void deleteBoard(BoardDTO boardDTO);

    @Select(value = "SELECT * FROM board WHERE number=#{number}")
    public BoardDTO getBoard(BoardDTO boardDTO);

    @Select(value = "SELECT * FROM board")
    public List<BoardDTO> getBoardList();

    @Select(value = "SELECT * FROM board WHERE writer=#{writer} ORDER BY number DESC")
    public List<BoardDTO> getUserBoardList(BoardDTO boardDTO);

    @Select(value = "SELECT * FROM board ORDER BY number DESC")
    public List<BoardDTO> getNumberDescBoardList();

    @Select(value = "SELECT * FROM board ORDER BY date DESC")
    public List<BoardDTO> getDateDescBoardList();

    @Select(value = "SELECT * FROM board ORDER BY hit DESC")
    public List<BoardDTO> getHitDescBoardList();

    @Select(value = "SELECT * FROM board ORDER BY commentCount DESC")
    public List<BoardDTO> getCommentDescBoardList();

    @Select(value = "SELECT * FROM board ORDER BY recommendationCount DESC")
    public List<BoardDTO> getRecommendationDescBoardList();

    @Select(value = "SELECT * FROM board ORDER BY decommendationCount DESC")
    public List<BoardDTO> getDecommendationDescBoardList();

    @Update(value = "UPDATE board SET commentCount=#{commentCount} WHERE number=#{number}")
    public void updateBoardOnlyCommentCount(BoardDTO boardDTO);

    @Insert(value = "INSERT INTO boardDecommendation (userId, boardNumber) VALUES(#{userId}, #{boardNumber})")
    public void insertBoardDecommendation(BoardDecommendationDTO boardDecommendationDTO);

    @Delete(value = "DELETE FROM boardDecommendation WHERE userId=#{userId} AND boardNumber=#{boardNumber}")
    public void deleteBoardDecommendation(BoardDecommendationDTO boardDecommendationDTO);

    @Select(value = "SELECT * FROM boardDecommendation WHERE userId=#{userId} AND boardNumber=#{boardNumber}")
    public BoardDecommendationDTO getBoardDecommendation(BoardDecommendationDTO boardDecommendationDTO);

    @Insert(value = "INSERT INTO boardRecommendation (userId, boardNumber) VALUES(#{userId}, #{boardNumber})")
    public void insertBoardRecommendation(BoardRecommendationDTO boardRecommendationDTO);

    @Delete(value = "DELETE FROM boardRecommendation WHERE userId=#{userId} AND boardNumber=#{boardNumber}")
    public void deleteBoardRecommendation(BoardRecommendationDTO boardRecommendationDTO);

    @Select(value = "SELECT * FROM boardRecommendation WHERE userId=#{userId} AND boardNumber=#{boardNumber}")
    public BoardRecommendationDTO getBoardRecommendation(BoardRecommendationDTO boardRecommendationDTO);
}
