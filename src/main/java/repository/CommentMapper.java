package repository;

import dto.CommentDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("commentMapper")
public interface CommentMapper {
    @Insert(value = "INSERT INTO comment (writer, content, boardNumber) VALUES(#{writer}, #{content}, #{boardNumber})")
    public void insertComment(CommentDTO commentDTO);

    @Update(value = "UPDATE comment SET content=#{content}, date=CURRENT_TIMESTAMP WHERE number=#{number}")
    public void updateComment(CommentDTO commentDTO);

    @Delete(value = "DELETE FROM comment WHERE number=#{number}")
    public void deleteComment(CommentDTO commentDTO);

    @Select(value = "SELECT * FROM comment WHERE boardNumber=#{boardNumber} ORDER BY number DESC")
    public List<CommentDTO> getCommentList(CommentDTO commentDTO);

    @Select(value = "SELECT * FROM comment")
    public List<CommentDTO> getAllCommentList();
}
