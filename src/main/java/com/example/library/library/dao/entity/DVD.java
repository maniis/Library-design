package com.example.library.library.dao.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.library.library.dao.enums.ItemType;

@Entity
@Table(name="dvds")
public class DVD implements Item {

	@Id
	private String dvdId;

	private String title;

	private String year;

	private String size;

	private String status;

	public DVD() {
	}

	public String getId() {
		return dvdId;
	}
	public void setId(String dvdId) {
		this.dvdId = dvdId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String string) {
		this.status = string;
	}

	
	@Override
	public String toString() {
		return "\nDVD [dvdId=" + dvdId + ", size=" + size  + ", title=" + title + ", year=" + year
				+ "]";
	}

	@Override
	public String getItemType() {
		
		return ItemType.DVD.getType();
	}
	@Override
	public String getItemId() {
		return String.valueOf(dvdId);
	}

	@Override
	public String getItemName() {
		return title;
	}
}