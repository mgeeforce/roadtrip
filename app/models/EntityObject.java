package models;


import javax.persistence.*;
import java.sql.Timestamp;

import play.db.ebean.Model;

import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;

import com.fasterxml.jackson.annotation.*;


/**
 * Abstract ebean superclass
 * @author Mike Gee
 */
@SuppressWarnings("serial")
@MappedSuperclass
public class EntityObject extends Model {
	
    @Id
    public Long id;

    @Version
    @CreatedTimestamp
    @JsonIgnore
    public Timestamp created;
    
    @UpdatedTimestamp
    @JsonIgnore
    public Timestamp updated;
    
    @ManyToOne
    @JsonIgnore
    public User createdBy;
    
    @ManyToOne
    @JsonIgnore
    public User updatedBy;
    
}