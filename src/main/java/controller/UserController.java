package controller;

import dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginView(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();

        if (session.getAttribute("information") != null) {
            model.addAttribute("information", session.getAttribute("information"));
            session.removeAttribute("information");
        }

        return "login";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String id = request.getParameter("id");
        String password = request.getParameter("password");

        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        userDTO.setPassword(password);

        String result = userService.userExist(userDTO);

        if (result.equals("userExist")) {
            session.setAttribute("user", userDTO);
            return "redirect:board-list/main?pageNumber=1";
        } else {
            session.setAttribute("information", result);
            return "redirect:login";
        }
    }

    @RequestMapping(value = "/view-join", method = RequestMethod.GET)
    public String getJoinView(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();

        if (session.getAttribute("information") != null) {
            model.addAttribute("information", session.getAttribute("information"));
            session.removeAttribute("information");
        }

        return "join";
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public String join(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String id = request.getParameter("id");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        userDTO.setPassword(password);
        userDTO.setEmail(email);

        String result = userService.insertUser(userDTO);

        session.setAttribute("information", result);

        if (result.equals("joinSuccess"))
            return "redirect:login";
        else
            return "redirect:view-join";
    }

    @RequestMapping(value = "/board-list/view-logout", method = RequestMethod.GET)
    public String getLogoutView(HttpServletRequest request, Model model) {
        model.addAttribute("pageNumber", request.getParameter("pageNumber"));

        return "checkLogout";
    }

    @RequestMapping(value = "/board-list/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();

        session.invalidate();

        return "redirect:../login";
    }

    @RequestMapping(value = "user/view-delete", method = RequestMethod.GET)
    public String getDeleteUserView(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();

        if (session.getAttribute("information") != null) {
            model.addAttribute("information", session.getAttribute("information"));
            session.removeAttribute("information");
        }

        model.addAttribute("pageNumber", request.getParameter("pageNumber"));

        return "checkDeleteUser";
    }

    @RequestMapping(value = "user", method = RequestMethod.DELETE)
    public String deleteUser(HttpServletRequest request) {
        HttpSession session = request.getSession();

        UserDTO user = (UserDTO)session.getAttribute("user");
        String password = request.getParameter("password");

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setPassword(password);

        boolean passwordCorrect = userService.checkCorrectPassword(userDTO);

        if (passwordCorrect) {
            userService.deleteUser(userDTO);
            return "redirect:login";
        }
        else {
            session.setAttribute("information", "passwordIncorrect");
            return "redirect:user/view-delete?pageNumber=" + request.getParameter("pageNumber");
        }
    }
}
