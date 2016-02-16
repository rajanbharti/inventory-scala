
import org.scalatest._

class InventoryManagerSpec extends FunSuite with Matchers with BeforeAndAfterAll {

  val inv = new InventorySystem

  test("add item") {
    inv.addNewItem("pen")
    inv.addNewItem("pencil")
    inv.itemCount() should be(2)
  }


  test("add inventory") {
    inv.addInventory("hyd", "hyd")
    inv.addInventory("blr", "blr")
    inv.addInventory("hyd1", "hyd")
    inv.inventoriesCount should be(3)
  }

  test("add items to inventory") {
    inv.addItemToInventory("pen", 40, 1)
    inv.getQuantity("pen", 1) should be(40)
  }

  test("add quantity") {
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


  override def afterAll = {
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
