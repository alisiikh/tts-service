package com.alisiikh.beans;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author alisiikh
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class AppState {

    private AtomicLong hits = new AtomicLong(0L);

    public long addHitAndGet() {
        return hits.incrementAndGet();
    }
}
