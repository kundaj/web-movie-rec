package com.uu.sys.recommender.movies;

import java.sql.*;

import com.google.gson.JsonObject;
import com.uu.sys.recommender.doa.Conn;

public class Movie {
    static String url = Conn.url;
    static String dbName = Conn.dbName;
    static String driver = Conn.driver;
    static String userName = Conn.userName;
    static String password = Conn.password;

    /*
     * public static void main(String[] args) { long movieID = 770681152; JsonObject movieObj = movieInfo(movieID);
     *
     * System.out.println(movieObj.get("title")+"\t"+movieObj.get("year")+"\t"+movieObj.get("synopsis")); }
     */

    public JsonObject movieInfo(final Long ID) {
        final JsonObject movie = new JsonObject();
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url + dbName, userName, password);

            final String sql = "SELECT * FROM movies WHERE movie_id =?";

            pst = conn.prepareStatement(sql);
            pst.setLong(1, ID);

            final ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                final String title = rs.getString("title");
                final String year = rs.getString("year");
                final String synopsis = rs.getString("synopsis");

                movie.addProperty("title", title);
                movie.addProperty("year", year);
                movie.addProperty("synopsis", synopsis);
            }
        } catch (final Exception e) {
            System.out.println(e);
        }

        finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (final SQLException e) {
                    e.printStackTrace();
                }
            }

            if (pst != null) {
                try {
                    pst.close();
                } catch (final SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return movie;
    }
}
