package inventory.db

import org.scalatest.{FlatSpec, Matchers}

class OrderManagerSpec extends FlatSpec with Matchers {
  val inv = new InventorySystem

  "Order Manager" should "place order" in {
    inv.placeOrder("pen", 1, 10)
    inv.getQuantity("pen", 1) should be(50)
  }

  it should "cancel order" in {
    inv.cancelOrder(1)
    inv.getQuantity("pen", 1) should be(60)
  }

}
