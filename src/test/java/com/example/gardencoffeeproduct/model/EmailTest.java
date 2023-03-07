package com.example.gardencoffeeproduct.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {
    @Test
    public void testInvalidEmail(){
        assertThrows(IllegalArgumentException.class, () -> {
            var email = new Email("accccc");
        });
    }

    @Test
    public void testValidEmail(){
        var email = new Email("accccc@naver.com");
        assertEquals("accccc@naver.com",email.getAddress());
    }
}