-- complete.lua
-- 결제 완료 처리 (활성 목록에서 제거)

local activeSetKey = KEYS[1]       -- payment:active:users
local activeCountKey = KEYS[2]     -- payment:active:count
local userMetaKey = KEYS[3]        -- payment:queue:user:{userId}

local userId = ARGV[1]

-- 1. 활성 Set에서 제거
local removed = redis.call('SREM', activeSetKey, userId)

if removed == 1 then
    -- 2. 활성 카운터 감소
    redis.call('DECR', activeCountKey)

    -- 3. 사용자 메타데이터 상태 업데이트
    redis.call('HSET', userMetaKey, 'status', 'COMPLETED')

    return 1  -- 성공
end

return 0  -- 활성 목록에 없었음
