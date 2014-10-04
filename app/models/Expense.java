package models;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.avaje.ebean.Page;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import play.data.validation.Constraints;
import play.db.ebean.Model.Finder;

/**
 * @author Mike Gee
 *
 */
@Entity
public class Expense extends EntityObject {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	@Column(nullable = false)
    public Date expenseDate;

	/**
	 * 
	 */
	@Column(precision = 9, scale = 3, nullable = false)
	@Constraints.Required
	public BigDecimal amount;
	
    /**
     * 
     */
    @Column(length = 255, nullable = false)
    @Constraints.MaxLength(255)
    @Constraints.Required
    public String merchantName;

    /**
     * 
     */
    @ManyToOne
    @JsonBackReference
    public Report report;
    
    /**
     * 
     */
    @ManyToOne
    @Constraints.Required
    public Category category;
    
    /**
     * 
     */
    @Column(length = 3, nullable = false)
    @Constraints.Required
    public Currency currency;
    
    /**
     * 
     */
    @Column
    @Constraints.Required
    public boolean remimbursable;
    
	/**
	 * 
	 */
	public byte[] attachment;
    
    public MimeType attachmentMimeType;    
    
    /**
     * 
     */
    @ManyToOne
    @JsonIgnore
    public User user;
    
    /**
     * 
     *
     */
    public static enum Currency {
    	CAD,
    	USD
    }
    
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
     * Generic query helper for entity Expense with id Long
     */
    public static Finder<Long, Expense> find = new Finder<Long, Expense>(Long.class, Expense.class); 

    /**
     * Return a page of expense items
     *
     * @param page Page to display
     * @param pageSize Number of expenses per page
     * @param sortBy Expense property used for sorting
     * @param order Sort order (either or asc or desc)
     * @param filter Filter applied on the title column
     * @param status Status attribute of entity
     */
    public static Page<Expense> page(User localUser, int page, int pageSize, String sortBy, String order, String filter) {
        	return 
                    find.where()
                    	.eq("created_by_id", localUser.id)
                        .ilike("description", "%" + filter + "%")
                        .orderBy(sortBy + " " + order)
                        .findPagingList(pageSize)
                        .setFetchAhead(false)
                        .getPage(page);
     }

}
