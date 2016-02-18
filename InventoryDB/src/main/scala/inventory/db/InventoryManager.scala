package inventory.db

import java.util

import scala.collection.mutable.ListBuffer

class InventoryManager extends inventory.core.InventoryManager {
  private val dataSource = DataSourceUtils.getDataSource

  def addItemToInventory(item: String, quantity: Int, inventoryId: Int) = {
    val conn = dataSource.getConnection
    if (getItemList.contains(item)) {
      val pstmt = conn.prepareStatement(s"insert into inventory(inv_id,item,qty) values(?,?,?)")
      pstmt.setInt(1, inventoryId)
      pstmt.setString(2, item)
      pstmt.setInt(3, quantity)
      pstmt.execute()
      conn.close()
    } else throw new Exception("Add item to itemList, before adding to inventory")
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
    val pstmt = conn.prepareStatement(s"update inventory set qty=? where inv_id=? and item=?")
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


  def getItemList: ListBuffer[String] = {
    val conn = dataSource.getConnection
    val stmt = conn.prepareStatement("select item_name from items order by item_name;")
    val rs = stmt.executeQuery()
    val itemList = new ListBuffer[String]
    while (rs.next()) {
      itemList += rs.getString("item_name")
    }
    conn.close()
    itemList
  }

  def addInventory(inventoryName: String, location: String) = {
    val conn = dataSource.getConnection
    val addInvQuery = conn.prepareStatement("insert into inventory_info " +
      "(inv_name,inv_location) values (?,?)")
    addInvQuery.setString(1, inventoryName)
    addInvQuery.setString(2, location)
    addInvQuery.execute()
    conn.close()
  }

  def removeInventory(inventoryId: Int) = {
    val conn = dataSource.getConnection
    val removeInvQuery = conn.prepareStatement("delete from inventory_info where inv_id=?")
    removeInvQuery.setInt(1, inventoryId)
    removeInvQuery.execute()
    conn.close()
  }

  def inventoriesCount(): Int = {
    val conn = dataSource.getConnection
    val pstsmt = conn.prepareStatement("select count(*) from inventory_info")
    val rs = pstsmt.executeQuery()
    rs.next()
    val count = rs.getInt("count(*)")
    conn.close
    count
  }
}
