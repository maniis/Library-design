package com.example.library.library.dao.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.library.library.dao.enums.ItemType;

@Entity
@Table(name="books")
public class Book implements Item {

	@Id
	private String bookId;

	private String title;

	private String author;

	private String publisher;
	
	private int pageCount;
	
	private String status;

	public Book() {
	}

	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public String getStatus() {
		return status;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	@Override
	public String toString() {
		return "\nBook [author=" + author + ", bookId=" + bookId + ", pageCount=" + pageCount + ", publisher=" + publisher
				+ ", title=" + title + "]";
	}
	@Override
	public String getItemType() {
		
		return ItemType.BOOK.getType();
	}
	@Override
	public String getItemId() {
		return bookId;
	}

	@Override
	public String getItemName() {
		return title;
	}

	
}