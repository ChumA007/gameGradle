package org.example.DBS;


import org.example.Connect;
import org.example.Crud;
import org.example.items.MedicalKit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MedicalKitS extends HttpServlet {

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

        if(pathParts[1].equals("get")){
            try{
                crud = new Crud(Connect.connector());
                int id = Integer.parseInt(req.getParameter("itemId"));
                MedicalKit medicalKit = crud.findMedicalKit(id);
                writer.write(medicalKit.toString());
            } catch (SQLException e){
                e.printStackTrace();
            }
        } else if(pathParts[1].equals("getAll")){
            try{
                crud = new Crud(Connect.connector());
                List<MedicalKit> medicalKits = crud.getAllMedicalKit();
                for (MedicalKit m : medicalKits) {
                    writer.write(m.toString());
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
                Random random = new Random();
                int id = random.nextInt(Integer.MAX_VALUE - 500) + 500;
                String name = req.getParameter("name");
                double weight = Double.parseDouble(req.getParameter("weight"));
                int width = Integer.parseInt(req.getParameter("width"));
                int length = Integer.parseInt(req.getParameter("length"));
                int durability = Integer.parseInt(req.getParameter("durability"));
                int healingPower = Integer.parseInt(req.getParameter("healingPower"));
                MedicalKit medicalKit = new MedicalKit(id, name, weight, width, length, durability, healingPower);
                crud.addMedicalKit(medicalKit);
                writer.write(medicalKit.toString());
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
                double weight = Double.parseDouble(req.getParameter("weight"));
                int width = Integer.parseInt(req.getParameter("width"));
                int length = Integer.parseInt(req.getParameter("length"));
                int durability = Integer.parseInt(req.getParameter("durability"));
                int healingPower = Integer.parseInt(req.getParameter("healingPower"));
                crud.updateMedicalKit(new MedicalKit(id, name, weight, width, length, durability, healingPower));
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
                crud.deleteMedicalKit(id);
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