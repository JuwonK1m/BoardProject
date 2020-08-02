package repository;

import dto.TodoDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("todoMapper")
public interface TodoMapper {
    @Insert(value = "INSERT INTO todo (writer, content) VALUES(#{writer}, #{content})")
    public void insertTodo(TodoDTO todoDTO);

    @Update(value = "UPDATE todo SET content=#{content} WHERE number=#{number}")
    public void updateTodo(TodoDTO todoDTO);

    @Delete(value = "DELETE FROM todo WHERE number=#{number}")
    public void deleteTodo(TodoDTO todoDTO);

    @Select(value = "SELECT * FROM todo WHERE number=#{number}")
    public TodoDTO getTodo(TodoDTO todoDTO);

    @Select(value = "SELECT * FROM todo WHERE writer=#{writer} ORDER BY number ASC")
    public List<TodoDTO> getTodoList(TodoDTO todoDTO);
}
