package com.fanlinc.fanlinc.post;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fanlinc.fanlinc.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity(name = "Posts")
public class Post {
	
	@Id
    @GeneratedValue
	private Long postId;
	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("content")
	private String content;
	
	@JsonProperty("authorId")
	private Long authorId;
	
	@JsonProperty("authorEmail")
	private String authorEmail;
	
	public Post(String title, String content, Long authorId, String authorEmail) {
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.authorEmail = authorEmail;
    }
	
	public Long getPostId() {
        return postId;
    }

    public void setPostId(long postId) { this.postId = postId; }

    public String getPostTitle() {
        return title;
    }

    public void setPostTitle(String title) {
        this.title = title;
    }

    public Long getAuthorId() { return authorId; }

    public void setAuthorId(Long authorId) {this.authorId = authorId; }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }
}
