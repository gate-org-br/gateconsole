SELECT
    Auth.id,
    Auth.Role$id AS 'role.id',
    Auth.Uzer$id AS 'user.id',
    Auth.Func$id AS 'func.id',
    Auth.scope,
    Auth.access,
    Auth.module,
    Auth.screen,
    Auth.action
FROM
    gate.Auth
ORDER BY access , scope , module , screen , action
