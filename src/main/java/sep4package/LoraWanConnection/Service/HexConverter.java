package sep4package.LoraWanConnection.Service;

import com.google.gson.Gson;
import sep4package.Model.CO2.CO2Measurement;
import sep4package.Model.Humidity.HumidityMeasurement;
import sep4package.Model.Sensors;
import sep4package.Model.Temperature.TemperatureMeasurement;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class HexConverter {
    private String data;

    public HexConverter(String data) {
        this.data = data;
    }

    public HexConverter() {

    }

    public void convertFromHexToInt(UpLinkDataMessage data) {
        int co2Level = 0;
        int temperature = 0;
        int humidity = 0;

        String co2String = new String();
        String humString = new String();
        String temString = new String();
        String winString = new String();
        String timeString = new String();
        String allString = new String();
        boolean windowStatus = false;
        LocalDateTime timestamp;

        if (data.getData() != null) {
            if (data.getData().length() >= 12) {
                //System.out.println(data.getData());
                String hexValCo2 = data.getData().substring(0, 4);
                co2Level = Integer.parseInt(hexValCo2, 16);
                co2String = "{\"co2Level\":\"" + co2Level + "\"}";
                sendPost(
                        "http://sep4v2-env.eba-asbxjuyz.eu-west-1.elasticbeanstalk.com/co2Sensor",
                        co2String);

                String hexValTemp = data.getData().substring(4, 8);
                temperature = Integer.parseInt(hexValTemp, 16) / 10;
                temString = "{\"temperature\":\"" + temperature + "\"}";
                sendPost(
                        "http://sep4v2-env.eba-asbxjuyz.eu-west-1.elasticbeanstalk.com/temperature",
                        temString);

                String hexValHum = data.getData().substring(8, 12);
                humidity = Integer.parseInt(hexValHum, 16) / 10;
                humString = "{\"humidity\":\"" + humidity + "\"}";
                sendPost(
                        "http://sep4v2-env.eba-asbxjuyz.eu-west-1.elasticbeanstalk.com/humiditySensor",
                        humString);
            }
        }

//    String hexValWin = data.getData().substring(12,16);
//    if(hexValWin == "0000")
//    {
//      windowStatus = false;
//    }
//    else if(hexValWin == "0064")
//    {
//      windowStatus = true;
//    }
        timestamp = new Timestamp(data.getTs()).toLocalDateTime();
//    winString = "\"windowOpen\":\"" + windowStatus + "\"}";   //don't forget ,  and delete }
//    System.out.println(winString);
//    timeString = "{\"timestamp\":\"" + timestamp + "\",";
//    allString = timeString + winString;
//    System.out.println(allString);
//    sendPost("http://sep4v2-env.eba-asbxjuyz.eu-west-1.elasticbeanstalk.com/newWindow",allString);
        Gson gson = new Gson();
        TemperatureMeasurement temperatureMeasurement = new TemperatureMeasurement(temperature);
        HumidityMeasurement humidityMeasurement = new HumidityMeasurement(humidity);
        CO2Measurement co2Measurement = new CO2Measurement(co2Level);
        Sensors sensors = new Sensors(temperatureMeasurement, humidityMeasurement, co2Measurement, timestamp);
        //String sensorString = gson.toJson(sensors);
        String sensorString = "{\"temperature\":" + "{" + "\"temperature\":" + temperature + "}," +
                "\"humidity\":{" +
                "\"humidity\":" + humidity + "}," +
                "\"time\":\"" + timestamp + "\"," +
                "\"co2\": {" +
                "\"co2Level\":" + co2Level + "}}";
        System.out.println(sensorString);
        sendPost("http://sep4v2-env.eba-asbxjuyz.eu-west-1.elasticbeanstalk.com/newSensors", sensorString);
    }

    public static void sendPost(String apiurl, String params) {
        try {
            URL url = new URL(apiurl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            String payload = params;
            // String payload = "{\"humidity\":\"29.9\"}";// This should be your json body i.e. {"Name" : "Mohsin"}
            byte[] out = payload.getBytes(StandardCharsets.UTF_8);
            OutputStream stream = connection.getOutputStream();
            stream.write(out);
            System.out.println(connection.getResponseCode()); // This is optional
            connection.disconnect();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Failed successfully");
        }
    }

}


