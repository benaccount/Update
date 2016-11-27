package com.CommonAction.Test;

import java.io.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


public class ApacheHttpRestClient2 {
 /**
 * This example demonstrates an alternative way to call a URL
 * using the RestAssured
*/
 @Test 
 public void RestAssured_1() {

    // when
    Response response =
            given().
                    header("Accept-Encoding", "application/json").
            when().
                    get("/data/date/my_data");

    // then
    assertThat(response.contentType()).isEqualTo("application/json; charset=utf-8");
    assertThat(response.statusCode()).isEqualTo(200);
    assertThat(response.body().jsonPath().getString("name")).isEqualTo("my_data");
}
@Test
public void RestAssured_2() throws Exception {
try {
URL url = new URL(
“http://someURL.API;);
HttpURLConnection conn = (HttpURLConnection) url.openConnection();
conn.setRequestMethod(“GET”);
conn.setRequestProperty(“Accept”, “application/json”);

if (conn.getResponseCode() != 200) {
throw new RuntimeException(” HTTP error code : ”
+ conn.getResponseCode());
}

Scanner scan = new Scanner(url.openStream());
String entireResponse = new String();
while (scan.hasNext())
entireResponse += scan.nextLine();

System.out.println(“Response : “+entireResponse);

scan.close();

JSONObject obj = new JSONObject(entireResponse );
String responseCode = obj.getString(“status”);
System.out.println(“status : ” + responseCode);

JSONArray arr = obj.getJSONArray(“results”);
for (int i = 0; i < arr.length(); i++) {
String placeid = arr.getJSONObject(i).getString(“place_id”);
String searchformat = arr.getJSONObject(i).getString(
“searchformat”);
System.out.println(“Address : ” + searchformat);

//validating per the requirement
if(searchformat.equalsIgnoreCase(“Expected Rest Assured))
{
System.out.println(“Found Expected”);
}
else
{
System.out.println(“Expected not found”);
}
}

conn.disconnect();
} catch (MalformedURLException e) {
e.printStackTrace();

} catch (IOException e) {

e.printStackTrace();

}
 }
  /**
 * This example demonstrates an alternative way to call a URL
 * using the Apache HttpClient HttpGet and HttpResponse
 * classes

*/
  public final static void main(String[] args) {
    
    HttpClient httpClient = new DefaultHttpClient();
    try {
      // this API call returns json results.
     
      HttpGet httpGetRequest = new HttpGet("http://someURL.API");

      // Execute HTTP request
      HttpResponse httpResponse = httpClient.execute(httpGetRequest);

      System.out.println("----------------------------------------");
      System.out.println(httpResponse.getStatusLine());
      System.out.println("----------------------------------------");

      // Get hold of the response entity
      HttpEntity entity = httpResponse.getEntity();

      // If the response does not enclose an entity, there is no need
      // to bother about connection release
      byte[] buffer = new byte[1024];
      if (entity != null) {
        InputStream inputStream = entity.getContent();
        try {
          int bytesRead = 0;
          BufferedInputStream bis = new BufferedInputStream(inputStream);
          while ((bytesRead = bis.read(buffer)) != -1) {
            String chunk = new String(buffer, 0, bytesRead);
            System.out.println(chunk);
          }
        } catch (IOException ioException) {
          // In case of an IOException the connection will be released
          // back to the connection manager automatically
          ioException.printStackTrace();
        } catch (RuntimeException runtimeException) {
          // In case of an unexpected exception you may want to abort
          // the HTTP request in order to shut down the underlying
          // connection immediately.
          httpGetRequest.abort();
          runtimeException.printStackTrace();
        } finally {
          // Closing the input stream will trigger connection release
          try {
            inputStream.close();
          } catch (Exception ignore) {
          }
        }
      }
    } catch (ClientProtocolException e) {
      // thrown by httpClient.execute(httpGetRequest)
      e.printStackTrace();
    } catch (IOException e) {
      // thrown by entity.getContent();
      e.printStackTrace();
    } finally {
      // When HttpClient instance is no longer needed,
      // shut down the connection manager to ensure
      // immediate deallocation of all system resources
      httpClient.getConnectionManager().shutdown();
    }
  }
}
