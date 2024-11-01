package main;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ConsumoApi {

    public void getExchangeRates(String baseCurrency) {
        try {
            // Define a URL com a moeda base escolhida pelo cliente
            String url_str = "https://v6.exchangerate-api.com/v6/280a1966ee47646c6e196094/latest/" + baseCurrency;
            URL url = new URL(url_str);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            // Converte a resposta para JSON
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject jsonobj = root.getAsJsonObject();

            // Verifica se a requisição foi bem-sucedida
            String req_result = jsonobj.get("result").getAsString();
            if ("success".equals(req_result)) {
                // Acessa as taxas de câmbio
                JsonObject conversionRates = jsonobj.getAsJsonObject("conversion_rates");

                // Cria um mapa para armazenar as taxas de câmbio
                Map<String, Double> exchangeRates = new HashMap<>();

                // Obtém as taxas específicas
                exchangeRates.put("USD", conversionRates.get("USD").getAsDouble());
                exchangeRates.put("BRL", conversionRates.get("BRL").getAsDouble());
                exchangeRates.put("ARS", conversionRates.get("ARS").getAsDouble());
                exchangeRates.put("COP", conversionRates.get("COP").getAsDouble());

                // Armazena as taxas de câmbio na classe Main
                Main.storeExchangeRates(exchangeRates);

                // Exibe as taxas de câmbio
                System.out.println("Taxa de câmbio para " + baseCurrency + " -> USD (Dólar): " + exchangeRates.get("USD"));
                System.out.println("Taxa de câmbio para " + baseCurrency + " -> BRL (Real): " + exchangeRates.get("BRL"));
                System.out.println("Taxa de câmbio para " + baseCurrency + " -> ARS (Peso Argentino): " + exchangeRates.get("ARS"));
                System.out.println("Taxa de câmbio para " + baseCurrency + " -> COP (Peso Colombiano): " + exchangeRates.get("COP"));

            } else {
                System.out.println("Falha ao obter as taxas de câmbio.");
            }
        } catch (Exception e) {
            System.out.println("Ocorreu um erro: " + e.getMessage());
        }
    }
}
