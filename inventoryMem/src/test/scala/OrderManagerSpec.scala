package inventory.memory

import org.scalatest.{FlatSpecLike, FlatSpec, Matchers, FunSuite}

class OrderManagerSpec extends FunSuite with Matchers {

  val inventorySystem = new InventorySystem

  test("place order") {
    inventorySystem.placeOrder("pen", 1, 10)
    inventorySystem.getQuantity("pen", 1) should be(50)
  }

  test("cancel order") {
    inventorySystem.cancelOrder(1)
    inventorySystem.getQuantity("pen", 1) should be(60)
  }

}
