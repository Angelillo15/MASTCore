package com.nookure.staff.database;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nookure.staff.Constants;
import com.nookure.staff.api.Logger;
import com.nookure.staff.api.config.partials.DatabaseConfig;
import com.nookure.staff.api.database.DataProvider;
import com.zaxxer.hikari.HikariDataSource;
import io.ebean.annotation.Platform;
import io.ebean.dbmigration.DbMigration;
import org.flywaydb.core.Flyway;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Singleton
public class MigrationService {
  @Inject
  private Logger logger;

  /**
   * Generate migrations for the database
   *
   * @param folder the folder to generate the migrations in
   */
  public void generateMigrations(@NotNull File folder) {
    Objects.requireNonNull(folder, "folder cannot be null");

    System.setProperty("ddl.migration.version", Constants.BUILD_TIME);

    if (!folder.exists() && !folder.mkdirs()) {
      throw new IllegalStateException("Failed to create the migrations folder");
    }

    DbMigration dbMigration = DbMigration.create();

    dbMigration.setApplyPrefix("V");
    dbMigration.addPlatform(Platform.SQLITE);
    dbMigration.addPlatform(Platform.MYSQL);
    dbMigration.setPathToResources(folder.getAbsolutePath());

    logger.debug("Generating migrations in %s", folder.getAbsolutePath());

    try {
      dbMigration.generateMigration();
    } catch (IOException e) {
      logger.severe("Failed to generate migrations %s", e);
      throw new RuntimeException(e);
    }

    logger.debug("Migrations generated successfully");
  }

  /**
   * Load the migrations for the database
   *
   * @param config           the database config
   * @param source           the hikari data source
   * @param migrationsFolder the folder containing the migrations
   */
  public void loadMigrations(@NotNull DatabaseConfig config, @NotNull HikariDataSource source, File migrationsFolder) {
    Flyway.configure()
        .dataSource(source)
        .locations(
            config.getType() == DataProvider.SQLITE ?
                "filesystem:" + migrationsFolder.getAbsolutePath() + "/sqlite" :
                "filesystem:" + migrationsFolder.getAbsolutePath() + "/mysql"
        )
        .baselineOnMigrate(true)
        .baselineVersion(Constants.VERSION_CODE)
        .baselineDescription(Constants.VERSION)
        .load()
        .migrate();

    logger.debug("Migrations loaded successfully");
  }
}
