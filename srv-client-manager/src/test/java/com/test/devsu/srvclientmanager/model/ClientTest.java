package com.test.devsu.srvclientmanager.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testing Client class")
class ClientTest {

    @Test
    void itShouldBeCreated(){
        Client client =  new Client();
        client.setClientId(1L);
        client.setName("Jhon Doe");
        client.setAge(32);
        client.setPhoneNumber("123456789");
        client.setGender('M');
        client.setAddress("Boulevar Street");
        client.setPassword("password");
        client.setStatus(true);

        assertEquals(1L, client.getClientId());
        assertEquals("Jhon Doe", client.getName());
        assertEquals("123456789", client.getPhoneNumber());
        assertEquals('M', client.getGender());
        assertEquals("Boulevar Street", client.getAddress());
        assertEquals("password", client.getPassword());
        assertEquals(32, client.getAge());
        assertEquals(true, client.getStatus());
    }
}