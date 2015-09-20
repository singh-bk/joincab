-- checkanddecr.lua
local current = redis.call('GET', KEYS[1])
if current >=1
then
    redis.call('SET', KEYS[2])
    return true
end
return false