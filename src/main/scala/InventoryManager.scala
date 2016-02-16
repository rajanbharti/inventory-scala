import java.util

trait InventoryManager {
  protected val dataSource = Utils.getDataSource

  def addItemToInventory(item: String, quantity: Int, inventoryId: Int) = {
    val conn = dataSource.getConnection
    val pstmt = conn.prepareStatement(s"insert into inventory(inv_id,item,qty) values(?,?,?)")
    pstmt.setInt(1, inventoryId)
    pstmt.setString(2, item)
    pstmt.setInt(3, quantity)
    pstmt.execute()
    conn.close()
  }

  def getQuantity(item: String, inventoryId: Int): Int = {
    val stmt = s"select qty from inventory where item=? and inv_id=?"
    val conn = dataSource.getConnection
    val pstmt = conn.prepareStatement(stmt)
    pstmt.setString(1, item)
    pstmt.setInt(2, inventoryId)
    val rs = pstmt.executeQuery()
    rs.next()
    val qty = rs.getInt("qty")
    conn.close()
    qty
  }

  def modifyQuantity(item: String, quantity: Int, inventoryId: Int) = {
    val conn = dataSource.getConnection
    val pstmt = conn.prepareStatement(s"update inventory set qty=? where inv_id=? and item=?")
    pstmt.setInt(1, quantity)
    pstmt.setInt(2, inventoryId)
    pstmt.setString(3, item)
    pstmt.execute()
    conn.close()
  }

  def addQuantity(item: String, quantity: Int, inventoryId: Int) = {
    val availableQty = getQuantity(item, inventoryId)
    val conn = dataSource.getConnection
    val pstmt = dataSource.getConnection.prepareStatement(s"update inventory set qty=? where inv_id=? and item=?")
    pstmt.setInt(1, quantity + availableQty)
    pstmt.setInt(2, inventoryId)
    pstmt.setString(3, item)
    pstmt.execute()
    conn.close()
  }


  def addNewItem(itemName: String) = {
    val conn = dataSource.getConnection
    val pstmt = conn.prepareStatement("insert into items(item_name) values (?)")
    pstmt.setString(1, itemName)
    pstmt.execute()
    conn.close()
  }

  def itemCount(): Int = {
    val conn = dataSource.getConnection
    val pstmt = conn.prepareStatement("select count(*) from items")
    val rs = pstmt.executeQuery()
    rs.next()
    val count = rs.getInt("count(*)")
    conn.close()
    count
  }


  def getItemList: util.ArrayList[String] = {
    val conn = dataSource.getConnection
    val stmt = conn.prepareStatement("select item_name from items order by item_name;")
    val rs = stmt.executeQuery()
    val itemList = new util.ArrayList[String]
    while (rs.next()) {
      itemList.add(rs.getString("item_name"))

    }
    conn.close()
    itemList
  }
}
