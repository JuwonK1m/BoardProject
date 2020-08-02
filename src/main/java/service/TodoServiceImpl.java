package service;

import dto.CompletedTodoDTO;
import dto.TodoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.CompletedTodoMapper;
import repository.TodoMapper;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {
    @Resource(name = "todoMapper")
    private TodoMapper todoMapper;
    @Resource(name = "completedTodoMapper")
    private CompletedTodoMapper completedTodoMapper;

    @Autowired
    public TodoServiceImpl(TodoMapper todoMapper, CompletedTodoMapper completedTodoMapper) {
        this.todoMapper = todoMapper;
        this.completedTodoMapper = completedTodoMapper;
    }

    public void insertTodo(TodoDTO todoDTO) {
        todoMapper.insertTodo(todoDTO);
    }

    public void insertCompletedTodo(CompletedTodoDTO completedTodoDTO) {
        completedTodoMapper.insertCompletedTodo(completedTodoDTO);
    }

    public TodoDTO getTodo(TodoDTO todoDTO) {
        TodoDTO returnTodoDTO = todoMapper.getTodo(todoDTO);
        return returnTodoDTO;
    }

    public void updateTodo(TodoDTO todoDTO) {
        todoMapper.updateTodo(todoDTO);
    }

    public void deleteTodo(TodoDTO todoDTO) {
        todoMapper.deleteTodo(todoDTO);
    }

    public List<TodoDTO> getTodoList(TodoDTO todoDTO, String date) {
        List<TodoDTO> todoList = todoMapper.getTodoList(todoDTO);
        List<TodoDTO> returnTodoList = new ArrayList<TodoDTO>();

        // 반환할 투두리스트에 date와 일치하는 날짜의 리스트만 넣는다
        for (int i = 0; i < todoList.size(); i++) {
            String tempDate = todoList.get(i).getDate();
            tempDate = tempDate.substring(0, 10);

            if (tempDate.equals(date))
                returnTodoList.add(todoList.get(i));
        }

        return returnTodoList;
    }

    public List<CompletedTodoDTO> getCompletedTodoList(CompletedTodoDTO completedTodoDTO, String today) {
        List<CompletedTodoDTO> completedTodoList = completedTodoMapper.getCompletedTodoList(completedTodoDTO);
        List<CompletedTodoDTO> returnCompletedTodoList = new ArrayList<CompletedTodoDTO>();

        // 반환할 완료 투두리스트에 오늘 날짜 리스트만 넣는다
        for (int i = 0; i < completedTodoList.size(); i++) {
            String date = completedTodoList.get(i).getDate();
            date = date.substring(0, 10);

            if (date.equals(today))
                returnCompletedTodoList.add(completedTodoList.get(i));
        }

        return returnCompletedTodoList;
    }
}
