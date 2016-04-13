package events;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
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
//    public void test_postEvent() throws URISyntaxException {
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
//    @Test
//    public void test_getEvents() throws URISyntaxException{
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
//        System.out.println("event erzeugt" +  event.getReason());
//
//        String body2 =
//                given().param("game", "hund").param("type", "katze")
//                .param("name", "bello").param("reason", "deswegen")
////                .param("resource", "bla").param("player", "blubb")
//                .when()
//                .get("http://localhost:4567/events")
//                .then().statusCode(200)
//                .and()
//                .extract().body().asString();
//
//        System.out.println(body2);
//
//        ArrayList<LinkedTreeMap> eventList = gson.fromJson(body2, ArrayList.class);
//
//        System.out.println(eventList.get(0));
//
//        for (LinkedTreeMap<String, Object> e : eventList) {
//            System.out.println("event in Scheife: " + event);
//            System.out.println("e in Scheife: " + e);
//
////            assertEquals(event.getPlayer(), new URI(e.get("player").toString()));
//            assertEquals(event.getType(), e.get("type"));
////            assertEquals(event.getResource(), new URI(e.get("resource").toString()));
//            assertEquals(event.getReason(), e.get("reason"));
//            assertEquals(event.getName(), e.get("name"));
//            assertEquals(event.getGame(), new URI(e.get("game").toString()));
//        }
//    }

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

    @Test
    public void test_deleteEvents() throws URISyntaxException{
         String body = given().param("game", "hund").param("type", "katze")
                            .param("name", "bello").param("reason", "deswegen")
                        //                .param("resource", "bla").param("player", "blubb")
                            .when()
                            .post("http://localhost:4567/events")
                            .then().statusCode(201).and()
                            .extract().body().asString();

        Event event = gson.fromJson(body, Event.class);

        System.out.println("event erzeugt" +  event);

        String body2 =
                given().param("game", "hund").param("type", "katze")
                        .param("name", "bello").param("reason", "deswegen")
    //                .param("resource", "bla").param("player", "blubb")
                        .when()
                        .delete("http://localhost:4567/events")
                        .then().statusCode(200)
                        .and()
                        .extract().body().asString();

        System.out.println("gelöschtes Event immer noch da: " + event);
        System.out.println("Body: " + body2);

        String body3 =
                         given().param("game", "hund").param("type", "katze")
                        .param("name", "bello").param("reason", "deswegen")
                        //                .param("resource", "bla").param("player", "blubb")
                        .when()
                        .get("http://localhost:4567/events")
                        .then().statusCode(500)
                        .and()
                        .extract().body().asString();

        System.out.println("body3: " + body3);
//        assertEquals(null, body3);


    }
}
