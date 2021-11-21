SELECT
    1 AS tipo, id, username AS entityID, name
FROM
    Uzer
WHERE
    username = ? OR email = ? OR name LIKE ?
UNION SELECT
    2 AS tipo, id, rolename AS entityID, name
FROM
    Role
WHERE
    rolename = ? OR email = ? OR name LIKE ?