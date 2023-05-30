package com.example.proyectogticsgrupo1.translate;

public class DayTranslator {

    public static String translateDay(String day) {
        switch (day) {
            case "Sunday":
                return "Domingo";
            case "Monday":
                return "Lunes";
            case "Tuesday":
                return "Martes";
            case "Wednesday":
                return "Miércoles";
            case "Thursday":
                return "Jueves";
            case "Friday":
                return "Viernes";
            case "Saturday":
                return "Sábado";
            default:
                return day;
        }
    }
}
