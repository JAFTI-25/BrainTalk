package ru.jafti.braintalk.server.service;

import java.util.UUID;

public interface TalkerProfileService {
    String findById(UUID id);
    UUID findByNickname(String nickname);
    UUID createWithNickname(String nickname);
    class Impl {
        public static TalkerProfileService get() {

            // TODO поменять на реализацию
            return new TalkerProfileService() {
                @Override
                public String findById(UUID id) {
                    return "MOCK_USER_NICKNAME";
                }

                @Override
                public UUID findByNickname(String nickname) {
                    return UUID.fromString("11111111-1d13-4c32-95d9-18be5c07dc8b");
                }

                @Override
                public UUID createWithNickname(String nickname) {
                    return UUID.fromString("11111111-1d13-4c32-95d9-18be5c07dc8b");
                }
            };
        }
    }
}

