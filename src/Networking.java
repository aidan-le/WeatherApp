import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;

public class Networking {
    private final static String API_KEY = "30f89806b18649fc976133318221805";

    public static Weather getCurrentWeather(String zipCode) {
        final String url = "https://api.weatherapi.com/v1/current.json?key="+API_KEY+"&q="+zipCode+"&aqi=no";
        String response = makeAPICall(url);

        if (response == null) {
            return null;
        }

        return parseJSON(response);
    }

    private static String makeAPICall(String url) {
        try {
            URI myUri = URI.create(url);
            HttpRequest request = HttpRequest.newBuilder().uri(myUri).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static Weather parseJSON(String json) {
        JSONObject jsonObject = new JSONObject(json);
        return new Weather(jsonObject);
    }
}
