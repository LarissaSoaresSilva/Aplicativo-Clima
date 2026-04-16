package com.example;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class Main {

    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {

            System.out.print("Digite o nome da cidade: ");
            String cidade = scanner.nextLine().trim();

            if (cidade.isEmpty()) {
                System.out.println("Por favor, digite o nome de uma cidade válida.");
                return;
            }

            try {
                String cidadeCodificada = URLEncoder.encode(cidade, StandardCharsets.UTF_8);

                // 1. Geocoding
                String geoUrl = "https://geocoding-api.open-meteo.com/v1/search?name="
                        + cidadeCodificada + "&count=1&language=pt&format=json";

                String geoResponse = fazerRequisicao(geoUrl);
                JSONObject geoJson = new JSONObject(geoResponse);

                if (!geoJson.has("results")) {
                    System.out.println("Cidade não encontrada.");
                    return;
                }

                JSONArray results = geoJson.getJSONArray("results");
                if (results.isEmpty()) {
                    System.out.println("Cidade não encontrada.");
                    return;
                }

                JSONObject dadosCidade = results.getJSONObject(0);

                double latitude = dadosCidade.getDouble("latitude");
                double longitude = dadosCidade.getDouble("longitude");
                String nomeCidade = dadosCidade.getString("name");

                // 2. Clima + previsão + código do tempo
                String weatherUrl = "https://api.open-meteo.com/v1/forecast?latitude="
                        + latitude
                        + "&longitude=" + longitude
                        + "&current_weather=true"
                        + "&daily=temperature_2m_max,temperature_2m_min"
                        + "&timezone=auto";

                String weatherResponse = fazerRequisicao(weatherUrl);
                JSONObject weatherJson = new JSONObject(weatherResponse);

                JSONObject climaAtual = weatherJson.getJSONObject("current_weather");

                double temperatura = climaAtual.getDouble("temperature");
                double vento = climaAtual.getDouble("windspeed");
                int weatherCode = climaAtual.getInt("weathercode");

                String descricao = getDescricaoClima(weatherCode);
                String icone = getIconeClima(weatherCode);

                // 3. Clima atual
                System.out.println("\n" + icone + " Clima atual em " + nomeCidade + ":");
                System.out.println("Temperatura: " + temperatura + "°C");
                System.out.println("Vento: " + vento + " km/h");
                System.out.println("Condição: " + descricao);

                // 4. Previsão
                JSONObject daily = weatherJson.getJSONObject("daily");

                JSONArray datas = daily.getJSONArray("time");
                JSONArray max = daily.getJSONArray("temperature_2m_max");
                JSONArray min = daily.getJSONArray("temperature_2m_min");

                DateTimeFormatter formatoBR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                int dias = Math.min(3, datas.length());

                System.out.println("\n📅 Previsão para os próximos " + dias + " dias:");

                for (int i = 0; i < dias; i++) {
                    String dataApi = datas.getString(i);
                    LocalDate dataFormatada = LocalDate.parse(dataApi);
                    String data = dataFormatada.format(formatoBR);

                    System.out.println(data + ": mínima " + min.getDouble(i) + "°C | máxima " + max.getDouble(i) + "°C");
                }

            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    public static String fazerRequisicao(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Erro HTTP: " + response.statusCode());
        }

        return response.body();
    }

    // 🌤 Tradução do clima
    public static String getDescricaoClima(int code) {
        if (code == 0) return "Céu limpo";
        if (code <= 3) return "Parcialmente nublado";
        if (code <= 48) return "Nevoeiro";
        if (code <= 67) return "Chuva";
        if (code <= 77) return "Neve";
        if (code <= 99) return "Tempestade";
        return "Desconhecido";
    }
public static String getIconeClima(int code) {
    if (code == 0) return "(* SOL *)";
    if (code <= 3) return "(* NUVENS *)";
    if (code <= 48) return "(* NEVOA *)";
    if (code <= 67) return "(* CHUVA *)";
    if (code <= 77) return "(* NEVE *)";
    if (code <= 99) return "(* TEMPESTADE *)";
    return "(* CLIMA *)";
}
}