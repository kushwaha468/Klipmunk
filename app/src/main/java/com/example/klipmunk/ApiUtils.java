package com.example.klipmunk;

class ApiUtils {

    private static final String BASE_URL="https://api.klipmunk.com";

    static UserService getUserService()
    {

        return Retrofitclient.getClient(BASE_URL).create(UserService.class);
    }
}
