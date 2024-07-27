package com.spotify.oauth2.api.applicationapi;

import com.spotify.oauth2.api.RestResource;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.ConfigLoader;
import io.restassured.response.Response;

import static com.spotify.oauth2.api.Route.PLAYLISTS;
import static com.spotify.oauth2.api.Route.USERS;
import static com.spotify.oauth2.api.SpecBuilder.getRequestSpec;
import static com.spotify.oauth2.api.SpecBuilder.getResponseSpec;
import static com.spotify.oauth2.api.TokenManager.getToken;
import static io.restassured.RestAssured.given;

public class PlaylistApi {


    public static Response post(Playlist requestPlayList) {
        return RestResource.post(USERS + "/" + ConfigLoader.getInstance().getUser() + PLAYLISTS,
                getToken(), requestPlayList);

    }

    public static Response post(String token, Playlist requestPlayList) {
        return RestResource.post(USERS + "/" + ConfigLoader.getInstance().getUser() + PLAYLISTS, token,
                requestPlayList);

    }

    public static Response get(String playlistId) {
        return RestResource.get(PLAYLISTS + "/" + playlistId, getToken());

    }

    public static Response update(String playlistId, Playlist requestPlayList) {
        return RestResource.update(PLAYLISTS + "/" + playlistId, getToken(), requestPlayList);

    }

}
