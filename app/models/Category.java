/**
 * 
 */
package models;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;

import play.data.validation.Constraints.Required;

import com.avaje.ebean.Page;

/**
 * @author Mike Gee
 *
 */
@Entity
public class Category extends EntityObject {
	
    private static final long serialVersionUID = 1L;

    @Required
    public String name;
    
	@Required
    public String description;
        
    /**
     * Generic query helper for entity Expense with id Long
     */
    public static Finder<Long, Category> find = new Finder<Long, Category>(Long.class, Category.class);
    
    /**
     * Generic helper to populate drop down select boxes
     * @return list of categories ordered by name
     */
    public static Map<String,String> options() {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(Category c: Category.find.orderBy("name").findList()) {
            options.put(c.id.toString(), c.name);
        }
        return options;
    }

    /**
     * Return a page of categories
     *
     * @param page Page to display
     * @param pageSize Number of reports per page
     * @param sortBy Category property used for sorting
     * @param order Sort order (either or asc or desc)
     * @param filter Filter applied on the name column
     */
    public static Page<Category> page(int page, int pageSize, String sortBy, String order, String filter) {
    	return 
            find.where()
                .ilike("name", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .findPagingList(pageSize)
                .setFetchAhead(false)
                .getPage(page);
     }
}
