import java.util.Date
import java.util.concurrent.TimeUnit

import com.jolbox.bonecp.{BoneCP, BoneCPConfig}

object BoneCpTimeoutReproduce {
  def main(args: Array[String]): Unit ={
    testQueryTwice
  }

  val maxageSeconds = 5

  def initPool(): BoneCP ={
    Class.forName("org.h2.Driver");
    val config:BoneCPConfig = new BoneCPConfig();
    config.setJdbcUrl("jdbc:h2:~/test");
    config.setMaxConnectionAge(maxageSeconds, TimeUnit.SECONDS)
    config.setIdleMaxAgeInSeconds(maxageSeconds)
    config.setMinConnectionsPerPartition(1)
    config.setMaxConnectionsPerPartition(1)
    config.setPartitionCount(1)
    config.setConnectionTimeout(30, TimeUnit.SECONDS)

    new BoneCP(config);

  }

  def testQueryTwice(): Unit ={
    val connectionPool =initPool()

    runQuery(connectionPool)
    runQuery(connectionPool)

    connectionPool.shutdown()
  }



  def runQuery(connectionPool: BoneCP): Unit ={

    val connection = connectionPool.getConnection();

    val statement = connection.createStatement()
    val rs = statement.executeQuery("select 1")
    while(rs.next()){
      println(new Date() + " tid=" + Thread.currentThread().getId + ", value=" + rs.getInt(1))
    }

    rs.close()
    statement.close()
    Thread.sleep((maxageSeconds + 1) * 1000)

    connection.close();


  }


}
