package inventory.db

import inventory.core.Order

trait OrderManager {
  private val dataSource = DataSourceUtils.getDataSource

  def inventoryManager = new InventoryManager

  def placeOrder(item: String, inventoryId: Int, quantity: Int) = {
    val availableQty = inventoryManager.getQuantity(item, inventoryId)
    val itemList = inventoryManager.getItemList
    val newQty = if ((availableQty > quantity) && itemList.contains(item)) availableQty - quantity
    else
      throw new Exception("Insufficient quantity or Item not available")
    val conn = dataSource.getConnection
    val pstmt = conn.prepareStatement("insert into orders(inv_id,item,qty) values (?,?,?)")
    pstmt.setInt(1, inventoryId)
    pstmt.setString(2, item)
    pstmt.setInt(3, quantity)
    pstmt.execute
    conn.close()
    inventoryManager.modifyQuantity(item, newQty, inventoryId)
  }

  def getOrderDetails(orderId: Int): Order = {
    val conn = dataSource.getConnection
    val orderQuery = conn.prepareStatement(s"select * from orders where id=?")
    orderQuery.setInt(1, orderId)
    val orderDetails = orderQuery.executeQuery()
    orderDetails.next()
    val qty = orderDetails.getInt("qty")
    val itemName = orderDetails.getString("item")
    val inventory_id = orderDetails.getInt("inv_id")
    conn.close()
    new Order(orderId, itemName, qty, inventory_id)
  }

  def cancelOrder(orderId: Int) = {
    val orderDetails = getOrderDetails(orderId)
    val conn = dataSource.getConnection
    val cancelOrderQuery = conn.prepareStatement(s"delete from orders where order_id=?")
    cancelOrderQuery.setInt(1, orderId)
    val qty = inventoryManager.getQuantity(orderDetails.item, orderDetails.inventoryId)
    conn.close()
    inventoryManager.modifyQuantity(orderDetails.item, qty + orderDetails.quantity, orderDetails.inventoryId)
  }
}
