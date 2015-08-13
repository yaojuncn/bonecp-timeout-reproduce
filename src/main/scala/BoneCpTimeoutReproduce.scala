import java.util.Date
import java.util.concurrent.TimeUnit

import com.jolbox.bonecp.{BoneCP, BoneCPConfig}

object BoneCpTimeoutReproduce {
  def main(args: Array[String]): Unit ={
    testcon1
  }

  val maxageSeconds = 30
  val connectionPool =initPool()



  def initPool(): BoneCP ={
    Class.forName("com.mysql.jdbc.Driver");
    val config:BoneCPConfig = new BoneCPConfig();
    config.setJdbcUrl("jdbc:mysql://localhost/test");
    config.setUsername("test");
    config.setPassword("test");
    config.setMaxConnectionAge(maxageSeconds, TimeUnit.SECONDS)
    config.setIdleMaxAgeInSeconds(maxageSeconds)
    config.setMinConnectionsPerPartition(1)
    config.setMaxConnectionsPerPartition(1)
    config.setPartitionCount(1)
    config.setConnectionTimeout(30, TimeUnit.SECONDS)

    new BoneCP(config);

  }

  def testcon1(): Unit ={
    runQuery()
    runQuery()
  }



  def runQuery(): Unit ={

    val connection = connectionPool.getConnection();

    val statement = connection.createStatement()
    val rs = statement.executeQuery("select id, name from test_t")
    while(rs.next()){
      println(new Date() + " tid=" + Thread.currentThread().getId + ", id=" + rs.getInt("id") + ", name=" + rs.getString("name"))
    }

    rs.close()
    statement.close()
    Thread.sleep((maxageSeconds + 1) * 1000)

    connection.close();


  }


}
