/*
 * Copyright (c) 2019. Yousif S
 */

package org.ys.tutorial;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static org.glassfish.jersey.internal.guava.Preconditions.checkNotNull;

/**
 * Class represents a todo item
 */
public class TodoItem {

    private final Integer id;
    private final String description;
    private final boolean done;
    private final Priority priority;

    /**
     * @param id the id of the todo item
     * @param description the description of the todo item
     * @param done flag indicating the item is done or not
     * @param priority the priority of the item
     */
    @JsonCreator
    private TodoItem(@JsonProperty("id") Integer id,
                     @JsonProperty("description") String description,
                     @JsonProperty("done") boolean done,
                     @JsonProperty("priority") Priority priority) {
        this.id = id;
        this.description = checkNotNull(description);
        this.done = done;
        this.priority = priority == null ? Priority.NORMAL : priority;
    }

    /**
     * @return the id of the item
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return description of the item
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return true if the item is done
     */
    public boolean isDone() {
        return done;
    }

    /**
     * @return priority of the item
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * @param description description of the item
     * @return builder for a new item
     */
    public static Builder builder(String description){
        return new Builder(description);
    }

    /**
     * Builder class for a {@code Todo Item}
     */
    public static class Builder {
        private Integer id;
        private String description;
        private boolean done;
        private Priority priority;

        /**
         * @param description the description of the item
         */
        private Builder(String description){
            this.description = description;
        }

        /**
         * Set the id of the item
         * @param id the id of the item
         * @return builder
         */
        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        /**
         * @param done
         * @return builder
         */
        public Builder done(boolean done) {
            this.done = done;
            return this;
        }

        /**
         * Set the priority of the item
         * @param priority the priority
         * @return builder
         */
        public Builder priority(Priority priority) {
            this.priority = priority;
            return this;
        }

        /**
         * @return item corresponding to this builder
         */
        public TodoItem build() {
            return new TodoItem(id, description, done, priority);
        }
    }

}
