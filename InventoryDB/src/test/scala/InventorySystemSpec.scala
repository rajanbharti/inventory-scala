package inventory.db

import org.scalatest._

class InventorySystemSpec extends Suites with BeforeAndAfterAll {

  new InventoryManagerSpec
  new OrderManagerSpec

  override def afterAll() = {
    val ds = DataSourceUtils.getDataSource
    val stm1 = ds.getConnection.createStatement()
    val sql1 = "truncate items"
    val sql2 = "truncate inventory_info"
    val sql3 = "truncate orders"
    val sql4 = "truncate inventory"
    stm1.addBatch(sql1)
    stm1.addBatch(sql2)
    stm1.addBatch(sql3)
    stm1.addBatch(sql4)
    stm1.executeBatch()
  }
}
