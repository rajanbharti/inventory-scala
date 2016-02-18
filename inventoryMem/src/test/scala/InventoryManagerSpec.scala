package inventory.memory

import org.scalatest.{FlatSpecLike, FlatSpec, Matchers, FunSuite}


class InventoryManagerSpec extends FunSuite with Matchers {
  val inv = new InventorySystem

  test("add items") {
    inv.addNewItem("pen")
    inv.addNewItem("pencil")
    inv.itemCount() should be(2)
  }

  test("add inventories to system") {
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

}
