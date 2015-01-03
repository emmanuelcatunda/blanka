package br.com.hiva.blanka.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notes")
@TypeAlias(value="Note")
public class Note {
	@Id
	private String id;
	@NotEmpty
	private String title;
	private String text;
	private Date date;
	private List<String> tags;

	public Note(String title,String text,String ... tags){
		this.title = title;
		this.text = text;
		this.date = new Date();
		this.tags = new ArrayList<String>(Arrays.asList(tags));
	}
	
	public Note(){}
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void addTag(String tag) {
		this.tags.add(tag);
	}
	
	public String listTags(){
		String tags = "";
		for (String tag : this.tags) {
			tags += tag+" ";
		} 
		return tags;
	}
}