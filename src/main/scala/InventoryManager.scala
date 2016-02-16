import java.util

trait InventoryManager {
  protected val dataSource = Utils.getDataSource

  def addItemToInventory(item: String, quantity: Int, inventoryId: Int) = {
    val stmt = s"insert into inventory(inv_id,item,qty) values(?,?,?)"
    val pstmt = dataSource.getConnection.prepareStatement(stmt)
    pstmt.setInt(1, inventoryId)
    pstmt.setString(2, item)
    pstmt.setInt(3, quantity)
    pstmt.execute()
    pstmt.close()
  }

  def getQuantity(item: String, inventoryId: Int): Int = {
    val stmt = s"select qty from inventory where item=? and inv_id=?"
    val pstmt = dataSource.getConnection.prepareStatement(stmt)
    pstmt.setString(1, item)
    pstmt.setInt(2, inventoryId)
    val rs = pstmt.executeQuery()
    pstmt.close()
    rs.next()
    rs.getInt("qty")
  }

  def modifyQuantity(item: String, quantity: Int, inventoryId: Int) = {
    val stmt = s"update inventory set qty=? where inv_id=? and item=?"
    val pstmt = dataSource.getConnection.prepareStatement(stmt)
    pstmt.setInt(1, quantity)
    pstmt.setInt(2, inventoryId)
    pstmt.setString(3, item)
    pstmt.execute()
    pstmt.close()
  }

  def addQuantity(item: String, quantity: Int, inventoryId: Int) = {
    val stmt = s"update inventory set qty=? where inv_id=? and item=?"
    val pstmt = dataSource.getConnection.prepareStatement(stmt)
    pstmt.setInt(1, quantity + getQuantity(item, inventoryId))
    pstmt.setInt(2, inventoryId)
    pstmt.setString(3, item)
    pstmt.execute()
    pstmt.close()
  }


  def addNewItem(itemName: String) = {
    val pstmt = dataSource.getConnection.prepareStatement("insert into items(item_name) values (?)")
    pstmt.setString(1, itemName)
    pstmt.execute()
    pstmt.close()
  }

  def itemCount(): Int = {
    val pstmt = dataSource.getConnection.prepareStatement("select count(*) from items")
    val rs = pstmt.executeQuery()
    pstmt.close()
    rs.next()
    rs.getInt("count(*)")
  }


  def getItemList: util.ArrayList[String] = {
    val stmt = dataSource.getConnection.prepareStatement("select item_name from items order by item_name;")
    val rs = stmt.executeQuery()
    val itemList = new util.ArrayList[String]
    stmt.close()
    while (rs.next()) {
      itemList.add(rs.getString("item_name"))
    }
    itemList
  }
}
