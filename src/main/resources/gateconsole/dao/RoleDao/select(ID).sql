SELECT
    Role.id,
    Role.name,
    Role.rolename,
    Role.description,
    Role.active,
    Role.master,
    Role.email,
    Parent.id AS 'role.id',
    Parent.name AS 'role.name',
    Manager.id AS 'manager.id',
    Manager.name AS 'manager.name'
FROM
    gate.Role
        LEFT JOIN
    Role AS Parent ON Role.Role$id = Parent.id
        LEFT JOIN
    gate.Uzer AS Manager ON Role.Manager$id = Manager.id
WHERE
    Role.id = ?
