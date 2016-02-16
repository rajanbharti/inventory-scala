
import org.scalatest._

class InventorySystemSpec extends FunSuite with Matchers with BeforeAndAfter {

  val inv = new InventorySystem

  test("item count") {
    inv.addNewItem("pen")
    inv.addNewItem("pencil")
    inv.itemCount() should be(2)
  }

  test("inventory count") {

    inv.addInventory("hyd", "hyd")
    inv.addInventory("blr", "blr")
    inv.addInventory("hyd1", "hyd")
    inv.inventoriesCount should be(3)
  }

  test("add items to inventory") {
    inv.addItemToInventory("pen", 40, 1)
    inv.getQuantity("pen", 1) should be(40)
  }

  test("update quantity") {
    inv.addQuantity("pen", 20, 1)
    inv.getQuantity("pen", 1) should be(60)
  }

  test("place order") {
    inv.placeOrder("pen", 1, 10)
    inv.getQuantity("pen", 1) should be(50)

  }

  test("cancel order") {
    inv.cancelOrder(1)
    inv.getQuantity("pen", 1) should be(60)
  }

  after {
    val ds = Utils.getDataSource
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
