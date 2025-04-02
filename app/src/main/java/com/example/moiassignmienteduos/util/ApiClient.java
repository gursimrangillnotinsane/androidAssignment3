package com.example.moiassignmienteduos.util;
//this class is going to be used to send asynchronous http requests to a api endpoint.

//importing all the necessary ohttp modules.

//this is the main class that glues everything together, i.e making network requests.
import okhttp3.OkHttpClient;
//this is required to make an asynchronous request to a given url
import okhttp3.Request;
//this is to get data from the url you sent the request to
import okhttp3.Callback;
//this is to specify the content type (format) of the received data
import okhttp3.MediaType;
public class ApiClient {

    //creating an okhttpclient obj
    private static final OkHttpClient client = new OkHttpClient();
    //creating a var to store the data obtained in Json format
    private static final MediaType JSON =  MediaType.get("application/json; charset=utf-8");

    //creating a public method to send a request asynchronously with a callback that we begrudgingly will have to make
    public static void sendRequest(String givenURL, Callback givenCallback){
        //creating a request variable that will house the built request from the url param we give
        Request request = new Request.Builder().url(givenURL).build();
        client.newCall(request).enqueue(givenCallback);
    }
}
