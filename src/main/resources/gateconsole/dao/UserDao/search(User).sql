SELECT
    Uzer.id,
    Uzer.active,
    Uzer.email,
    Uzer.CPF,
    Uzer.name,
    Uzer.username,
    Role$id AS 'role.id',
    gate.fullname(Role$id) AS 'role.name',
    Uzer.registration
FROM
    gate.Uzer