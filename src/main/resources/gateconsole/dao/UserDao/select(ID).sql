SELECT
    Uzer.id AS id,
    Uzer.active AS active,
    Uzer.code as code,
    Role.id AS 'role.id',
    Role.name AS 'role.name',
    Uzer.name AS name,
    Uzer.username AS username,
    Uzer.email AS email,
    Uzer.description AS description,
    Uzer.phone AS phone,
    Uzer.cellPhone AS cellPhone,
    Uzer.photo AS photo,
    Uzer.CPF AS CPF,
    Uzer.sex AS sex,
    Uzer.birthdate AS birthdate,
    Uzer.registration AS registration
FROM
    Uzer
       LEFT JOIN
    Role ON Uzer.Role$id = Role.id
WHERE
    Uzer.id = ?
