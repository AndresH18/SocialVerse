package com.andresd.socialverse.data.model;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.Date;

@IgnoreExtraProperties
public class AbstractScheduleItem implements Comparable<AbstractScheduleItem> {


    private String id;
    private Date dateTime;
    private String title;
    private String details;

    public AbstractScheduleItem() {
        // required Empty Constructor
    }

    protected AbstractScheduleItem(Date date, String title, String details) {
//        this.dateTime = new Timestamp(date);
        this.dateTime = date;
        this.title = title;
        this.details = details;
//        dateTime = new Timestamp(new java.util.Date());
    }


    public Date getDateTime() {
        return dateTime;
    }

    protected void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    protected void setTimestampFromDate(Date date) {
        this.dateTime = (date);
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
        return dateTime.compareTo(o.dateTime);
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

        if (dateTime != null ? !dateTime.equals(that.dateTime) : that.dateTime != null)
            return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        return details != null ? details.equals(that.details) : that.details == null;
    }

    @Override
    public int hashCode() {
        int result = dateTime != null ? dateTime.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (details != null ? details.hashCode() : 0);
        return result;
    }


    public static class MutableScheduleItem extends AbstractScheduleItem {

        public MutableScheduleItem() {
        }

        public MutableScheduleItem(Date date, String title, String details) {
            super(date, title, details);
        }

        @Override
        public void setDateTime(Date dateTime) {
            super.setDateTime(dateTime);
        }

        @Override
        public void setTimestampFromDate(Date date) {
            super.setTimestampFromDate(date);
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
