package servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import service.UserService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("list".equals(action)) {
            List<User> users = userService.getAllUsers();
            response.setContentType("text/plain");
            PrintWriter out = response.getWriter();

            for (User user : users) {
                out.println(user.getId()+" | "+user.getName()+" | "+user.getEmail());
            }
        } else if ("get".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            User user = userService.getUser(id);
            response.getWriter().write(user != null ? user.getName()+" | "+ user.getEmail() : "User not found");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            userService.addUser(user);
            response.getWriter().println("successful added...");
        } else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String email = request.getParameter("email");

            User user = new User();
            user.setId(id);
            user.setName(name);
            user.setEmail(email);
            userService.updateUser(user);
            response.getWriter().println("successful updated...");
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            userService.deleteUser(id);
            response.getWriter().println("successful deleted...");
        }
    }
}
