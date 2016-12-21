package fi.hut.soberit.agilefant.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.hibernate.annotations.BatchSize;

import flexjson.JSON;

/**
 * Hibernate entity bean which represents an hour entry owned by a backlog.
 * 
 * Represents a job effort logged for a specific backlog.
 * 
 * 
 * @see fi.hut.soberit.agilefant.model.HourEntry
 * @author
 * 
 */
@Entity
@BatchSize(size = 20)
@XmlAccessorType( XmlAccessType.NONE )
public class BacklogHourEntry extends HourEntry {

    private Backlog backlog;
    // Added by Sujit
    public BacklogHourEntry(){}
     
    public BacklogHourEntry(BacklogHourEntry other)
    {
        this.setBacklog(other.getBacklog());
    }
    // End
    public void setBacklog(Backlog backlog) {
        this.backlog = backlog;
    }

    @ManyToOne
    @JSON(include = false)
    public Backlog getBacklog() {
        return backlog;
    }

}
