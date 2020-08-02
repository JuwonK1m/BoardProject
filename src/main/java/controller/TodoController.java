package controller;

import dto.CompletedTodoDTO;
import dto.TodoDTO;
import dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import other.Day;
import service.TodoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class TodoController {
    private TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @RequestMapping(value = "board-list/todolist", method = RequestMethod.GET)
    public String getTodoList(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();

        Day day = new Day();
        String today = day.getToday();
        String yesterday = day.getYesterday();

        model.addAttribute("today", today);
        model.addAttribute("yesterday", yesterday);

        // 계정의 오늘 투두리스트 가져오기
        TodoDTO todoDTO = new TodoDTO();
        UserDTO user = (UserDTO)session.getAttribute("user");
        todoDTO.setWriter(user.getId());
        List<TodoDTO> todoList = todoService.getTodoList(todoDTO, today);
        model.addAttribute("todoList", todoList);

        // 계정의 오늘 완료 투두리스트 가져오기
        CompletedTodoDTO completedTodoDTO = new CompletedTodoDTO();
        completedTodoDTO.setWriter(user.getId());
        List<CompletedTodoDTO> completedTodoList = todoService.getCompletedTodoList(completedTodoDTO, today);
        model.addAttribute("completedTodoList", completedTodoList);

        // 계정의 어제 수행하지 않은 투두리스트 가져오기
        List<TodoDTO> unexecutedTodoList = todoService.getTodoList(todoDTO, yesterday);
        model.addAttribute("unexecutedTodoList", unexecutedTodoList);

        model.addAttribute("pageNumber", request.getParameter("pageNumber"));

        return "getTodoList";
    }

    @RequestMapping(value = "board-list/todo", method = RequestMethod.POST)
    public String insertTodo(HttpServletRequest request) {
        HttpSession session = request.getSession();

        UserDTO user = (UserDTO)session.getAttribute("user");
        String writer = user.getId();
        String content = request.getParameter("content");

        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setWriter(writer);
        todoDTO.setContent(content);

        todoService.insertTodo(todoDTO);

        return "redirect:todolist?pageNumber=" + request.getParameter("pageNumber");
    }

    @RequestMapping(value = "board-list/todo/view-update/{number}", method = RequestMethod.GET)
    public String getUpdateTodoView(HttpServletRequest request, @PathVariable int number, Model model) {
        HttpSession session = request.getSession();

        model.addAttribute("number", number);

        Day day = new Day();
        String today = day.getToday();
        String yesterday = day.getYesterday();

        model.addAttribute("today", today);
        model.addAttribute("yesterday", yesterday);

        // 계정의 오늘 투두리스트 가져오기
        TodoDTO todoDTO = new TodoDTO();
        UserDTO user = (UserDTO)session.getAttribute("user");
        todoDTO.setWriter(user.getId());
        List<TodoDTO> todoList = todoService.getTodoList(todoDTO, today);
        model.addAttribute("todoList", todoList);

        // 계정의 오늘 완료 투두리스트 가져오기
        CompletedTodoDTO completedTodoDTO = new CompletedTodoDTO();
        completedTodoDTO.setWriter(user.getId());
        List<CompletedTodoDTO> completedTodoList = todoService.getCompletedTodoList(completedTodoDTO, today);
        model.addAttribute("completedTodoList", completedTodoList);

        // 계정의 어제 수행하지 않은 투두리스트 가져오기
        List<TodoDTO> unexecutedTodoList = todoService.getTodoList(todoDTO, yesterday);
        model.addAttribute("unexecutedTodoList", unexecutedTodoList);

        model.addAttribute("pageNumber", request.getParameter("pageNumber"));

        return "updateTodo";
    }

    @RequestMapping(value = "board-list/todo/{number}", method = RequestMethod.PUT)
    public String updateTodo(@PathVariable int number, HttpServletRequest request) {
        String revisedContent = request.getParameter("revisedContent");

        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setNumber(number);
        todoDTO.setContent(revisedContent);

        todoService.updateTodo(todoDTO);

        return "redirect:../todolist?pageNumber=" + request.getParameter("pageNumber");
    }

    @RequestMapping(value = "board-list/todo/{number}", method = RequestMethod.DELETE)
    public String deleteTodo(@PathVariable int number, HttpServletRequest request) {
        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setNumber(number);

        todoService.deleteTodo(todoDTO);

        return "redirect:../todolist?pageNumber=" + request.getParameter("pageNumber");
    }

    @RequestMapping(value = "board-list/todo-complete/{number}", method = RequestMethod.POST)
    public String updateCompletedTodo(@PathVariable int number, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserDTO userDTO = (UserDTO)session.getAttribute("user");
        String writer = userDTO.getId();

        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setNumber(number);
        todoDTO = todoService.getTodo(todoDTO);
        String completedContent = todoDTO.getContent();

        todoService.deleteTodo(todoDTO);

        CompletedTodoDTO completedTodoDTO = new CompletedTodoDTO();
        completedTodoDTO.setWriter(writer);
        completedTodoDTO.setContent(completedContent);
        todoService.insertCompletedTodo(completedTodoDTO);

        return "redirect:../todolist?pageNumber=" + request.getParameter("pageNumber");
    }
}
