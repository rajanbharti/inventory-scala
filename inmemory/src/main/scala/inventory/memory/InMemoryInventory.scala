package inventory.memory

import java.util.ArrayList

import inventory.core._

class InMemoryInventory {
  val orders = new ArrayList[Order]
  val inventoryInfo = new ArrayList[InventoryInfo]
  val items = new ArrayList[Item]
  val inventory = new ArrayList[Inventory]
}

