package controller;

import model.User;
import service.UserManage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ServletUser", urlPatterns = "/users")
public class ServletUser extends HttpServlet {
    private UserManage userManage=new UserManage();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action=request.getParameter("action");
        if (action==null) action="";
        switch (action){
            case "find":
                showResult(request,response);
                break;
            case "create":
                create(request,response);
                break;
            default:
                showList(request,response);
        }

    }

    private void create(HttpServletRequest request, HttpServletResponse response) {
        String name=request.getParameter("name");
        String email=request.getParameter("email");
        int id= (int) (Math.random()*100000);
        User user=new User(id,name,email);
        userManage.add(user);
        try {
            response.sendRedirect("/users");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action=request.getParameter("action");
        if (action==null) action="";
        switch (action){
            case "find":
                showResult(request,response);
                break;
            case "create":
                showFormCreate(request,response);
                break;
            default:
                showList(request,response);
        }
    }

    private void showFormCreate(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher dispatcher=request.getRequestDispatcher("create.jsp");
        try {
            dispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showResult(HttpServletRequest request, HttpServletResponse response) {
        int id=Integer.parseInt(request.getParameter("id"));
        User user=userManage.find(id);
        RequestDispatcher dispatcher;
        if (user==null){
            dispatcher=request.getRequestDispatcher("notFound.jsp");
        }else {
            request.setAttribute("user",user);
            dispatcher=request.getRequestDispatcher("findResult.jsp");
        }
        try {
            dispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void showList(HttpServletRequest request, HttpServletResponse response) {
        List<User> list=userManage.findAll();
        request.setAttribute("list",list);
        RequestDispatcher dispatcher=request.getRequestDispatcher("list.jsp");
        try {
            dispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
