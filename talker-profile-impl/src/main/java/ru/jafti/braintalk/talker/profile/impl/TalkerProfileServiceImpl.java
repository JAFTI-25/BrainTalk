package ru.jafti.braintalk.talker.profile.impl;

import org.springframework.stereotype.Component;
import ru.jafti.braintalk.talker.profile.api.TalkerProfileService;
import ru.jafti.braintalk.talker.profile.impl.persist.DbConnection;
import ru.jafti.braintalk.talker.profile.impl.repository.TalkerProfileRepository;
import ru.jafti.braintalk.talker.profile.impl.repository.model.TalkerProfileEntity;

import java.util.UUID;

@Component
public class TalkerProfileServiceImpl implements TalkerProfileService {
    private TalkerProfileRepository repository;

    public void setRepository(TalkerProfileRepository repository){ this.repository=repository;}

    public TalkerProfileServiceImpl(DbConnection dbConnection) {
        this.repository = repository;
    }

    @Override
    public String findById(UUID id){
        // Здесь Hikari CP предоставляет соединение,
        // а Spring Data JDBC выполняет запрос
        return repository.findById(id);
    }

    @Override
    public UUID findByNickname(String nickname){
        return repository.findByNickname(nickname);
    }

    @Override
    public UUID createWithNickname(String nickname) {
        TalkerProfileEntity entity = new TalkerProfileEntity();
        entity.setTalkerNickname(nickname);
        repository.save(entity);
        return entity.getTalkerId();
    }


//    @Override
//    public String findById(UUID id) {
//        Connection connection = dbConnection.getConnection();
//        try (Statement statement = connection.createStatement()) {
//            ResultSet resultSet = statement.executeQuery(SELECT_REQUEST);
//
//            while (resultSet.next()) {
//                var storedUuid = UUID.fromString(resultSet.getString(DbInitializer.ID_COLUMN_NAME));
//                var nickname = resultSet.getString(DbInitializer.NICKNAME_COLUMN_NAME);
//
//                if (id.equals(storedUuid)) {
//                    resultSet.close();
//                    return nickname;
//                }
//            }
//
//            return null;
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public UUID findByNickname(String nickname) {
//        Connection connection = dbConnection.getConnection();
//        try (Statement statement = connection.createStatement()) {
//            ResultSet resultSet = statement.executeQuery(SELECT_REQUEST);
//
//            while (resultSet.next()) {
//                var uuid = UUID.fromString(resultSet.getString(DbInitializer.ID_COLUMN_NAME));
//                var storedNickname = resultSet.getString(DbInitializer.NICKNAME_COLUMN_NAME);
//
//                if (Objects.equals(nickname, storedNickname)) {
//                    resultSet.close();
//                    return uuid;
//                }
//            }
//
//            return null;
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public UUID createWithNickname(String nickname) {
//        var uuid = UUID.randomUUID();
//        var insertSql = String.format(INSERT_REQUEST_FORMAT, uuid, nickname);
//        Connection connection = dbConnection.getConnection();
//
//        try (Statement statement = connection.createStatement()) {
//            statement.executeUpdate(insertSql);
//            return uuid;
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
