select
    Role.id,
    Role.rolename as rolename,
    Role.name as name
from
    Role
        join
    RoleFunc ON Role.id = RoleFunc.Role$id
where
    RoleFunc.Func$id = ?