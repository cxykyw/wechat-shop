package com.kyw.common.entity;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseEntity {

	private Long id;
	private Timestamp created;
	private Timestamp updated;
}
