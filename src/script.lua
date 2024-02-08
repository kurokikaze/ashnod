--[[if height > 150 then
    area = width * height * 2
else
    area = width * height
end

if address ~= nil then
    street = address.street .. ' ' .. address.house
end

if deep then
    deep.deeper.deepest.val = 10
end

bigEnough = area > 10000
structure = {}
structure.a = 12
structure.b = "string"

a = {
    val = 12,
    unit = "cm"
}

b = {
    val = 22,
    unit = "cm"
}

if a.unit == b.unit then
    c = {
        val = a.val + b.val,
        unit = a.unit
    }
end
easyStruct = {
    a = "str",
    b = 44
}
comment = "Mighty " .. source
word1 = "test"
word2 = "tesx"
tesst = 1332
]]--
if configParams and partNumber then
    result = configParams + partNumber
else
    result = "Nope"
end