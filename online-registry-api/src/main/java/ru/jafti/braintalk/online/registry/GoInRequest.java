package ru.jafti.braintalk.online.registry;

import java.util.UUID;

public record GoInRequest(
   UUID talkerGuid,
   String nickname,
   Channel channel
) {}
