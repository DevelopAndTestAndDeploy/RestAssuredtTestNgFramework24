package com.spotify.oauth2.test;

import com.spotify.oauth2.api.StatusCode;
import com.spotify.oauth2.api.applicationapi.PlaylistApi;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.DataLoader;
import io.qameta.allure.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.spotify.oauth2.api.SpecBuilder.getRequestSpec;
import static com.spotify.oauth2.api.SpecBuilder.getResponseSpec;
import static com.spotify.oauth2.utils.FakerUtils.generateDescription;
import static com.spotify.oauth2.utils.FakerUtils.generateName;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Epic(("Spotify oauth 2.0"))
@Feature("Playlist API")

public class PlaylistsTests extends BaseTest {

    @Story("Create a playlist story")
    @Link("https://example.org")
    @Link(name = "allure", type = "mylink")
    @TmsLink("12345")
    @Issue("1234567")
    @Description("This is the description")
    @Test(description = "Should be able to create a playlist")
    public void shouldBeAbleToCreateAPlaylist(){
        Playlist requestPlayList = playListBuilder(generateName(),
                generateDescription(),
                false);


        Response response = PlaylistApi.post(requestPlayList);
        assertStatusCode(response.statusCode(), StatusCode.CODE_201);

       assertPlaylistEqual(response.as(Playlist.class), requestPlayList);


    }

    @Test
    public void shouldBeAbleToGetAPlaylist(){

        Playlist requestPlayList = playListBuilder("New Playlist",
                "New playlist description",
                true);


        Response response = PlaylistApi.get(DataLoader.getInstance().getPlaylistId());
        assertStatusCode(response.statusCode(), StatusCode.CODE_200);

        assertPlaylistEqual(response.as(Playlist.class), requestPlayList);



    }

    @Test
    public void shouldBeAbleToUpdateAPlaylist(){

        Playlist requestPlayList = playListBuilder(generateName(),
                generateDescription(),
                false);


        Response response = PlaylistApi.update(DataLoader.getInstance().getPlaylistId(),
                requestPlayList);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200);


    }

    @Story("Create a playlist story")
    @Test
    public void shouldNotBeAbleToCreateAPlaylistWithoutName(){

        Playlist requestPlayList = playListBuilder("",
                generateDescription(),
                false);


        Response response = PlaylistApi.post(requestPlayList);
        assertStatusCode(response.statusCode(), StatusCode.CODE_400);
        Error error = response.as(Error.class);

        assertError(response.as(Error.class), StatusCode.CODE_400);


       }

    @Story("Create a playlist story")
    @Test
    public void shouldNotBeAbleToCreateAPlaylistWithAnExpiredToken(){

        String invalidToken = "12345";
        Playlist requestPlayList = playListBuilder(generateName(),
                generateDescription(),
                false);

        Response response = PlaylistApi.post(invalidToken, requestPlayList);
        assertStatusCode(response.statusCode(), StatusCode.CODE_401);
        assertError(response.as(Error.class), StatusCode.CODE_401);
    }

    @Step
    public Playlist playListBuilder(String name, String description, boolean _public){
        return Playlist.builder().
                name(name).
                description(description).
                _public(_public).
                build();

       
    }

    @Step
    public void assertPlaylistEqual(Playlist responsePlayList, Playlist requestPlayList){
        assertThat(responsePlayList.getName(), equalTo(requestPlayList.getName()));
        assertThat(responsePlayList.getDescription(), equalTo(requestPlayList.getDescription()));
        assertThat(responsePlayList.get_public(), equalTo(requestPlayList.get_public()));
    }

    @Step
    public void assertStatusCode(int actualStatusCode, StatusCode statusCode){
        assertThat(actualStatusCode, equalTo(statusCode.code));

    }

    @Step
    public void assertError(Error responseErr, StatusCode statusCode){
        assertThat(responseErr.getError().getStatus(), equalTo(statusCode.code));
        assertThat(responseErr.getError().getMessage(), equalTo(statusCode.message));
    }

}
