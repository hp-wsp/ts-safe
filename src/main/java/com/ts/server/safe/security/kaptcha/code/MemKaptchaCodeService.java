package com.ts.server.safe.security.kaptcha.code;

import com.ts.server.safe.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 内存实现{@link KaptchaCodeService}
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class MemKaptchaCodeService implements KaptchaCodeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MemKaptchaCodeService.class);

    private final ConcurrentMap<Integer, ConcurrentMap<String, String>> cache = new ConcurrentHashMap<>(20);
    private final long expireMills;

    public MemKaptchaCodeService(int expireSeconds){
        this.expireMills = expireSeconds * 1000L;
    }

    @Override
    public void save(String codeKey, String code) {
        int index = getIndex();

        ConcurrentMap<String, String> keys = cache.get(index);
        if(keys == null){
            ConcurrentMap<String, String> tmp = new ConcurrentHashMap<>(50);
            keys = cache.putIfAbsent(index, tmp);
            keys = keys == null? tmp: keys;
        }

        if(keys.putIfAbsent(codeKey, code)!=null){
            LOGGER.error("Mem code save code fail codeKey={}", codeKey);
            throw new BaseException("验证码KEY已经存在");
        }

        LOGGER.debug("Mem code save index={}, codeKey={}, code={}", index, codeKey, code);
    }

    private int getIndex(){
        return (int)(System.currentTimeMillis() / expireMills);
    }

    @Override
    public Optional<String> get(String codeKey) {
        int index = getIndex();
        for(int i = 0; i < 2; i++){
            int c = index - i;
            Optional<String> codeOpt = getKeysOpt(c).flatMap(keys -> Optional.ofNullable(keys.get(codeKey)));
            if(codeOpt.isPresent()){
                return codeOpt;
            }
        }
        return Optional.empty();
    }

    private Optional<ConcurrentMap<String, String>> getKeysOpt(int index){
        return Optional.ofNullable(cache.get(index));
    }

    @Override
    public void delete(String codeKey) {
        int index = getIndex();
        for(int i = 0; i < 2; i++){
            int c = index -1;
            getKeysOpt(c).ifPresent(e -> e.remove(codeKey));
        }
    }

    @Override
    public void clearExpired() {
        int index = getIndex();
        for(int i = 0; i < 5; i++){
            int c = index - 2 - i;
            cache.remove(c);
        }
    }
}
