package inventory.core {

case class InventoryInfo(name: String, location: String)

case class InventoryItem(inventoryId: Int, item: String, var quantity: Int)

case class Order(item: String, quantity: Int, inventoryId: Int)

}