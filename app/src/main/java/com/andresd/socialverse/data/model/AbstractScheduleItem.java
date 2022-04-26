package com.andresd.socialverse.data.model;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;

import java.util.Date;

public class AbstractScheduleItem implements Comparable<AbstractScheduleItem> {

    // FIXME : use java.util.Date?
    private String id;
    private Timestamp timestamp;
    private String title;
    private String details;

    public AbstractScheduleItem() {
        // required Empty Constructor
    }

    protected AbstractScheduleItem(Timestamp dateTime, String title, String details) {
        this.timestamp = dateTime;
        this.title = title;
        this.details = details;
//        dateTime = new Timestamp(new java.util.Date());
    }

    protected AbstractScheduleItem(Date date, String title, String details) {
        this(new Timestamp(date), title, details);
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    protected void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    protected void setTimestampFromDate(Date date) {
        this.timestamp = new Timestamp(date);
    }

    public String getTitle() {
        return title;
    }

    protected void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    protected void setDetails(String details) {
        this.details = details;
    }

    public String getId() {
        return id;
    }

    protected void setId(String id) {
        this.id = id;
    }

    @Override
    public int compareTo(AbstractScheduleItem o) {
        return timestamp.compareTo(o.timestamp);
    }

    @NonNull
    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractScheduleItem that = (AbstractScheduleItem) o;

        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null)
            return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        return details != null ? details.equals(that.details) : that.details == null;
    }

    @Override
    public int hashCode() {
        int result = timestamp != null ? timestamp.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (details != null ? details.hashCode() : 0);
        return result;
    }


    public static class MutableScheduleItem extends AbstractScheduleItem {

        public MutableScheduleItem() {
        }

        public MutableScheduleItem(Timestamp timestamp, String title, String details) {
            super(timestamp, title, details);
        }

        public MutableScheduleItem(Date date, String title, String details) {
            super(date, title, details);
        }

        @Override
        public void setTimestampFromDate(Date date) {
            super.setTimestampFromDate(date);
        }

        @Override
        public void setTimestamp(Timestamp timestamp) {
            super.setTimestamp(timestamp);
        }

        @Override
        public void setTitle(String title) {
            super.setTitle(title);
        }

        @Override
        public void setDetails(String details) {
            super.setDetails(details);
        }

        @Override
        public void setId(String id) {
            super.setId(id);
        }
    }
}
