package org.example.CRUD;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Scanner;

public class dbConexion {

    private Connection conn = null;

    private void conexion() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "root", "gimena");
            System.out.println("Conexión correcta");
        } catch (SQLException e) {
            System.out.println("Hubo problemas de conexión: " + e.getMessage());
        }
    }

    public void insertarDatos() throws SQLException {
        conexion();
        Scanner sc = new Scanner(System.in);

        System.out.println("Ingrese nombre:");
        String nombre = sc.nextLine();

        System.out.println("Ingrese apellido:");
        String apellido = sc.nextLine();

        System.out.println("Ingrese fecha de registro (en formato 'YYYY-MM-DD'):");
        String fechaRegistros = sc.nextLine();
        Date fechaRegistro = Date.valueOf(fechaRegistros);

        System.out.println("Ingrese sueldo base:");
        BigDecimal sueldoBase = sc.nextBigDecimal();

        sc.nextLine(); // Limpiar el buffer del scanner

        System.out.println("Ingrese sexo:");
        String sexo = sc.nextLine();

        System.out.println("Ingrese edad:");
        int edad = sc.nextInt();

        System.out.println("Ingrese longitud:");
        double longitud = sc.nextDouble();

        System.out.println("Ingrese latitud:");
        double latitud = sc.nextDouble();

        sc.nextLine(); // Limpiar el buffer del scanner

        System.out.println("Ingrese comentario:");
        String comentario = sc.nextLine();

        String query = "INSERT INTO datos (nombre, apellido, `fecha de registro`, `sueldo base`, sexo, edad, longitud, latitud, comentario) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = null;

        ps = conn.prepareStatement(query);
        ps.setString(1, nombre);
        ps.setString(2, apellido);
        ps.setString(3, String.valueOf(fechaRegistro));
        ps.setBigDecimal(4, sueldoBase);
        ps.setString(5, sexo);
        ps.setInt(6, edad);
        ps.setDouble(7, longitud);
        ps.setDouble(8, latitud);
        ps.setString(9, comentario);

        ps.executeUpdate();
        System.out.println("Fila(s) insertada(s) correctamente.");
    }

    public void leerDatos() throws SQLException {
        conexion();
        String query = "SELECT * FROM datos";
        PreparedStatement ps = null;
        ps = conn.prepareStatement(query);

        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()) {
            System.out.println("Código Personal: " + resultSet.getInt("codigo personal"));
            System.out.println("Nombre: " + resultSet.getString("nombre"));
            System.out.println("Apellido: " + resultSet.getString("apellido"));
            Date fechaRegistros = resultSet.getDate("fecha de registro");
            if (fechaRegistros != null) {
                System.out.println("Fecha de Registro: " + fechaRegistros.toString());
            } else {
                System.out.println("Fecha de Registro: N/A");
            }
            System.out.println("Sueldo Base: " + resultSet.getBigDecimal("sueldo base"));
            System.out.println("Sexo: " + resultSet.getString("sexo"));
            System.out.println("Edad: " + resultSet.getInt("edad"));
            System.out.println("Longitud: " + resultSet.getDouble("longitud"));
            System.out.println("Latitud: " + resultSet.getDouble("latitud"));
            System.out.println("Comentario: " + resultSet.getString("comentario"));
            System.out.println("----------------------");
        }
    }

    public void actualizarDatos() throws SQLException {
        conexion();
        Scanner sc = new Scanner(System.in);

        System.out.println("Ingrese el código personal del registro a actualizar:");
        int codigoPersonal = sc.nextInt();
        sc.nextLine(); // Limpiar el buffer del scanner

        System.out.println("Seleccione el campo que desea actualizar: \n" +
                "1. Nombre \n" +
                "2. Apellido \n" +
                "3. Fecha de Registro \n" +
                "4. Sueldo Base \n" +
                "5. Sexo \n" +
                "6. Edad \n" +
                "7. Longitud \n" +
                "8. Latitud \n" +
                "9. Comentario");

        int opcion = sc.nextInt();
        sc.nextLine(); // Limpiar el buffer del scanner

        String campoAActualizar = "";
        String nuevoValor = "";

        switch (opcion) {
            case 1:
                campoAActualizar = "nombre";
                System.out.println("Ingrese el nuevo nombre:");
                nuevoValor = sc.nextLine();
                break;
            case 2:
                campoAActualizar = "apellido";
                System.out.println("Ingrese el nuevo apellido:");
                nuevoValor = sc.nextLine();
                break;
            case 3:
                campoAActualizar = "fecha de registro";
                System.out.println("Ingrese la nueva fecha de registro (en formato 'YYYY-MM-DD'):");
                nuevoValor = sc.nextLine();
                break;
            case 4:
                campoAActualizar = "sueldo base";
                System.out.println("Ingrese el nuevo sueldo base:");
                nuevoValor = String.valueOf(sc.nextBigDecimal());
                sc.nextLine(); // Limpiar el buffer del scanner
                break;
            case 5:
                campoAActualizar = "sexo";
                System.out.println("Ingrese el nuevo sexo:");
                nuevoValor = sc.nextLine();
                break;
            case 6:
                campoAActualizar = "edad";
                System.out.println("Ingrese la nueva edad:");
                nuevoValor = String.valueOf(sc.nextInt());
                sc.nextLine(); // Limpiar el buffer del scanner
                break;
            case 7:
                campoAActualizar = "longitud";
                System.out.println("Ingrese la nueva longitud:");
                nuevoValor = String.valueOf(sc.nextDouble());
                sc.nextLine(); // Limpiar el buffer del scanner
                break;
            case 8:
                campoAActualizar = "latitud";
                System.out.println("Ingrese la nueva latitud:");
                nuevoValor = String.valueOf(sc.nextDouble());
                sc.nextLine(); // Limpiar el buffer del scanner
                break;
            case 9:
                campoAActualizar = "comentario";
                System.out.println("Ingrese el nuevo comentario:");
                nuevoValor = sc.nextLine();
                break;
            default:
                System.out.println("Opción no válida.");
                return;
        }

        String query = "UPDATE datos SET " + campoAActualizar + " = ? WHERE `codigo personal` = ?";

        PreparedStatement ps = null;

        ps = conn.prepareStatement(query);
        if (opcion == 4 || opcion == 6 || opcion == 7 || opcion == 8) {
            // Caso de campos numéricos
            ps.setObject(1, nuevoValor);
        } else {
            ps.setString(1, nuevoValor);
        }

        ps.setInt(2, codigoPersonal);

        int rowsAffected = ps.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println(rowsAffected + " fila(s) actualizada(s) correctamente.");
        } else {
            System.out.println("No se encontraron registros para actualizar con el código personal proporcionado.");
        }
    }

    public void eliminarRegistro() throws SQLException {
        conexion();
        Scanner sc = new Scanner(System.in);

        System.out.println("Ingrese el código personal del registro que desea eliminar:");
        int codigoPersonal = sc.nextInt();
        sc.nextLine(); // Limpiar el buffer del scanner

        String query = "DELETE FROM datos WHERE `codigo personal` = ?";

        PreparedStatement ps = null;

        ps = conn.prepareStatement(query);
        ps.setInt(1, codigoPersonal);

        int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println(rowsAffected + " fila(s) eliminada(s) correctamente.");
        } else {
            System.out.println("No se encontraron registros para eliminar con el código personal proporcionado.");
        }
    }
}





