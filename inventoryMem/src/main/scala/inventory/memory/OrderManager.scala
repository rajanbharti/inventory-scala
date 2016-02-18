package inventory.memory

import inventory.core.Order

trait OrderManager extends inventory.core.OrderManager {

  def inventoryManager = new InventoryManager

  def placeOrder(item: String, inventoryId: Int, quantity: Int) = {
    val availableQty = inventoryManager.getQuantity(item, inventoryId)
    if (quantity < availableQty && inventoryManager.getItemList.contains(item)) {
      InMemoryInventory.orders += (orderCount + 1 -> new Order(item, quantity, inventoryId))
      inventoryManager.modifyQuantity(item, availableQty - quantity, inventoryId)
    } else {
      throw new Exception("Insufficient quantity or Item not available")
    }
  }

  def getOrderDetails(orderId: Int): Order = {
    InMemoryInventory.orders.get(orderId).get
  }

  private def orderCount: Int = {
    InMemoryInventory.orders.size
  }

  def cancelOrder(orderId: Int) = {
    val orderDetail = getOrderDetails(orderId)
    val oldQty = inventoryManager.getQuantity(orderDetail.item, orderDetail.inventoryId)
    InMemoryInventory.orders.remove(orderId)
    inventoryManager.modifyQuantity(orderDetail.item, oldQty + orderDetail.quantity, orderDetail.inventoryId)
  }

}
