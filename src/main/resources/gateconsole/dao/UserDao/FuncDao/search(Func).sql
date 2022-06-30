SELECT 
    Uzer.id,
    Uzer.username AS username,
    Uzer.name AS name,
    gate.fullname(Uzer.Role$id) AS 'role.name'
FROM
    Uzer
        JOIN
    UzerFunc ON Uzer.id = UzerFunc.Uzer$id
WHERE
    UzerFunc.Func$id = ?
ORDER BY Uzer.name