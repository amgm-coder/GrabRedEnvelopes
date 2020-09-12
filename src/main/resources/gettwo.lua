local key_1 = KEYS[1]
local key_2 = KEYS[2]
-- grub
local result_1 = redis.call('GET', key_1)
local result_2 = redis.call('GET', key_2)
return result_1 .. "SPLIT" .. result_2