package com.thedev.rajaratacommunity.Models;

import java.util.ArrayList;

public class QA {
    String id,email,question;
    ArrayList<String> answers;

    public QA() {
    }

    public QA(String id, String email, String question, ArrayList<String> answers) {
        this.id = id;
        this.email = email;
        this.question = question;
        this.answers = answers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getanswers() {
        return answers;
    }

    public void setanswers(ArrayList<String> answers) {
        this.answers = answers;
    }
}
