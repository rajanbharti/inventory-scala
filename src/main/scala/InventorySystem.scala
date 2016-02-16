
class InventorySystem extends OrderManager {

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
