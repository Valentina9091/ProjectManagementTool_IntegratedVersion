package fi.hut.soberit.agilefant.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "projects")
public class ProjectEntity {

	@Id
	@Column(name = "id")
	private int		id;

	@Column(name = "name")
	private String	name;

	@Column(name = "product_id")
	private int		productId;

	@Column(name = "description")
	private String	description;

	@Column(name = "startDate", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date	startDate;

	@Column(name = "endDate", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date	endDate;

	@Column(name = "rank")
	private Integer	rank			= 0;

	@Column(name = "baselineLoad")
	private long	baselineLoad	= 0;

	@Column(name = "backlogSize")
	private long	backlogSize		= 0;

	@Column(name = "status")
	private Status	status			= Status.GREEN;

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

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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
