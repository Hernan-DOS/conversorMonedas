/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Hernan
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class ConversorMonedas {

    private static final String API_KEY = "2f5d355e1a960a712bd4ec31";
    private static final String BASE_URL = "https://api.exchangeratesapi.io/latest";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese la cantidad a convertir: ");
        double cantidad = scanner.nextDouble();

        System.out.print("Ingrese la moneda de origen (por ejemplo, USD): ");
        String monedaOrigen = scanner.next().toUpperCase();

        System.out.print("Ingrese la moneda de destino (por ejemplo, EUR): ");
        String monedaDestino = scanner.next().toUpperCase();

        String url = BASE_URL + "?access_key=" + API_KEY + "&symbols=" + monedaDestino;

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                StringBuilder response;
                try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                    String inputLine;
                    response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                }

                JSONObject json = new JSONObject(response.toString());
                double tasaCambio = json.getJSONObject("rates").getDouble(monedaDestino);
                double resultado = cantidad * tasaCambio;

                System.out.println(cantidad + " " + monedaOrigen + " equivalen a " + resultado + " " + monedaDestino);
            } else {
                System.out.println("Error al obtener la tasa de cambio. Código de respuesta: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
