import java.sql.*
import java.util.*

object SqlConnection {
    internal var conn: Connection? = null
    internal var username = "sampleuser"
    internal var password = "sampleuser"

    @JvmStatic fun main(args: Array<String>) {
        getConnection()
        executeMySQLQuery()
    }

    fun executeMySQLQuery() {
        var stmt: Statement? = null
        var resultset: ResultSet? = null

        try {
            stmt = conn!!.createStatement()
            stmt!!.executeQuery("USE sampleDb;")
            var sql = "SELECT * FROM todos;"
            resultset = stmt!!.executeQuery(sql)

            if (stmt.execute(sql)) {
                resultset = stmt.resultSet
            }

            while (resultset!!.next()) {
                println(resultset.getString("id"))
                println(resultset.getString("title"))
                println(resultset.getString("image_url"))
            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        } finally {
            if (resultset != null) {
                try {
                    resultset.close()
                } catch (sqlEx: SQLException) {
                }

                resultset = null
            }

            if (stmt != null) {
                try {
                    stmt.close()
                } catch (sqlEx: SQLException) {
                }

                conn = null
            }
        }
    }

    fun getConnection() {
        val connectionProps = Properties()
        connectionProps.put("user", username)
        connectionProps.put("password", password)
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance()
            conn = DriverManager.getConnection(
                    "jdbc:" + "mysql" + "://" +
                            "192.168.44.10" +
                            ":" + "3306" + "/" +
                            "",
                    connectionProps)
        } catch (ex: SQLException) {
            ex.printStackTrace()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}
