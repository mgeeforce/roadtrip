/**
 * 
 */
package models;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import play.data.validation.Constraints;
import play.db.ebean.Model.Finder;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Mike Gee
 *
 */
@Entity
@Table(name = "roadtrip_user")
public class User extends EntityObject {

	private static final long serialVersionUID = 1L;

	private String authToken;
    
    @Column(length = 255, unique = true, nullable = false)
    @Constraints.MaxLength(255)
    @Constraints.Required
    @Constraints.Email
    private String emailAddress;

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress.toLowerCase();
    }

    @Column(length = 64, nullable = false)
    private byte[] shaPassword;

    @Transient
    @Constraints.Required
    @Constraints.MinLength(6)
    @Constraints.MaxLength(256)
    @JsonIgnore
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        shaPassword = getSha512(password);
    }

    @Column(length = 256, nullable = false)
    @Constraints.Required
    @Constraints.MinLength(2)
    @Constraints.MaxLength(256)
    public String fullName;

    public String createToken() {
        authToken = UUID.randomUUID().toString();
        save();
        return authToken;
    }

    public void deleteAuthToken() {
        authToken = null;
        save();
    }
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdBy")
    @JsonIgnore
    public List<Report> reports = new ArrayList<Report>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdBy")
    @JsonIgnore
    public List<Expense> expenses = new ArrayList<Expense>();

    
    public User() {}

    public User(String emailAddress, String password, String fullName) {
        setEmailAddress(emailAddress);
        setPassword(password);
        this.fullName = fullName;
    }


    public static byte[] getSha512(String value) {
        try {
            return MessageDigest.getInstance("SHA-512").digest(value.getBytes("UTF-8"));
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Finder<Long, User> find = new Finder<Long, User>(Long.class, User.class);
    
    public static User findByAuthToken(String authToken) {
        if (authToken == null) {
            return null;
        }

        try  {
            return find.where().eq("authToken", authToken).findUnique();
        }
        catch (Exception e) {
            return null;
        }
    }

    public static User findByEmailAddressAndPassword(String emailAddress, String password) {
        // todo: verify this query is correct.  Does it need an "and" statement?
        return find.where().eq("emailAddress", emailAddress.toLowerCase()).eq("shaPassword", getSha512(password)).findUnique();
    }

}
