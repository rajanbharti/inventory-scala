package inventory.memory

import org.scalatest.Suites

class InventorySystemSpec extends Suites {
  new InventoryManagerSpec
  new OrderManagerSpec
}
