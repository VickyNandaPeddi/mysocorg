package com.good.platform.entity;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.ToString;

/**
 * @author Arya C Achari
 * @since 18-Nov-2021
 *
 */
@ToString
@Data
@MappedSuperclass
public abstract class BaseId {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private boolean active;
	private boolean deleted;
	private Long createdAt;
	private Long modifiedAt;
	private String createdBy;
	private String lastModifiedBy;
	private LocalDateTime syncedAt;

	public BaseId() {
		this.setActive(true);
		this.createdAt = new Date().getTime();
		this.modifiedAt = this.createdAt;
		this.setDeleted(false);
	}

}
