package fi.hut.soberit.agilefant.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "iterations")
public class IterationEntity {

	@Id
	@Column(name = "id")
	private int		id;

	@Column(name = "name")
	private String	name;

	@Column(name = "project_id")
	private int		projectId;

	@Column(name = "description")
	private String	description;

	@Column(name = "startDate", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date	startDate;

	@Column(name = "endDate", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date	endDate;

	@Column(name = "baselineLoad")
	private long	baselineLoad	= 0;

	@Column(name = "backlogSize")
	private long	backlogSize		= 0;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getBaselineLoad() {
		return baselineLoad;
	}

	public void setBaselineLoad(long baselineLoad) {
		this.baselineLoad = baselineLoad;
	}

	public long getBacklogSize() {
		return backlogSize;
	}

	public void setBacklogSize(long backlogSize) {
		this.backlogSize = backlogSize;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
