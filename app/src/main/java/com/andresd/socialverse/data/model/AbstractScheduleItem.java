package com.andresd.socialverse.data.model;

import androidx.annotation.NonNull;

public class AbstractScheduleItem {

    private String dateTime;
    private String title;
    private String details;

    public AbstractScheduleItem() {
        // required Empty Constructor
    }

    protected AbstractScheduleItem(String dateTime, String title, String details) {
        this.dateTime = dateTime;
        this.title = title;
        this.details = details;
    }

    public String getDateTime() {
        return dateTime;
    }

    protected void setDateTime(String dateTime) {
        this.dateTime = dateTime;
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

    @NonNull
    @Override
    public String toString() {
        return title;
    }

    public static class MutableScheduleItem extends AbstractScheduleItem {

        public MutableScheduleItem() {
        }

        public MutableScheduleItem(String dateTime, String title, String details) {
            super(dateTime, title, details);
        }

        @Override
        public void setDateTime(String dateTime) {
            super.setDateTime(dateTime);
        }

        @Override
        public void setTitle(String title) {
            super.setTitle(title);
        }

        @Override
        public void setDetails(String details) {
            super.setDetails(details);
        }
    }
}
