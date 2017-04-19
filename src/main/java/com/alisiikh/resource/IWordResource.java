package com.alisiikh.resource;

import javax.ejb.Local;
import javax.ws.rs.core.Response;

/**
 * @author alisiikh
 */
@Local
public interface IWordResource {
    Response getWord();
}
