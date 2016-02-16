
trait InventoryControl {
  protected val datasource = Utils.getDataSource

  def addItemToInventory(item: String, quantity: Int, inventoryId: Int) = {
    try {
      val stmt = s"insert into inventory values(?,?,?)"
      val pstmt = datasource.getConnection.prepareStatement(stmt)
      pstmt.setInt(1, quantity)
      pstmt.setInt(2, inventoryId)
      pstmt.setString(3, item)
      pstmt.execute()
    }
  }

  def addNewItem(itemName: String) = {
    val pstmt = datasource.getConnection.prepareStatement("insert into items(item_name) values (?)")
    pstmt.setString(1, itemName)
    pstmt.execute()
  }

  def itemCount(): Int = {
    val pstsmt = datasource.getConnection.prepareStatement("select count(*) from items")
    val rs = pstsmt.executeQuery()
    rs.next()
    rs.getInt("count(*)")
  }

  protected def getQuantity(item: String, inventoryId: Int): Int = {
    try {
      val stmt = s"select qty from inventory where item=? and inventory_id=?"
      val pstmt = datasource.getConnection.prepareStatement(stmt)
      val rs = pstmt.executeQuery()
      rs.next()
      rs.getInt("qty")
    }
  }

  def updateQuantity(item: String, quantity: Int, inventoryId: Int) = {
    try {
      val stmt = s"update inventory set qty=? where inventory_id=? and item=?"
      val pstmt = datasource.getConnection.prepareStatement(stmt)
      pstmt.setInt(1, quantity + getQuantity(item, inventoryId))
      pstmt.setInt(2, inventoryId)
      pstmt.setString(3, item)
      pstmt.execute()
    }
  }
}
