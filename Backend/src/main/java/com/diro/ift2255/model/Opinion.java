package com.diro.ift2255.model;

public class Opinion {

    private String message_id;
    private String guild_id;
    private String channel_id;
    private String channel_name;
    private String author_id;
    private String author_name;
    private String created_at;
    public String text;
    public String course_code;
    private String professor_name;

    // Obligatoire pour Jackson
    public Opinion() {}

    public String getMessage_id() {
        return message_id;
    }

    public String getGuild_id() {
        return guild_id;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public String getChannel_name() {
        return channel_name;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getText() {
        return text;
    }

    public String getCourse_code() {
        return course_code;
    }

    public String getProfessor_name() {
        return professor_name;
    }
}
