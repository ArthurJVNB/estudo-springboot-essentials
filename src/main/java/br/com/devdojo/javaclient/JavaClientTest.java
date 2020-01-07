package br.com.devdojo.javaclient;

import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class JavaClientTest {
    public static void main(String[] args) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        String username = "seya";
        String password = "cavaleiro";

        try {
            URL url = new URL("http://localhost:8080/v1/protected/students/3");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.addRequestProperty("Authorization", "Basic " + encodeUsernamePassword(username, password));
            System.out.println(encodeUsernamePassword(username, password));
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder jsonStringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonStringBuilder.append(line);
            }
            System.out.println(jsonStringBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(reader);
            if (connection != null)
                connection.disconnect();
        }
    }

    private static String encodeUsernamePassword(String username, String password) {
        String usernamePassword = username + ":" + password;
        return new String(Base64.encodeBase64(usernamePassword.getBytes()));
    }
}
