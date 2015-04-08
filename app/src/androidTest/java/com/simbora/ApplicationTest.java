package com.simbora;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    protected String testeRepositorio="";
    public ApplicationTest() {
        super(Application.class);
    }

    public String getTesteRepositorio() {
        return testeRepositorio;
    }
}