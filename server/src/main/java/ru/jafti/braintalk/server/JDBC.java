package ru.jafti.braintalk.server;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JDBC {
    public JDBC() throws SQLException {

    }

    public static void main(String []args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:mydatabase.db");

        String createTableSQL = "CREATE TABLE IF NOT EXISTS talker_profile (talker_id UUID PRIMARY KEY, nickname TEXT)";
        Statement statement = connection.createStatement();
        statement.execute(createTableSQL);

        // Вставляем данные
        String insertSQL = "INSERT INTO talker_profile VALUES ('67e4156f-2644-8013-8f54-afc03cf1360c', 'Olga')";
        statement.executeUpdate(insertSQL);

        // Запрашиваем данные
        String selectSQL = "SELECT * FROM talker_profile";
        ResultSet resultSet = statement.executeQuery(selectSQL);

        List<TalkerProfile> talkerProfiles = new ArrayList<>();
        while (resultSet.next()) {
            TalkerProfile talkerProfile = new TalkerProfile();
            talkerProfile.setTalkerId(UUID.fromString(resultSet.getString("talker_id")));
            talkerProfile.setNickname(resultSet.getString("nickname"));
            talkerProfiles.add(talkerProfile);
        }

        System.out.println(talkerProfiles);
    }

    static class TalkerProfile{
        private String talkerName;
        private UUID talkerId;

        public void setTalkerId(UUID talkerId) {
            this.talkerId = talkerId;
        }

        public void setNickname(String talkerName) {
            this.talkerName = talkerName;
        }

    }

}
