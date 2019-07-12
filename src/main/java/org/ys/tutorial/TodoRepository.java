/*
 * Copyright (c) 2019. Yousif S
 */

package org.ys.tutorial;

import java.util.List;

public interface TodoRepository {
    /**
     * @return list all the items
     * @throws TodoRepositoryException
     */
    List<TodoItem> listItems() throws TodoRepositoryException;

    /**
     * Add an item
     * @param item the item to add, expecting the id to be null
     * @throws TodoRepositoryException
     */
    void addItem(TodoItem item) throws TodoRepositoryException;

    /**
     * Update an existing item
     * @param item the item to update, expecting the id to not be null
     * @throws TodoRepositoryException
     */
    void updateItem(TodoItem item) throws TodoRepositoryException;

    /**
     * @param id the id of the item to delete
     * @throws TodoRepositoryException
     */
    void deleteItem(int id) throws TodoRepositoryException;

    /**
     * Exception to wrap the underlying exceptions thrown by the different repo implementations
     */
    class TodoRepositoryException extends Exception{
        /**
         * @param cause The underlying cause of the exception
         */
        public TodoRepositoryException(Throwable cause) {
            super(cause);
        }
    }
}
