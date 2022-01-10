SELECT
    Uzer.id AS id,
    Uzer.active AS active,
    Uzer.code AS code,
    Role$id AS 'role.id',
    gate.fullname(Role$id) AS 'role.name',
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
WHERE
    Uzer.id = ?