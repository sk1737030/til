-- enqueue.lua
-- 대기열에 사용자를 추가하는 Lua 스크립트
-- 중복 체크, 용량 체크를 원자적으로 수행

local queueKey = KEYS[1]           -- payment:queue:active
local userMetaKey = KEYS[2]        -- payment:queue:user:{userId}

local userId = ARGV[1]             -- 사용자 ID
local timestamp = ARGV[2]          -- 입장 신청 타임스탬프
local maxSize = tonumber(ARGV[3])  -- 대기열 최대 크기
local ipAddress = ARGV[4]          -- IP 주소

-- 1. 이미 대기열에 있는지 확인
local existingScore = redis.call('ZSCORE', queueKey, userId)
if existingScore then
    -- 이미 대기 중이면 현재 순번 반환
    local rank = redis.call('ZRANK', queueKey, userId)
    return {1, rank + 1}  -- {성공, 순번}
end

-- 2. 대기열 용량 체크
local currentSize = redis.call('ZCARD', queueKey)
if currentSize >= maxSize then
    return {0, "Queue is full"}  -- {실패, 에러 메시지}
end

-- 3. 대기열에 추가 (Sorted Set - score는 타임스탬프)
redis.call('ZADD', queueKey, timestamp, userId)

-- 4. 사용자 메타데이터 저장 (Hash)
redis.call('HSET', userMetaKey,
    'enqueueTime', timestamp,
    'status', 'WAITING',
    'ipAddress', ipAddress or ''
)

-- 5. 현재 순번 반환
local rank = redis.call('ZRANK', queueKey, userId)
return {1, rank + 1}  -- {성공, 순번} - ZRANK는 0부터 시작하므로 +1
