package repository;

import dto.CompletedTodoDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("completedTodoMapper")
public interface CompletedTodoMapper {
    @Insert(value = "INSERT INTO completedTodo (writer, content) VALUES(#{writer}, #{content})")
    public void insertCompletedTodo(CompletedTodoDTO completedTododto);

    @Select(value = "SELECT * FROM completedTodo WHERE writer=#{writer} ORDER BY number ASC")
    public List<CompletedTodoDTO> getCompletedTodoList(CompletedTodoDTO completedTodoDTO);
}
