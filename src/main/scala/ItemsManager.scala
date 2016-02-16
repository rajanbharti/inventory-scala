import java.util

trait ItemsManager {
  protected val dataSource = Utils.getDataSource

  def addNewItem(itemName: String) = {
    val pstmt = dataSource.getConnection.prepareStatement("insert into items(item_name) values (?)")
    pstmt.setString(1, itemName)
    pstmt.execute()
  }

  def itemCount(): Int = {
    val pstsmt = dataSource.getConnection.prepareStatement("select count(*) from items")
    val rs = pstsmt.executeQuery()
    rs.next()
    rs.getInt("count(*)")
  }


  def getItemList: util.ArrayList[String] = {
    val stmt = dataSource.getConnection.prepareStatement("select item_name from items order by item_name;")
    val rs = stmt.executeQuery()
    val itemList = new util.ArrayList[String]
    while (rs.next()) {
      itemList.add(rs.getString("item_name"))
    }
    itemList
  }

}
