import java.util

trait InventoryManager {
  protected val dataSource = Utils.getDataSource

  def addItemToInventory(item: String, quantity: Int, inventoryId: Int) = {
    val stmt = s"insert into inventory values(?,?,?)"
    val pstmt = dataSource.getConnection.prepareStatement(stmt)
    pstmt.setInt(1, quantity)
    pstmt.setInt(2, inventoryId)
    pstmt.setString(3, item)
    pstmt.execute()
  }

  protected def getQuantity(item: String, inventoryId: Int): Int = {
    val stmt = s"select qty from inventory where item=? and inventory_id=?"
    val pstmt = dataSource.getConnection.prepareStatement(stmt)
    val rs = pstmt.executeQuery()
    rs.next()
    rs.getInt("qty")
  }

  def updateQuantity(item: String, quantity: Int, inventoryId: Int) = {
    val stmt = s"update inventory set qty=? where inventory_id=? and item=?"
    val pstmt = dataSource.getConnection.prepareStatement(stmt)
    pstmt.setInt(1, quantity + getQuantity(item, inventoryId))
    pstmt.setInt(2, inventoryId)
    pstmt.setString(3, item)
    pstmt.execute()
  }
}
