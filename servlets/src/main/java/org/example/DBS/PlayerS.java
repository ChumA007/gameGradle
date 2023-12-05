package org.example.DBS;

import org.example.Connect;
import org.example.Crud;
import org.example.Player;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class PlayerS extends HttpServlet {

    private static final Scanner scanner = new Scanner(System.in);

    private static Crud crud;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter writer = res.getWriter();
        String path = req.getPathInfo();
        String[] pathParts = path.split("/");

        if(!checkPath(path, pathParts, writer)){
            return;
        }

        if(pathParts[1].equals("getByName")){
            try{
                crud = new Crud(Connect.connector());
                String name = req.getParameter("name");
                List<Player> players = crud.findPlayer(name);
                for (Player p : players) {
                    writer.write(p.toString());
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        } else if(pathParts[1].equals("getAll")){ // added else if to handle "getAll" path
            try{
                crud = new Crud(Connect.connector());
                List<Player> players = crud.getAllPlayer();
                for (Player p : players) {
                    writer.write(p.toString());
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter writer = res.getWriter();
        String path = req.getPathInfo();
        String[] pathParts = path.split("/");

        if(!checkPath(path, pathParts, writer)){
            return;
        }

        if(pathParts[1].equals("add")){
            try{
                crud = new Crud(Connect.connector());
                String name = req.getParameter("name");
                crud.addPlayer(name);
                writer.write("Add successful");
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter writer = res.getWriter();
        String path = req.getPathInfo();
        String[] pathParts = path.split("/");

        if(!checkPath(path, pathParts, writer)){
            return;
        }

        if(pathParts[1].equals("update")){
            try{
                crud = new Crud(Connect.connector());
                int id = Integer.parseInt(req.getParameter("id"));
                String name = req.getParameter("name");
                crud.updatePlayer(id, name);
                writer.write("Update successful");
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter writer = res.getWriter();
        String path = req.getPathInfo();
        String[] pathParts = path.split("/");

        if(!checkPath(path, pathParts, writer)){
            return;
        }

        if(pathParts[1].equals("delete")){
            try{
                crud = new Crud(Connect.connector());
                int id = Integer.parseInt(req.getParameter("id"));
                System.out.println(id);
                crud.deletePlayer(id);
                writer.write("Delete successful");
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public boolean checkPath(String path, String[] pathParts, PrintWriter writer) {
        if(path == null){
            writer.write("Path not specified");
            return false;
        }

        if(pathParts.length <= 1){
            writer.write("Invalid path");
            return false;
        }

        return true;
    }
}