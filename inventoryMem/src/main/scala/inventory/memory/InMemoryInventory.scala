package inventory.memory

import inventory.core._
import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object InMemoryInventory {
  val orders = new mutable.ListMap[Int, Order]
  var inventoryInfo = new mutable.ListMap[Int, InventoryInfo]
  var items = new mutable.ListMap[Int, String]
  val inventory = new ListBuffer[InventoryItem]

}

