package com.blackcat.scaffolding.common.security;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 *  Token 黑名单
 * @author : zhangdahui  2025/2/26 下午2:02
 */
@Component
public class TokenBlacklist {

    private final Set<String> blacklist = new HashSet<>();

    public void addToBlacklist(String token) {
        blacklist.add(token);
    }

    public boolean isBlacklisted(String token) {
        return blacklist.contains(token);
    }
}
