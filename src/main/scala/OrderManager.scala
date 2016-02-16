
class OrderManager extends InventoryManager with ItemsManager {

  def placeOrder(item: String, inventoryId: Int, quantity: Int) = {
    val availableQty = getQuantity(item, inventoryId)
    val itemList = getItemList
    val newQty = if ((availableQty > quantity) && itemList.contains(item)) availableQty - quantity
    else
      throw new Exception("Insufficient quantity or Item not available")
    val addOrderStmt = s"insert into orders values (?,?,?)"
    val pstmt = dataSource.getConnection.prepareStatement(addOrderStmt)
    pstmt.setString(1, item)
    pstmt.setInt(2, inventoryId)
    pstmt.setInt(3, quantity)
    pstmt.execute
    updateQuantity(item, inventoryId, newQty)
  }

  def getOrderDetails(orderId: Int): Order = {
    val orderQuery = dataSource.getConnection.prepareStatement(s"select * from orders where order_id=?")
    orderQuery.setInt(1, orderId)
    val orderDetails = orderQuery.executeQuery()

    orderDetails.next()
    val qty = orderDetails.getInt("qty")
    val itemName = orderDetails.getString("item")
    val inventory_id = orderDetails.getInt("inventory_id")

    new Order(orderId, itemName, qty, inventory_id)
  }

  def cancelOrder(orderId: Int) = {
    val orderDetails = getOrderDetails(orderId)
    val cancelOrderQuery = dataSource.getConnection.prepareStatement(s"delete from orders where order_id=?")
    cancelOrderQuery.setInt(1, orderId)
    val qty = getQuantity(orderDetails.item, orderDetails.inventoryId)
    updateQuantity(orderDetails.item, qty + orderDetails.qty, orderDetails.inventoryId)
  }
}

case class Order(orderID: Int, item: String, qty: Int, inventoryId: Int)