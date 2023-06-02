package com.old.common.domain.login;

import java.time.LocalDateTime;

public record LoginInfoBo(String token, String userName, String ipaddr, LocalDateTime loginTime) {
}
