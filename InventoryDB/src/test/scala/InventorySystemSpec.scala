package inventory.db

import org.scalatest._

class InventorySystemSpec extends Suites with BeforeAndAfterAll {

  new InventoryManagerSpec
  new OrderManagerSpec

}
