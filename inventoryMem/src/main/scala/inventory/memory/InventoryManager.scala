package inventory.memory

import java.util
import inventory.core._

import scala.collection.mutable.ListBuffer

class InventoryManager extends inventory.core.InventoryManager {

  override def addItemToInventory(item: String, quantity: Int, inventoryId: Int): Unit = {
    InMemoryInventory.inventory += new InventoryItem(inventoryId, item, quantity)
  }

  override def addNewItem(itemName: String): Unit = {
    InMemoryInventory.items += (itemCount() + 1 -> itemName)
  }

  override def itemCount(): Int = {
    InMemoryInventory.items.size
  }

  override def removeInventory(inventoryId: Int): Unit = {
    InMemoryInventory.inventoryInfo.remove(inventoryId)
  }

  override def addQuantity(item: String, qty: Int, inventoryId: Int): Unit = {
    val oldQty = getQuantity(item, inventoryId)
    modifyQuantity(item, oldQty + qty, inventoryId)
  }

  override def modifyQuantity(item: String, qty: Int, inventoryId: Int): Unit = {
    InMemoryInventory.inventory.foreach(x => if (x.inventoryId == inventoryId && x.item == item) x.quantity = qty)
  }

  override def getQuantity(item: String, inventoryId: Int): Int = {
    var qty: Int = 0
    InMemoryInventory.inventory.foreach(x => if (x.inventoryId == inventoryId && x.item == item) qty = x.quantity)
    qty
  }

  override def inventoriesCount(): Int = {
    InMemoryInventory.inventoryInfo.size
  }

  override def getItemList: ListBuffer[String] = {
    val itemList = new ListBuffer[String]
    InMemoryInventory.items.foreach(x => itemList += InMemoryInventory.items.get(x._1).get)
    itemList
  }

  override def addInventory(inventoryName: String, location: String): Unit = {
    val id = inventoriesCount() + 1
    InMemoryInventory.inventoryInfo += (id -> new InventoryInfo(inventoryName, location))
  }
}
