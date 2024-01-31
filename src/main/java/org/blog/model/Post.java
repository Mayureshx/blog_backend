package org.blog.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Data;

@Entity
@Data
//@Builder
public class Post {
	
	@Id
	private long postId;
	
	@Column(length = 300, nullable = false, updatable = true)
	private String postTitle;
	
	@CreationTimestamp
	private Date postCreation;
	
	@UpdateTimestamp
	private Date postUpdation;
	
	@Column(length = 8000, nullable = false, updatable = true)
	private String postContent;
	
	@Column(nullable = false)
	@Lob //learge object
	private byte[] postImage;
	
	@Column(length = 20, updatable = true,nullable = true)
	private String status;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="categoryId")
	private Category category;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="userId")
	private User user;
}
