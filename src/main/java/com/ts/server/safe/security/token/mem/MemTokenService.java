package com.ts.server.safe.security.token.mem;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.security.Credential;
import com.ts.server.safe.security.token.TokenService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * 基于内存实现{@link TokenService}
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class MemTokenService implements TokenService {
    private static final ConcurrentMap<String, Credential> POOL = new ConcurrentHashMap<>();

    @Override
    public String generate(Credential t) {
        String token = genToken();
        POOL.put(token, t);
        return token;
    }

    @Override
    public boolean update(String token, Credential t) {
        POOL.put(token, t);
        return true;
    }

    @Override
    public Credential validateAndGet(String token) {
        Credential t = POOL.get(token);
        if(t == null){
            throw new BaseException(110, "认证不存在");
        }
        return t;
    }

    @Override
    public void destroy(String token) {
        POOL.remove(token);
    }

    public void clearExpired(){
        List<String> tokens = POOL.entrySet().stream()
                .filter(e -> e.getValue().isExpired())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        tokens.forEach(POOL::remove);
    }
}
