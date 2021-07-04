package com.example.library.library.dao.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="records")
public class Record{

	private static final long DEFAULT_PERIOD = 7;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int recordId;

	private String itemId;

	private long customerId;

	private String itemName;

	private Date issueDate;

	private Date returnDate;

	private Double fine;

	private String itemType;

	private long duration;

	public Record() {
	}

	public static class RecordBuilder {
		private String itemId;
		private long customerId;
		private String itemName;
		private Date issueDate;
		private Date returnDate;
		private Double fine;
		private String itemType;
		private long duration;


        public RecordBuilder(String itemId, String itemType, String itemName) {
            this.itemId = itemId;
            this.itemType = itemType;
            this.itemName = itemName;
        }

        public RecordBuilder setIssueDate(Date issueDate) {
            this.issueDate = issueDate;
            return this;
		}

		public RecordBuilder setReturnDate(Date returnDate) {
            this.returnDate = returnDate;
            return this;
		}

		public RecordBuilder setCustomerId(long customerId) {
            this.customerId = customerId;
            return this;
		}

		public RecordBuilder setDuration(long duration) {
            this.duration = duration;
            return this;
		}

		public RecordBuilder setFine(Double fine) {
            this.fine = fine;
            return this;
		}
		public Record build() {
            return new Record(this);
        }
	}

	private Record(RecordBuilder builder) {
        this.itemId = builder.itemId;
        this.customerId = builder.customerId;
        this.duration = builder.duration;
        this.issueDate = builder.issueDate;
        this.itemName = builder.itemName;
        this.itemType = builder.itemType;
        this.fine = builder.fine;
		this.returnDate = builder.returnDate;
    }

	public Record(String itemId,String itemName, long customerId, Date issueDate, Double fine, String itemType) {
		this.itemName = itemName;
		this.itemId = itemId;
		this.customerId = customerId;
		this.issueDate = issueDate;
		this.fine = fine;
		this.itemType = itemType;
		this.duration = DEFAULT_PERIOD;
	}

	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public long getCustomerId() {
		return customerId;
	}


	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public Double getFine() {
		return fine;
	}

	public void setFine(Double fine) {
		this.fine = fine;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getItemType() {
		return itemType;
	}

	public static long getDefaultPeriod() {
		return DEFAULT_PERIOD;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}


	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Override
	public String toString() {
		return "Record [customerId=" + customerId + ", duration=" + duration + ", fine=" + fine + ", issueDate="
				+ issueDate + ", itemId=" + itemId + ", itemName=" + itemName + ", itemType=" + itemType + ", recordId="
				+ recordId + ", returnDate=" + returnDate + "]";
	}

	


	
	
}