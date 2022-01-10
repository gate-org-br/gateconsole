SELECT
	Role.id,
	Role.active,
	Role.master,
	Role.email,
	Role.name,
	Role.rolename,
	Role.description,
	Role.Role$id AS 'role.id',
	Manager.id AS 'manager.id',
	Manager.name AS 'manager.name'
FROM
	gate.Role
	LEFT JOIN gate.Uzer AS Manager ON Role.Manager$id = Manager.id