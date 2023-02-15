package shop.yesaladin.front.interceptor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class ProductViewCounterInterceptor implements HandlerInterceptor {

    private static final String KEY = "product-view-count";
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler
    ) throws Exception {
        String clientIp =
                Objects.isNull(request.getHeader("x-forwarded-for")) ? request.getRemoteAddr()
                        : request.getHeader("x-forwarded-for");

        String[] splitPath = request.getServletPath().split("/");
        String productId = splitPath[splitPath.length - 1];

        String value = (String) redisTemplate.opsForHash().get(KEY, productId);
        Set<String> valueSet = Objects.isNull(value) ? new HashSet<>()
                : objectMapper.readValue(value, new TypeReference<Set<String>>() {
                });

        valueSet.add(clientIp);

        redisTemplate.opsForHash().put(KEY, productId, objectMapper.writeValueAsString(valueSet));

        return true;
    }
}
