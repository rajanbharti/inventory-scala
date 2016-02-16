
import org.scalatest._

class InventorySystemSpec extends FunSuite with Matchers with BeforeAndAfter with MustMatchers {

  val inv = new InventorySystem


  test("add new item") {
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

  }

  test("place order and cancel order") {
    inv.placeOrder("pen", 1, 2)

  }

  test("cancel order") {
    inv.cancelOrder(1)
  }


  after {
    val ds = Utils.getDataSource
    val pstmt = ds.getConnection.prepareStatement("truncate ?")
    pstmt.setString(1, "inventory_info")
    pstmt.addBatch()
    pstmt.setString(1, "items")
    pstmt.addBatch()
    pstmt.setString(1, "orders")
    pstmt.addBatch()
    pstmt.setString(1, "inventory")
    pstmt.executeBatch()
  }

}
