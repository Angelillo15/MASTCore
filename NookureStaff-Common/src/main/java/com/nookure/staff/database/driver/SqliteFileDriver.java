package com.nookure.staff.database.driver;

import com.craftmend.storm.connection.StormDriver;
import com.craftmend.storm.dialect.Dialect;
import com.craftmend.storm.dialect.sqlite.SqliteDialect;

import java.io.File;
import java.sql.*;

// This class is not mine, it is from the Storm library.
@SuppressWarnings("ALL")
public class SqliteFileDriver implements StormDriver {
  private final Connection conn;

  public SqliteFileDriver(File dataFile) throws SQLException {
    String url = "jdbc:sqlite:" + dataFile.getAbsolutePath();
    this.conn = DriverManager.getConnection(url);
  }

  public SqliteFileDriver(Connection conn) throws SQLException {
    this.conn = conn;
  }

  public void executeQuery(String query, StormDriver.Callback callback, Object... arguments) throws Exception {
    PreparedStatement ps = this.conn.prepareStatement(query);

    try {
      int i = 0;

      while(true) {
        if (i >= arguments.length) {
          callback.onAccept(ps.executeQuery());
          break;
        }

        ps.setObject(i + 1, arguments[i]);
        ++i;
      }
    } catch (Throwable var8) {
      if (ps != null) {
        try {
          ps.close();
        } catch (Throwable var7) {
          var8.addSuppressed(var7);
        }
      }

      throw var8;
    }

    if (ps != null) {
      ps.close();
    }

  }

  public boolean execute(String query) throws SQLException {
    Statement ps = this.conn.createStatement();

    boolean var3;
    try {
      var3 = ps.execute(query);
    } catch (Throwable var6) {
      if (ps != null) {
        try {
          ps.close();
        } catch (Throwable var5) {
          var6.addSuppressed(var5);
        }
      }

      throw var6;
    }

    if (ps != null) {
      ps.close();
    }

    return var3;
  }

  public int executeUpdate(String query, Object... arguments) throws SQLException {
    PreparedStatement ps = this.conn.prepareStatement(query, 1);

    int var6;
    label79: {
      int var12;
      try {
        int o;
        for(o = 0; o < arguments.length; ++o) {
          ps.setObject(o + 1, arguments[o]);
        }

        o = ps.executeUpdate();
        ResultSet generated = ps.getGeneratedKeys();

        label81: {
          try {
            if (!generated.next()) {
              break label81;
            }

            var6 = generated.getInt(1);
          } catch (Throwable var10) {
            if (generated != null) {
              try {
                generated.close();
              } catch (Throwable var9) {
                var10.addSuppressed(var9);
              }
            }

            throw var10;
          }

          if (generated != null) {
            generated.close();
          }
          break label79;
        }

        if (generated != null) {
          generated.close();
        }

        var12 = o;
      } catch (Throwable var11) {
        if (ps != null) {
          try {
            ps.close();
          } catch (Throwable var8) {
            var11.addSuppressed(var8);
          }
        }

        throw var11;
      }

      if (ps != null) {
        ps.close();
      }

      return var12;
    }

    if (ps != null) {
      ps.close();
    }

    return var6;
  }

  public DatabaseMetaData getMeta() throws SQLException {
    return this.conn.getMetaData();
  }

  public boolean isOpen() {
    try {
      return this.conn != null && !this.conn.isClosed();
    } catch (SQLException var2) {
      return false;
    }
  }

  public void close() {
    if (this.isOpen()) {
      try {
        this.conn.close();
      } catch (SQLException var2) {
      }
    }

  }

  public Dialect getDialect() {
    return new SqliteDialect();
  }
}
