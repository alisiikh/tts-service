package com.alisiikh.filters;

import com.alisiikh.beans.AppState;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import java.io.IOException;

/**
 * @author alisiikh
 */
public class HitsCounterFilter implements ContainerRequestFilter {

    @Inject
    AppState appState;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        System.out.println("Hit " + appState.addHitAndGet());
    }
}
