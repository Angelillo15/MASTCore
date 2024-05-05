package com.nookure.staff.api.database;

import com.nookure.staff.api.NookureStaff;
import com.nookure.staff.api.exception.database.EntityManagerNotInitializedException;
import com.nookure.staff.api.exception.database.EntityNotFoundException;
import com.nookure.staff.api.exception.database.DatabaseException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.transaction.Transactional;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static java.util.Objects.requireNonNull;

public class TableContainer<T> {
  private final Class<T> entityClass;
  private AbstractPluginConnection conn;
  private NookureStaff plugin;
  private static EntityManagerFactory entityManagerFactory;

  public static <O> TableContainer<O> create(Class<O> entityClass) {
    return new TableContainer<>(entityClass);
  }

  /**
   * Sets up the database connection
   *
   * @param conn The connection to the database
   */
  public void setUpDatabase(@NotNull AbstractPluginConnection conn, NookureStaff plugin) {
    requireNonNull(conn, "Connection cant be null!");
    requireNonNull(plugin, "Plugin cant be null!");
    this.plugin = plugin;
    this.conn = conn;
  }

  private TableContainer(@NotNull Class<T> entityClass) {
    requireNonNull(entityClass, "Entity class cant be null!");
    this.entityClass = entityClass;
  }

  /**
   * Inserts an entity into the database
   *
   * @param entity The entity to create
   * @throws DatabaseException If an error occurs while creating the entity
   */
  public synchronized void insert(@NotNull T entity) throws DatabaseException {
    EntityManager entityManager = getEntityManager();
    EntityTransaction entityTransaction = entityManager.getTransaction();

    try {
      requireNonNull(entity, "Entity can't be null!");

      entityTransaction.begin();

      entityManager.persist(entity);

      entityTransaction.commit();
    } catch (PersistenceException e) {
      if (entityTransaction.isActive()) {
        entityTransaction.rollback();
      }
      throw new DatabaseException("Error occurred while creating the entity", e);
    } finally {
      if (entityManager.isOpen()) {
        entityManager.close();
      }
    }
  }

  /**
   * Inserts an entity into the database asynchronously
   *
   * @param entity The entity to create
   */
  public CompletableFuture<Void> insertAsync(@NotNull T entity) {
    requireNonNull(entity, "Entity cant be null!");

    return CompletableFuture.runAsync(() -> {
      try {
        insert(entity);
      } catch (DatabaseException e) {
        throw new CompletionException(e);
      }
    });
  }

  /**
   * Updates or inserts an entity into the database
   *
   * @param entity The entity to update or insert
   * @throws DatabaseException If an error occurs while updating or inserting the entity
   */
  @Transactional(rollbackOn = DatabaseException.class)
  public synchronized void updateOrInsert(@NotNull T entity) throws DatabaseException {
    requireNonNull(entity, "Entity can't be null!");

    T existingEntity = null;

    try {
      existingEntity = find(entity);
    } catch (DatabaseException e) {
      if (!(e instanceof EntityNotFoundException)) {
        throw e;
      }
    }

    if (existingEntity == null) {
      insert(entity);
    } else {
      edit(entity);
    }
  }

  /**
   * Updates or inserts an entity into the database asynchronously
   *
   * @param entity The entity to update or insert
   */
  public CompletableFuture<Void> updateOrInsertAsync(@NotNull T entity) {
    requireNonNull(entity, "Entity cant be null!");

    return CompletableFuture.runAsync(() -> {
      try {
        updateOrInsert(entity);
      } catch (DatabaseException e) {
        throw new CompletionException(e);
      }
    });
  }

  /**
   * Edits an entity
   *
   * @param entity The entity to edit
   * @return The edited entity
   * @throws DatabaseException If an error occurs while editing the entity
   */
  @Transactional(rollbackOn = DatabaseException.class)
  public T edit(@NotNull T entity) throws DatabaseException {
    try {
      requireNonNull(entity, "Entity can't be null!");
      return getEntityManager().merge(entity);
    } catch (PersistenceException e) {
      throw new DatabaseException("Error occurred while updating the entity", e);
    }
  }

  /**
   * Edits an entity asynchronously
   *
   * @param entity The entity to edit
   */
  public CompletableFuture<T> editAsync(@NotNull T entity) {
    requireNonNull(entity, "Entity cant be null!");

    return CompletableFuture.supplyAsync(() -> {
      try {
        return edit(entity);
      } catch (DatabaseException e) {
        throw new CompletionException(e);
      }
    });
  }

  /**
   * Removes an entity
   *
   * @param entity The entity to remove
   * @throws DatabaseException If an error occurs while removing the entity
   */
  @Transactional(rollbackOn = DatabaseException.class)
  public void remove(@NotNull T entity) throws DatabaseException {
    try {
      requireNonNull(entity, "Entity can't be null!");
      EntityManager entityManager = getEntityManager();
      entityManager.remove(entityManager.merge(entity));
    } catch (PersistenceException e) {
      throw new DatabaseException("Error occurred while removing the entity", e);
    }
  }

  /**
   * Removes an entity asynchronously
   *
   * @param entity The entity to remove
   * @return A CompletableFuture
   */
  public CompletableFuture<Void> removeAsync(@NotNull T entity) {
    requireNonNull(entity, "Entity cant be null!");

    return CompletableFuture.runAsync(() -> {
      try {
        remove(entity);
      } catch (DatabaseException e) {
        throw new CompletionException(e);
      }
    });
  }

  /**
   * Finds an entity
   *
   * @param id The id of the entity
   * @return The entity
   * @throws DatabaseException If an error occurs while finding the entity
   */
  @Transactional(rollbackOn = DatabaseException.class)
  public T find(Object id) throws DatabaseException {
    try {
      requireNonNull(id, "ID can't be null!");
      EntityManager entityManager = getEntityManager();
      T entity = entityManager.find(entityClass, id);
      if (entity == null) {
        throw new EntityNotFoundException(entityClass);
      }
      return entity;
    } catch (EntityNotFoundException e) {
      throw e;
    } catch (Exception e) {
      throw new DatabaseException("Error occurred while finding the entity", e);
    }
  }

  /**
   * Finds an entity asynchronously
   *
   * @param id The id of the entity
   * @return A CompletableFuture
   */
  public CompletableFuture<Void> findAsync(@NotNull Object id) {
    return CompletableFuture.runAsync(() -> {
      try {
        find(id);
      } catch (DatabaseException e) {
        throw new CompletionException(e);
      }
    });
  }

  /**
   * Finds an entity
   *
   * @return The entity
   * @throws DatabaseException If an error occurs while finding the entity
   */
  @Transactional(rollbackOn = DatabaseException.class)
  public List<T> findAll() throws DatabaseException {
    try {
      EntityManager entityManager = getEntityManager();
      CriteriaQuery<T> cq = entityManager.getCriteriaBuilder().createQuery(entityClass);
      cq.select(cq.from(entityClass));
      return entityManager.createQuery(cq).getResultList();
    } catch (Exception e) {
      throw new DatabaseException("Error occurred while finding all entities", e);
    }
  }

  /**
   * Finds an entity asynchronously
   *
   * @return A CompletableFuture
   */
  public CompletableFuture<List<T>> findAllAsync() {
    return CompletableFuture.supplyAsync(() -> {
      try {
        return findAll();
      } catch (DatabaseException e) {
        throw new CompletionException(e);
      }
    });
  }

  protected EntityManager getEntityManager() throws DatabaseException {
    setupEntityManagerFactory();
    if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
      return entityManagerFactory.createEntityManager();
    } else {
      throw new EntityManagerNotInitializedException();
    }
  }

  private void setupEntityManagerFactory() {
    if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
      return;
    }

    if (conn == null) {
      throw new IllegalStateException("Connection cant be null!");
    }
    Properties properties = getProperties();

    System.out.println(conn.getHikariDataSource().getJdbcUrl());

    entityManagerFactory = new HibernatePersistenceProvider()
        .createContainerEntityManagerFactory(new NookurePersistenceUnit(conn), properties);
  }

  @NotNull
  private Properties getProperties() {
    Properties properties = new Properties();

    properties.put("hibernate.hbm2ddl.auto", "update");
    properties.put("hibernate.transaction.jta.platform", "org.hibernate.service.jta.platform.internal.NoJtaPlatform");
    properties.put("hibernate.connection.provider_class", "org.hibernate.hikaricp.internal.HikariCPConnectionProvider");

    if (plugin.isDebug()) {
      properties.put("hibernate.show_sql", "true");
      properties.put("hibernate.format_sql", "true");
    }

    switch (conn.getDataProvider()) {
      case MYSQL -> {
        properties.put("hibernate.dialect", "com.nookure.staff.api.database.dialect.MySQLDialect");
        properties.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        properties.put("hibernate.connection.url", conn.getHikariDataSource().getJdbcUrl());
        properties.put("hibernate.connection.username", conn.getHikariDataSource().getUsername());
        properties.put("hibernate.connection.password", conn.getHikariDataSource().getPassword());
      }

      case MARIADB -> {
        properties.put("hibernate.dialect", "com.nookure.staff.api.database.dialect.MariaDBDialect");
        properties.put("hibernate.connection.driver_class", "org.mariadb.jdbc.Driver");
        properties.put("hibernate.connection.url", conn.getHikariDataSource().getJdbcUrl().replace("mysql", "mariadb"));
        properties.put("hibernate.connection.username", conn.getHikariDataSource().getUsername());
        properties.put("hibernate.connection.password", conn.getHikariDataSource().getPassword());
        properties.put("hibernate.boot.allow_jdbc_metadata_access", "false");
      }

      case SQLITE -> {
        properties.put("hibernate.dialect", "com.nookure.staff.api.database.dialect.SQLiteDialect");
        properties.put("hibernate.connection.driver_class", "org.sqlite.JDBC");
        properties.put("hibernate.connection.url", conn.getHikariDataSource().getJdbcUrl());
      }
    }

    return properties;
  }

  /**
   * Closes the entity manager factory
   * <p>
   * This method should be called when the plugin is disabled.
   * If the entity manager factory is already closed, this method should do nothing.
   * </p>
   */
  public static void closeEntityManagerFactory() {
    if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
      entityManagerFactory.close();
    }
  }
}