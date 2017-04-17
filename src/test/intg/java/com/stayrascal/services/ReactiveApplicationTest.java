package com.stayrascal.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
// default value is "file:src/main/webapp", @WebAppConfiguration("classpath:test-web-resources")
// must be used in conjunction with @ContextConfiguration
@WebAppConfiguration
@ContextConfiguration(locations = {}, classes = {}, inheritInitializers = true) // load the context
public class ReactiveApplicationTest {

    @Test
    public void contextLoads() {

    }
}