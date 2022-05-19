import org.json.JSONObject;

public class Weather {
    private final double temperatureF;
    private final double temperatureC;
    private final String currentCondition;
    private final String conditionIcon;

    public Weather(JSONObject jsonObject) {
        JSONObject current = jsonObject.getJSONObject("current");
        JSONObject condition = current.getJSONObject("condition");

        temperatureF = current.getDouble("temp_f");
        temperatureC = current.getDouble("temp_c");
        currentCondition = condition.getString("text");
        conditionIcon = "https:"+condition.getString("icon");
    }

    public double getTemperatureF() {
        return temperatureF;
    }

    public double getTemperatureC() {
        return temperatureC;
    }

    public String getCurrentCondition() {
        return currentCondition;
    }

    public String getConditionIcon() {
        return conditionIcon;
    }
}
