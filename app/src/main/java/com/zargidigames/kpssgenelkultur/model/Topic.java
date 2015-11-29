package com.zargidigames.kpssgenelkultur.model;

public class Topic {

    public int id;
    public String description;

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }

    public String getHtmlContent() {
        //return Html.fromHtml(description).toString();
        return description;
    }
}
