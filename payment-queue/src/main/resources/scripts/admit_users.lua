-- admit_users.lua
-- 대기열에서 N명을 선발하여 활성 상태로 이동
-- 스케줄러가 2초마다 호출 (Redisson 분산 락으로 중복 실행 방지)

local queueKey = KEYS[1]           -- payment:queue:active
local activeSetKey = KEYS[2]       -- payment:active:users
local activeCountKey = KEYS[3]     -- payment:active:count

local maxActive = tonumber(ARGV[1])   -- 1000 (최대 동시 활성 사용자)
local batchSize = tonumber(ARGV[2])   -- 5 (회차당 입장 인원)

-- 1. 현재 활성 사용자 수 조회
local currentActive = tonumber(redis.call('GET', activeCountKey) or 0)

-- 2. 입장 가능 인원 계산
local canAdmit = math.min(batchSize, maxActive - currentActive)
if canAdmit <= 0 then
    return {}  -- 입장 불가 (빈 리스트 반환)
end

-- 3. 대기열 앞에서 N명 선택 (ZRANGE: score 순으로 정렬됨)
local users = redis.call('ZRANGE', queueKey, 0, canAdmit - 1)

-- 4. 각 사용자를 대기열 → 활성으로 이동
for i, userId in ipairs(users) do
    -- 대기열에서 제거
    redis.call('ZREM', queueKey, userId)

    -- 활성 Set에 추가
    redis.call('SADD', activeSetKey, userId)

    -- 활성 카운터 증가
    redis.call('INCR', activeCountKey)

    -- 사용자 메타데이터 상태 업데이트
    redis.call('HSET', 'payment:queue:user:' .. userId, 'status', 'ADMITTED')
end

-- 5. 입장한 사용자 목록 반환
return users
