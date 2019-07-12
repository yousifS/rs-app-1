/*
 * Copyright (c) 2019. Yousif S
 */

package org.ys.tutorial;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.ys.tutorial.TodoRepository.TodoRepositoryException;

@Path("todo")
public class TodoService {

    @Inject
    TodoRepository todoRepo;

    /**
     * @return reutrns a list of todo items
     * @throws TodoRepositoryException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TodoItem> listItems() throws TodoRepositoryException {
        return todoRepo.listItems();
    }

    /**
     * Add a todo item
     * @param item the item to add, expecting id to be null
     * @throws TodoRepositoryException
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void addItem(TodoItem item ) throws TodoRepositoryException {
        if(item.getId() != null){
            throw new WebApplicationException("Cannot specify an id on adding an item", Response.Status.BAD_REQUEST);
        }
        todoRepo.addItem(item);
    }

    /**
     * The item to update, epecting its id to not be null
     * @param item item to update
     * @throws TodoRepositoryException
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(TodoItem item ) throws TodoRepositoryException {
        if(item.getId() == null){
            throw new WebApplicationException("Cannot update an item without an id", Response.Status.BAD_REQUEST);
        }
        todoRepo.updateItem(item);
    }

    /**
     * Delete an item
     * @param id the id of the item to delete
     * @throws TodoRepositoryException
     */
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") int id) throws TodoRepositoryException {
        todoRepo.deleteItem(id);
    }

}
