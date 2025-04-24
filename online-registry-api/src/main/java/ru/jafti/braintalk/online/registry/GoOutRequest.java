package ru.jafti.braintalk.online.registry;

import java.util.UUID;

public record GoOutRequest(
   UUID talkerGuid,
   String nickname
) {}
