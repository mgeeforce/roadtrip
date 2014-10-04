/**
 * 
 */
package controllers;

import play.*;
import play.mvc.*;

/**
 * @author Mike Gee
 *
 */
@Security.Authenticated(Secured.class)
public class ReceiptController extends Controller {
	
	public static Result getAll() {
		return ok();
	}
	
	public static Result findById(Long id) {
		return ok();
	}
	
	public static Result create() {
		return ok();
	}
	
	public static Result update(Long id) {
		return ok();
	}
	
	public static Result delete(Long id) {
		return ok();
	}

}
