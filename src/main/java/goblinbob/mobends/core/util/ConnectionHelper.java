/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: ConnectionHelper.java
 */

package goblinbob.mobends.core.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import goblinbob.mobends.core.asset.AssetLocation;
import goblinbob.mobends.core.supporters.BindPoint;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import net.minecraft.world.entity.Entity;

public class ConnectionHelper
{
    public static ConnectionHelper INSTANCE = new ConnectionHelper();
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private Gson gson;

    /**
     * Makes it so we can't instantiate this class.
     */
    private ConnectionHelper()
    {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.registerTypeAdapter(Color.class, new ColorAdapter());
        builder.registerTypeAdapter(BindPoint.class, new BindPoint.Adapter());
        builder.registerTypeAdapter(AssetLocation.class, new AssetLocation.Adapter());
        this.gson = builder.create();
    }

    public Gson getGson()
    {
        return gson;
    }

    public static <T> T sendGetRequest(URL url, Map<String, String> params, Class<T> responseClass) throws IOException, URISyntaxException
    {
        HttpGet request = new HttpGet();

        URIBuilder uriBuilder = new URIBuilder(url.toURI());
        for (Map.Entry<String, String> entry : params.entrySet())
        {
            uriBuilder.addParameter(entry.getKey(), entry.getValue());
        }

        request.setURI(uriBuilder.build());

        try (CloseableHttpResponse response = INSTANCE.httpClient.execute(request))
        {
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                // return it as a String
                return INSTANCE.gson.fromJson(EntityUtils.toString(entity), responseClass);
            }
        }

        return null;
    }

    public static <T> T sendPostRequest(URL url, JsonObject body, Class<T> responseClass) throws IOException
    {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        byte[] out = (new Gson()).toJson(body).getBytes(StandardCharsets.UTF_8);
        int length = out.length;

        connection.setFixedLengthStreamingMode(length);
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.connect();

        try (OutputStream os = connection.getOutputStream())
        {
            os.write(out);
        }

        // Response
        BufferedReader json = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        T response = INSTANCE.gson.fromJson(json, responseClass);

        return response;
    }
}
