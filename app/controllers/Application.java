package controllers;


import play.*;
import play.mvc.*;
import views.html.*;
import static play.libs.Json.toJson;


public class Application extends Controller {

    public static Result index() {
    	Logger.info("in index");
        return ok(index.render());
    }

    public static Result testMessage() {
    	Logger.info("in testMessage");
    	return ok(toJson(new Message("Backbone Hello World")));
    }
    
    public static class Message {
    	public String value;
    	
    	public Message(String value) {
    		this.value = value;
    	}
    }
}
