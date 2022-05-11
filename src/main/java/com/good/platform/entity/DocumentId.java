package com.good.platform.entity;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@MappedSuperclass
public abstract class DocumentId {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(columnDefinition = "CHAR(32)")
	private String id;
	private boolean active;
	private boolean deleted;
	private Long createdAt;
	private Long modifiedAt;
	private String createdBy;
	private String lastModifiedBy;
	private LocalDateTime syncedAt;

	public DocumentId() {
		this.setActive(true);
		this.createdAt = new Date().getTime();
		this.modifiedAt = this.createdAt;
		this.setDeleted(false);
	}

}
