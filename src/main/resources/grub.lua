local packet_count_id = KEYS[1] -- 红包余量 key
local packet_amount_id = KEYS[2] -- 红包余额 key
local user_id = KEYS[3] -- 用户ID 用于校验是否已经抢过红包
local red_packet_id = KEYS[4] -- 红包ID用于校验是否已经抢过红包
-- grub
local bloom_name = red_packet_id .. '_BLOOM_GRAB_REDPACKET'; -- 布隆过滤器ID
local rcount = redis.call('GET', packet_count_id) --  获取红包余量
local ramount = redis.call('GET', packet_amount_id) --  获取红包余额
local amount = ramount; --  默认红包金额为余额，用于只剩一个红包的情况

if tonumber(rcount) > 0 then
    -- 如果有红包才做真正的抢红包动作
    local flag = redis.call('BF.EXISTS', bloom_name, user_id) -- 通过布隆过滤器校验是否存在
    if (flag == 1) then
        -- 如果存在（可能存在）这是个待优化点
        return "1" -- 不能完全确定用户已经存在
    elseif (tonumber(rcount) ~= 1) then
        -- 不存在则计算抢红包金额，并实施真正的扣减
        local maxamount = ramount / rcount * 2;
        amount = math.random(1, maxamount);
    end
    local result_2 = redis.call('DECR', packet_count_id)
    local result_3 = redis.call('DECRBY', packet_amount_id, amount)
    redis.call('BF.ADD', bloom_name, user_id)
    return amount .. "SPLIT" .. rcount
else
    return "0"
end