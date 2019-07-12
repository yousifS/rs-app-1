/*
 * Copyright (c) 2019. Yousif S
 */

package org.ys.tutorial;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.glassfish.jersey.internal.guava.Preconditions.checkArgument;

/**
 * TodoRepository implementation backed by SQL lite
 */
public class SqliteTodoRepository implements TodoRepository{

    static Logger logger = LoggerFactory.getLogger(SqliteTodoRepository.class);

    private Connection connection;

    public SqliteTodoRepository() throws TodoRepositoryException{
        String url = "jdbc:sqlite:rc.db";
        try {
            connection = DriverManager.getConnection(url);
            connection.createStatement().execute(
                    "CREATE TABLE IF NOT EXISTS todo" +
                            " (id integer primary key, " +
                            "description varchar(200), " +
                            "done boolean, " +
                            "priority varchar(5)" +
                        ");");
            logger.info("Established a connection db");
        } catch (Exception e) {
            logger.error("Failed to get connection", e);
            throw new TodoRepositoryException(e);
        }

    }

    @Override
    public List<TodoItem> listItems() throws TodoRepositoryException{

        try(Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT id, description, done, priority FROM todo");
            List<TodoItem> results = new ArrayList<>();
            while (rs.next()) {
                TodoItem item = TodoItem.builder(rs.getString(2))
                        .id(rs.getInt(1))
                        .done(rs.getBoolean(3))
                        .priority(Priority.valueOf(rs.getString(4)))
                        .build();
                results.add(item);
            }
            rs.close();
            return results;
        } catch (Exception e){
            logger.error("Failed to fetch items from the database",e);
            throw new TodoRepositoryException(e);
        }
    }

    @Override
    public void addItem(TodoItem item) throws TodoRepositoryException{
        checkArgument(item.getId() == null, "Expecting item id to be null");

        try(PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO todo(description, done, priority) VALUES (?, ?, ?);")) {
            statement.setString(1, item.getDescription());
            statement.setBoolean(2, item.isDone());
            statement.setString(3, item.getPriority().name());
            statement.execute();
        } catch (Exception e){
            logger.error("Failed to add an item", e);
            throw new TodoRepositoryException(e);
        }
    }

    @Override
    public void updateItem(TodoItem item) throws TodoRepositoryException{
        checkArgument(item.getId() != null, "Expecting item id to not be null");

        try(PreparedStatement statement = connection.prepareStatement(
                "Update todo set description = ?, done = ?, priority = ? where id = ?;")) {
            statement.setString(1, item.getDescription());
            statement.setBoolean(2, item.isDone());
            statement.setString(3, item.getPriority().name());
            statement.setInt(4, item.getId());
            statement.execute();
        } catch (Exception e){
            logger.error("Failed to update an item", e);
            throw new TodoRepositoryException(e);
        }
    }

    @Override
    public void deleteItem(int id) throws TodoRepositoryException {
        try(PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM todo WHERE id = ? ;")) {
            statement.setInt(1, id);
            statement.execute();
            logger.info("Deleting id "+id);
        } catch (Exception e){
            logger.error("Failed to delete an item", e);
            throw new TodoRepositoryException(e);
        }
    }


}

