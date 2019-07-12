/*
 * Copyright (c) 2019. Yousif S
 */

package org.ys.tutorial;

import org.junit.Test;

import javax.ws.rs.core.Response;


public class TodoIntegrationTest extends IntegrationTest {

    @Test
    public void test() {
        Response response = get("todo");
        assertSuccess(response);
    }



}
