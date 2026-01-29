-- get_position.lua
-- 사용자의 대기 순번을 조회

local queueKey = KEYS[1]           -- payment:queue:active
local userId = ARGV[1]

-- ZRANK: Sorted Set에서 순위 조회 (0부터 시작)
local rank = redis.call('ZRANK', queueKey, userId)

if not rank then
    return nil  -- 대기열에 없음
end

-- 순번 반환 (1부터 시작)
return rank + 1
