package org.example;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.example.DBS.MedicalKitS;
import org.example.DBS.WeaponS;
import org.example.DBS.PlayerS;

import java.sql.SQLException;

//Поднимаем tomcat, создаем сервлеты для классов, проверяем через postman
public class Main {
    public static void main(String[] args) throws LifecycleException, SQLException {

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8081);
        tomcat.getConnector();

        Context ctx = tomcat.addContext("", null);

        Wrapper weaponServlet = Tomcat.addServlet(ctx, "weaponS", new WeaponS());
        weaponServlet.setLoadOnStartup(1);
        weaponServlet.addMapping("/weapon/*");

        Wrapper armorServlet = Tomcat.addServlet(ctx, "medicalKitS", new MedicalKitS());
        armorServlet.setLoadOnStartup(1);
        armorServlet.addMapping("/medicalKit/*");

        Wrapper playerServlet = Tomcat.addServlet(ctx, "playerS", new PlayerS());
        playerServlet.setLoadOnStartup(1);
        playerServlet.addMapping("/player/*");

        tomcat.start();
    }
}