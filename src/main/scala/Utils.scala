import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.{HikariDataSource, HikariConfig}

object Utils {
  lazy val config = ConfigFactory.load()

  private val username = config.getString("username")
  private val password = config.getString("password")
  private val dbUrl = config.getString("url")
  private val driverClass = config.getString("driverClass")

  private val hikariConfig = new HikariConfig()
  hikariConfig.setDataSourceClassName(driverClass)
  hikariConfig.addDataSourceProperty("url", dbUrl)
  hikariConfig.addDataSourceProperty("password", password)
  hikariConfig.addDataSourceProperty("user", username)

  def getDataSource: HikariDataSource = new HikariDataSource(hikariConfig)

}
