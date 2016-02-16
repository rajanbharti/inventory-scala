
class OrderManager extends InventoryManager {

  def placeOrder(item: String, inventoryId: Int, quantity: Int) = {
    val availableQty = getQuantity(item, inventoryId)
    val itemList = getItemList
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
    modifyQuantity(item, newQty, inventoryId)
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
    val qty = getQuantity(orderDetails.item, orderDetails.inventoryId)
    conn.close()
    modifyQuantity(orderDetails.item, qty + orderDetails.qty, orderDetails.inventoryId)
  }
}

case class Order(orderID: Int, item: String, qty: Int, inventoryId: Int)