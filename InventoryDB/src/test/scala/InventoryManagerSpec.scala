package inventory.db

import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers, FunSuite}

class InventoryManagerSpec extends FlatSpec with Matchers with BeforeAndAfterAll {
  val inventorySystem = new InventorySystem

  override def beforeAll {
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

  "Inventory Manager" should " add items" in {
    inventorySystem.addNewItem("pen")
    inventorySystem.addNewItem("pencil")
    inventorySystem.itemCount() should be(2)
  }

  it should "add inventories to system" in {
    inventorySystem.addInventory("hyd", "hyd")
    inventorySystem.addInventory("blr", "blr")
    inventorySystem.addInventory("hyd1", "hyd")
    inventorySystem.inventoriesCount should be(3)
  }

  it should "add items to inventory" in {
    inventorySystem.addItemToInventory("pen", 40, 1)
    inventorySystem.getQuantity("pen", 1) should be(40)
  }

  it should "add quantity" in {
    inventorySystem.addQuantity("pen", 20, 1)
    inventorySystem.getQuantity("pen", 1) should be(60)
  }


}
