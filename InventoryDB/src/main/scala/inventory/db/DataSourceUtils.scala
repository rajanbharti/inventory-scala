package inventory.db

import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.{HikariConfig, HikariDataSource}

object DataSourceUtils {
  lazy val config = ConfigFactory.load()

  private val driverClass = config.getString("driverClass")
  private val username = config.getString("username")
  private val password = config.getString("password")
  private val dbUrl = config.getString("url")


  private val hikariConfig = new HikariConfig()
  hikariConfig.setDriverClassName(driverClass)
  hikariConfig.setJdbcUrl(dbUrl)
  hikariConfig.setUsername(username)
  hikariConfig.setPassword(password)

  def getDataSource: HikariDataSource = new HikariDataSource(hikariConfig)

}
