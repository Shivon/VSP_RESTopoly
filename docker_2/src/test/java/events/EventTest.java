package events;

import com.google.gson.Gson;
import com.jayway.restassured.RestAssured;
import events.model.Event;
import org.junit.Test;
import spark.utils.Assert;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static com.jayway.restassured.RestAssured.*;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by Jana on 10.04.2016.
 */
public class EventTest {

    Gson gson = new Gson();
    List<Event> eventList = new ArrayList<>();

//    @Test
//    public void test_postEvent1() throws URISyntaxException {
//        String body = given().param("game", "hund").param("type", "katze")
//                .param("name", "bello").param("reason", "deswegen")
////                .param("resource", "bla").param("player", "blubb")
//                .when()
//                .post("http://localhost:4567/events")
//                .then().statusCode(201).and()
//                .extract().body().asString();
//
//        Event event = gson.fromJson(body, Event.class);
//
//        assertEquals(event.getGame(), new URI("hund"));
//        assertEquals(event.getName(), "bello");
//        assertEquals(event.getType(), "katze");
//        assertEquals(event.getReason(), "deswegen");
////        assertEquals(event.getResource(), new URI("bla"));
////        assertEquals(event.getPlayer(), new URI("blubb"));
//        Assert.notNull(event.getId());
//        System.out.println(body);
//    }
//
    @Test
    public void test_getEvents() throws URISyntaxException{
        String body = given().param("game", "hund").param("type", "katze")
                .param("name", "bello").param("reason", "deswegen")
                .param("resource", "bla").param("player", "blubb")
                .when()
                .post("http://localhost:4567/events")
                .then().statusCode(201).and()
                .extract().body().asString();

        Event event = gson.fromJson(body, Event.class);

        String body2 = given().param("game", "hund").param("type", "katze")
                .param("name", "bello").param("reason", "deswegen")
                .param("resource", "bla").param("player", "blubb")
                .when()
                .get("http://localhost:4567/events/")
                .then().statusCode(200)
                .and()
                .extract().body().asString();


        ArrayList<Event> eventList = gson.fromJson(body2, ArrayList.class);

        for (Event e : eventList) {
            assertEquals(event.getPlayer(), e.getPlayer());
            assertEquals(event.getType(), e.getType());
            assertEquals(event.getResource(), e.getResource());
            assertEquals(event.getReason(), e.getReason());
            assertEquals(event.getName(), e.getName());
            assertEquals(event.getGame(), e.getGame());
        }
    }

//    @Test
//    public void test_getEvents_EventId() throws URISyntaxException{
//        String body = given().param("game", "hund").param("type", "katze")
//                .param("name", "bello").param("reason", "deswegen")
//                .param("resource", "bla").param("player", "blubb")
//                .when()
//                .post("http://localhost:4567/events")
//                .then().statusCode(201).and()
//                .extract().body().asString();
//        Event event = gson.fromJson(body, Event.class);
//
//        String eventId = event.getId();
//
//        String body2 = get("http://localhost:4567/events/" + eventId)
//                .then().statusCode(200)
//                .and()
//                .extract().body().asString();
//
//
//        Event event2 = gson.fromJson(body2, Event.class);
//
//        assertEquals(event, event2);
//        assertEquals(event.getId(), event2.getId());
//        assertEquals(event.getGame(), event2.getGame());
//        assertEquals(event.getName(), event2.getName());
//        assertEquals(event.getType(), event2.getType());
//        assertEquals(event.getReason(), event2.getReason());
//        assertEquals(event.getResource(), event2.getResource());
//        assertEquals(event.getPlayer(), event2.getPlayer());
//    }
//
//    @Test
//    public void test_deleteEvents() throws URISyntaxException{
//        String body = given().param("game", "hund").param("type", "katze")
//                .param("name", "bello").param("reason", "deswegen")
//                .param("resource", "bla").param("player", "blubb")
//                .when()
//                .delete("http://localhost:4567/events")
//                .then().statusCode(200).and()
//                .extract().body().toString();
//
//        Event event = gson.fromJson(body, Event.class);
//
//    String body2 = given().param("game", "hund").param("type", "katze")
//            .param("name", "bello").param("reason", "deswegen")
//            .param("resource", "bla").param("player", "blubb")
//            .when()
//            .delete("http://localhost:4567/events/")
//            .then().statusCode(200)
//            .and()
//            .extract().body().asString();
//
//
//             ArrayList<Event> eventList = gson.fromJson(body2, ArrayList.class);
//    }
}
