/**
 * 
 */
package models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import play.data.validation.Constraints;
import play.db.ebean.Model;

/**
 * @author Mike Gee
 *
 */
@Entity
public class Report extends EntityObject {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@Column(length = 255, nullable = false)
    @Constraints.MaxLength(255)
	@Constraints.Required
	public String name;
	
    @JsonManagedReference
    @OneToMany(mappedBy="report", cascade=CascadeType.ALL)
	public List<Expense> expenses;
	
	/**
	 * 
	 */
	@Transient
	private BigDecimal total;

    @Constraints.Required
    public ApprovalStatus status;
    
    @JsonIgnore
    @ManyToOne
    public User approvedBy;
    
    public Date submitted;
    
    public Date approved;
    
 	/**
	 * 
	 *
	 */
	public enum ApprovalStatus {
		
		OPEN(0, "Open"),
		SUBMITTED(1, "Submitted"),
		APPROVED(2, "Approved"),
		REJECTED(3, "Rejected"),
		CLOSED(4, "Closed");
		
		private final int value;
		private final String name;
		
		private ApprovalStatus(int value, String name) {
			this.value = value;
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
		
		public int getValue() {
			return value;
		}
	}

   /**
     * Generic query helper for entity Report with id Long
     */
    public static Model.Finder<Long, Report> find = new Model.Finder<Long, Report>(Long.class, Report.class);

    /**
     * Returns a map of report names for the user in open status
     * 
     * @return		the Map of report names
     */
    public static Map<String,String> options(Long userId) {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(Report r: Report.find.where()
        		.eq("created_by", userId)
        		.eq("status", ApprovalStatus.OPEN)
        		.orderBy("name").findList()) {
            		options.put(r.id.toString(), r.name);
        		}
        return options;
    }

    /**
     * Checks if the user is the owner of the indicated report.
     * 
     * @param	reportId 	the id of the report to check ownership of
     * @param	userId		the id of the user to check report ownership for
     */
    public static boolean isOwnerOf(Long reportId, Long userId) {
        return find.where()
            .eq("created_by", userId)
            .eq("id", reportId)
            .findRowCount() > 0;
    }
    
    /**
     * Checks if the user is the owner of this report.
     * 
     * @param 	user	the user 		
     * @return			if user is owner of this	
     */
    public boolean isOwner(User user) {
    	return (this.createdBy == user);
    }
    
    /**
     * Finds a list of reports for a given user and status.
     * 
     * @param	userId	userId to filter report list
     * @param	status	ApprovalStatus to filter report list
     * @return			list of filtered reports
     */
    public static List<Report> findForUserAndStatus(Long userId, ApprovalStatus status) {
        return find.where()
            .eq("created_by", userId)
            .eq("status", status)
            .findList();
    }
}
