
import org.scalatest._

class InventorySystemSpec extends FunSuite with Matchers {

  val inv = new InventorySystem
  val ds = Utils.getDataSource

  test("add new item") {
    val count = inv.itemCount()
    inv.addNewItem("pen")
    assert(inv.itemCount() == (count + 1))
  }

  test("add inventory") {
    val count = inv.inventoriesCount()
    inv.addInventory("hyd", "hyd")
    assert(inv.inventoriesCount() == (count + 1))
  }

  test(""){

  }



}
