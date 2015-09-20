-- checkandset.lua
local current = redis.call('GET', KEYS[1])
local xxx = tonumber(current)
return xxx