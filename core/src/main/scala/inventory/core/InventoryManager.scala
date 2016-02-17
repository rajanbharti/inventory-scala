package inventory.core

import java.util

abstract class InventoryManager {

  def addItemToInventory(item: String, quantity: Int, inventoryId: Int)

  def getQuantity(item: String, inventoryId: Int): Int

  def modifyQuantity(item: String, quantity: Int, inventoryId: Int)

  def addQuantity(item: String, quantity: Int, inventoryId: Int)


  def addNewItem(itemName: String)

  def itemCount(): Int


  def getItemList: util.ArrayList[String]

  def addInventory(inventoryName: String, location: String)

  def removeInventory(inventoryId: Int)

  def inventoriesCount(): Int
}
