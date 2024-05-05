package com.nookure.staff.api.database;

import jakarta.persistence.SharedCacheMode;
import jakarta.persistence.ValidationMode;
import jakarta.persistence.spi.ClassTransformer;
import jakarta.persistence.spi.PersistenceUnitInfo;
import jakarta.persistence.spi.PersistenceUnitTransactionType;

import javax.sql.DataSource;
import java.net.URL;
import java.util.List;
import java.util.Properties;

public class NookurePersistenceUnit implements PersistenceUnitInfo {
  private final AbstractPluginConnection conn;

  public NookurePersistenceUnit(AbstractPluginConnection conn) {
    this.conn = conn;
  }

  public static final String PERSISTENCE_UNIT_NAME = "NookurePersistenceUnit";

  @Override
  public String getPersistenceUnitName() {
    return PERSISTENCE_UNIT_NAME;
  }

  @Override
  public String getPersistenceProviderClassName() {
    return "org.hibernate.jpa.HibernatePersistenceProvider";
  }

  @Override
  public PersistenceUnitTransactionType getTransactionType() {
    return PersistenceUnitTransactionType.RESOURCE_LOCAL;
  }

  @Override
  public DataSource getJtaDataSource() {
    return conn.getHikariDataSource();
  }

  @Override
  public DataSource getNonJtaDataSource() {
    return conn.getHikariDataSource();
  }

  @Override
  public List<String> getMappingFileNames() {
    return List.of();
  }

  @Override
  public List<URL> getJarFileUrls() {
    return List.of();
  }

  @Override
  public URL getPersistenceUnitRootUrl() {
    return null;
  }

  @Override
  public List<String> getManagedClassNames() {
    return List.of(
        "com.nookure.staff.api.model.PlayerRow"
    );
  }

  @Override
  public boolean excludeUnlistedClasses() {
    return false;
  }

  @Override
  public SharedCacheMode getSharedCacheMode() {
    return SharedCacheMode.NONE;
  }

  @Override
  public ValidationMode getValidationMode() {
    return ValidationMode.AUTO;
  }

  @Override
  public Properties getProperties() {
    return new Properties();
  }

  @Override
  public String getPersistenceXMLSchemaVersion() {
    return "2.2";
  }

  @Override
  public ClassLoader getClassLoader() {
    return NookurePersistenceUnit.class.getClassLoader();
  }

  @Override
  public void addTransformer(ClassTransformer classTransformer) {

  }

  @Override
  public ClassLoader getNewTempClassLoader() {
    return null;
  }
}
