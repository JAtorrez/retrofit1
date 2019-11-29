package com.example.retrofit.modelo.atributos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Nextpage {

    @SerializedName("next_page_token")
    @Expose
    private String next_page_token;



    /**
     * No args constructor for use in serialization
     *
     */

    public Nextpage() {
    }


    public Nextpage(String next_page_token) {
        super();
        this.next_page_token = next_page_token;
    }

    /**
     *
     * @return
     * The height
     */


    public String getNextPageToken() {
        return next_page_token;
    }

    /**
     *
     * @param next_page_token
     */

    public void setNextPageToken(String nextpagetoken) {
        this.next_page_token = next_page_token;
    }


}

