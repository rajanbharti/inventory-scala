
class InventorySystem extends OrderControl {

  def addInventory(inventoryName: String, location: String) = {
    val addInvQuery = datasource.getConnection.prepareStatement("insert into inventory_info " +
      "(inventory,location) values (?,?)")
    addInvQuery.setString(1, inventoryName)
    addInvQuery.setString(2, location)
    addInvQuery.execute()
  }

  def removeInventory(inventoryId: Int) = {
    val removeInvQuery = datasource.getConnection.prepareStatement("delete from inventory_info where inv_id=?")
    removeInvQuery.setInt(1, inventoryId)
    removeInvQuery.execute()
  }

  def inventoriesCount(): Int = {
    val pstsmt = datasource.getConnection.prepareStatement("select count(*) from inventory_info")
    val rs = pstsmt.executeQuery()
    rs.next()
    rs.getInt("count(*)")
  }


}
