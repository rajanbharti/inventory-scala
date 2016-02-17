package inventory.core {

case class Item(id: Int, name: String)

case class InventoryInfo(id: Int, name: String, location: String)

case class Inventory(inventoryId: Int, item: String, quantity: Int)

case class Order(orderId: Int, item: String, quantity: Int, inventoryId: Int)

}