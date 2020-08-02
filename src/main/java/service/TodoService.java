package service;

import dto.CompletedTodoDTO;
import dto.TodoDTO;

import java.util.List;

public interface TodoService {
    // Create
    public void insertTodo(TodoDTO todoDTO);
    public void insertCompletedTodo(CompletedTodoDTO completedTodoDTO);

    // Read
    public TodoDTO getTodo(TodoDTO todoDTO);

    // Update
    public void updateTodo(TodoDTO todoDTO);

    // Delete
    public void deleteTodo(TodoDTO todoDTO);

    // get list
    public List<TodoDTO> getTodoList(TodoDTO todoDTO, String today);
    public List<CompletedTodoDTO> getCompletedTodoList(CompletedTodoDTO completedTodoDTO, String today);
}
