/*
 *  Copyright (c) 2020 Otávio Santana and others
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  and Apache License v2.0 which accompanies this distribution.
 *  The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 *  and the Apache License v2.0 is available at http://www.opensource.org/licenses/apache2.0.php.
 *  You may elect to redistribute this code under either of these licenses.
 *  Contributors:
 *  Otavio Santana
 */
package org.eclipse.jnosql.artemis.document.reactive;

import jakarta.nosql.NonUniqueResultException;
import jakarta.nosql.document.DocumentDeleteQuery;
import jakarta.nosql.document.DocumentQuery;
import jakarta.nosql.mapping.IdNotFoundException;
import jakarta.nosql.mapping.document.DocumentTemplate;
import org.eclipse.jnosql.artemis.reactive.Observable;

import java.time.Duration;
import java.util.Optional;
import java.util.stream.Stream;

public interface ReactiveDocumentTemplate {

    /**
     * Saves entity
     *
     * @param entity entity to be saved
     * @param <T>    the instance type
     * @return the entity saved
     * @throws NullPointerException when document is null
     */
    <T> Observable<T> insert(T entity);

    /**
     * Saves entity with time to live
     *
     * @param entity entity to be saved
     * @param <T>    the instance type
     * @param ttl    the time to live
     * @return the entity saved
     * @throws NullPointerException when either entity or ttl are null
     */
    <T> Observable<T> insert(T entity, Duration ttl);

    /**
     * Saves entity, by default it's just run for each saving using
     * {@link DocumentTemplate#insert(Object)},
     * each NoSQL vendor might replace to a more appropriate one.
     *
     * @param entities entities to be saved
     * @param <T>      the instance type
     * @return the entity saved
     * @throws NullPointerException when entities is null
     */
    <T> Observable<T> insert(Iterable<T> entities);

    /**
     * Saves documents collection entity with time to live, by default it's just run for each saving using
     * {@link DocumentTemplate#insert(Object, Duration)},
     * each NoSQL vendor might replace to a more appropriate one.
     *
     * @param entities entities to be saved
     * @param <T>      the instance type
     * @param ttl      time to live
     * @return the entity saved
     * @throws NullPointerException when entities is null
     */
    <T> Observable<T> insert(Iterable<T> entities, Duration ttl);

    /**
     * Updates a entity
     *
     * @param entity entity to be updated
     * @param <T>    the instance type
     * @return the entity updated
     * @throws NullPointerException when entity is null
     */
    <T> Observable<T> update(T entity);

    /**
     * Updates entity, by default it's just run for each saving using
     * {@link DocumentTemplate#update(Object)},
     * each NoSQL vendor might replace to a more appropriate one.
     *
     * @param entities entities to be saved
     * @param <T>      the instance type
     * @return the entity saved
     * @throws NullPointerException when entities is null
     */
    <T> Observable<T> update(Iterable<T> entities);

    /**
     * Deletes an entity
     *
     * @param query query to delete an entity
     * @return the {@link Observable} with the result
     * @throws NullPointerException query is null
     */
    Observable<Void> delete(DocumentDeleteQuery query);

    /**
     * Finds entities from query
     *
     * @param query - query to figure out entities
     * @param <T>   the instance type
     * @return entities found by query
     * @throws NullPointerException when query is null
     */
    <T> Observable<T> select(DocumentQuery query);


    /**
     * Executes a query then bring the result as a {@link Stream}
     *
     * @param query the query
     * @param <T>   the entity type
     * @return the result as {@link Stream}
     * @throws NullPointerException when the query is null
     */
    <T> Observable<T> query(String query);

    /**
     * Executes a query then bring the result as a unique result
     *
     * @param query the query
     * @param <T>   the entity type
     * @return the result as {@link Optional}
     * @throws NullPointerException     when the query is null
     * @throws NonUniqueResultException if returns more than one result
     */
    <T> Observable<T> singleResult(String query);

    /**
     * Finds by Id.
     *
     * @param entityClass the entity class
     * @param id          the id value
     * @param <T>         the entity class type
     * @param <K>         the id type
     * @return the entity instance otherwise {@link Optional#empty()}
     * @throws NullPointerException when either the entityClass or id are null
     * @throws IdNotFoundException  when the entityClass does not have the Id annotation
     */
    <T, K> Observable<T> find(Class<T> entityClass, K id);

    /**
     * Deletes by Id.
     *
     * @param entityClass the entity class
     * @param id          the id value
     * @param <T>         the entity class type
     * @param <K>         the id type
     * @return the {@link Observable} with the result
     * @throws NullPointerException when either the entityClass or id are null
     * @throws IdNotFoundException  when the entityClass does not have the Id annotation
     */
    <T, K> Observable<Void> delete(Class<T> entityClass, K id);

    /**
     * Returns the number of elements from document collection
     *
     * @param documentCollection the document collection
     * @return the number of elements
     * @throws NullPointerException          when document collection is null
     * @throws UnsupportedOperationException when the database dot not have support
     */
    Observable<Long> count(String documentCollection);

    /**
     * Returns the number of elements from document collection
     *
     * @param <T>        entityType
     * @param entityType the document collection
     * @return the number of elements
     * @throws NullPointerException          when document collection is null
     * @throws UnsupportedOperationException when the database dot not have support
     */
    <T> Observable<Long> count(Class<T> entityType);

    /**
     * Returns a single entity from query
     *
     * @param query - query to figure out entities
     * @param <T>   the instance type
     * @return an entity on {@link Optional} or {@link Optional#empty()} when the result is not found.
     * @throws NonUniqueResultException when the result has more than 1 entity
     * @throws NullPointerException     when query is null
     */
    <T> Observable<T> singleResult(DocumentQuery query);
}
