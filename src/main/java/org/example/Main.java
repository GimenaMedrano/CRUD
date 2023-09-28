package org.example;

import org.example.CRUD.dbConexion;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {


        dbConexion cone= new dbConexion();


        try {
            cone.insertarDatos();
            cone.leerDatos();
            cone.actualizarDatos();
            cone.eliminarRegistro();
        } catch (SQLException e) {
            System.out.println("Error en la operación: " + e.getMessage());
        }
    }

}