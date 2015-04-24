package com.maxmind.minfraud.input;

import com.maxmind.minfraud.input.Event.Builder;
import com.maxmind.minfraud.input.Event.Type;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class EventTest {

    @Test
    public void testTransactionId() throws Exception {
        Event event = new Builder().transactionId("t12").build();
        assertEquals("t12", event.getTransactionId());
    }

    @Test
    public void testShopId() throws Exception {
        Event event = new Builder().shopId("s12").build();
        assertEquals("s12", event.getShopId());
    }

    @Test
    public void testTime() throws Exception {
        Date date = new Date();
        Event event = new Builder().time(date).build();
        assertEquals(date, event.getTime());
    }

    @Test
    public void testType() throws Exception {
        Event event = new Builder().type(Type.ACCOUNT_CREATION).build();
        assertEquals(Type.ACCOUNT_CREATION, event.getType());
    }
}