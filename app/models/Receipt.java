package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.avaje.ebean.Page;
import com.fasterxml.jackson.annotation.JsonBackReference;

import play.data.validation.Constraints;
import play.db.ebean.Model.Finder;

/**
 * @author Mike Gee
 *
 */
@Entity
public class Receipt extends EntityObject {
	
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JsonBackReference
	public Expense expense;
	
	/**
	 * 
	 */
	@Constraints.Required
	public byte[] attachment;
    
	@Constraints.Required
	public MimeType attachmentMimeType;    
    
    /**
     * 
     *
     */
    public static enum MimeType {
    	GIF ("image/gif"),
    	JPG ("image/jpeg"),
    	PNG ("image/png"),
    	PDF ("application/pdf");
    	
    	private final String contentType;
    	
    	private MimeType(String contentType) {
    		this.contentType = contentType;
    	}
    	
    	public static MimeType get(String contentType) {
    	      for (MimeType type: values()) {
    	        if (type.contentType.equals(contentType)) {
    	          return type;
    	        }
    	      }
    	      return null;
    	    }
    }
    
    /**
     * Generic query helper for entity Receipt with id Long
     */
    public static Finder<Long, Receipt> find = new Finder<Long, Receipt>(Long.class, Receipt.class); 

    /**
     * Return a page of receipts
     *
     * @param page Page to display
     * @param pageSize Number of receipts per page
     * @param sortBy Expense property used for sorting
     * @param order Sort order (either or asc or desc)
     * @param filter Filter applied on the title column
     * @param status Status attribute of entity
     */
    public static Page<Receipt> page(User localUser, int page, int pageSize, String sortBy, String order, String filter) {
        	return 
                    find.where()
                    	.eq("created_by_id", localUser.id)
                        .orderBy(sortBy + " " + order)
                        .findPagingList(pageSize)
                        .setFetchAhead(false)
                        .getPage(page);
     }

}
