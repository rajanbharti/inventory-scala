package inventory.core

trait OrderManager {

  def inventoryManager: InventoryManager

  def placeOrder(item: String, inventoryId: Int, quantity: Int)

  def getOrderDetails(orderId: Int): Order

  def cancelOrder(orderId: Int)
}
