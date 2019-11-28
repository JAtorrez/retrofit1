package com.example.retrofit.modelo.atributos;

public class Nextpage {
  private String next_page_token;



    /**
     * No args constructor for use in serialization
     *
     */
    public Nextpage() {
    }

    /**
     *
     * @param next_page_token
     */
    public Nextpage(String next_page_token) {
        super();
        this.next_page_token = next_page_token;
    }


    public String getNextPageToken() {
        return next_page_token;
    }

    public void setNextPageToken(String nextpagetoken) {
        this.next_page_token = next_page_token;
    }


}

